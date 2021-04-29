package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.eaglewallet.models.Balances;
import com.example.eaglewallet.models.Card;
import com.example.eaglewallet.models.Transaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    ImageButton calenderHScreenBtn, paymentHScreenBtn,settingHScreenBtn, hideDollarsBtn, cardEmulatorHScreenBtn, logout;
    TextView mealPlanText,sodexoText,eagleDollarText,dinningText;
    boolean hideCondition;
    LinearLayout ProgressBarLayout, ProgressNameLayout, hiddenButton;
    AlertDialog.Builder builder;
    ArrayList<Transaction> userTransactions;
    ArrayList<Card> cards;
    Balances balances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        hideCondition = true;
        logout = (ImageButton) findViewById(R.id.logOutBtn);
        logout.setOnClickListener(v-> clickedLogOut());

        hiddenButton = (LinearLayout) findViewById(R.id.hiddenButton);
        hiddenButton.setOnClickListener(v-> hiddenFeature());

        mealPlanText = (TextView) findViewById(R.id.mealPlanAmount);
        sodexoText = (TextView) findViewById(R.id.sodexoAmount);
        eagleDollarText = (TextView) findViewById(R.id.eagleDollarsText);
        dinningText = (TextView) findViewById(R.id.dinningDollarText);

        ProgressNameLayout = (LinearLayout)findViewById(R.id.ProgressNameLayout);
        ProgressBarLayout = (LinearLayout)findViewById(R.id.ProgressBarLayout);

        hideDollarsBtn = (ImageButton) findViewById(R.id.hideDollarsBtn);
        hideDollarsBtn.setOnClickListener(v -> clickedHideDollars());

        calenderHScreenBtn = (ImageButton) findViewById(R.id.calenderHScreenBtn);
        calenderHScreenBtn.setOnClickListener(v ->
                clickedCalenderBtn( "https://eraudining.sodexomyway.com/dining-near-me/hours"));{}

        paymentHScreenBtn = (ImageButton)findViewById(R.id.paymentHScreenBtn);
        paymentHScreenBtn .setOnClickListener(v ->
                clickedPaymentBtn());{}

        settingHScreenBtn = (ImageButton)findViewById(R.id.settingsHScreenBtn);
        settingHScreenBtn.setOnClickListener(v ->
                clickedSettingBtn());{}

        cardEmulatorHScreenBtn = (ImageButton)findViewById(R.id.cardEmulatorHScreenBtn);
        cardEmulatorHScreenBtn.setOnClickListener(v ->
                clickedCardEmulatorBtn());{}

        //Balances balances = (Balances) getIntent().getSerializableExtra("balances");
        Balances balances = StorageClass.getInstance().getCurrentBalances(Volley.newRequestQueue(getApplicationContext()));
        Log.i("EAGLE", String.valueOf(balances.getEagleDollars()));
        setEagleDollar(Double.toString(balances.getEagleDollars()));
        setDinningDollar(Double.toString(balances.getDiningDollars()));
        setSodexo(Double.toString(balances.getSodexoBucks()));

    }

    private void hiddenFeature() {
        Intent intent=new Intent(HomeScreen.this, HiddenPages.class);
        startActivity(intent);
    }

    private  void clickedHideDollars()
    {
        hideCondition = hideCondition? false:true;
        if(!hideCondition)
        {
            ProgressBarLayout.setVisibility(View.GONE);
            ProgressNameLayout.setVisibility(View.GONE);
            hideDollarsBtn.setImageResource(R.drawable.ic_hidden_eye);
        }
        else
        {
            ProgressBarLayout.setVisibility(View.VISIBLE);
            ProgressNameLayout.setVisibility(View.VISIBLE);
            hideDollarsBtn.setImageResource(R.drawable.ic_show_eye);
        }

    }

    private  void clickedLogOut()
    {
        Intent intent=new Intent(HomeScreen.this, loginPage.class);
        startActivity(intent);
    }

    private void clickedCalenderBtn(String url){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);

    }

    private void clickedPaymentBtn()
    {
        Intent intent=new Intent(HomeScreen.this, PaymentHomeScreen.class);
        startActivity(intent);
    }


    private void clickedSettingBtn()
    {
        Intent intent=new Intent(HomeScreen.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void clickedCardEmulatorBtn()
    {
        Intent intent=new Intent(HomeScreen.this, CardEmulator.class);
        startActivity(intent);
    }

    private  void updateMealPlan(String amountTaken, String totalAmount)
    {
        mealPlanText.setText("$" + amountTaken + "/" + totalAmount);
    }

    private void setEagleDollar(String amount)
    {
        eagleDollarText.setText("$"+ checkSetNull(amount));
    }

    private void setDinningDollar(String amount)
    {
        dinningText.setText("$"+ checkSetNull(amount));
    }

    private void setSodexo(String amount)
    {
        sodexoText.setText("$"+ checkSetNull(amount));
    }

    private String checkSetNull(String string)
    {
        if(string == null)
        {
            return "0";
        }
        return string;
    }


    private void alert(String error)
    {
        builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
        //Setting message manually and performing action on button click
        builder.setMessage("Error: " + error)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Transaction Error:");
        alert.show();
    }


}