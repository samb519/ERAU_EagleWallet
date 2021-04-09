package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;

public class PaymentHomeScreen extends AppCompatActivity {

    ImageButton calenderPScreenBtn;
    ImageButton homePScreenBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_home_screen);

        homePScreenBtn = (ImageButton)findViewById(R.id.homePScreenBtn);
        homePScreenBtn.setOnClickListener(v ->
                clickedHomeBtn());{}

        calenderPScreenBtn = (ImageButton) findViewById(R.id.calenderPScreenBtn);
        calenderPScreenBtn.setOnClickListener(v ->
                clickedCalenderBtn("https://eraudining.sodexomyway.com/dining-near-me/hours"));{}

    }

    public  void clickedHomeBtn()
    {
        Intent intent=new Intent(PaymentHomeScreen.this, HomeScreen.class);
        startActivity(intent);
    }

    public void clickedCalenderBtn(String url)
    {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}