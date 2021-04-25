package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    Button SettingsSubmit, SettingsBack;
    EditText name,lastname,email;
    TextView studentId;
    ArrayList<String> cardList = new ArrayList<>();
    Spinner cardSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        cardSpinner = (Spinner) findViewById(R.id.removeList);

//        //Testing - Will need to be removed
        ArrayList<String> cards = new ArrayList<>();
        cards.add("User Card 1");
        cards.add("User Card 2");
        loadCardList(cards);

        studentId = (TextView) findViewById(R.id.studentId_text);
        name = (EditText) findViewById(R.id.editFirstNameSettings);
        lastname = (EditText) findViewById(R.id.editLastNameSettings);
        email = (EditText) findViewById(R.id.editEmailSettings);

        SettingsSubmit = (Button) findViewById(R.id.SettingsSubmit);
        SettingsSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send the data to the databse
                Intent intent = new Intent(SettingsActivity.this, HomeScreen.class);
                startActivity(intent);

                if(isCardSelected() == true)
                {
                    //It is -1 because None is position 0. I assume you don't have none in your database; therefore, to match it I -1
                    int cardRemove = getSelectedCard()-1;
                }

            }
        });

        SettingsBack = (Button) findViewById(R.id.SettingsBack);
        SettingsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, HomeScreen.class);
                startActivity(intent);
            }
        });
    }

    //Call these methods to display the information
    private  void setId(String name)
    {
        studentId.setText(name);
    }

    private void setFirstName(String name)
    {
        this.name.setHint(name);
    }

    private  void setLastName(String name)
    {
        lastname.setHint(name);
    }

    private void setEmail(String name)
    {
        email.setHint(name);
    }

    //Call these methods to add to the database upon submit
    private  String getFirstName()
    {
        return name.getText().toString();
    }

    private  String getLastName()
    {
        return  lastname.getText().toString();
    }

    private  String getEmail()
    {
        return  email.getText().toString();
    }

    //Load list of cards from the database
    private  void loadCardList(ArrayList<String> cards)
    {
        cardList.add("None");
        for(String currentCard : cards)
        {
            cardList.add(currentCard);
        }
        addToSpinner(cardSpinner,  cardList);
    }

    private  void addToSpinner(Spinner spinner, ArrayList<String> arrayList)
    {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_dropwdown_item, arrayList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropwdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    private  int getSelectedCard()
    {
        return cardSpinner.getSelectedItemPosition();
    }

    private  boolean isCardSelected()
    {
        //Matches None
       if(getSelectedCard() == 0)
       {
           return false;
       }
           return true;
    }
}