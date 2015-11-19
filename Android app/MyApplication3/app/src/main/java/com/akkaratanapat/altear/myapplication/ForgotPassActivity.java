package com.akkaratanapat.altear.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Parnmet on 11/19/2015.
 */
public class ForgotPassActivity extends AppCompatActivity {

    Button submitEmail;
    EditText emailForgot;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setCustomComponent();
    }

    public void setCustomComponent() {
        submitEmail = (Button) findViewById(R.id.buttonForgot);
        emailForgot = (EditText) findViewById(R.id.email);

        submitEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseJsonFromWeb(emailForgot.getText().toString());
                finish();
            }
        });
    }

    public void responseJsonFromWeb(String email) {
        String url = "http://203.151.92.184:8080/forgot/" + email;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //    StringJson
                        try {
                            JSONObject resultObjectJSON = response.getJSONObject("resultObject");
                            String resultStringJson = resultObjectJSON.getString("response");
                            Toast.makeText(getApplicationContext(), resultStringJson, Toast.LENGTH_SHORT).show();
                            if (resultStringJson.equals("true")) {
                                Toast.makeText(getApplicationContext(), "Success : " + response.getString("status"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error : " + response.getString("status"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue myrequestQueue = Volley.newRequestQueue(this);
        myrequestQueue.add(jsObjRequest);
    }
}