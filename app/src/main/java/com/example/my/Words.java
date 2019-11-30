package com.example.my;

import android.provider.BaseColumns;

public class Words {
    String name;
    String meaning;
    String sample;
public  Words(String name, String meaning, String sample){
this.name = name;
this.meaning = meaning;
this.sample = sample;
}
public  String getName(){
    return name;
}
public  String getMeaning(){
    return  meaning;
}
public String getSample(){
    return sample;
}
public  static  abstract class Word implements BaseColumns{
    public static final String TABLE_NAME = "words";
    public static final String COLUMN_NAME_WORD = "word";
    public static final String COLUMN_NAME_MEANING = "meaning";
    public static final String COLUMN_NAME_SAMPLE = "sample";
}
}
