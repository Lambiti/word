package com.example.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class WordsAdapter extends ArrayAdapter<Words>{
private  int resourceID;

    public WordsAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Words> objects) {
        super(context, resource, textViewResourceId, objects);
        resourceID = textViewResourceId;
    }


    public View getView(int position, View converView, ViewGroup parent){
        Words words = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        TextView word = (TextView)view.findViewById(R.id.textview_5);
        TextView mengning = (TextView)view.findViewById(R.id.textview_6);
        TextView sample = (TextView)view.findViewById(R.id.textview_7);
        word.setText("单词:" + words.getName());
        mengning.setText("释义: " + words.getMeaning());
        sample.setText("例句: " + words.getSample());
        return  view;
    }
}
