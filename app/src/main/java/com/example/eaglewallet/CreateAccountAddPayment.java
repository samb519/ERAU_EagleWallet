package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateAccountAddPayment extends AppCompatActivity {

    Button createAccountAddPaymentBtn;
    Button  createAccountAddPaymentBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_add_payment);


        createAccountAddPaymentBtn = (Button) findViewById(R.id.createAccountAddPaymentBtn);
        createAccountAddPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountAddPayment.this, loginPage.class);
                startActivity(intent);
            }
        });

        createAccountAddPaymentBackBtn = (Button) findViewById(R.id.createAccountAddPaymentBackBtn);
        createAccountAddPaymentBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountAddPayment.this, CreateAccount.class);
                startActivity(intent);
            }
        });
    }
}