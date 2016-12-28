package com.example.kelly.bubbly;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kelly on 12/4/2016.
 */
public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://bubbly.comeze.com/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String first, String last, String email, String password, int type, String barName, String barSpecial, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("FirstName", first);
        params.put("LastName", last);
        params.put("Email", email);
        params.put("Password", password);
        params.put("Type", type + "");
        params.put("barName", barName);
        params.put("barSpecial", barSpecial);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
