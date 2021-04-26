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
import com.android.volley.toolbox.Volley;

public class loginPage extends AppCompatActivity {

    Button loginButton;
    TextView CreateAccountTxt;
    SharedPreferences userDetails;
    SharedPreferences.Editor editor;
    String is_signed_in = "";
    ProgressBar pgsBar;
    AlertDialog.Builder builder;

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
                //postUserLogin();
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
                    String id = response.getString("id");
                    String email = response.getString("email");

                    if (!id.isEmpty()) {
                        editor.putString("issignedin", "true");
                        editor.putString("userid", id);
                        editor.putString("email", email);

                        Intent intent = new Intent(loginPage.this, HomeScreen.class);
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
}