package com.akkaratanapat.altear.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendListFragment extends Fragment {

    ListView listViewFriend;

    String ID;
    String[] list, idFriend,emailFriend;
    View v;
    FriendList friendList;
    int mode;

    public FriendListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_friend_list, container, false);
        setComponent();
        return v;
    }

    public void setComponent(){
        Bundle bundle = this.getArguments();
        list = bundle.getStringArray("List");
        mode = bundle.getInt("mode");
        idFriend = bundle.getStringArray("IDFriend");
        ID = bundle.getString("ID");
        emailFriend = bundle.getStringArray("EmailFriend");
        friendList = new FriendList(getActivity(), android.R.layout.simple_list_item_1, list,emailFriend);

        listViewFriend = (ListView) v.findViewById(R.id.listFriend);
        listViewFriend.setAdapter(friendList);
        listViewFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mode == 1) {//chat
                    Intent i = new Intent(getActivity(), ChatActivity.class);
                    i.putExtra("ID", ID);
                    i.putExtra("Friend", list[position]);
                    i.putExtra("IdFriend",idFriend[position]);
                    startActivity(i);
                    Toast.makeText(getActivity(), list[position], Toast.LENGTH_SHORT).show();
                } else if (mode == 2) {//marked
                    Intent i = new Intent(getActivity(), MarkedActivity.class);
                    i.putExtra("ID", ID);
                    i.putExtra("Friend", list[position]);
                    i.putExtra("IdFriend",idFriend[position]);
                    startActivity(i);
                    Toast.makeText(getActivity(), list[position], Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

