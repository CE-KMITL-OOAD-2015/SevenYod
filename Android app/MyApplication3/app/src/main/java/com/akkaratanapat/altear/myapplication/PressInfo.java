package com.akkaratanapat.altear.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

public class PressInfo extends AppCompatActivity {

    EditText userText, userNameText, passText, emailText;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_press_info);
        setCustomButton();

    }

    public void setCustomButton() {
        userText = (EditText) findViewById(R.id.user);
        userNameText = (EditText) findViewById(R.id.userName);
        passText = (EditText) findViewById(R.id.pwd);
        emailText = (EditText) findViewById(R.id.email);
        submitBtn = (Button) findViewById(R.id.btnReg);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseJsonFromWeb(userText.getText().toString(), userNameText.getText().toString(), passText.getText().toString(), emailText.getText().toString());
                finish();
            }
        });

    }

    public void responseJsonFromWeb(String user, String name, String pass, String email) {
        String url = "http://203.151.92.184:8080/regis/" + email + "/" + user + "/" + name + "/" + pass;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_press_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
