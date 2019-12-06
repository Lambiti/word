package com.example.wordbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class Word_List_FragMent extends Fragment {
    private View view;
    private List<Word> WordList = new ArrayList<>();
    private int position1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.word_list_layout,container,false);
        init();
        ItemListener();
        return view;
    }

public void init(){
    LitePal.getDatabase();
    WordList = LitePal.findAll(Word.class);
    ListView listView = (ListView)view.findViewById(R.id.ListView_Word);
    WordAdapter wordAdapter = new WordAdapter(getContext(),R.layout.word_details_layout,WordList);
    listView.setAdapter(wordAdapter);
}
public void ItemListener(){
    final ListView listView = (ListView)view.findViewById(R.id.ListView_Word);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            listView.showContextMenu();
            position1 =position;
        }
    });
    listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0,0,1,"修改");
            menu.add(0,1,1,"删除");
            menu.add(0,2,1,"取消");
        }
    });
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean onContextItemSelected(final MenuItem item){
        final String lo_1 = WordList.get(position1).getWord();
        switch (item.getItemId()){
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
               final View view = LayoutInflater.from(getContext()).inflate(R.layout.updata_dialog_layout,null);
               builder.setView(view);
               builder.setTitle("更新");
               builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       EditText EditText_Word = (EditText)view.findViewById(R.id.updata_dialog_word);
                       EditText EditText_Meaning = (EditText)view.findViewById(R.id.updata_dialog_meaning);
                       EditText EditText_Sample = (EditText)view.findViewById(R.id.updata_dialog_sample);
                       String E_W = EditText_Word.getText().toString();
                       String E_M = EditText_Meaning.getText().toString();
                       String E_S = EditText_Sample.getText().toString();
                       if(E_W==null){
                           System.out.println("输入为空");
                       }
                       else{
                           System.out.println("输入为" + lo_1);
                       }
                       Word word = new Word();
                       word.setWord(E_W);
                       word.setMeaning(E_M);
                       word.setSample(E_S);
                       word.updateAll("word = ?",lo_1);
                       init();
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
            case 1:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setTitle("删除");
                builder1.setMessage("是否删除？");
                builder1.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LitePal.deleteAll(Word.class,"word=?",lo_1);
                        init();
                    }
                });
                builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder1.create().show();
                break;
            case 2:
                break;
        }
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
