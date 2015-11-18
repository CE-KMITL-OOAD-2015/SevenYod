package com.akkaratanapat.altear.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    View v;
    String name,email,id;
    TextView nameText,emailText;
    Button btnEdit;
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
        Bundle bundle = this.getArguments();
        name = bundle.getString("Name");
        email = bundle.getString("Email");
        id = bundle.getString("ID");
        nameText = (TextView) v.findViewById(R.id.textName);
        emailText = (TextView) v.findViewById(R.id.textEmail);
        btnEdit = (Button)v.findViewById(R.id.buttonEdit);
        nameText.setText(name);
        emailText.setText(email);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Edit", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

