package com.example.todo;

public class Record {
    private long id;
    private String title;
    private String content;
    private String time;
    private String tag;
    public Record(){
    }
    public Record(String title,String content,String time,String tag){
        this.title=title;
        this.content=content;
        this.time =time;
        this.tag =tag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
