package com.scrpn.mostviewedarticles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scrpn.mostviewedarticles.model.Article;

import java.util.Date;
import java.util.List;

public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder> {

    private List<Article> articles;
    private int rowLayout;
    private Context context;

    public ArticleRecyclerViewAdapter(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public ArticleRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_list_content, parent, false);
        return new ArticleRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ArticleRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.article = articles.get(position);
        //holder.mIdView.setText(articles.get(position).getPublishedDate().toString());
        holder.titleView.setText(articles.get(position).getTitle());
        holder.bylineView.setText(articles.get(position).getByLine());
        holder.dateView.setText(articles.get(position).getPublishedDate().toString());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mTwoPane) {
//                    Bundle arguments = new Bundle();
//                    arguments.putString(ArticleDetailFragment.ARG_ITEM_ID, holder.article.id);
//                    ArticleDetailFragment fragment = new ArticleDetailFragment();
//                    fragment.setArguments(arguments);
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.article_detail_container, fragment)
//                            .commit();
//                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ArticleDetailActivity.class);
                    intent.putExtra(ArticleDetailFragment.ARG_ARTICLE_URL, holder.article.getUrl());
                    intent.putExtra(ArticleDetailFragment.ARG_ARTICLE_TITLE, holder.article.getTitle());
                    intent.putExtra(ArticleDetailFragment.ARG_ARTICLE_ABSTRACT, holder.article.getAbstractText());
                    intent.putExtra(ArticleDetailFragment.ARG_ARTICLE_BYLINE, holder.article.getByLine());
                    intent.putExtra(ArticleDetailFragment.ARG_ARTICLE_DATE, holder.article.getPublishedDate());

                    context.startActivity(intent);
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView titleView;
        public final TextView bylineView;
        public final TextView dateView;
        public Article article;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titleView = (TextView) view.findViewById(R.id.list_title);
            bylineView = (TextView) view.findViewById(R.id.list_byline);
            dateView = (TextView) view.findViewById(R.id.list_date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + dateView.getText() + "'";
        }
    }
}
