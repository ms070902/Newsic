package com.example.newsic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsic.News.NewsHeadlines;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class DetailedNews extends AppCompatActivity{
    NewsHeadlines headlines;
    TextView title, author, description, time, content;
    ImageView imgNews;
    Button speak, stopSpeaking;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_news);

        title = findViewById(R.id.newsHeadline);
        author = findViewById(R.id.newsAuthor);
        description = findViewById(R.id.newsDescription);
        time = findViewById(R.id.newsTime);
        content = findViewById(R.id.newsContent);
        imgNews = findViewById(R.id.imgNews);
        speak = findViewById(R.id.textToSpeech);
        stopSpeaking = findViewById(R.id.textToSpeechOff);

        headlines = (NewsHeadlines) getIntent().getSerializableExtra("data");

        title.setText(headlines.getTitle());
        author.setText(headlines.getAuthor());
        time.setText(headlines.getPublishedAt());
        description.setText(headlines.getDescription());
        content.setText(headlines.getContent());

        Picasso.get().load(headlines.getUrlToImage()).into(imgNews);



        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i == TextToSpeech.SUCCESS){
                    textToSpeech.setLanguage(Locale.ENGLISH);
                    speak.setEnabled(true);
                }
                else{
                    Toast.makeText(DetailedNews.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                read();
            }
        });
        stopSpeaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.stop();
                textToSpeech.shutdown();
            }
        });
    }

    private void read() {
        String title = headlines.getTitle();
        String description = headlines.getDescription();
        String content = headlines.getContent();
        String news = title + description + content;
        textToSpeech.setPitch(50);
        textToSpeech.setSpeechRate(50);
        textToSpeech.speak(news,TextToSpeech.QUEUE_FLUSH, null);
    }


}