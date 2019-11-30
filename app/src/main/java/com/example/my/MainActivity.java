package com.example.my;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
WordsDBHelper mDbHelper;
private List<Words> wordsList = new ArrayList<>();
    List<Map<String,Object>> listItems;
    Map<String, Object> map;
    SimpleAdapter simpleAdapter;
    WordsAdapter wordsAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView)findViewById(R.id.List_1);
       /* listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("确认删除?");
        menu.add(0,0,0,"确认");
        menu.add(0,1,1,"取消");
    }
});*/


        mDbHelper = new WordsDBHelper(this,"BookStore.db",null,1);
        final SQLiteDatabase DB =  mDbHelper.getWritableDatabase();
        final Cursor cusor = DB.query("words",null,null,null,null,null,null);
       if(cusor.moveToFirst()) {
           do {
               String name = cusor.getString(cusor.getColumnIndex("word"));
               String meaning = cusor.getString(cusor.getColumnIndex("meaning"));
               String sample = cusor.getString(cusor.getColumnIndex("sample"));
               Log.d("MainActivity", "word is " + String.valueOf(name));
               Log.d("MainActivity", "word meaning is" + String.valueOf(meaning));
               Log.d("MainActivity", "word sample is " + String.valueOf(sample));
               Words words = new Words(name,meaning,sample);
               Log.d("MainActivity", String.valueOf(words));
               wordsList.add(words);
               wordsAdapter = new WordsAdapter(MainActivity.this,0,R.layout.words,wordsList);
               listView = (ListView)findViewById(R.id.List_1);
               wordsAdapter.notifyDataSetChanged();
               listView.setAdapter(wordsAdapter);
           } while (cusor.moveToNext());
       }
        listItems=new ArrayList<Map<String, Object>>();
        if(cusor.moveToFirst()) {
            do {
                String name = cusor.getString(cusor.getColumnIndex("word"));
                String meaning = cusor.getString(cusor.getColumnIndex("meaning"));
                String sample = cusor.getString(cusor.getColumnIndex("sample"));
                map = new HashMap<String, Object>();
                map.put("header", name);
                map.put("personName",meaning);
                map.put("desc", sample);
                //把列表项加进列表集合
                listItems.add(map);
            }while (cusor.moveToNext());
        } cusor.close();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setMessage("确认删除吗");
                builder.setTitle("提示");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        arg0.dismiss();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(cusor.moveToFirst()) {
                            String name = cusor.getString(cusor.getColumnIndex("word"));
                            if (listItems.remove(position) != null) {
                                System.out.println("success");
                            } else {
                                System.out.println("failed");
                            }
                            DB.delete("words", "word=?", new String[]{name});
                            wordsAdapter.notifyDataSetChanged();
                        }
                    }
                });
                builder.create().show();
                return false;
            }
        });


    }
   /* @Override
    public boolean onContextItemSelected(MenuItem item) {
        ContextMenu.ContextMenuInfo menuInfo = (ContextMenu.ContextMenuInfo)item.getMenuInfo();
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int id = (int)adapterContextMenuInfo.id;
        View view;
        mDbHelper = new WordsDBHelper(this,"BookStore.db",null,2);
        SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();
        Cursor cusor = sqLiteDatabase.query("words",null,null,null,null,null,null);
        switch (item.getItemId()){
            case 0:
                listItems=new ArrayList<Map<String, Object>>();
               if(cusor.moveToFirst()) {
                   do {
                       String name = cusor.getString(cusor.getColumnIndex("word"));
                       String meaning = cusor.getString(cusor.getColumnIndex("meaning"));
                       String sample = cusor.getString(cusor.getColumnIndex("sample"));
                       map = new HashMap<String, Object>();
                       map.put("header", name);
                       map.put("personName",meaning);
                       map.put("desc", sample);
                       //把列表项加进列表集合
                       listItems.add(map);
                   }while (cusor.moveToNext());

               }
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        builder.setMessage("确认删除吗");
                        builder.setTitle("提示");
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                // TODO Auto-generated method stub
                                arg0.dismiss();
                            }
                        });
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(listItems.remove(position)!=null){
                                    System.out.println("success");
                                }else {
                                    System.out.println("failed");
                                }
                                simpleAdapter.notifyDataSetChanged();
                                Toast.makeText(getBaseContext(), "删除列表项", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.create().show();
                        return false;
                    }
                });
                break;
            case 1:
                break;
                }
        return super.onContextItemSelected(item);
    }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.i_1:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,insert_Activity.class);
                startActivity(intent);
                break;
            case R.id.i_2:
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this,chaxun_Activity.class);
                startActivity(intent1);
                break;
            case R.id.i_4:
                Intent intent3 = new Intent();
                intent3.setClass(MainActivity.this,xiugai_Activity.class);
                startActivity(intent3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
