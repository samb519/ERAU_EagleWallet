package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PaymentHomeScreen extends AppCompatActivity {

    ImageButton homePScreenBtn, calenderPScreenBtn;
    Button addFundBtnPScreen;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_home_screen);

        layout = (LinearLayout) findViewById(R.id.historyLinearLayout);
        addFundBtnPScreen = (Button) findViewById(R.id.addFundBtnPScreen);
        addFundBtnPScreen.setOnClickListener(v ->
                clickedAddFundBtn());{}
        homePScreenBtn = (ImageButton) findViewById(R.id.homePScreenBtn);
        homePScreenBtn.setOnClickListener(v ->
                clickedHomeBtn());{}

        calenderPScreenBtn = (ImageButton) findViewById(R.id.calenderPScreenBtn);
        calenderPScreenBtn.setOnClickListener(v ->
                clickedCalenderBtn( "https://eraudining.sodexomyway.com/dining-near-me/hours"));{}

                //MUST REMOVE: Testing purposes
        ArrayList<String> test = new ArrayList<>();
        test.add("3/22/2021: Payment of Sodexo bought for $200");
        test.add("4/22/2021: Payment of Sodexo bought for $200");
        createHistoryTable(test);
    }

    private void createHistoryTable(ArrayList<String> history)
    {
        for(int i = 0; i<history.size();i++)
        {
            TextView text = new TextView(this);
            text.setText(history.get(i));
            text.setTextSize(14);
            text.setTextColor(Color.parseColor("#000000"));
            text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ((LinearLayout) layout).addView(text);
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

    private void clickedCalenderBtn(String url){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);

    }
}