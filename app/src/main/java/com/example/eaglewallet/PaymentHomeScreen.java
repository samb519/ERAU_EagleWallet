package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class PaymentHomeScreen extends AppCompatActivity {

    ImageButton calenderPScreenBtn;
    ImageButton homePScreenBtn;
    ProgressBar dinningDollars,sodexo, eagleDollars, mealPlans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_home_screen);

        dinningDollars = (ProgressBar)findViewById(R.id.Dinning_DollarsBar);
        sodexo = (ProgressBar)findViewById(R.id.SodexoBar);
        eagleDollars = (ProgressBar)findViewById(R.id.Eagle_DollarsBar);
        mealPlans = (ProgressBar)findViewById(R.id.Meal_PlansBar);

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

    public void setMealProgressBar(ProgressBar bar, int currentMeal, int totalMeal)
    {
        int current = (currentMeal/totalMeal)*100;
        bar.setProgress(current);
    }
}