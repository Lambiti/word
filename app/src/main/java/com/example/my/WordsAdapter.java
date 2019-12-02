package com.example.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

public class WordsAdapter extends ArrayAdapter<Words>{
private  int resourceID;

    public WordsAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Words> objects) {
        super(context, resource, textViewResourceId, objects);
        resourceID = textViewResourceId;
    }


    public View getView(int position, View converView, ViewGroup parent){
        Words words = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(converView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceID, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.word = (TextView)view.findViewById(R.id.textview_5);
            viewHolder.meaning = (TextView)view.findViewById(R.id.textview_6);
            viewHolder.sample = (TextView)view.findViewById(R.id.textview_7);
            view.setTag(viewHolder);
        }
        else{
            view = converView;
            viewHolder = (ViewHolder)view.getTag();
        }
           viewHolder.word.setText(words.getName());
            viewHolder.meaning.setText("释义: " + words.getMeaning());
            viewHolder.sample.setText("例句: " + words.getSample());
        return  view;
    }
    class ViewHolder{
        TextView word;
        TextView meaning;
        TextView sample;
    }

}
