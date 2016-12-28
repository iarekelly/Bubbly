package com.example.kelly.bubbly;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kelly on 12/10/2016.
 */

public class LoadCurrentSpecials extends StringRequest {
    private static final String VIEW_REQUEST_URL = "http://bubbly.comeze.com/Load.php";
    private Map<String, String> params;

    public LoadCurrentSpecials(Response.Listener<String> listener){
        super(Method.POST, VIEW_REQUEST_URL, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

