package com.example.newsic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsic.News.NewsHeadlines;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapterNews extends RecyclerView.Adapter {
    Context context;
    List<NewsHeadlines> headlines;


    public RecyclerViewAdapterNews(Context context, List<NewsHeadlines> headlines) {
        this.context = context;
        this.headlines = headlines;
    }
    RecyclerViewHolderNews rec;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_news,parent,false);
        rec = new RecyclerViewHolderNews(v);
        return rec;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder = rec;
        int p = rec.getAbsoluteAdapterPosition();
        NewsHeadlines newsHeadlines = headlines.get(p);
        rec.title.setText(newsHeadlines.getTitle());
        rec.source.setText(newsHeadlines.getSource().getName());
        if (newsHeadlines.getUrlToImage() != null){
            Picasso.get().load(newsHeadlines.getUrlToImage()).into(rec.imgHeadline);
        }
        rec.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedNews.class);
                intent.putExtra("data", headlines.get(p));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }

    public class RecyclerViewHolderNews extends RecyclerView.ViewHolder{
        TextView title;
        TextView source;
        ImageView imgHeadline;
        CardView cardView;

        public RecyclerViewHolderNews(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.headline);
            source = itemView.findViewById(R.id.source);
            imgHeadline = itemView.findViewById(R.id.imgNews);
            cardView = itemView.findViewById(R.id.card_news);
        }
    }
}
