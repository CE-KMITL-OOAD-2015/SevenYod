package com.akkaratanapat.altear.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendProfileActivity extends AppCompatActivity {

    Bundle b;
    ImageView imageView;
    TextView textName, textEmail;
    Button button;
    int mode;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        setComponent();
    }

    public void setComponent() {
        b = getIntent().getExtras();

        imageView = (ImageView) findViewById(R.id.imageView);
        textName = (TextView) findViewById(R.id.textNameFriend);
        textEmail = (TextView) findViewById(R.id.textEmailFriend);
        button = (Button) findViewById(R.id.buttonFriend);

        textName.setText(b.getString("name"));
        textEmail.setText(b.getString("email"));
        final String idFriend = b.getString("idFriend");
        mode = b.getInt("mode");
        userID = b.getString("ID");
        if(mode == 1){
            Toast.makeText(getBaseContext(), "1", Toast.LENGTH_SHORT).show();
            button.setText("Unfriend");
            button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        else if(mode == 2){
            Toast.makeText(getBaseContext(), "2", Toast.LENGTH_SHORT).show();
            button.setText("Add Friend");
            button.setBackgroundColor(getResources().getColor(R.color.blue_btn));
        }
        else if(mode == 3){
            Toast.makeText(getBaseContext(), "3", Toast.LENGTH_SHORT).show();
            button.setText("Requesting");
            button.setBackgroundColor(getResources().getColor(R.color.blue_btn));
        }
        else if(mode == 4){
            Toast.makeText(getBaseContext(), "4", Toast.LENGTH_SHORT).show();
            button.setText("Accept");
            button.setBackgroundColor(getResources().getColor(R.color.blue_btn));
            //button.setImageResource(getResources().getDrawable(R.drawable.ic_account_box_white_48dp));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == 1) {//friend
                    Toast.makeText(getBaseContext(), "1", Toast.LENGTH_SHORT).show();
                    button.setText("Add friend");
                    button.setBackgroundColor(getResources().getColor(R.color.blue_btn));
                    responseJsonFromWeb(userID, idFriend, 2);//UnFriend
                    mode = 2;
                    //button.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_account_box_white_48dp));
                } else if (mode == 2) {//no friend
                    Toast.makeText(getBaseContext(), "2", Toast.LENGTH_SHORT).show();
                    button.setText("Request sent");
                    button.setBackgroundColor(getResources().getColor(R.color.orange_btn));
                    responseJsonFromWeb(userID, idFriend, 1);//Add Friend
                    mode = 1;
                    //button.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_account_circle_white_48dp));
                } else if (mode == 3) {//requesting
                    Toast.makeText(getBaseContext(), "3", Toast.LENGTH_SHORT).show();
                    button.setText("Add Friend");
                    button.setBackgroundColor(getResources().getColor(R.color.blue_btn));
                    responseJsonFromWeb(userID,idFriend,3);//Cancle
                    mode = 2;
                    //button.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_account_circle_white_48dp));
                } else if (mode == 4) {//requested
                    Toast.makeText(getBaseContext(), "4", Toast.LENGTH_SHORT).show();
                    button.setText("Unfriend");
                    button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    responseJsonFromWeb(userID, idFriend, 4);//Accept
                    mode = 1;
                    //button.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_account_circle_white_48dp));
                }
            }
        });
    }

    public void responseJsonFromWeb(final String idUser, final String idBuddy,int mode) {
        //String url = "http://mol100.esy.es/ooad/lin?user=" + idUser + "&pass=" + idUser;
        String url = "";
        if(mode ==1){// add
            url = "http://203.151.92.184:8080/addfriend/" + idUser + "/" + idBuddy;
        }
        else if(mode ==2){//un
            url = "http://203.151.92.184:8080/rejectfriend/" + idUser + "/" + idBuddy;
        }
        else if(mode ==3){//cancle
            url = "http://203.151.92.184:8080/rejectfriend/" + idUser + "/" + idBuddy;
        }
        else if(mode ==4){//accept
            url = "http://203.151.92.184:8080/acceptfriend/" + idUser + "/" + idBuddy;
        }
        else{//reject
            url = "http://203.151.92.184:8080/rejectfriend/" + idUser + "/" + idBuddy;
        }
        Toast.makeText(getBaseContext(),idUser  + " : "+ idBuddy + " : " + url,Toast.LENGTH_SHORT).show();
//        final ProgressDialog dia = ProgressDialog.show(ChatActivity.this, null,
//                "Loading");
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject resultObjectJSON = response.getJSONObject("resultObject");
                            String responText = resultObjectJSON.getString("status");
                            if (responText.equals("true")) {
                                //dia.dismiss();
                                Log.i("aaaaaa",idUser  + " : "+ idBuddy);
                                Toast.makeText(getBaseContext(),"Success",Toast.LENGTH_SHORT).show();
                            } else {
                                //dia.dismiss();
                                Log.i("bbbbbb",idUser  + " : "+ idBuddy);
                                Toast.makeText(getBaseContext(),"Unsuccess",Toast.LENGTH_SHORT).show();
                            }
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
}
