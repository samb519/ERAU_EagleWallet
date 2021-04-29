package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class HiddenPages extends AppCompatActivity {

    ImageButton homeBtn,calenderBtn,paymentBtn;
    TextView video,discoveryDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_page);
        homeBtn = (ImageButton) findViewById(R.id.hiddenPageHomeBtn);
        homeBtn.setOnClickListener(v -> clickedHome());
        calenderBtn = (ImageButton) findViewById(R.id.hiddenPageCalBtn);
        calenderBtn.setOnClickListener(v->clickedCalender("https://eraudining.sodexomyway.com/dining-near-me/hours"));
        paymentBtn = (ImageButton) findViewById(R.id.hiddenPagePayBtn);
        paymentBtn.setOnClickListener(v->clickedPayment());
        video = (TextView) findViewById(R.id.youtubeBtn);
        video.setOnClickListener(v-> clickedCalender("https://youtu.be/Y7g2iNrWVIA"));
        discoveryDay = (TextView) findViewById(R.id.discoveryBtn);
        discoveryDay.setOnClickListener(v->clickedCalender("https://commons.erau.edu/discovery-day/db-discovery-day-2021/poster-session/32/"));
    }

    private void clickedPayment() {
        Intent intent=new Intent(HiddenPages.this, PaymentHomeScreen.class);
        startActivity(intent);
    }

    private void clickedCalender(String url) {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void clickedHome() {
        Intent intent=new Intent(HiddenPages.this, HomeScreen.class);
        startActivity(intent);
    }


}