package com.akkaratanapat.altear.myapplication;

/**
 * The Class Conversation is a Java Bean class that represents a single chat
 * conversation message.
 */
public class Conversation {

    /**
     * The Constant STATUS_SENDING.
     */
    public static final int STATUS_SENDING = 0;

    /**
     * The Constant STATUS_SENT.
     */
    public static final int STATUS_SENT = 1;

    /**
     * The Constant STATUS_FAILED.
     */
    public static final int STATUS_FAILED = 2;
    /**
     * The msg.
     */
    private String msg;

    /**
     * The status.
     */
    private int status = STATUS_SENT;

    /**
     * The date.
     */
    private String date;

    /**
     * The sender.
     */
    private String sender, client, ID;
    private String mark;

    /**
     * Instantiates a new conversation.
     *
     * @param msg    the msg
     * @param date   the date
     * @param sender the sender
     */
    public Conversation(String msg, String date, String sender, String client) {
        this.msg = msg;
        this.date = date;
        this.sender = sender;
        this.client = client;
    }

    /**
     * Instantiates a new conversation.
     */
    public Conversation() {
    }

    /**
     * Gets the msg.
     *
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets the msg.
     *
     * @param msg the new msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Checks if is sent.
     *
     * @return true, if is sent
     */
    public boolean isSent() {
        if (client.equals(sender)) {
            return true;
        } else return false;
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date.
     *
     * @param date the new date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the sender.
     *
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Sets the sender.
     *
     * @param sender the new sender
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void changeMark() {
        if (mark.equals("false")) {
            mark = "true";
        } else {
            mark = "false";
        }
    }

    public String getMark() {
        return mark;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

}
