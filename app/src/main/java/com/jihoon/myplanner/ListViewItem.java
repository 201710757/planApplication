package com.jihoon.myplanner;

public class ListViewItem {
    private String title;
    private String todo;
    private int id;

    public void setTitle(String title)
    {
        this.title = title;
    }
    public void setTodo(String todo)
    {
        this.todo = todo;
    }
    public void setId(int id) {this.id = id;}

    public String getTitle()
    {
        return title;
    }
    public String getTodo()
    {
        return todo;
    }
    public int getId() { return id; }
}
