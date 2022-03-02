package com.example.newsic;

import com.example.newsic.News.NewsHeadlines;

import java.util.List;

public interface OnDataFetchListener<NewsApiResponse> {
    void onDataFetch(List<NewsHeadlines> list, String msg);
    void onError(String msg);
}
