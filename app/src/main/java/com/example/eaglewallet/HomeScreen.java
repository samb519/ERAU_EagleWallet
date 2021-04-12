package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    ImageButton calenderHScreenBtn;
    ImageButton paymentHScreenBtn;
    ImageButton settingHScreenBtn;
    ProgressBar pBarHScreen;
    TextView processText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        pBarHScreen = (ProgressBar)findViewById(R.id.pBarHScreen);
        disEnableScannerProgress();

        processText = (TextView) findViewById(R.id.scannerProcessText);
        disProcessText();

        calenderHScreenBtn = (ImageButton) findViewById(R.id.calenderHScreenBtn);
        calenderHScreenBtn.setOnClickListener(v ->
                clickedCalenderBtn( "https://eraudining.sodexomyway.com/dining-near-me/hours"));{}

        paymentHScreenBtn = (ImageButton)findViewById(R.id.paymentHScreenBtn);
        paymentHScreenBtn .setOnClickListener(v ->
                clickedPaymentBtn());{}

        settingHScreenBtn = (ImageButton)findViewById(R.id.settingsHScreenBtn);
        settingHScreenBtn.setOnClickListener(v ->
                clickedSettingBtn());{}
    }

    private void clickedCalenderBtn(String url){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);

    }

    private   void clickedPaymentBtn()
    {
        Intent intent=new Intent(HomeScreen.this, PaymentHomeScreen.class);
        startActivity(intent);
    }

    private   void clickedSettingBtn()
    {
        Intent intent=new Intent(HomeScreen.this, SettingsActivity.class);
        startActivity(intent);
    }

    private  void enableScannerProgress()
    {
        pBarHScreen.setVisibility(View.VISIBLE);
    }

    private void disEnableScannerProgress()
    {
        pBarHScreen.setVisibility(View.GONE);
    }

    private  void processFailed()
    {
        processText.setText("Process Failed");
    }

    private  void processCurrently()
    {
        processText.setText("Processing");
    }

    private  void processComplete()
    {
        processText.setText("Process Completed");
    }

    private  void disProcessText()
    {
        processText.setVisibility(View.GONE);
    }

    private  void enableProcessText()
    {
        processText.setVisibility(View.VISIBLE);
    }
}