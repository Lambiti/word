package com.example.wordbook;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class Single_Word_list_FragMent extends Fragment {
    View view;
    View view1;
    List<Word> wordList = new ArrayList<>();
    int position1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.word_single_layout,container,false);
        init();
        ItemListener();
        view1 = view;
        return view;

    }
    public void init(){
        LitePal.getDatabase();
        wordList = LitePal.findAll(Word.class);
        ListView listView = (ListView)view.findViewById(R.id.ListView_Single_Word);
        WordAdapter_Single wordAdapter = new WordAdapter_Single(getContext(),R.layout.word_list_item_layout ,wordList);
        listView.setAdapter(wordAdapter);
    }
    public void ItemListener(){
        final ListView listView = (ListView)view.findViewById(R.id.ListView_Single_Word);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position1 =position;
                final String lo_1 = wordList.get(position).getWord();
                String lo_2 = wordList.get(position).getMeaning();
                String lo_3 = wordList.get(position).getSample();
                System.out.println("总的为" + lo_1 + lo_2 + lo_3);
                //View view1 = LayoutInflater.from(getContext()).inflate(R.layout.word_single_layout,null);
                //TextView TextView_Single_Word = (TextView)view1.findViewById(R.id.TextView_Word_Details);
                //TextView TextView_Single_Meaning = (TextView)view1.findViewById(R.id.TextView_Meaning_Details);
                //TextView TextView_Single_Sample = (TextView)view1.findViewById(R.id.TextView_Sample_Details);
                TextView TextView_Single_Word = (TextView)view1.findViewById(R.id.TextView_Word_Fragment);
                TextView TextView_Single_Meaning = (TextView)view1.findViewById(R.id.TextView_Meaning_Fragment);
                TextView TextView_Single_Sample = (TextView)view1.findViewById(R.id.TextView_Sample_Fragment);
                if(TextView_Single_Word == null){
                    System.out.println("文本为空");
                }
                else {
                    System.out.println("文本不为空");
                    TextView_Single_Word.setText(lo_1);
                    System.out.println(TextView_Single_Word.getText().toString() + "是什么");
                    TextView_Single_Meaning.setText(lo_2);
                    System.out.println(TextView_Single_Meaning.getText().toString() + "是什么");
                    TextView_Single_Sample.setText(lo_3);
                    Toast.makeText(getContext(),"This is " + lo_1 ,Toast.LENGTH_SHORT);
                }

            }
        });
    }
}
