package com.example.wordbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Word_Details_Activiity extends AppCompatActivity {
    public static void actionstart(Context context, String Word, String Meaning, String Sample){
        Intent intent = new Intent(context,Word_Details_Activiity.class);
        intent.putExtra("Word",Word);
        intent.putExtra("Meaning",Meaning);
        intent.putExtra("Sample",Sample);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word__details__activiity);

        String word = getIntent().getStringExtra("Word");
        String meaning = getIntent().getStringExtra("Meaning");
        String sample = getIntent().getStringExtra("Sample");
        Word_Details_FragMent word_details_fragMent = (Word_Details_FragMent)getSupportFragmentManager().findFragmentById(R.id.fragment_details);
        word_details_fragMent.refresh(word,meaning,sample);
    }
}
