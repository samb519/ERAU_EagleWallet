package com.example.eaglewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    AlertDialog.Builder builder;
    //ProgressBar pgsBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        builder = new AlertDialog.Builder(this);

        //pgsBar = (ProgressBar)findViewById(R.id.pBarCreateAccount);
       //pgsBar.setVisibility(View.GONE);

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
//        pgsBar.setVisibility(View.VISIBLE);
//        final String[] code = {""};
//
//        EditText firstNameText = (EditText) findViewById(R.id.editTextTextPersonFirstName);
//        String firstName = firstNameText.getText().toString();
//
//        EditText lastNameText = (EditText) findViewById(R.id.editTextTextPersonLastName);
//        String lastName = lastNameText.getText().toString();
//
//        EditText studentIDText = (EditText) findViewById(R.id.editStudentID);
//        int studentID = Integer.parseInt(studentIDText.getText().toString());
//
//        EditText passwordText = (EditText) findViewById(R.id.editTextTextPassword2);
//        String password = passwordText.getText().toString();
//
//        EditText emailText = (EditText) findViewById(R.id.editTextTextEmailAddress2);
//        String email = emailText.getText().toString();
//
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        String url ="https://eaglewallet.wise-net.xyz/api/Auth/register";
//        JSONObject jsonBody = new JSONObject();
//        try {
//            jsonBody.put("studentID", studentID);
//            jsonBody.put("firstName", firstName);
//            jsonBody.put("lastName", lastName);
//            jsonBody.put("password", password);
//            jsonBody.put("email", email);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest req = new JsonObjectRequest(url, jsonBody, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.i("VOLLEY", response.toString());
//
//                try {
//                    String id = response.getString("id");
//                    String email = response.getString("email");
//
//                    if (!id.isEmpty()) {
//                        editor.putString("issignedin", "true");
//                        editor.putString("userid", id);
//                        editor.putString("email", email);
//
                        Intent intent = new Intent(CreateAccount.this, loginPage.class );
                        startActivity(intent);
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("VOLLEY", error.toString());
//                alert(error.toString());
//            }
//        }) {
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//        };
//
//        queue.add(req);
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
                        //pgsBar.setVisibility(View.GONE);
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Error Creating Account");
        alert.show();
    }
}