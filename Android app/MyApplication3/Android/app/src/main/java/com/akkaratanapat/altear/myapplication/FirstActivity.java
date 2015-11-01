package com.akkaratanapat.altear.myapplication;

import android.app.Dialog;
import android.content.Intent;
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

public class FirstActivity extends AppCompatActivity {

    Button loginBtn, registerBtn, acceptBtn, deniedBtn, submitBtn, cancleBtn;
    Dialog dialog;
    EditText userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        setCustomComponent();
    }

    public void setCustomComponent() {
        loginBtn = (Button) findViewById(R.id.button);
        registerBtn = (Button) findViewById(R.id.button2);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(FirstActivity.this);
                dialog.setContentView(R.layout.activity_login);

                submitBtn = (Button) dialog.findViewById(R.id.submit_btn);
                cancleBtn = (Button) dialog.findViewById(R.id.cancle_btn);
                userName = (EditText) dialog.findViewById(R.id.editText);
                password = (EditText) dialog.findViewById(R.id.editText2);

                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        responseJsonFromWeb(userName.getText().toString(), password.getText().toString());
                        dialog.cancel();
                    }
                });

                cancleBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.setCancelable(true);
                dialog.setTitle("Register");
                dialog.show();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(FirstActivity.this);
                dialog.setContentView(R.layout.activity_permission);
                acceptBtn = (Button) dialog.findViewById(R.id.acceptBtn);
                deniedBtn = (Button) dialog.findViewById(R.id.deniedBtn);

                acceptBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), PressInfo.class);
                        startActivity(i);
                        dialog.cancel();
                    }
                });
                deniedBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.setCancelable(true);
                dialog.setTitle("Register");
                dialog.show();
            }
        });
    }

    public void responseJsonFromWeb(String name, String pass) {
        //Toast.makeText(getBaseContext(),"K",Toast.LENGTH_SHORT).show();
        String url = "http://mol100.esy.es/ooad/lin?user=" + name + "&pass=" + pass;
        //String url = "http://mol100.esy.es/ooad/lin?user=admin&pass=0020";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //    StringJson
//                          try{
//                              Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
//                             String resultStringJson = response.getString("");
//                          } catch (JSONException e) {
//                             e.printStackTrace();
//                          }
                        //      JsonObject
                        try {
                            //Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                            JSONObject resultObjectJSON = response.getJSONObject("resultObject");
                            String responText = resultObjectJSON.getString("res");
                            if (responText.equals("true")) {
                                Toast.makeText(getApplicationContext(), resultObjectJSON.getString("name") + " has logged in", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getBaseContext(), MainActivity.class);
                                i.putExtra("Name", resultObjectJSON.getString("name"));
                                i.putExtra("Email", resultObjectJSON.getString("email"));
                                i.putExtra("ID", resultObjectJSON.getString("id"));

                                startActivity(i);
                            } else
                                Toast.makeText(getApplicationContext(), responText, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //JsonArray
//                        try{
//                            Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
//                            JSONArray resultArrayJSON = response.getJSONArray("resultArray");
//                            //int sizeArray = resultArrayJSON.length();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
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
