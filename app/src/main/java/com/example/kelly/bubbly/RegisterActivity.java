package com.example.kelly.bubbly;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    EditText emailEt;
    EditText pwEt;
    Button regBtn;
    EditText firstEt;
    EditText lastEt;
    CheckBox check;
    EditText barNameEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailEt = (EditText) findViewById(R.id.emailEt);
        pwEt = (EditText) findViewById(R.id.pwEt);
        regBtn = (Button) findViewById(R.id.regBtn);
        firstEt = (EditText) findViewById(R.id.firstEt);
        lastEt = (EditText) findViewById(R.id.lastEt);
        check = (CheckBox) findViewById(R.id.checkBox);
        barNameEt = (EditText) findViewById(R.id.barNameEt);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                 barNameEt.setVisibility(View.VISIBLE);
                                                 barNameEt.setEnabled(true);
                                             }
        }
        );
    }

    public void regClick(View view){
        final String FirstName = firstEt.getText().toString();
        final String LastName = lastEt.getText().toString();
        final String Email = emailEt.getText().toString();
        final String Password = pwEt.getText().toString();
        final String barName = barNameEt.getText().toString();
        final int userType;
        if(check.isChecked() == true){
            userType = 1; //1 is bar manager type
        }else{
            userType = 0; //0 is general user type
        }
        Response.Listener<String> responseListener = new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        RegisterActivity.this.startActivity(intent);
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("Register Failed").setNegativeButton("Retry", null).create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        if(userType == 0) {
            RegisterRequest registerRequest = new RegisterRequest(FirstName, LastName, Email, Password, userType, "", "", responseListener);
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
            queue.add(registerRequest);
        }else{
            RegisterRequest registerRequest = new RegisterRequest(FirstName, LastName, Email, Password, userType, barName, "", responseListener);
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
            queue.add(registerRequest);
        }
    }
}
