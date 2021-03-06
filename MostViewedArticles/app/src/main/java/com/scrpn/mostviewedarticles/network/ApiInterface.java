package com.scrpn.mostviewedarticles.network;


import com.scrpn.mostviewedarticles.model.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("mostviewed/{section}/{time-period}.json")
    Call<ArticleResponse> getArticles(@Path("section") String section, @Path("time-period") int timePeriod, @Query("api_key") String apiKey);
}
