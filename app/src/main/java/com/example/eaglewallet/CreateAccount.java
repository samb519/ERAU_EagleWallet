package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class CreateAccount extends AppCompatActivity {

    Button createAccountBackBtn;
    Button CreatAccountNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

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
                String code = postUserRegistration();

                if (code == "200") {
                    Intent intent = new Intent(CreateAccount.this, ChoosePayment.class );
                    startActivity(intent);
                } else if (code == "401") {

                }
            }
        });

    }

    private String postUserRegistration() {
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
        return code[0];
    }
}