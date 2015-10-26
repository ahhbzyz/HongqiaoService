package com.fyp.hongqiaoservice.nearby.pojo;

/**
 * Created by Administrator on 2015/2/17.
 */
public class deals {
    private String id;

    private String description;

    private String url;

    public deals() {
    }

    public deals(String id, String description, String url) {
        this.id = id;
        this.description = description;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Deals{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
