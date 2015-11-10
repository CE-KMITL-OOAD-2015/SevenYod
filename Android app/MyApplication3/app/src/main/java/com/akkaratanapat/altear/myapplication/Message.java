package com.akkaratanapat.altear.myapplication;

import java.util.Date;

/**
 * Created by Altear on 11/2/2015.
 */
public class Message {

    public static final int  TEXT = 0,HYPERLINK = 1,IMAGE =2;
    public static final int  SENDING = 0,SENT = 1,FAIL =2;

    int typeMessage = TEXT,staus = SENDING;

    String msg,sender;
    Date date;

    public Message(String msg, Date date, String sender,int typeMessage){
        this.msg = msg;
        this.date = date;
        this.sender = sender;
        this.typeMessage = typeMessage;
    }

    public String getMsg(){
        return  msg;
    }

    public String getSender(){
        return  sender;
    }

    public  Date getDate(){
        return date;
    }

    public int getTypeMessage(){
        return  typeMessage;
    }

    public int getStaus(){
        return staus;
    }

}
