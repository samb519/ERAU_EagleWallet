package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;

public class HiddenPages extends AppCompatActivity {

    ImageButton homeBtn,calenderBtn,paymentBtn;

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