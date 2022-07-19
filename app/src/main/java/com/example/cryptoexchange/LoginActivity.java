package com.example.cryptoexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText username, email;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        btnlogin = findViewById(R.id.btnlogin);


        btnlogin.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                String name = username.getText().toString();
                String pass = email.getText().toString();

                JSONParse(name,pass);


            }
        });
    }

    private void JSONParse(String name, String emailid) {

        String url = "https://jsonplaceholder.typicode.com/users";

        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            /* JSONArray jsonArray = response.getJSONObject("employees");*/

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                String sname = jsonObject.getString("username");
                                String semail = jsonObject.getString("email");
/*
                                System.out.println(sname + " "  + semail);
*/

                                if (sname.equals(name) && semail.equals(emailid)) {


                                    final Toast toast = Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT);
                                    toast.show();

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            toast.cancel();
                                        }
                                    }, 500);
                                    Intent iNext = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(iNext);



                                } /*else
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
*/
                                if (!sname.equals(name) && !semail.equals(emailid)) {


                                    final Toast toast = Toast.makeText(getApplicationContext(), "Unable to login", Toast.LENGTH_SHORT);
                                    toast.show();

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            toast.cancel();
                                        }
                                    }, 500);

                                }

                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(request);
    }
}