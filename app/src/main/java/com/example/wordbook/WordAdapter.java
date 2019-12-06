package com.example.wordbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {
    private int resourceID;
    List<?> temp=null;
    public WordAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Word> objects) {
        super(context, textViewResourceId, objects);
        resourceID = textViewResourceId;
        temp = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Word word = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        TextView TextView_Word = (TextView)view.findViewById(R.id.TextView_Word_Details);
        TextView TextView_Meaaning = (TextView)view.findViewById(R.id.TextView_Meaning_Details);
        TextView TextView_Sample = (TextView)view.findViewById(R.id.TextView_Sample_Details);
        TextView_Word.setText(word.getWord());
        TextView_Meaaning.setText(word.getMeaning());
        TextView_Sample.setText(word.getSample());
        return view;
    }

    public int getSize(){
        return temp.size();
    }
}
