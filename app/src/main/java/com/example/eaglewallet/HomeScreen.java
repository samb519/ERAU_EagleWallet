package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.eaglewallet.models.Balances;
import com.example.eaglewallet.models.Transaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    ImageButton calenderHScreenBtn, paymentHScreenBtn,settingHScreenBtn, hideDollarsBtn, cardEmulatorHScreenBtn;
    ProgressBar pBarHScreen;
    TextView processText, mealPlanText,sodexoText,eagleDollarText,dinningText;
    boolean hideCondition;
    LinearLayout ProgressBarLayout, ProgressNameLayout;
    AlertDialog.Builder builder;

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

        Balances balances = (Balances) getIntent().getSerializableExtra("Balances");
        setEagleDollar(Double.toString(balances.getEagleDollars()));
        setDinningDollar(Double.toString(balances.getDiningDollars()));
        setSodexo(Double.toString(balances.getSodexoBucks()));

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

    private void clickedPaymentBtn()
    {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://eaglewallet.wise-net.xyz/api/Transaction/history/" + loginPage.getId();

        JsonArrayRequest req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("VOLLEY", response.toString());
                List<Transaction> userTransactions = new ArrayList<Transaction>();

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Transaction trans = new Transaction();

                        trans.setDate(jsonObject.getString("transactionDate"));
                        trans.setDiningDollars(jsonObject.getDouble("diningDollars"));
                        trans.setEagleDollars(jsonObject.getDouble("eagleDollars"));
                        trans.setMealPlans(jsonObject.getDouble("mealPlans"));
                        trans.setSodexoBucks(jsonObject.getDouble("sodexoBucks"));
                        trans.setTransId(jsonObject.getInt("transactionId"));
                        trans.setUserId(jsonObject.getInt("userId"));

                        userTransactions.add(trans);
                    }

                    Intent intent=new Intent(HomeScreen.this, PaymentHomeScreen.class);
                    intent.putExtra("UserTransactions", (Serializable) userTransactions);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                alert(error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };

        queue.add(req);
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

    private  void processCurrently()
    {
        processText.setText("Processing");
    }

    private  void disProcessText()
    {
        processText.setVisibility(View.GONE);
    }

    private  void enableProcessText()
    {
        processText.setVisibility(View.VISIBLE);
    }

    private void alert(String error)
    {
        disProcessText();
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