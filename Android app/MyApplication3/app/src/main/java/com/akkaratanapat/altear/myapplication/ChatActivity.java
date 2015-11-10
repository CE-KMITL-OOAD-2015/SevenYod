package com.akkaratanapat.altear.myapplication;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

import java.sql.Timestamp;
import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity {

    ListView lv;
    Button sendBtn;
    EditText messageText;
    TextView textViewName;
    private ArrayList<Conversation> convList = new ArrayList<>();
    ChatAdapter nChat;
    Bundle b;
    Handler handler = new Handler();
    boolean isRunning = true;

    public ChatActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        b = getIntent().getExtras();
        lv = (ListView) findViewById(R.id.list);
        textViewName = (TextView)findViewById(R.id.textViewName);
        sendBtn = (Button) findViewById(R.id.btnSend);
        messageText = (EditText) findViewById(R.id.txt);
        nChat = new ChatAdapter(ChatActivity.this, convList);

        setComponent();
        lv.setAdapter(nChat);
        lv.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        lv.setStackFromBottom(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                convList.get(position).changeMark();
                nChat.notifyDataSetChanged();
            }
        });

        handler = new Handler();
        if(isRunning) {
            final Runnable r = new Runnable() {
                public void run() {
                loadConversation();
                handler.postDelayed(this, 1000);
            }
        };
            handler.postDelayed(r, 1000);
        }
//        handler.postDelayed(new Runnable() {
//
//            @Override
//            public void run()
//            {
//                    loadConversation();
//            }
//        }, 3000);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        isRunning = true;
        loadConversation();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        isRunning = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    public void setComponent() {
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        textViewName.setText(b.getString("Friend"));
    }

    public void sendMessage() {
        if (messageText.getText().toString().length() != 0) {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            Conversation c = new Conversation(messageText.getText().toString(), ts.toString(), b.getString("ID"), b.getString("ID"));
            c.setStatus(Conversation.STATUS_SENDING);
            c.setMark("false");
            responseJsonFromWeb(b.getString("ID"), b.getString("IdFriend"), messageText.getText().toString(),c);
            nChat.notifyDataSetChanged();
            messageText.setText("");
        }
    }

    public void loadConversation() {
        String time = "2015-11-04 09:09:05";
        int length = convList.size();
        if(length>0) {
            time = convList.get(length - 1).getDate();
        }
        responseJsonFromWebForLoadMessage(b.getString("ID"), b.getString("IdFriend"), time);
    }


    public void responseJsonFromWeb(String idUser, String idBuddy, final String message, final Conversation c) {
        //String url = "http://mol100.esy.es/ooad/lin?user=" + idUser + "&pass=" + idUser;
        String url = "http://203.151.92.184:8080/sendmessage/" + idUser + "/" + idBuddy + "/" + message;
//        final ProgressDialog dia = ProgressDialog.show(ChatActivity.this, null,
//                "Loading");
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject resultObjectJSON = response.getJSONObject("resultObject");
                            String responText = resultObjectJSON.getString("response");
                            if (responText.equals("true")) {
                                //dia.dismiss();
                                c.setStatus(Conversation.STATUS_SENT);
                            } else {
                                //dia.dismiss();
                                c.setStatus(Conversation.STATUS_FAILED);
                            }
                            convList.add(c);
                            nChat.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        dia.dismiss();
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue myrequestQueue = Volley.newRequestQueue(this);
        myrequestQueue.add(jsObjRequest);
    }


    public void responseJsonFromWebForLoadMessage(final String idUser, String idBuddy, String date) {
        String url = "http://203.151.92.184:8080/loadmessage/" + idUser + "/" + idBuddy + "/" + date.substring(0, 10) + "%20" + date.substring(11, 19);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject resultObjectJSON = response.getJSONObject("resultObject");
                            String res = resultObjectJSON.getString("response");
                            if (res.equals("true")) {
                                JSONArray message = resultObjectJSON.getJSONArray("message");
                                for (int i = 0; i < message.length(); i++) {
                                    JSONObject obj = (JSONObject) message.get(i);
                                    Conversation c = new Conversation(obj.getString("text")
                                            , obj.getString("date"), obj.getString("sender"), idUser);
                                    c.setMark(obj.getString("marked"));
                                    c.setID(obj.getString("id"));
                                    convList.add(c);
                                }
                                nChat.notifyDataSetChanged();
                            }
                            else{
                                Toast.makeText(getBaseContext(), "Just do it!!!", Toast.LENGTH_SHORT).show();
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

