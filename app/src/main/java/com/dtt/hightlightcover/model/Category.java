package com.dtt.hightlightcover.model;

import java.util.ArrayList;

public class Category {
    private String title;
    private ArrayList<ItemCustom> itemCustoms;

    public Category() {
        itemCustoms = new ArrayList<>();
    }

    public Category(String title, ArrayList<ItemCustom> itemCustoms) {
        this.title = title;
        this.itemCustoms = itemCustoms;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ItemCustom> getItemCustoms() {
        return itemCustoms;
    }

    public void setItemCustoms(ArrayList<ItemCustom> itemCustoms) {
        this.itemCustoms = itemCustoms;
    }

    @Override
    public String toString() {
        return "Category{" +
                "title='" + title + '\'' +
                ", itemCustoms=" + itemCustoms.toString()  +
                '}';
    }
}
