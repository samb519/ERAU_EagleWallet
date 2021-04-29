package com.example.eaglewallet;

import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
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

public class StorageClass {

    private static final StorageClass instance = new StorageClass();
    public static final StorageClass getInstance()
    {
        return instance;
    }

    public Balances balances;
    public ArrayList<Card> cards;
    public ArrayList<Transaction> transactions;

    public int userId;

    public StorageClass(){
        balances = new Balances();
        cards = new ArrayList<>();
        transactions = new ArrayList<>();
    }


    public Balances getCurrentBalances(RequestQueue queue)
    {
        String url = "https://eaglewallet.wise-net.xyz/api/transaction/balances/" + userId;
        JSONObject jsonBody = new JSONObject();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("VOLLEY", response.toString());
                balances = new Balances();

                try {
                    balances.setSodexoBucks(response.getDouble("sodexoBucks"));
                    balances.setDiningDollars(response.getDouble("diningDollars"));
                    balances.setEagleDollars(response.getDouble("eagleDollars"));
                    balances.setMealPlans(response.getInt("mealPlans"));
                    if(balances.getMealPlans() < 0)
                        balances.setMealPlans(0);
                    StorageClass.getInstance().balances = balances;
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

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return balances;
    }


    public ArrayList<Transaction> getTransactionHistory(RequestQueue queue){
        String url = "https://eaglewallet.wise-net.xyz/api/Transaction/history/" + userId;

        JsonArrayRequest req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("VOLLEY", response.toString());
                transactions = new ArrayList<Transaction>();

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Transaction trans = new Transaction();

                        trans.setDate(jsonObject.getString("transactionDate"));
                        trans.setDiningDollars(jsonObject.getDouble("diningDollars"));
                        trans.setEagleDollars(jsonObject.getDouble("eagleDollars"));
                        trans.setMealPlans(jsonObject.getDouble("mealPlans"));
                        trans.setSodexoBucks(jsonObject.getDouble("sodexoBucks"));
                        trans.setTransId(jsonObject.getInt("transactionId"));
                        trans.setUserId(jsonObject.getInt("userId"));

                        transactions.add(trans);
                    }

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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return transactions;
    }
}
