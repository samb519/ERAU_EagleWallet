package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class PaymentHomeScreen extends AppCompatActivity {

    ImageButton homePScreenBtn;
    Button addFundBtnPScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_home_screen);

        addFundBtnPScreen = (Button) findViewById(R.id.addFundBtnPScreen);
        addFundBtnPScreen.setOnClickListener(v ->
                clickedAddFundBtn());
        {
        }
        homePScreenBtn = (ImageButton) findViewById(R.id.homePScreenBtn);
        homePScreenBtn.setOnClickListener(v ->
                clickedHomeBtn());
        {
        }
    }

    private void clickedAddFundBtn() {
        Intent intent = new Intent(PaymentHomeScreen.this, AddPaymentFromPaymentScreen.class);
        startActivity(intent);
    }

    private void clickedHomeBtn() {
        Intent intent = new Intent(PaymentHomeScreen.this, HomeScreen.class);
        startActivity(intent);
    }
}