package com.example.management;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final EditText ageText = (EditText) findViewById(R.id.ageText);
        final Button registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userName = nameText.getText().toString();
                int userAge = Integer.parseInt(ageText.getText().toString());

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Member registration was successful.")
                                            .setPositiveButton("OK", null)
                                            .create()
                                            .show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    RegisterActivity.this.startActivity(intent);
                                }
                                else
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Member registration failed.")
                                            .setNegativeButton("One more", null)
                                            .create()
                                            .show();
                                }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userAge, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
