package com.akkaratanapat.altear.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    String[] friendList, friendList2;
    ProgressDialog pd;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setCustomComponent();
    }

    public void setCustomComponent() {
        b = getIntent().getExtras();
        nameText = (TextView) findViewById(R.id.nameText);
        emailText = (TextView) findViewById(R.id.emailText);
        nameText.setText(b.getString("Name"));
        emailText.setText(b.getString("Email"));
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
        dialog.setIcon(R.drawable.mchat);
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

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //
        String[] a = {"",""};
        if (id == R.id.nav_profile) {
            changeFrament(new ProfileFragment(),a,a);
            Toast.makeText(getBaseContext(), "Fragment profile", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_community) {
            responseJsonFromWebFriendList(b.getString("ID"));
            //changeFrament(new CommunityFragment(), a, a);
            Toast.makeText(getBaseContext(), "Fragment community", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_chat) {
            changeFrament(new ChatFragment(),a,a);
            Toast.makeText(getBaseContext(), "Fragment chat", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_marked) {
            changeFrament(new MarkedFragment(),a,a);
            Toast.makeText(getBaseContext(), "Fragment marked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_about) {
            changeFrament(new AboutFragment(),a,a);
            Toast.makeText(getBaseContext(), "Fragment about", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_loglout) {
            Toast.makeText(getBaseContext(), "Finish", Toast.LENGTH_SHORT).show();
            logOut();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void changeFrament(Fragment f, String[] str,String[] str2) {
        Bundle bundle2 = new Bundle();
        bundle2.putStringArray("List", str);
        bundle2.putStringArray("List2", str2);
        f.setArguments(bundle2);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, f);
        transaction.commit();
    }

    public String[] responseJsonFromWebFriendList(String id) {
        //Toast.makeText(getBaseContext(),"K",Toast.LENGTH_SHORT).show();
        String url = "http://mol100.esy.es/ooad/get_friend?id=" + id;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //    StringJson
//                          try{
//                              Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
//                             String resultStringJson = response.getString("");
//                          } catch (JSONException e) {
//                             e.printStackTrace();
//                          }
                        //      JsonObject
//                        try {
//                            //Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
//                            JSONObject resultObjectJSON = response.getJSONObject("resultObject");
//                            String responText = resultObjectJSON.getString("res");
//                            Toast.makeText(getActivity(), responText, Toast.LENGTH_SHORT).show();
//                            if (responText.equals("true")) {
//                                Toast.makeText(getActivity(), resultObjectJSON.getString("name") + " has login", Toast.LENGTH_SHORT).show();
//                                Intent i = new Intent(getActivity(), MainActivity.class);
//                                i.putExtra("Name", resultObjectJSON.getString("name"));
//                                i.putExtra("Email", resultObjectJSON.getString("email"));
//                                startActivity(i);
//                            } else
//                                Toast.makeText(getActivity(), responText, Toast.LENGTH_SHORT).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                        //JsonArray

                        try {
                            JSONArray resultArrayJSON = response.getJSONArray("resultObject");
                            //friendList[0] = new String[resultArrayJSON.length()+1];
                            int count = 0;
                            for(int j =0;j<resultArrayJSON.length();j++){
                                if(!(resultArrayJSON.getJSONObject(j).getString("status").equals("Add Friend"))){
                                    count++;
                                }
                            }
                            friendList = new String[count];
                            friendList2 = new String[resultArrayJSON.length()-count];
                            for (int i = 0; i < resultArrayJSON.length(); i++) {
                                if (!(resultArrayJSON.getJSONObject(i).getString("status").equals("Add Friend")))
                                    friendList[i] = resultArrayJSON.getJSONObject(i).getString("name")
                                            + " : " + resultArrayJSON.getJSONObject(i).getString("status")
                                            + "\n" + resultArrayJSON.getJSONObject(i).getString("date_create")
                                            + "\n" + resultArrayJSON.getJSONObject(i).getString("email");
                                else {
                                    friendList2[i-count] = resultArrayJSON.getJSONObject(i).getString("name")
                                            + " : " + resultArrayJSON.getJSONObject(i).getString("status")
                                            + "\n" + resultArrayJSON.getJSONObject(i).getString("date_create")
                                            + "\n" + resultArrayJSON.getJSONObject(i).getString("email");
                                }

                            }
                            pd = new ProgressDialog(MainActivity.this);
                            pd.setTitle("Loading");
                            pd.show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try{
                                        Thread.sleep(1000);
                                    }catch (Exception e){

                                    }
                                    pd.dismiss();
                                }
                            }).start();
                            changeFrament(new CommunityFragment(), friendList,friendList2);
                            //int sizeArray = resultArrayJSON.length();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue myrequestQueue = Volley.newRequestQueue(getBaseContext());
        myrequestQueue.add(jsObjRequest);
        return friendList;
    }
}
