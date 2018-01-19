package com.example.zumirka.androidquiz.Model;

/**
 * Created by Zumirka on 29.12.2017.
 */

public class Category {
    private int id;
    private String categoryName;

    public Category() {

    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return categoryName;
    }

    public void setName(String cat) {
        this.categoryName = cat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
