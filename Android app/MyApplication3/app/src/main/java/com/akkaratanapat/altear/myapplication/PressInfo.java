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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PressInfo extends AppCompatActivity {

    EditText userText,nameText,passText,repassText;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_press_info);
        userText = (EditText)findViewById(R.id.userText);
        nameText = (EditText)findViewById(R.id.nameText);
        passText = (EditText)findViewById(R.id.passText);
        repassText = (EditText)findViewById(R.id.repassText);
        setCustomButton();

    }

    public void setCustomButton(){
        submitBtn = (Button) findViewById(R.id.subBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  //Intent i = new Intent(getApplicationContext(),MainActivity.class);
//                startActivity(i);

                responseJsonFromWeb(userText.getText().toString(),nameText.getText().toString(),passText.getText().toString(),repassText.getText().toString());
                finish();
            }
        });

    }

    public void responseJsonFromWeb(String user,String name,String pass,String repass){
        String url = "http://mol100.esy.es/ooad/reg?user="+user+"&name="+name+"&pass="+pass+"&repass="+repass;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //    StringJson
                          try{
                              Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                             String resultStringJson = response.getString("");
                          } catch (JSONException e) {
                             e.printStackTrace();
                          }
                        //      JsonObject
                        try{
                            Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                            JSONObject resultObjectJSON = response.getJSONObject("resultObject");
                            String responText = resultObjectJSON.getString("res");
                            if(responText.equals("true"))Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                            else Toast.makeText(getApplicationContext(),responText,Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //JsonArray
                        try{
                            Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                            JSONArray resultArrayJSON = response.getJSONArray("res");
                            String result = resultArrayJSON.getString(0);
                            if(result.equals("true")){
                                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(getApplicationContext(),"Unsuccess",Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
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
