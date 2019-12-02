package com.example.my;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class insert_Activity extends AppCompatActivity {
private EditText editText_1;
private  EditText editText_2;
private  EditText editText_3;
ListView listView;
private List<Words> wordsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_);

        editText_1 =  (EditText)findViewById(R.id.Edittext_1);
        editText_2 = (EditText)findViewById(R.id.Edittext_5);
        editText_3 = (EditText)findViewById(R.id.Edittext_6);
        final WordsDBHelper wordsDBHelper = new WordsDBHelper(this);

        Button addbt = (Button)findViewById(R.id.bt_2);
        addbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase database = wordsDBHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();

                String word1 = editText_1.getText().toString();
                String meaning1 = editText_2.getText().toString();
                String sample1 = editText_3.getText().toString();

                contentValues.put("word",word1);
                contentValues.put("meaning",meaning1);
                contentValues.put("sample",sample1);
                database.insert("words",null,contentValues);


               Log.d("MainActivity","插入成功");
               Log.d("MainActivity","word is " + word1);
            }
        });
    }
}
