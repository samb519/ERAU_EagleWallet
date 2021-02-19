package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateAccount extends AppCompatActivity {

    Button createAccountBackBtn;
    Button CreatAccountNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        createAccountBackBtn = (Button)findViewById(R.id.createAccountBackBtn);
        createAccountBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccount.this, loginPage.class );
                startActivity(intent);
            }
        });

        CreatAccountNext = (Button)findViewById(R.id.CreatAccountNext);
        CreatAccountNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccount.this, ChoosePayment.class );
                startActivity(intent);
            }
        });

    }
}