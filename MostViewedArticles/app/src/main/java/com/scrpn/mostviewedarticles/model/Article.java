package com.scrpn.mostviewedarticles.model;

import com.google.gson.annotations.SerializedName;

public class Article {
    @SerializedName("url")
    String url;

    @SerializedName("title")
    String title;

    @SerializedName("abstract")
    String abstractText;

    @SerializedName("published_date")
    String publishedDate; // TODO: date type
}
