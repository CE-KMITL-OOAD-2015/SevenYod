package com.akkaratanapat.altear.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Altear on 11/2/2015.
 */
public class User implements Parcelable {

    public String UserName ="",UserEmail = "",ID="";

    public User(String UserName,String UserEmail,String ID){
        this.UserName = UserName;
        this.UserEmail = UserEmail;
        this.ID = ID;
    }

    protected User(Parcel in) {
        UserName = in.readString();
        UserEmail = in.readString();
        ID = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(UserName);
        dest.writeString(UserEmail);
        dest.writeString(ID);
    }
}
