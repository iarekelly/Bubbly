package com.example.kelly.bubbly;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kelly on 12/9/2016.
 */
public class UpdateSpecial extends StringRequest {
    private static final String UPDATE_REQUEST_URL = "http://bubbly.comeze.com/Update.php";
    private Map<String, String> params;

    public UpdateSpecial(String email, String barSpecial, Response.Listener<String> listener){
        super(Method.POST, UPDATE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Email", email);
        params.put("barSpecial", barSpecial);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}