package com.mean.punchbuddy;

import java.util.ArrayList;

public class StrikeList {
    private ArrayList<String> list;
    private String name;

    public StrikeList(String name){
        list = new ArrayList<String>();
        this.name = name;
    }

    public StrikeList(String name, ArrayList<String> list) {
        this.list = list;
        this.name = name;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public String getName() {
        return name;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public void setName(String name) {
        this.name = name;
    }
}