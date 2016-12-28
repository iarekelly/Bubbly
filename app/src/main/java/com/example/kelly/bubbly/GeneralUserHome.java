package com.example.kelly.bubbly;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class GeneralUserHome extends AppCompatActivity {
    TextView tv;
    Bundle b;
    String email;
    TextView nameTv;
    RelativeLayout relLayout;
    LinearLayout linearLayout;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_user_home);
        nameTv = (TextView) findViewById(R.id.nameEt);
        b = getIntent().getExtras();
        tv = (TextView) findViewById(R.id.tv);
        text = (TextView) findViewById(R.id.textView);
        relLayout = (RelativeLayout) findViewById(R.id.relLayout);
        linearLayout = new LinearLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.BELOW, text.getId());
        linearLayout.setLayoutParams(params);
        relLayout.addView(linearLayout);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        tv.setText(dayOfTheWeek + " Specials/Events:");
        if (b != null){
            email = b.getString("email");
        }
     loadCurrentSpecials();
        getAllData(email);
    }
    public void getAllData(String email){
        Response.Listener<String> viewListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        String first = jsonResponse.getString("FirstName");
                        nameTv.setText("Hello, " + first + "!");
                    }else{
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ViewData viewData = new ViewData(email, viewListener);
        RequestQueue queue = Volley.newRequestQueue(GeneralUserHome.this);
        queue.add(viewData);
    }

    public void loadCurrentSpecials(){
        Response.Listener<String> loadListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Iterator x = jsonResponse.keys();
                        JSONArray jsonArray = new JSONArray();
                        while (x.hasNext()) {
                            String key = (String) x.next();
                            jsonArray.put(jsonResponse.get(key));
                        }
                        Button[] b = new Button[(jsonArray.length() - 1)];
                        for (int i = 0; i < jsonArray.length() - 1; i++) {
                            final String barName = jsonResponse.getString("" + i);
                            i++;
                            String barSpecial = jsonResponse.getString("" + i);
                            String special = barName + ":\n" + barSpecial;
                            b[i] = new Button(GeneralUserHome.this);
                            b[i].setText(special);
                            linearLayout.addView(b[i]);
                            b[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Uri gmmIntentUri = Uri.parse("geo:32.075868, -81.093194?q=" + barName);
                                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                    mapIntent.setPackage("com.google.android.apps.maps");
                                    startActivity(mapIntent);
                                }
                            });
                        }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LoadCurrentSpecials loadData = new LoadCurrentSpecials(loadListener);
        RequestQueue queue = Volley.newRequestQueue(GeneralUserHome.this);
        queue.add(loadData);
    }
}
