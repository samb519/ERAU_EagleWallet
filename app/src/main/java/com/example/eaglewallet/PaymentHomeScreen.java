package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Trace;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eaglewallet.models.Balances;
import com.example.eaglewallet.models.Card;
import com.example.eaglewallet.models.Transaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PaymentHomeScreen extends AppCompatActivity {

    ImageButton homePScreenBtn, calenderPScreenBtn;
    Button addFundBtnPScreen;
    LinearLayout layout;
    List<Transaction> userTransactions;
    List<Card> cards;
    Balances balances;


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

        ArrayList<String> test = new ArrayList<>();

        balances = (Balances) getIntent().getSerializableExtra("balances");

        cards = (List<Card>) getIntent().getSerializableExtra("Cards");

        if (getIntent().getExtras() != null) {
            userTransactions = (List<Transaction>) getIntent().getSerializableExtra("UserTransactions");
            for (Transaction trans : userTransactions) {
                if (trans.getSodexoBucks() > 0) {
                    test.add("Deposit on " + trans.getDate() + ": $" + trans.getSodexoBucks() + " Sodexo Bucks");
                }
                if (trans.getSodexoBucks() < 0) {
                    test.add("Spent on " + trans.getDate() + ": $" + trans.getSodexoBucks() + " Sodexo Bucks");
                }
                if (trans.getDiningDollars() > 0) {
                    test.add("Deposit on " + trans.getDate() + ": $" + trans.getDiningDollars() + " Dining Dollars");
                }
                if (trans.getDiningDollars() < 0) {
                    test.add("Spent on " + trans.getDate() + ": $" + trans.getDiningDollars() + " Dining Dollars");
                }
                if (trans.getEagleDollars() > 0) {
                    test.add("Deposit on " + trans.getDate() + ": $" + trans.getEagleDollars() + " Eagle Dollars");
                }
                if (trans.getEagleDollars() < 0) {
                    test.add("Spent on " + trans.getDate() + ": $" + trans.getEagleDollars() + " Eagle Dollars");
                }
                if (trans.getMealPlans() > 0) {
                    test.add("Deposit on " + trans.getDate() + ": " + trans.getMealPlans() + " Meal Plans");
                }
                if (trans.getMealPlans() < 0) {
                    test.add("Spent on " + trans.getDate() + ": $" + trans.getMealPlans() + " Meal Plans");
                }
            }
        }
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
        intent.putExtra("Transactions", (Serializable) userTransactions);
        intent.putExtra("Cards", (Serializable) cards);
        intent.putExtra("balances", (Serializable) balances);
        startActivity(intent);
    }

    private void clickedHomeBtn() {
        Intent intent = new Intent(PaymentHomeScreen.this, HomeScreen.class);
        intent.putExtra("balances", (Serializable) balances);
        startActivity(intent);
    }

    private void clickedCalenderBtn(String url){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);

    }
}