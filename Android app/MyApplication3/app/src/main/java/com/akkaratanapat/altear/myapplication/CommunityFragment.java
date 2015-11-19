package com.akkaratanapat.altear.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends Fragment {

    ListView listViewFriend, listViewNoFriend,listViewRequestingFriend,listViewRequestedFriend;
    String[] list,list2,list3,list4,email,email2,email3,email4,idFriend,idNoFriend,idRequestingFriend,idRequestedFriend;
    View v;
    FriendList friendList, noFriendList,requestingList,requestedList;
    String userID;
    Handler handler = new Handler();
    Runnable r;

    public CommunityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_community, container, false);
        Bundle bundle = this.getArguments();

        list = bundle.getStringArray("ListFriend");
        list2 = bundle.getStringArray("ListNoFriend");
        list3 = bundle.getStringArray("ListRequestingFriend");
        list4 = bundle.getStringArray("ListRequestedFriend");

        email = bundle.getStringArray("EmailFriend");
        email2 = bundle.getStringArray("EmailNoFriend");
        email3 = bundle.getStringArray("EmailRequestingFriend");
        email4 = bundle.getStringArray("EmailRequestedFriend");


        idFriend = bundle.getStringArray("IDFriend");
        idNoFriend = bundle.getStringArray("IDNoFriend");
        idRequestingFriend = bundle.getStringArray("IDRequestingFriend");
        idRequestedFriend = bundle.getStringArray("IDRequestedFriend");

        userID = bundle.getString("ID");

        friendList = new FriendList(getActivity(),android.R.layout.simple_list_item_1,list,email);
        noFriendList = new FriendList(getActivity(),android.R.layout.simple_list_item_1,list2,email2);
        requestingList =  new FriendList(getActivity(),android.R.layout.simple_list_item_1,list3,email3);
        requestedList =  new FriendList(getActivity(),android.R.layout.simple_list_item_1,list4,email4);

        listViewFriend = (ListView) v.findViewById(R.id.listViewFriend);
        listViewFriend.setAdapter(friendList);
        listViewFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), FriendProfileActivity.class);
                i.putExtra("name", list[position]);
                i.putExtra("email", email[position]);
                i.putExtra("idFriend", idFriend[position]);
                i.putExtra("mode", 1);
                i.putExtra("ID", userID);
                startActivity(i);
                Toast.makeText(getActivity(), list[position], Toast.LENGTH_SHORT).show();
            }
        });

        listViewFriend.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        listViewNoFriend = (ListView)v.findViewById(R.id.listViewOther);
        listViewNoFriend.setAdapter(noFriendList);
        listViewNoFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), FriendProfileActivity.class);
                i.putExtra("name", list2[position]);
                i.putExtra("email", email2[position]);
                i.putExtra("idFriend", idNoFriend[position]);
                i.putExtra("mode", 2);
                i.putExtra("ID", userID);
                startActivity(i);
                Toast.makeText(getActivity(), list[position], Toast.LENGTH_SHORT).show();
            }
        });

        listViewNoFriend.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        listViewRequestingFriend = (ListView)v.findViewById(R.id.listViewRequesting);
        listViewRequestingFriend.setAdapter(requestingList);
        listViewRequestingFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), FriendProfileActivity.class);
                i.putExtra("name", list3[position]);
                i.putExtra("email", email3[position]);
                i.putExtra("idFriend", idRequestingFriend[position]);
                i.putExtra("mode", 3);
                i.putExtra("ID", userID);
                startActivity(i);
                Toast.makeText(getActivity(), list[position], Toast.LENGTH_SHORT).show();
            }
        });

        listViewRequestingFriend.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        listViewRequestedFriend = (ListView)v.findViewById(R.id.listViewRequested);
        listViewRequestedFriend.setAdapter(requestedList);
        listViewRequestedFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), FriendProfileActivity.class);
                i.putExtra("name", list4[position]);
                i.putExtra("email", email4[position]);
                i.putExtra("idFriend", idRequestedFriend[position]);
                i.putExtra("mode", 4);
                i.putExtra("ID", userID);
                startActivity(i);
                Toast.makeText(getActivity(), list[position], Toast.LENGTH_SHORT).show();
            }
        });

        listViewRequestedFriend.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                v.onTouchEvent(event);
                return true;
            }
        });

//        handler = new Handler();
//        r = new Runnable() {
//            public void run() {
//                responseJsonFromWebFriendList(userID);
//                handler.postDelayed(this, 60000);
//            }
//        };
//        handler.postDelayed(r, 60000);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Des : ","des");
        handler.removeCallbacks(r);
    }

    public void responseJsonFromWebFriendList(final String id) {
        String url = "http://203.151.92.184:8080/getfriend/" + id;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject resultObject = response.getJSONObject("resultObject");

                            JSONArray resultFriendArrayJSON = resultObject.getJSONArray("friend");
                            JSONArray resultNoFriendArrayJSON = resultObject.getJSONArray("notfriend");
                            JSONArray resultrequestingFriendArrayJSON = resultObject.getJSONArray("requesting");
                            JSONArray resultrequestedFriendArrayJSON = resultObject.getJSONArray("requested");

                            list = new String[resultFriendArrayJSON.length()];
                            list2 = new String[resultNoFriendArrayJSON.length()];
                            list3 = new String[resultrequestingFriendArrayJSON.length()];
                            list4 = new String[resultrequestedFriendArrayJSON.length()];

                            idFriend = new String[resultFriendArrayJSON.length()];
                            idNoFriend = new String[resultNoFriendArrayJSON.length()];
                            idRequestingFriend = new String[resultrequestingFriendArrayJSON.length()];
                            idRequestedFriend = new String[resultrequestedFriendArrayJSON.length()];

                            email = new String[resultFriendArrayJSON.length()];
                            email2 = new String[resultNoFriendArrayJSON.length()];
                            email3 = new String[resultrequestingFriendArrayJSON.length()];
                            email4 = new String[resultrequestedFriendArrayJSON.length()];

                            for (int i = 0; i < resultFriendArrayJSON.length(); i++) {
                                JSONObject obj = (JSONObject) resultFriendArrayJSON.get(i);
                                list[i] = obj.getString("alias");
                                idFriend[i] = obj.getString("userid");
                                email[i] = obj.getString("email");
                            }
                            for (int i = 0; i < resultNoFriendArrayJSON.length(); i++) {
                                JSONObject obj2 = (JSONObject) resultNoFriendArrayJSON.get(i);
                                list2[i] = obj2.getString("alias");
                                idNoFriend[i] = obj2.getString("userid");
                                email2[i] = obj2.getString("email");
                            }

                            for (int i = 0; i < resultrequestingFriendArrayJSON.length(); i++) {
                                JSONObject obj3 = (JSONObject) resultrequestingFriendArrayJSON.get(i);
                                list3[i] = obj3.getString("alias");
                                idRequestingFriend[i] = obj3.getString("userid");
                                email3[i] = obj3.getString("email");
                            }
                            for (int i = 0; i < resultrequestedFriendArrayJSON.length(); i++) {
                                JSONObject obj4 = (JSONObject) resultrequestedFriendArrayJSON.get(i);
                                list4[i] = obj4.getString("alias");
                                idRequestedFriend[i] = obj4.getString("userid");
                                email4[i] = obj4.getString("email");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue myrequestQueue = Volley.newRequestQueue(getActivity());
        myrequestQueue.add(jsObjRequest);
    }
}

