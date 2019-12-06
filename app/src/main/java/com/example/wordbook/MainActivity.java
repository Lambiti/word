package com.example.wordbook;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="myTag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onConfigurationChanged();
        //setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        /*@SuppressLint("ServiceCast") WindowManager systemService = (WindowManager) getSystemService(WALLPAPER_SERVICE);
        //通过WindowManager对象拿到手机宽高的参数
        int height=getWindowManager().getDefaultDisplay().getHeight();
        int  width=getWindowManager().getDefaultDisplay().getWidth();*/

    }

    private void onConfigurationChanged() {
        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            // 切换成竖屏
            setContentView(R.layout.activity_main);
            // findViewById
            // 进行一些操作。。。
        } else {
            // 切换成横屏
            setContentView(R.layout.activity_select__list_);
            // findViewById
            // 进行一些操作。。。
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.item_add:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("添加");
                final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_dialog_layout,null);
                builder.setView(view);
                final TextView TextView_Sample;
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Word word = new Word();
                        EditText TextView_Word = (EditText) view.findViewById(R.id.add_dialog_word);
                        EditText TextView_Meaning = (EditText)view.findViewById(R.id.add_dialog_meaning);
                        EditText TextView_Sample = (EditText) view.findViewById(R.id.add_dialog_sample);
                        String T_W = TextView_Word.getText().toString();
                        String T_M = TextView_Meaning.getText().toString();
                        String T_S = TextView_Sample.getText().toString();
                        word.setWord(T_W);
                        word.setMeaning(T_M);
                        word.setSample(T_S);
                        word.save();
                        Toast.makeText(MainActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                        refreshWordsList();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
            case R.id.item_ref:
                refreshWordsList();
                break;
            case R.id.item_help:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle("帮助");
                builder1.setMessage("帮助信息");
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     dialog.dismiss();
                    }
                });
                builder1.create().show();
                break;
            case R.id.item_select:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                builder2.setTitle("查询");
                final View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.select_dialog_layout,null);
                builder2.setView(view1);
                final View view2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.select_list_layout,null);
                builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         EditText EditText_select = (EditText)view1.findViewById(R.id.select_dialog_EditText);
                         String select_str = EditText_select.getText().toString();
                         System.out.println("查询：" + select_str);
                         List<Word> list = new ArrayList<>();
                         refreshWordsList(select_str);
                    }
                });
                builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                    }
                });
                builder2.create().show();
                break;
            case R.id.item_news:
                Intent intent = new Intent(MainActivity.this,News_Activity.class);
                startActivity(intent);
                break;
            case R.id.item_select_online:
                Intent intent1 = new Intent(MainActivity.this,Translate_Activity.class);
                startActivity(intent1);
                break;
        }
        //noinspection SimplifiableIfStatement
        return true;
    }
    public void refreshWordsList(){
        LitePal.getDatabase();
        if(LitePal.getDatabase()!=null){
            List<Word> WordList = new ArrayList<>();
            WordList = LitePal.findAll(Word.class);
            ListView listView = (ListView)findViewById(R.id.ListView_Word);
            WordAdapter wordAdapter = new WordAdapter(MainActivity.this,R.layout.word_details_layout,WordList);
            listView.setAdapter(wordAdapter);
        }
    }
    public  void refreshWordsList(String select_str){
        LitePal.getDatabase();
        if(LitePal.getDatabase()!=null){
            List<Word> WordList = new ArrayList<>();
            WordList =  LitePal.where("word like ?","%" + select_str + "%").find(Word.class);
            ListView listView = (ListView)findViewById(R.id.ListView_Word);
            WordAdapter wordAdapter = new WordAdapter(MainActivity.this,R.layout.word_details_layout,WordList);
            listView.setAdapter(wordAdapter);
        } else{
            Toast.makeText(MainActivity.this,"Not found",Toast.LENGTH_LONG).show();
        }
    }
    /*@Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        // 屏幕切换时的操作
        if (config.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            // 切换成竖屏
            setContentView(R.layout.activity_main);
            // findViewById
            // 进行一些操作。。。
        } else {
            // 切换成横屏
            setContentView(R.layout.activity_main);
            // findViewById
            // 进行一些操作。。。
        }
    }*/

}
