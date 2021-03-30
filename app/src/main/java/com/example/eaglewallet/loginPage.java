package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;

public class loginPage extends AppCompatActivity {

    Button loginButton;
    TextView CreateAccountTxt;
    SharedPreferences userDetails;
    SharedPreferences.Editor editor;
    String is_signed_in = "";
    TextView warningText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        warningText = (TextView) findViewById((R.id.loginWarningText));
        warningText.setText("Username/Password is incorrect");
        displayWarning(false);
        userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
        editor = userDetails.edit();

        is_signed_in = userDetails.getString("issignedin", "false");

//        if (is_signed_in.equals("true")) {
//            Intent intent = new Intent(loginPage.this, homePage.class);
//            startActivity(intent);
//        }

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                postUserLogin();

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

    private  void displayWarning(boolean condition)
    {
        if(condition)
        {
            warningText.setVisibility(View.VISIBLE);
        }
        else
        {
            warningText.setVisibility(View.INVISIBLE);
        }
    }

    private void postUserLogin() {

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

                        Intent intent = new Intent(loginPage.this, homePage.class);
                        displayWarning(false);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    displayWarning(true);
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

//            @Override
//           public Response<JSONObject> parseNetworkResponse(NetworkResponse response)
//           {
//                try {
//                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//                    JSONObject obj = new JSONObject(jsonString);
//                    return Response.success(obj, HttpHeaderParser.parseCacheHeaders(response));
//                }
//               catch (UnsupportedEncodingException e)
//               {
//                  return Response.error(new ParseError(e));
//               }
//               catch (JSONException je)
//               {
//                   return Response.error(new ParseError(je));
//               }
//           }
       };

        queue.add(req);
    }

}