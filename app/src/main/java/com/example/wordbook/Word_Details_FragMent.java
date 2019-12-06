package com.example.wordbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Word_Details_FragMent extends Fragment {
    private View view;
    public static String ARG_ID = "id";
    private static final String TAG="myTag";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.word_details_layout,container,false);
        return view;
    }
    public void refresh(String Word, String Meaning, String Sample){
        View view_1 = view.findViewById(R.id.LinearLayout_word_details);
        view_1.setVisibility(View.VISIBLE);
        TextView TextView_Word = (TextView)view_1.findViewById(R.id.TextView_Word_Details);
        TextView TextView_Meaning = (TextView)view_1.findViewById(R.id.TextView_Meaning_Details);
        TextView TextView_Sample = (TextView)view_1.findViewById(R.id.TextView_Sample_Details);
        TextView_Word.setText(Word);
        TextView_Meaning.setText(Meaning);
        TextView_Sample.setText(Sample);
    }
}
