package com.akkaratanapat.altear.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FriendList extends ArrayAdapter<String> {

    String[] str;
    //int[] image;
    Context mContext;
    LayoutInflater INFLATER;

    public FriendList(Context context, int viewResourceId
            , String[] objects) {
        super(context, viewResourceId, objects);
        str = objects;
        //image = resImage;
        mContext = context;
        INFLATER = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.activity_friend_list, parent, false);

        TextView textView = (TextView)view.findViewById(R.id.textView);
        textView.setText(str[position]);

        //ImageView imageView = (ImageView)view.findViewById(R.id.imageView1);
        //imageView.setBackgroundResource(resId[position]);

        return view;
    }
}
