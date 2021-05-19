package com.example.doctorappointmentsystem.model;

public class category {

    private String _id;

    private String categoryImage;
    private String categoryName;

    public category(String categoryImage, String categoryName){
        this.categoryImage = categoryImage;
        this.categoryName = categoryName;
    }


    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

}
