package com.dtt.sleepsound.model;


public class Sound {
    private String name;
    private int imageView;
    private int file;

    public Sound(String name, int imageView, int file) {
        this.name = name;
        this.imageView = imageView;
        this.file = file;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }

    public Sound() {
        super();
    }

    public int getImageView() {
        return imageView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setImageView(int imageView) {
        this.imageView = imageView;
    }
}
