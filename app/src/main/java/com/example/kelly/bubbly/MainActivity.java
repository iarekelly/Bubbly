package com.example.kelly.bubbly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    EditText emailEt;
    EditText pwEt;
    Button regBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        emailEt = (EditText) findViewById(R.id.emailEt);
        pwEt = (EditText) findViewById(R.id.pwEt);
        regBtn = (Button) findViewById(R.id.regBtn);
        super.onCreate(savedInstanceState);
    }

    public void onRegClick(View view) {
        Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
        MainActivity.this.startActivity(registerIntent);
    }

    public void onLoginClick(View view){
        final String email = emailEt.getText().toString();
        final String pw = pwEt.getText().toString();
        Response.Listener<String> responseListener = new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    Boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        int userType = jsonResponse.getInt("Type");
                        if(userType == 0){
                            Intent genUserIntent = new Intent(MainActivity.this, GeneralUserHome.class);
                            Bundle b = new Bundle();
                            b.putString("email", email);
                            genUserIntent.putExtras(b);
                            MainActivity.this.startActivity(genUserIntent);
                        }else{
                            Intent barManIntent = new Intent(MainActivity.this, BarManagerHome.class);
                            Bundle b2 = new Bundle();
                            b2.putString("email", email);
                            barManIntent.putExtras(b2);
                            MainActivity.this.startActivity(barManIntent);
                        }
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Login Failed").setNegativeButton("Retry", null).create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        LoginRequest loginRequest = new LoginRequest(email, pw, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(loginRequest);
    }
}
