package com.example.my;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

WordsDBHelper mDbHelper = new WordsDBHelper(this);
List<Words> wordsList = new ArrayList<>();
 WordsAdapter wordsAdapter;
     ListView listView;
int position1;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        /*if(wordsAdapter == null){
            init();
        }
        else {
            wordsAdapter.clear();
            init();
        }*/



       //listview item 点击菜单
        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0,0,0,"修改");
                menu.add(0,1,1,"删除");
                menu.add(0,2,1,"取消");
            }
        });
        //listview item 点击事件
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                position1 = position;
                listView.showContextMenu();
            }
        });
       //listview item 长按点击删除
        /*final SQLiteDatabase DB = mDbHelper.getWritableDatabase();
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
                        String name1 = wordsList.get(position).name;
                        wordsAdapter.notifyDataSetChanged();
                        DB.delete("words", "word=?", new String[] {name1});
                        init();
                    }
                });
                builder.create().show();
                return false;
            }
        });*/
    }
//listview 显示
    public void init() {
        final SQLiteDatabase DB = mDbHelper.getWritableDatabase();
        final Cursor cusor = DB.query("words", null, null, null, null, null, null);
        //listview 显示
        if (cusor.moveToFirst()) {
            do {
                String id = cusor.getString(cusor.getColumnIndex(Words.Word._ID));
                String name = cusor.getString(cusor.getColumnIndex("word"));
                String meaning = cusor.getString(cusor.getColumnIndex("meaning"));
                String sample = cusor.getString(cusor.getColumnIndex("sample"));
                Words words = new Words(id,name, meaning, sample);
                wordsList.add(words);
                wordsAdapter = new WordsAdapter(MainActivity.this, 0, R.layout.words, wordsList);
                listView = (ListView) findViewById(R.id.List_1);
                listView.setAdapter(wordsAdapter);
            } while (cusor.moveToNext());
        }
    }

    //主菜单（）
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //listview 点击修改
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean onContextItemSelected(final MenuItem item){
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final View view = info.targetView;
        TextView textView = (TextView)view.findViewById(R.id.textview_5);
        final String ls_1 = textView.getText().toString();
        switch (item.getItemId()){
            case 0:
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("更新").setView(R.layout.xiugai);
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        @SuppressLint("ResourceType") View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.xiugai,null);
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        final  EditText editText_1 = (EditText) view1.findViewById(R.id.Edittext_单词);
                        final  EditText editText_2 = (EditText) view1.findViewById(R.id.Edittext_释义);
                        final  EditText editText_3 = (EditText) view1.findViewById(R.id.Edittext_例句_xiugai);
                        if(editText_1==null){
                            System.out.println("ed为空");
                        }
                        System.out.println("aosdiap"+editText_1.getText().toString());
                        String ed_1 = editText_1.getText().toString();
                        System.out.println(ed_1);
                        if(ed_1!=null){
                            System.out.println("ed_1为空");
                        }
                        String ed_2 = editText_2.getText().toString();
                        String ed_3 = editText_3.getText().toString();
                        System.out.println("更新为" + ed_1);

                        ContentValues contentValues = new ContentValues();
                        contentValues.put("word",ed_1);
                        contentValues.put("meaning",ed_2);
                        contentValues.put("sample",ed_3);
                        db.update("words",contentValues,"word=?",new String[] {ls_1});
                    }
                    });
                builder.create().show();
                break;
            case  1:
                final SQLiteDatabase DB = mDbHelper.getWritableDatabase();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setMessage("确认删除吗");
                builder1.setTitle("提示");
                builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                                // TODO Auto-generated method stub
                        arg0.dismiss();
                    }
                });
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id1 = wordsList.get(position1).id;
                        System.out.println("name1 = " + id1);
                        DB.delete("words", "Words.Word._ID=?", new String[] {id1 });
                            }
                        });
                        builder1.create().show();
                        break;
            case 2:
                break;
        }
        return false;
    }


   //帮助
    private void help(){
        new AlertDialog.Builder(this)
                .setTitle("帮助")
                .setMessage("帮助信息")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }

    //主菜单跳转
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
            case R.id.help:
                help();
                return  true;
            case  R.id.ref:
                if(wordsAdapter==null){
                    init();
                }
                else{
                    wordsAdapter.clear();
                    init();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
