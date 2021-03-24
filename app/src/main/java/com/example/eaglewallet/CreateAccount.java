package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class CreateAccount extends AppCompatActivity {

    Button createAccountBackBtn;
    Button CreatAccountNext;
    SharedPreferences regDetails;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        regDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
        editor = regDetails.edit();

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
                postUserRegistration();

            }
        });

    }

    private void postUserRegistration() {
        final String[] code = {""};

        EditText usernameText = (EditText) findViewById(R.id.editStudentID);
        String username = usernameText.getText().toString();

        EditText passwordText = (EditText) findViewById(R.id.editTextTextPassword2);
        String password = passwordText.getText().toString();

        EditText emailText = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        String email = emailText.getText().toString();

        Log.i("USERNAME", username);
        Log.i("PASSWORD", password);
        Log.i("EMAIL", email);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://eaglewallet.wise-net.xyz/api/Auth/register";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password", password);
            jsonBody.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest(url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("VOLLEY", response.toString());

                try {
                    String id = response.getString("id");
                    String username = response.getString("username");
                    String email = response.getString("email");

                    if (!id.isEmpty()) {
                        editor.putString("issignedin", "true");
                        editor.putString("userid", id);
                        editor.putString("username", username);
                        editor.putString("email", email);

                        Intent intent = new Intent(CreateAccount.this, ChoosePayment.class );
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
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };

        queue.add(req);
    }
}