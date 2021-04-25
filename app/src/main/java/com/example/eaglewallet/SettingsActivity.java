package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    Button SettingsSubmit, SettingsBack;
    EditText name,lastname,email;
    TextView studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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
}