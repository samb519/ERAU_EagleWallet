package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eaglewallet.models.Balances;

import java.io.Serializable;

public class loginPage extends AppCompatActivity {

    Button loginButton;
    TextView CreateAccountTxt;
    SharedPreferences userDetails;
    SharedPreferences.Editor editor;
    String is_signed_in = "";
    ProgressBar pgsBar;
    AlertDialog.Builder builder;
    static String id;
    String userEmail;
    Balances balances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        builder = new AlertDialog.Builder(this);

        userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
        editor = userDetails.edit();

        is_signed_in = userDetails.getString("issignedin", "false");

        pgsBar = (ProgressBar)findViewById(R.id.pBarHScreen);
        pgsBar.setVisibility(View.GONE);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                postUserLogin();

                pgsBar.setVisibility(View.GONE);
            }
        });

        CreateAccountTxt = (TextView) findViewById(R.id.CreateAccountTxt);
        CreateAccountTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(loginPage.this, CreateAccount.class);
                startActivity(intent);
            }
        });
    }

    private void postUserLogin() {
        pgsBar.setVisibility(View.VISIBLE);
        EditText emailText = (EditText) findViewById(R.id.editTextTextEmailAddress);
        String email = emailText.getText().toString();

        EditText passwordText = (EditText) findViewById(R.id.editTextTextPassword);
        String password = passwordText.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://eaglewallet.wise-net.xyz/api/Auth/login";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest req = new JsonObjectRequest(url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("VOLLEY", response.toString());

                try {
                    id = response.getString("id");
                    userEmail = response.getString("email");

                    if (!id.isEmpty()) {
                        editor.putString("issignedin", "true");
                        editor.putString("userid", id);
                        editor.putString("email", email);

                        Log.i("ID", id);
                        getAccountBalances(id);

                        Intent intent = new Intent(loginPage.this, HomeScreen.class);
                        intent.putExtra("id", id);
                        intent.putExtra("Balances", (Serializable) balances);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                alert(error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

       };

        queue.add(req);
    }

    public void getAccountBalances(String id) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://eaglewallet.wise-net.xyz/api/Transaction/balances/" + id;

        JsonObjectRequest req = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("VOLLEY", response.toString());

                balances = new Balances();

                try {
                    balances.setSodexoBucks(response.getDouble("sodexoBucks"));
                    balances.setDiningDollars(response.getDouble("diningDollars"));
                    balances.setEagleDollars(response.getDouble("eagleDollars"));
                    balances.setMealPlans(response.getInt("mealPlans"));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                alert(error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };

        queue.add(req);
    }

    private  void alert(String error)
    {
        builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage("Error: " + error)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        pgsBar.setVisibility(View.GONE);
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Error Logging In");
        alert.show();
    }

    public static String getId() {
        return id;
    }
}