package com.example.kelly.bubbly;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kelly on 12/4/2016.
 */
public class LoginRequest extends StringRequest{
        private static final String Login_REQUEST_URL = "http://bubbly.comeze.com/login.php";
        private Map<String, String> params;

        public LoginRequest(String email, String password, Response.Listener<String> listener){
            super(Request.Method.POST, Login_REQUEST_URL, listener, null);
            params = new HashMap<>();
            params.put("Email", email);
            params.put("Password", password);
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
}
