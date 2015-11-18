package com.akkaratanapat.altear.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Altear on 11/4/2015.
 */
public class MenuMessageAdapter extends BaseAdapter
{
    private String[] menu;
    Context mContext;
    LayoutInflater INFLATER;


    public MenuMessageAdapter(Context context,String[] menu){
        mContext = context;
        this.menu = menu;
        INFLATER = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount()
    {
        return menu.length;
    }

    @Override
    public String getItem(int arg0)
    {
        return menu[arg0];
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

        String s = getItem(pos);

        v = mInflater.inflate(R.layout.dialog_message, null);
         TextView text = (TextView) v.findViewById(R.id.textActivity);
         text.setText(s);
        return v;
    }

}
