package com.example.news3;
import com.google.gson.annotations.SerializedName;

public class News {
    @SerializedName("ctime")
    public String time;

    public String title;

    public String description;

    public String picUrl;

    public String url="baidu.com";

    public int ImageId;

    public News(String introduction, int imageId)
    {
        this.description = introduction;
        this.ImageId = imageId;
    }

    public int type;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getUrl() {
        return url;
    }

    public int getImageId() {
        return ImageId;
    }
}