package com.akkaratanapat.altear.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView nameText, emailText;
    String[] friendList, noFriendList,requestingFriendList,requestedFriendList,friendEmail,noFriendEmail,requestingFriendEmail
            ,requestedFriendEmail, idFriend, idNoFriend,idRequestingFriend,idRequestedFriend;
    User userObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setCustomComponent();
    }

    public void setCustomComponent() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent i = getIntent();
        userObject = i.getParcelableExtra("User");
        nameText = (TextView) findViewById(R.id.nameText);
        emailText = (TextView) findViewById(R.id.emailText);
        nameText.setText(userObject.UserName);
        emailText.setText(userObject.UserEmail);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            logOut();
        }
    }

    public void logOut() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Log out");
        dialog.setIcon(R.drawable.ic_power_settings_new_black_48dp);
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to log out?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_profile) {
            Fragment f = new ProfileFragment();
            Bundle bundle = new Bundle();
            bundle.putString("Name",userObject.UserName);
            bundle.putString("Email",userObject.UserEmail);
            bundle.putString("ID",userObject.ID);
            f.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, f);
            transaction.commit();
            Toast.makeText(getBaseContext(), "Fragment profile", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_community) {
            responseJsonFromWebFriendList(userObject.ID, 0);
            Toast.makeText(getBaseContext(), "Fragment community", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_chat) {
            responseJsonFromWebFriendList(userObject.ID, 1);
            Toast.makeText(getBaseContext(), "Fragment chat", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_marked) {
            responseJsonFromWebFriendList(userObject.ID, 2);
            Toast.makeText(getBaseContext(), "Fragment marked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_about) {
            Fragment f = new AboutFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, f);
            transaction.commit();
            Toast.makeText(getBaseContext(), "Fragment about", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_loglout) {
            Toast.makeText(getBaseContext(), "Finish", Toast.LENGTH_SHORT).show();
            logOut();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void responseJsonFromWebFriendList(final String id, final int mode) {
        //String url = "http://mol100.esy.es/ooad/get_friend?id=" + id;
        String url = "http://203.151.92.184:8080/getfriend/" + id;
        final ProgressDialog dia = ProgressDialog.show(MainActivity.this, null,
                "Loading");
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

                            friendList = new String[resultFriendArrayJSON.length()];
                            noFriendList = new String[resultNoFriendArrayJSON.length()];
                            requestingFriendList = new String[resultrequestingFriendArrayJSON.length()];
                            requestedFriendList = new String[resultrequestedFriendArrayJSON.length()];

                            idFriend = new String[resultFriendArrayJSON.length()];
                            idNoFriend = new String[resultNoFriendArrayJSON.length()];
                            idRequestingFriend = new String[resultrequestingFriendArrayJSON.length()];
                            idRequestedFriend = new String[resultrequestedFriendArrayJSON.length()];

                            friendEmail = new String[resultFriendArrayJSON.length()];
                            noFriendEmail = new String[resultNoFriendArrayJSON.length()];
                            requestingFriendEmail = new String[resultrequestingFriendArrayJSON.length()];
                            requestedFriendEmail = new String[resultrequestedFriendArrayJSON.length()];

                            Log.i("ingggggggggggggg",""+resultrequestingFriendArrayJSON.length());
                            Log.i("edddddddddddd",""+resultrequestedFriendArrayJSON.length());

                            for (int i = 0; i < resultFriendArrayJSON.length(); i++) {
                                JSONObject obj = (JSONObject) resultFriendArrayJSON.get(i);
                                friendList[i] = obj.getString("alias");
                                idFriend[i] = obj.getString("userid");
                                friendEmail[i] = obj.getString("email");
                            }
                            for (int i = 0; i < resultNoFriendArrayJSON.length(); i++) {
                                JSONObject obj2 = (JSONObject) resultNoFriendArrayJSON.get(i);
                                noFriendList[i] = obj2.getString("alias");
                                idNoFriend[i] = obj2.getString("userid");
                                noFriendEmail[i] = obj2.getString("email");
                            }

                            for (int i = 0; i < resultrequestingFriendArrayJSON.length(); i++) {
                                JSONObject obj3 = (JSONObject) resultrequestingFriendArrayJSON.get(i);
                                requestingFriendList[i] = obj3.getString("alias");
                                idRequestingFriend[i] = obj3.getString("userid");
                                requestingFriendEmail[i] = obj3.getString("email");
                            }
                            for (int i = 0; i < resultrequestedFriendArrayJSON.length(); i++) {
                                JSONObject obj4 = (JSONObject) resultrequestedFriendArrayJSON.get(i);
                                requestedFriendList[i] = obj4.getString("alias");
                                idRequestedFriend[i] = obj4.getString("userid");
                                requestedFriendEmail[i] = obj4.getString("email");
                            }
                            dia.dismiss();
                            if (mode == 0) {
                                Fragment f = new CommunityFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("ID", userObject.ID);
                                bundle.putStringArray("ListFriend", friendList);
                                bundle.putStringArray("ListNoFriend", noFriendList);
                                bundle.putStringArray("ListRequestingFriend", requestingFriendList);
                                bundle.putStringArray("ListRequestedFriend", requestedFriendList);
                                bundle.putStringArray("IDFriend", idFriend);
                                bundle.putStringArray("IDNoFriend", idNoFriend);
                                bundle.putStringArray("IDRequestingFriend", idRequestingFriend);
                                bundle.putStringArray("IDRequestedFriend", idRequestedFriend);
                                bundle.putStringArray("EmailFriend",friendEmail);
                                bundle.putStringArray("EmailNoFriend",noFriendEmail);
                                bundle.putStringArray("EmailRequestingFriend",requestingFriendEmail);
                                bundle.putStringArray("EmailRequestedFriend",requestedFriendEmail);
                                bundle.putString("ID", id);
                                f.setArguments(bundle);
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, f);
                                transaction.commit();
                            } else if (mode == 1) {
                                Fragment f = new FriendListFragment();
                                Bundle bundle = new Bundle();
                                bundle.putStringArray("List", friendList);
                                bundle.putInt("mode", 1);
                                bundle.putStringArray("IDFriend", idFriend);
                                bundle.putStringArray("EmailFriend",friendEmail);
                                bundle.putString("ID", id);
                                f.setArguments(bundle);
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, f);
                                transaction.commit();
                            } else if (mode == 2) {
                                Fragment f = new FriendListFragment();
                                Bundle bundle = new Bundle();
                                bundle.putStringArray("List", friendList);
                                bundle.putStringArray("IDFriend", idFriend);
                                bundle.putStringArray("EmailFriend",friendEmail);
                                bundle.putString("ID", id);
                                bundle.putInt("mode", 2);
                                f.setArguments(bundle);
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, f);
                                transaction.commit();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dia.dismiss();
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue myrequestQueue = Volley.newRequestQueue(getBaseContext());
        myrequestQueue.add(jsObjRequest);
    }
}
