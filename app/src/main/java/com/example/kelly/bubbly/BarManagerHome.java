package com.example.kelly.bubbly;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class BarManagerHome extends AppCompatActivity {
    Button alertBtn;
    Button clearBtn;
    EditText specialEt;
    TextView specialTv;
    TextView barTv;
    Bundle b;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_manager_home);
        b = getIntent().getExtras();
        specialTv = (TextView) findViewById(R.id.specialTv);
        alertBtn = (Button) findViewById(R.id.alertBtn);
        clearBtn = (Button) findViewById(R.id.clearBtn);
        specialEt = (EditText) findViewById(R.id.specialEt);
        barTv = (TextView) findViewById(R.id.barTv);
        if (b != null){
            email = b.getString("email");
        }
        showCurrentSpecial(email);
    }

    public void showCurrentSpecial(String email){
        Response.Listener<String> viewListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        String barName = jsonResponse.getString("barName");
                        barTv.setText(barName);
                        String currSpecial = jsonResponse.getString("barSpecial");
                        specialTv.setText(currSpecial);
                    }else{
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ViewData viewData = new ViewData(email, viewListener);
        RequestQueue queue = Volley.newRequestQueue(BarManagerHome.this);
        queue.add(viewData);
    }

    public void alertClick(View view){
        if (b != null){
            email = b.getString("email");
        }
        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        String currSpecial = jsonResponse.getString("barSpecial");
                        specialTv.setText(currSpecial);
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(BarManagerHome.this);
                        builder.setMessage("Alert Failed").setNegativeButton("Retry", null).create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        String special = specialEt.getText().toString();
        UpdateSpecial update = new UpdateSpecial(email, special, responseListener);
        RequestQueue queue = Volley.newRequestQueue(BarManagerHome.this);
        queue.add(update);
    }

    public void clearClick(View view) {
            email = b.getString("email");
        Response.Listener<String> clearListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        String currSpecial = jsonResponse.getString("barSpecial");
                        specialTv.setText(currSpecial);
                    }else{
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ClearSpecial clear = new ClearSpecial(email, clearListener);
        RequestQueue queue = Volley.newRequestQueue(BarManagerHome.this);
        queue.add(clear);
    }
}
