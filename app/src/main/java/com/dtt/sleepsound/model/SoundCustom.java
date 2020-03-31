package com.dtt.sleepsound.model;

public class SoundCustom {

    private int image;
    private int file;

    public SoundCustom(int image, int file) {
        this.image = image;
        this.file = file;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }
}
