package com.akkaratanapat.altear.myapplication;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarkedActivity extends AppCompatActivity {


    ListView lv;
    private ArrayList<Conversation> convList = new ArrayList<>();
    ChatAdapter nChat;
    Bundle b;
    private Handler handler;

    public MarkedActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marked);
        b = getIntent().getExtras();
        lv = (ListView) findViewById(R.id.list);
        setComponent();
        final Timestamp ts = new Timestamp(System.currentTimeMillis());
        Conversation con = new Conversation("aaa", "2015-11-04%2009:09:05", "1", b.getString("ID"));
        Conversation con2 = new Conversation("bbb", "2015-11-04%2009:09:05", "2", b.getString("ID"));
        con.setMark("false");
        con2.setMark("false");
        convList.add(con);
        convList.add(con2);
        nChat = new ChatAdapter(MarkedActivity.this, convList);
        lv.setAdapter(nChat);
        lv.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        lv.setStackFromBottom(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (responseJsonFromWebToggleMarked(b.getString("ID"), b.getString("IdFriend"), ts.toString(), convList.get(position).getMsg())) {
                    convList.get(position).changeMark();
                } else {
                    Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                }
                nChat.notifyDataSetChanged();
            }
        });

        handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                loadConversation();
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);
    }


    public void setComponent() {
    }


    public void loadConversation() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        responseJsonFromWebForLoadMarked(b.getString("ID"), b.getString("IdFriend"), ts.toString());
    }


    public boolean responseJsonFromWebToggleMarked(String idUser, String idBuddy, String date, String message) {
        String url = "http://mol100.esy.es/ooad/lin?user=" + idUser + "&pass=" + idUser;
        final boolean[] check = {false};
        final ProgressDialog dia = ProgressDialog.show(MarkedActivity.this, null,
                "Loading");
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject resultObjectJSON = response.getJSONObject("resultObject");
                            String responText = resultObjectJSON.getString("res");
                            if (responText.equals("true")) {
                                dia.dismiss();
                                check[0] = true;
                                Toast.makeText(getApplicationContext(), resultObjectJSON.getString("name") + " has logged in", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getBaseContext(), MainActivity.class);
                                i.putExtra("User", new User(resultObjectJSON.getString("name"), resultObjectJSON.getString("email"), resultObjectJSON.getString("id")));
                                startActivity(i);
                            } else {
                                dia.dismiss();
                                check[0] = false;
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
                        check[0] = false;
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue myrequestQueue = Volley.newRequestQueue(this);
        myrequestQueue.add(jsObjRequest);
        return check[0];
    }

    public void responseJsonFromWebForLoadMarked(String idUser, String idBuddy, String date) {
        String url = "http://mol100.esy.es/ooad/lin?user=" + idUser + "&pass=" + idUser;
        final ProgressDialog dia = ProgressDialog.show(MarkedActivity.this, null,
                "Loading");
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject resultObjectJSON = response.getJSONObject("resultObject");
                            String responText = resultObjectJSON.getString("res");
                            if (responText.equals("true")) {
                                dia.dismiss();
                                Toast.makeText(getApplicationContext(), resultObjectJSON.getString("name") + " has logged in", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getBaseContext(), MainActivity.class);
                                i.putExtra("User", new User(resultObjectJSON.getString("name"), resultObjectJSON.getString("email"), resultObjectJSON.getString("id")));
                                startActivity(i);
                            } else {
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

