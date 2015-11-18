package com.akkaratanapat.altear.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

    Button loginBtn, registerBtn;
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
                userName = (EditText) findViewById(R.id.user);
                password = (EditText) findViewById(R.id.pwd);
                if(userName.getText().toString().length() == 0||password.getText().toString().length() == 0){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(FirstActivity.this);
                    dialog.setTitle("WTF");
                    dialog.setIcon(R.drawable.ic_close_black_48dp);
                    dialog.setCancelable(true);
                    dialog.setMessage("Pls input username and password");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();
                }
                else{
                    responseJsonFromWeb(userName.getText().toString(),password.getText().toString());
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PressInfo.class);
                startActivity(i);
            }
        });
    }

    public void responseJsonFromWeb(String name, String pass) {
        String url = "http://203.151.92.184:8080/login/"+name+"/"+pass;
        final ProgressDialog dia = ProgressDialog.show(FirstActivity.this, null,
                "Loading");
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject resultObjectJSON = response.getJSONObject("resultObject");
                            String responText = resultObjectJSON.getString("response");
                            //Toast.makeText(getApplicationContext(), resultObjectJSON.getString("response"), Toast.LENGTH_SHORT).show();
                            if (responText.equals("true")) {
                                dia.dismiss();
                                Toast.makeText(getApplicationContext(), resultObjectJSON.getString("alias") + " has logged in", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getBaseContext(), MainActivity.class);
                                i.putExtra("User",new User(resultObjectJSON.getString("alias"),resultObjectJSON.getString("email"),resultObjectJSON.getString("userid")));
                                startActivity(i);
                            } else{
                                dia.dismiss();
                                Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dia.dismiss();
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue myrequestQueue = Volley.newRequestQueue(this);
        myrequestQueue.add(jsObjRequest);
    }
}
