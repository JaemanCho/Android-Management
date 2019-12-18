package com.example.management;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://10.0.2.2/Register.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userName, int userAge, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();

            }
        });
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userAge", userAge + "");
    }

    // 送信する時にパラメータをこのメソッドから取得する。
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
