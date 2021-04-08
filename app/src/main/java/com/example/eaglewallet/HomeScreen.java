package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    ImageButton calenderPScreenBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        calenderPScreenBtn = (ImageButton) findViewById(R.id.calenderHScreenBtn);
        calenderPScreenBtn .setOnClickListener(v ->
                clickedCalenderBtn( "https://eraudining.sodexomyway.com/dining-near-me/hours"));{ }
    }

    public void clickedCalenderBtn(String url){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);

    }
}