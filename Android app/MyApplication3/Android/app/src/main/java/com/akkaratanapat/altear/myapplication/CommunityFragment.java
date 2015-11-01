package com.akkaratanapat.altear.myapplication;


import android.app.ProgressDialog;
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
public class CommunityFragment extends Fragment {

    ListView listViewFriend, listViewOther;

    String ID;
    String[] list,list2;
    View v;
    FriendList friendList,friendList2;
    ProgressDialog pd;

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
        list = bundle.getStringArray("List");
        list2 = bundle.getStringArray("List2");

        friendList = new FriendList(getActivity(),android.R.layout.simple_list_item_1,list);
        friendList2 = new FriendList(getActivity(),android.R.layout.simple_list_item_1,list2);

        listViewFriend = (ListView) v.findViewById(R.id.listViewFriend);
        listViewFriend.setAdapter(friendList);
        listViewFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), list[position], Toast.LENGTH_SHORT).show();
            }
        });

        listViewOther = (ListView)v.findViewById(R.id.listViewOther);
        listViewOther.setAdapter(friendList2);
        listViewOther.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), list[position], Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}

