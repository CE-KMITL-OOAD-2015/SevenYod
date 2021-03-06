package com.akkaratanapat.altear.myapplication;


import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
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

import java.io.File;
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
    Runnable r;
    Dialog dialog;

    public ChatActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        b = getIntent().getExtras();
        setComponent();
        nChat = new ChatAdapter(ChatActivity.this, convList);
        lv.setAdapter(nChat);
        lv.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        lv.setStackFromBottom(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                dialog = new Dialog(ChatActivity.this);
                dialog.setContentView(R.layout.activity_menu);
                final String[] a = {"Copy", "Mark", "Cancel"};
                MenuMessageAdapter adpter = new MenuMessageAdapter(getBaseContext(), a);
                ListView listActivity = (ListView) dialog.findViewById(R.id.listViewActivityMessage);
                listActivity.setAdapter(adpter);
                listActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position2, long id) {
                        Toast.makeText(getBaseContext(), a[position2], Toast.LENGTH_SHORT).show();
                        if (a[position2].equals("Copy")) {
                            ClipboardManager cm = (ClipboardManager) getBaseContext().getSystemService(Context.CLIPBOARD_SERVICE);
                            cm.setText(convList.get(position).getMsg());
                            Toast.makeText(getBaseContext(), "Copied to clipboard : " + cm.getText(), Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        } else if (a[position2].equals("Mark")) {
                            convList.get(position).changeMark();
                            toggleMarked(convList.get(position).getID());
                            nChat.notifyDataSetChanged();
                            dialog.cancel();
                        } else {
                            dialog.cancel();
                        }
                    }
                });
                dialog.setCancelable(true);
                dialog.setTitle("Menu");
                dialog.show();

            }
        });

        handler = new Handler();
        r = new Runnable() {
            public void run() {
                loadConversation();
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(r, 1000);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run()
//            {
//                    loadConversation();
//            }
//        }, 3000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(r);
    }

    public void setComponent() {
        lv = (ListView) findViewById(R.id.list);
        textViewName = (TextView)findViewById(R.id.textViewName);
        sendBtn = (Button) findViewById(R.id.btnSend);
        messageText = (EditText) findViewById(R.id.txt);
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
        //String time = "2015-11-04 09:09:05";
        String id = "-1";
        int length = convList.size();
        if(length>0) {
            id = convList.get(length - 1).getID();
        }
        responseJsonFromWebForLoadMessage(b.getString("ID"), b.getString("IdFriend"), id);
    }

    public void toggleMarked(final String idMessage){
        String url = "http://203.151.92.184:8080/togglemarked/" + idMessage;
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
                                Toast.makeText(getBaseContext(),""+idMessage,Toast.LENGTH_SHORT).show();
                            } else {
                                //dia.dismiss();
                                Toast.makeText(getBaseContext(),"sorry",Toast.LENGTH_SHORT).show();
                            }
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
                                c.setID(resultObjectJSON.getString("id"));
                                convList.add(c);
                            } else {
                                //dia.dismiss();
                                //c.setStatus(Conversation.STATUS_FAILED);
                                Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                            }
                            //convList.add(c);
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

    public void responseJsonFromWebForLoadMessage(final String idUser, String idBuddy, String id) {
        deleteCache(this);
        //String url = "http://203.151.92.184:8080/loadmessage/" + idUser + "/" + idBuddy + "/" + date.substring(0, 10) + "%20" + date.substring(11, 19);
        String url = "http://203.151.92.184:8080/loadmessage/" + idUser + "/" + idBuddy + "/" + id;
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
                                //Toast.makeText(getBaseContext(), res, Toast.LENGTH_SHORT).show();
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

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}

