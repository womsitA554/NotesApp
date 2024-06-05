package com.example.note;

import java.io.Serializable;

public class Note implements Serializable {
    private int id ;
    private String title;
    private String content;
    private String date;
    private boolean pin;

    public Note(int id, String title, String content, String date, boolean pin) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.pin = pin;
    }

    public Note() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String tiTile) {
        this.title = tiTile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPin() {
        return pin;
    }

    public void setPin(boolean pin) {
        this.pin = pin;
    }
}
