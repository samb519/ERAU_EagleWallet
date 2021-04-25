package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PaymentHomeScreen extends AppCompatActivity {

    ImageButton homePScreenBtn;
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

                //MUST REMOVE: Testing purposes
//        ArrayList<String> test = new ArrayList<>();
//        test.add("3/22/2021: Payment of Sodexo bought for $200");
//        test.add("4/22/2021: Payment of Sodexo bought for $200");
//        createHistoryTable(test);
    }

    private void createHistoryTable(ArrayList<String> history)
    {
        for(int i = 0; i<history.size();i++)
        {
            TextView text = new TextView(this);
            text.setText(history.get(i));
            text.setTextSize(14);
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
}