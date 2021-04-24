package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HomeScreen extends AppCompatActivity {

    ImageButton calenderHScreenBtn, paymentHScreenBtn,settingHScreenBtn, hideDollarsBtn;
    ProgressBar pBarHScreen;
    TextView processText, mealPlanText,sodexoText,eagleDollarText,dinningText;
    boolean hideCondition;
    LinearLayout ProgressBarLayout, ProgressNameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mealPlanText = (TextView) findViewById(R.id.mealPlanAmount);
        sodexoText = (TextView) findViewById(R.id.sodexoAmount);
        eagleDollarText = (TextView) findViewById(R.id.eagleDollarsText);
        dinningText = (TextView) findViewById(R.id.dinningDollarText);

        ProgressNameLayout = (LinearLayout)findViewById(R.id.ProgressNameLayout);
        ProgressBarLayout = (LinearLayout)findViewById(R.id.ProgressBarLayout);

        hideDollarsBtn = (ImageButton) findViewById(R.id.hideDollarsBtn);
        hideCondition = true;
        hideDollarsBtn.setOnClickListener(v -> clickedHideDollars());

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

    private  void clickedHideDollars()
    {
        hideCondition = hideCondition? false:true;
        if(!hideCondition)
        {
            ProgressBarLayout.setVisibility(View.GONE);
            ProgressNameLayout.setVisibility(View.GONE);
            hideDollarsBtn.setImageResource(R.drawable.oclusionoff);
        }
        else
        {
            ProgressBarLayout.setVisibility(View.VISIBLE);
            ProgressNameLayout.setVisibility(View.VISIBLE);
            hideDollarsBtn.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
        }

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

    private void setMealPlan(String amountTaken, String totalAmount)
    {
        mealPlanText.setText("$" + amountTaken + "/" + totalAmount);
    }

    private  void setEagleDollar(String amount)
    {
        eagleDollarText.setText("$"+amount);
    }

    private void setDinningDollar(String amount)
    {
        dinningText.setText("$"+amount);
    }

    private void setSodexo(String amount)
    {
        sodexoText.setText("$"+amount);
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