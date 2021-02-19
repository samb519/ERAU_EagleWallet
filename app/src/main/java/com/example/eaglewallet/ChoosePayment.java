package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ChoosePayment extends AppCompatActivity {

    ImageButton CreditBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_payment);

        CreditBtn = (ImageButton) findViewById(R.id.CreditBtn);
        CreditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoosePayment.this, CreateAccountAddPayment.class);
                startActivity(intent);
            }
        });
    }
}