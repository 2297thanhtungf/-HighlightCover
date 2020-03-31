package com.dtt.hightlightcover.model;

import java.io.Serializable;

public class ItemCustom implements Serializable {

    private String name;

    private Boolean backgroundLock;

    private Integer type;

    private Content content;

    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Boolean getBackgroundLock() {
        return backgroundLock;
    }

    public void setBackgroundLock(Boolean backgroundLock) {
        this.backgroundLock = backgroundLock;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ItemCustom{" +
                "name='" + name + '\'' +
                ", backgroundLock=" + backgroundLock +
                ", type=" + type +
                ", content=" + content.toString() +
                ", id=" + id +
                '}';
    }
}

