package com.example.eaglewallet.ui.payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.eaglewallet.CreateAccount;
import com.example.eaglewallet.CreateAccountAddPayment;
import com.example.eaglewallet.R;
import com.example.eaglewallet.homePage;
import com.example.eaglewallet.loginPage;

public class PaymentFragment extends Fragment{

    private PaymentViewModel paymentViewModel;

    Button addFunds;
    Button addCard;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        addFunds = (Button)getView().findViewById(R.id.button_addFunds);
        addFunds.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentFragment.this.getContext(), CreateAccountAddPayment.class);
                startActivity(intent);

            }
        }));



        paymentViewModel =
                new ViewModelProvider(this).get(PaymentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_payment, container, false);
        return root;
    }
}