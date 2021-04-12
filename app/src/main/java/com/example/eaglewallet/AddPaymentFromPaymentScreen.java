package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class AddPaymentFromPaymentScreen extends AppCompatActivity {

    CheckBox addCard,existCard, gPayCard;
    boolean addCardCondition, existCardCondition, gpayCardConditon;
    ScrollView addPScreen_Layout;
    ArrayList<String> cardList = new ArrayList<>();
    Spinner cardSpinner, planOptionsSpinner, mealOptionsSpinner;
    int clickedPlanOption;
    LinearLayout fundOptionsLayout,mealOptionsLayout,ExistCardPScreen_CardLayout; //within the spinners
    TextView amountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment_from_payment_screen);

        addCardCondition = existCardCondition = gpayCardConditon = false;

        cardSpinner = (Spinner) findViewById(R.id.AddPScreen_CardSpinner);

        //Testing - Will need to be removed
        ArrayList<String> cards = new ArrayList<>();
        cards.add("User Card 1");
        cards.add("User Card 2");
        loadCardToAdds(cards);

        addPScreen_Layout = (ScrollView) findViewById(R.id.AddPScreen_Layout);

        addCard = (CheckBox) findViewById(R.id.AddPScreen_CheckB0);
        addCard.setOnClickListener(v ->
                clickedAddCard());{}

        existCard = (CheckBox) findViewById(R.id.AddPScreen_CheckB1);
        existCard.setOnClickListener(v ->
                clickedAddExistCard());{}


        gPayCard = (CheckBox) findViewById(R.id.AddPScreen_CheckB2);
        gPayCard.setOnClickListener(v ->
                clickedAddGPay());{}

        fundOptionsLayout = (LinearLayout) findViewById(R.id.fundOption_Layout);
        ExistCardPScreen_CardLayout = (LinearLayout) findViewById(R.id.ExistCardPScreen_CardLayout);
        mealOptionsLayout = (LinearLayout) findViewById(R.id.mealOptionsLayout);
        planOptionsSpinner = (Spinner) findViewById(R.id.planOptions);
        mealOptionsSpinner = (Spinner) findViewById(R.id.mealOptions);

        loadPlanOptions();
        loadMeals();
        amountText = (TextView) findViewById(R.id.AddPScreen_Amount);

        planOptionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clickedPlanOption = position;
                if(position == 0) //Meal Plans
                {
                    amountText.setVisibility(View.GONE);
                    mealOptionsSpinner.setVisibility(View.VISIBLE);
                }
                else //Everything else
                {
                    mealOptionsSpinner.setVisibility(View.GONE);
                    amountText.setVisibility(View.VISIBLE);

                }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

        //Default:
        addCard.setChecked(true);
        clickedAddCard();

    }

    private void clickedAddCard()
    {
        //addCard.setChecked(addCardCondition = addCardCondition? true:false);
        if(addCard.isChecked())
        {
            existCard.setChecked(false);
            gPayCard.setChecked(false);
            addPScreen_Layout.setVisibility(View.VISIBLE);
            ExistCardPScreen_CardLayout.setVisibility(View.GONE);
            fundOptionsLayout.setTranslationY(0);
        }
        else
        {
            addPScreen_Layout.setVisibility(View.GONE);
        }
    }

    private  void clickedAddExistCard()
    {
        //existCard.setChecked(existCardCondition= existCardCondition? true:false);
        if(existCard.isChecked())
        {
            addCard.setChecked(false);
            gPayCard.setChecked(false);
            addPScreen_Layout.setVisibility(View.GONE);
            ExistCardPScreen_CardLayout.setVisibility(View.VISIBLE);
            fundOptionsLayout.setTranslationY(-700);
        }
        else
        {
            ExistCardPScreen_CardLayout.setVisibility(View.GONE);
        }
    }

    private void clickedAddGPay()
    {
        //gPayCard.setChecked(gpayCardConditon = gpayCardConditon ? true:false);
        if(gPayCard.isChecked())
        {
            existCard.setChecked(false);
            addCard.setChecked(false);
            addPScreen_Layout.setVisibility(View.GONE);
            ExistCardPScreen_CardLayout.setVisibility(View.GONE);
        }
        else
        {
            fundOptionsLayout.setTranslationY(-700);
        }
    }

    //Load list of cards from the database
    private  void loadCardToAdds(ArrayList<String> cards)
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
        planOptions.add("14");
        planOptions.add("21");
        addToSpinner(mealOptionsSpinner,  planOptions);
    }

    private  void addToSpinner(Spinner spinner, ArrayList<String> arrayList)
    {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }
}