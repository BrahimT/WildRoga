package com.example.model;

public class Category {

    String category;
    String categoryPicURL;

    public Category(String category, String categoryPicURL) {
        this.category = category;
        this.categoryPicURL = categoryPicURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryPicURL() {
        return categoryPicURL;
    }

    public void setCategoryPicURL(String categoryPicURL) {
        this.categoryPicURL = categoryPicURL;
    }
}
