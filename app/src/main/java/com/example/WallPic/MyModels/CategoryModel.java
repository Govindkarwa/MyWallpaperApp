package com.example.WallPic.MyModels;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    private Integer ImageId;
    private String CategoryName;
    private String SearchData;

    public CategoryModel(Integer imageId, String categoryName, String searchData) {
        ImageId = imageId;
        CategoryName = categoryName;
        SearchData = searchData;
    }

    public Integer getImageId() {
        return ImageId;
    }

    public void setImageId(Integer imageId) {
        ImageId = imageId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getSearchData() {
        return SearchData;
    }

    public void setSearchData(String searchData) {
        SearchData = searchData;
    }
}
