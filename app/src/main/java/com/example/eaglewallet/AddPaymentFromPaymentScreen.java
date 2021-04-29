package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.eaglewallet.models.Balances;
import com.example.eaglewallet.models.Card;
import com.example.eaglewallet.models.Transaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddPaymentFromPaymentScreen extends AppCompatActivity {

    CheckBox addCard,existCard;
            //gPayCard;
    boolean addCardCondition, existCardCondition, gpayCardConditon;
    ScrollView addPScreen_Layout;
    ArrayList<String> cardList = new ArrayList<>();
    Spinner cardSpinner, planOptionsSpinner, mealOptionsSpinner;
    int clickedPlanOption;
    LinearLayout fundOptionsLayout,mealOptionsLayout,ExistCardPScreen_CardLayout; //within the spinners
    Button AddPScreen_Back, AddPScreen_Submit;
    EditText credCardNum,CVC,ExpDate,FullName,StreetAddr, City,State,Zip, amountText;
    List<Transaction> userTransactions;
    List<Card> cards;
    Balances balances;
    Double amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment_from_payment_screen);
        addCardCondition = existCardCondition = gpayCardConditon = false;
        findEditText();
        findSpinnerAndLayouts();
        findBtns();

        userTransactions = (List<Transaction>) getIntent().getSerializableExtra("Transactions");
        cards = (List<Card>) getIntent().getSerializableExtra("Cards");
        balances = (Balances) getIntent().getSerializableExtra("balances");

//        //Testing - Will need to be removed
//        ArrayList<String> cards = new ArrayList<>();
//        cards.add("User Card 1");
//        cards.add("User Card 2");
//        loadCardList(cards);


        //Default:
        loadPlanOptions();
        loadMeals();
        addCard.setChecked(true);
        clickedAddCard();
    }

    private  void findSpinnerAndLayouts()
    {
        fundOptionsLayout = (LinearLayout) findViewById(R.id.fundOption_Layout);
        ExistCardPScreen_CardLayout = (LinearLayout) findViewById(R.id.ExistCardPScreen_CardLayout);
        mealOptionsLayout = (LinearLayout) findViewById(R.id.mealOptionsLayout);
        planOptionsSpinner = (Spinner) findViewById(R.id.planOptions);
        mealOptionsSpinner = (Spinner) findViewById(R.id.mealOptions);
        cardSpinner = (Spinner) findViewById(R.id.AddPScreen_CardSpinner);
        addPScreen_Layout = (ScrollView) findViewById(R.id.AddPScreen_Layout);
    }

    private void findEditText()
    {
        credCardNum = (EditText) findViewById(R.id.PScreen_CreditCardNum);
        CVC = (EditText) findViewById(R.id.AddPScreen_CVC);
        ExpDate= (EditText) findViewById(R.id.AddPScreen_ExpirationDate);
        FullName= (EditText) findViewById(R.id.PScreen_FullName);
        StreetAddr = (EditText) findViewById(R.id.PScreen_Street);
        City= (EditText) findViewById(R.id.PScreen_City);
        State= (EditText) findViewById(R.id.PScreen_State);
        Zip= (EditText) findViewById(R.id.PScreen_ZipCode);;
        amountText = (EditText) findViewById(R.id.AddPScreen_Amount);
    }

    private void findBtns()
    {
        AddPScreen_Back = (Button) findViewById(R.id.AddPScreen_Back);
        AddPScreen_Back.setOnClickListener(v ->
                clickedBack());{}
        AddPScreen_Submit = (Button) findViewById(R.id.AddPScreen_Submit);
        AddPScreen_Submit.setOnClickListener(v ->
                clickedSubmit());{}
        addCard = (CheckBox) findViewById(R.id.AddPScreen_CheckB0);
        addCard.setOnClickListener(v ->
                clickedAddCard());{}
        existCard = (CheckBox) findViewById(R.id.AddPScreen_CheckB1);
        existCard.setOnClickListener(v ->
                clickedAddExistCard());{}

        planOptionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clickedPlanOption = position;
                if(position == 0) //Meal Plans
                {
                    System.out.println("Amount doesn't text shows!");
                    amountText.setVisibility(View.GONE);
                    mealOptionsSpinner.setVisibility(View.VISIBLE);
                }
                else //Everything else
                {
                    System.out.println("Amount text shows!");
                    amountText.setVisibility(View.VISIBLE);
                    mealOptionsSpinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void clickedBack() {
        Intent intent=new Intent(AddPaymentFromPaymentScreen.this, PaymentHomeScreen.class);
        intent.putExtra("balances", balances);
        startActivity(intent);
    }

    private void clickedSubmit() {
        if (existCard.isChecked() ) {
            modifyBalances();
        } else {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "https://eaglewallet.wise-net.xyz/api/Card/create";
            JSONObject jsonBody = new JSONObject();

            findEditText();

            String cardNum = credCardNum.getText().toString();
            String cvc = CVC.getText().toString();
            String expDate = ExpDate.getText().toString();
            String fullName = FullName.getText().toString();
            String street = StreetAddr.getText().toString();
            String city = City.getText().toString();
            String state = State.getText().toString();
            String zip = Zip.getText().toString();

            Log.i("ID", String.valueOf(userTransactions.get(0).getUserId()));

            try {
                jsonBody.put("cardId", 0);
                jsonBody.put("userId", userTransactions.get(0).getUserId());
                jsonBody.put("cardNumber", cardNum);
                jsonBody.put("cvc", cvc);
                jsonBody.put("expirationDate", expDate);
                jsonBody.put("fullName", fullName);
                jsonBody.put("streetAddress", street);
                jsonBody.put("city", city);
                jsonBody.put("state", state);
                jsonBody.put("zipCode", zip);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest req = new JsonObjectRequest(url, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("VOLLEY", response.toString());

                    modifyBalances();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

            };

            queue.add(req);
        }

        if(addCard.isChecked())
        {
            //Add the card to database
            fundsSelectedForSubmit();//Find the selected plan
        }
        else if (existCard.isChecked())
        {
            getSelectedCard(); //Gets the selected card
            fundsSelectedForSubmit();//Find the selected plan
        }

    }

    private void modifyBalances() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://eaglewallet.wise-net.xyz/api/Transaction/modifyBalances";
        JSONObject jsonBody = new JSONObject();

        amount = Double.parseDouble(amountText.getText().toString());

        String jsonString = "";

        if (clickedPlanOption == 1) {
            jsonString = "sodexoBucks";
        } else if (clickedPlanOption == 2) {
            jsonString = "diningDollars";
        } else if (clickedPlanOption == 3) {
            jsonString = "eagleDollars";
        } else if (clickedPlanOption == 0) {
            jsonString = "mealPlans";
        }

        try {
            jsonBody.put("userId", userTransactions.get(0).getUserId());
            jsonBody.put(jsonString, amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest(url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("VOLLEY", response.toString());

                getBalances();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };

        queue.add(req);
    }

    private void getBalances() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://eaglewallet.wise-net.xyz/api/Transaction/balances/" + userTransactions.get(0).getUserId();

        JsonObjectRequest req = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("VOLLEY", response.toString());
                balances = new Balances();

                try {
                    balances.setSodexoBucks(response.getDouble("sodexoBucks"));
                    balances.setDiningDollars(response.getDouble("diningDollars"));
                    balances.setEagleDollars(response.getDouble("eagleDollars"));
                    balances.setMealPlans(response.getInt("mealPlans"));

                    Intent intent=new Intent(AddPaymentFromPaymentScreen.this, HomeScreen.class);
                    intent.putExtra("balances", (Serializable) balances);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };

        queue.add(req);
    }

    private int getSelectedCard()
    {
        return cardSpinner.getSelectedItemPosition();
    }

    private void fundsSelectedForSubmit()
    {
        if(clickedPlanOption == 0) //Meal Plans
        {
            getMealPlanSelected(); //gets the meal plan index selected
        }
        else if(clickedPlanOption == 1)//Sodexo
        {

        }
        else if(clickedPlanOption == 2) //Dinning
        {

        }
        else if (clickedPlanOption == 3) //Eagle
        {

        }
    }

    private int getMealPlanSelected()
    {
        return mealOptionsSpinner.getSelectedItemPosition();
    }

    private void clickedAddCard()
    {
        //addCard.setChecked(addCardCondition = addCardCondition? true:false);
        if(addCard.isChecked())
        {
            addPScreen_Layout.setVisibility(View.VISIBLE);
            ExistCardPScreen_CardLayout.setVisibility(View.GONE);
            fundOptionsLayout.setVisibility(View.VISIBLE);
            fundOptionsLayout.setTranslationY(0);
            existCard.setChecked(false);
        }
        else
        {
            addPScreen_Layout.setVisibility(View.GONE);
            fundOptionsLayout.setVisibility(View.GONE);
        }
    }

    private  void clickedAddExistCard()
    {
        //existCard.setChecked(existCardCondition= existCardCondition? true:false);
        if(existCard.isChecked())
        {
            addPScreen_Layout.setVisibility(View.GONE);
            ExistCardPScreen_CardLayout.setVisibility(View.VISIBLE);
            fundOptionsLayout.setVisibility(View.VISIBLE);
            fundOptionsLayout.setTranslationY(-800);
            addCard.setChecked(false);
        }
        else
        {
            ExistCardPScreen_CardLayout.setVisibility(View.GONE);
           fundOptionsLayout.setVisibility(View.GONE);
        }
    }

//    private void clickedAddGPay()
//    {
//        //gPayCard.setChecked(gpayCardConditon = gpayCardConditon ? true:false);
//        if(gPayCard.isChecked())
//        {
//            existCard.setChecked(false);
//            addCard.setChecked(false);
//            addPScreen_Layout.setVisibility(View.GONE);
//            ExistCardPScreen_CardLayout.setVisibility(View.GONE);
//            fundOptionsLayout.setVisibility(View.VISIBLE);
//            fundOptionsLayout.setTranslationY(-700);
//        }
//        else
//        {
//            fundOptionsLayout.setVisibility(View.GONE);
//        }
//    }

    //Load list of cards from the database
    private  void loadCardList(ArrayList<String> cards)
    {
        for(String currentCard : cards)
        {
            cardList.add(currentCard);
        }
        addToSpinner(cardSpinner,  cardList);
    }

    private  void loadPlanOptions()
    {
        ArrayList<String> planOptions = new ArrayList<String>();
        planOptions.add("Meal Plans");
        planOptions.add("Sodexo");
        planOptions.add("Dinning Dollars");
        planOptions.add("Eagle Dollars");
        addToSpinner(planOptionsSpinner,  planOptions);
    }

    private  void loadMeals()
    {
        ArrayList<String> planOptions = new ArrayList<String>();
        planOptions.add("7");
        planOptions.add("14");
        planOptions.add("21");
        addToSpinner(mealOptionsSpinner,  planOptions);
    }

    private  void addToSpinner(Spinner spinner, ArrayList<String> arrayList)
    {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_dropwdown_item, arrayList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropwdown_item);
        spinner.setAdapter(arrayAdapter);
    }

}