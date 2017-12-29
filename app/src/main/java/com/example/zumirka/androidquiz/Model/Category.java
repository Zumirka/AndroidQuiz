package com.example.zumirka.androidquiz.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zumirka on 29.12.2017.
 */

public class Category {
    private int Id;
    private String NameCategory;
    public Category() {

    }

    public void setCategoryID(int id){this.Id=id;}
    public void setNameOFCategory(String cat){this.NameCategory=cat;}
    public String getCategory(){return NameCategory;}
    public int getCategoryId(){return Id;}

}
