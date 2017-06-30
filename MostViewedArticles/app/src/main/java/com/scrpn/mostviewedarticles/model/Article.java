package com.scrpn.mostviewedarticles.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Article {
    @SerializedName("url")
    private String url;

    @SerializedName("title")
    private String title;

    @SerializedName("abstract")
    private String abstractText;

    @SerializedName("published_date")
    private Date publishedDate;

    public Article(String url, String title, String abstractText, Date publishedDate) {
        this.url = url;
        this.title = title;
        this.abstractText = abstractText;
        this.publishedDate = publishedDate;
    }


    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }
}
