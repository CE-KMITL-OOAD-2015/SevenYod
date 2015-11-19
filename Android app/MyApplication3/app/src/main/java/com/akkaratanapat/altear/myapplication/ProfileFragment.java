package com.akkaratanapat.altear.myapplication;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    View v;
    String name,email,id;
    TextView nameText,emailText;
    Button btnEdit,btnRefresh;
    Bundle bundle;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        setComponent();
        return v;
    }

    public void setComponent(){
        bundle = this.getArguments();
        name = bundle.getString("Name");
        email = bundle.getString("Email");
        id = bundle.getString("ID");
        nameText = (TextView) v.findViewById(R.id.textName);
        emailText = (TextView) v.findViewById(R.id.textEmail);
        btnEdit = (Button)v.findViewById(R.id.buttonEdit);
        btnRefresh = (Button)v.findViewById(R.id.buttonRefresh);
        nameText.setText(name);
        emailText.setText(email);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Edit", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), EditProfileActivity.class);
                i.putExtra("ID", id);
                startActivity(i);
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseJsonFromWeb(bundle.getString("Username"),bundle.getString("Pass"));
            }
        });
    }

    public void responseJsonFromWeb(final String name, final String pass) {
        String url = "http://203.151.92.184:8080/login/"+name+"/"+pass;
        final ProgressDialog dia = ProgressDialog.show(getActivity(), null,
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
                                nameText.setText(resultObjectJSON.getString("alias"));
                                emailText.setText(resultObjectJSON.getString("email"));
                                Toast.makeText(getActivity(), resultObjectJSON.getString("alias") + " : " + resultObjectJSON.getString("email"), Toast.LENGTH_SHORT).show();
                                //Intent i = new Intent(getActivity(), MainActivity.class);
                                //i.putExtra("User",new User(resultObjectJSON.getString("alias"),resultObjectJSON.getString("email"),resultObjectJSON.getString("userid")));
                                //startActivity(i);
                            } else{
                                dia.dismiss();
                                Toast.makeText(getActivity(), name +" : "+ pass, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dia.dismiss();
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue myrequestQueue = Volley.newRequestQueue(getActivity());
        myrequestQueue.add(jsObjRequest);
    }
}

