package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;

public class loginPage extends AppCompatActivity {

    Button loginButton;
    TextView CreateAccountTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String code = postUserLogin();

                if (code == "200") {
                    Intent intent = new Intent(loginPage.this,homePage.class);
                    startActivity(intent);
                } else if (code == "401") {
                    String failure = "Some failure message";
                } else { //Realistically only 200 and 401 should occur, everything else is some weird scenario
                    String failure = "We have no clue what happened";
                }
            }
        });

        CreateAccountTxt = (TextView)findViewById(R.id.CreateAccountTxt);
        CreateAccountTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(loginPage.this, CreateAccount.class );
                startActivity(intent);
            }
        });


    }

    private String postUserLogin() {
        final String[] code = {""};

        EditText usernameText = (EditText) findViewById(R.id.editTextTextEmailAddress);
        String username = usernameText.getText().toString();

        EditText passwordText = (EditText) findViewById(R.id.editTextTextPassword);
        String password = passwordText.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://eaglewallet.wise-net.xyz/api/Auth/login";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonBody.toString();

        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
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

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse res) {
                String responseCode = "";
                if (res != null) {
                    responseCode = String.valueOf(res.statusCode);
                    code[0] = responseCode;
                }
                return Response.success(responseCode, HttpHeaderParser.parseCacheHeaders(res));
            }
        };

        queue.add(req);
        //Temp
//        http://arnab.ch/blog/2013/08/asynchronous-http-requests-in-android-using-volley/
        return code[0];
    }

}