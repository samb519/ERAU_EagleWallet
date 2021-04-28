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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment_from_payment_screen);
        addCardCondition = existCardCondition = gpayCardConditon = false;
        findEditText();
        findSpinnerAndLayouts();
        findBtns();

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
        startActivity(intent);
    }

    private void clickedSubmit() {
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

        Intent intent=new Intent(AddPaymentFromPaymentScreen.this, HomeScreen.class);
        startActivity(intent);
    }

    private  int getSelectedCard()
    {
        return cardSpinner.getSelectedItemPosition();
    }

    private  void fundsSelectedForSubmit()
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

    private void postUserRegistration() {

        EditText firstNameText = (EditText) findViewById(R.id.editTextTextPersonFirstName);
        String firstName = firstNameText.getText().toString();

        EditText lastNameText = (EditText) findViewById(R.id.editTextTextPersonLastName);
        String lastName = lastNameText.getText().toString();

        EditText studentIDText = (EditText) findViewById(R.id.editStudentID);
        int studentID = Integer.parseInt(studentIDText.getText().toString());

        EditText passwordText = (EditText) findViewById(R.id.editTextTextPassword2);
        String password = passwordText.getText().toString();

        EditText emailText = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        String email = emailText.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://eaglewallet.wise-net.xyz/api/Auth/register";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("studentID", studentID);
            jsonBody.put("firstName", firstName);
            jsonBody.put("lastName", lastName);
            jsonBody.put("password", password);
            jsonBody.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest(url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("VOLLEY", response.toString());

                try {
                    String id = response.getString("id");
                    String email = response.getString("email");

                   // if (!id.isEmpty()) {
                    //    Intent intent = new Intent(CreateAccount.this, loginPage.class );
                     //   startActivity(intent);
                    //}

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
}