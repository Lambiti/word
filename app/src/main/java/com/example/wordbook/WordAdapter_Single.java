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

public class WordAdapter_Single extends ArrayAdapter<Word> {
    private int resourceID;
    Context mContent;
    public WordAdapter_Single(@NonNull Context context, int textViewResourceId, @NonNull List<Word> objects) {
        super(context, textViewResourceId, objects);
        resourceID = textViewResourceId;
        mContent = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Word word = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate((int) resourceID,parent,false);
        TextView TextView_Word = (TextView)view.findViewById(R.id.TextView_Word_item);
        TextView_Word.setText(word.getWord());
        return view;
    }

}
