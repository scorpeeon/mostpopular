package com.scrpn.mostviewedarticles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.scrpn.mostviewedarticles.model.Article;
import com.scrpn.mostviewedarticles.model.ArticleResponse;
import com.scrpn.mostviewedarticles.network.ApiClient;
import com.scrpn.mostviewedarticles.network.ApiInterface;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An activity representing a list of Articles. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ArticleDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ArticleListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private final static String TAG = "ArticleListActivity";
    private static String API_KEY = ""; // TODO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        API_KEY = getResources().getString(R.string.nytimes_api_key);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArticleListActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.article_list);
        assert recyclerView != null;

        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.article_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        /////

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
            return;
        }

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        refreshItems();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
    }

    void refreshItems() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ArticleResponse> call = apiService.getArticles("all-sections", 7, API_KEY);
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse>call, Response<ArticleResponse> response) {
                int statusCode = response.code();
                if(statusCode == HttpURLConnection.HTTP_OK) {
                    List<Article> articles = response.body().getArticles();
                    recyclerView.setAdapter(new ArticleRecyclerViewAdapter(articles, ArticleListActivity.this));
                }
            }

            @Override
            public void onFailure(Call<ArticleResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

}
