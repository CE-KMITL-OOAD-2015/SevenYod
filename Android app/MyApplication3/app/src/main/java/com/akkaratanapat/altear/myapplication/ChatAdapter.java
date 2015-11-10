package com.akkaratanapat.altear.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Altear on 11/4/2015.
 */
public class ChatAdapter extends BaseAdapter
{
    private ArrayList<Conversation> convList = new ArrayList<>();
    Context mContext;
    LayoutInflater INFLATER;

    public ChatAdapter(Context context,ArrayList<Conversation> convList){
        mContext = context;
        this.convList = convList;
        INFLATER = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount()
    {
        return convList.size();
    }

    @Override
    public Conversation getItem(int arg0)
    {
        return convList.get(arg0);
    }

    @Override
    public long getItemId(int arg0)
    {
        return arg0;
    }

    @Override
    public View getView(int pos, View v, ViewGroup arg2)
    {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Conversation c = getItem(pos);
        if (c.isSent()){
            if(c.getMark().equals("false")) v = mInflater.inflate(R.layout.send_msg, null);
            else v = mInflater.inflate(R.layout.send_mark, null);
        }
        else {
            if(c.getMark().equals("false")) v = mInflater.inflate(R.layout.rcv_msg, null);
            else v = mInflater.inflate(R.layout.rcv_mark, null);
        }
        TextView lbl = (TextView) v.findViewById(R.id.lbl1);

        lbl.setText(c.getDate());

        lbl = (TextView) v.findViewById(R.id.lbl2);
        lbl.setText(c.getMsg());

        lbl = (TextView) v.findViewById(R.id.lbl3);
        if (c.isSent())
        {
            if (c.getStatus() == Conversation.STATUS_SENT)
                lbl.setText("Delivered");
            else if (c.getStatus() == Conversation.STATUS_SENDING)
                lbl.setText("Sending...");
            else
                lbl.setText("Failed");
        }
        else
            lbl.setText("");

        return v;
    }

}
