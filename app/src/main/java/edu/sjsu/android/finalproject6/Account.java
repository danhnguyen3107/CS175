package edu.sjsu.android.finalproject6;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Account implements Parcelable {

    private int id;
    private String username;
    private String accountName;
    private String accountPassword;

    public Account() {

    }

    public Account(int id, String username, String accountName, String accountPassword) {
        this.id = id;
        this.username = username;
        this.accountName = accountName;
        this.accountPassword = accountPassword;
    }

    public Account(String username, String accountName, String accountPassword) {
        this.username = username;
        this.accountName = accountName;
        this.accountPassword = accountPassword;
    }
    protected Account(Parcel in) {
        this.id = in.readInt();
        this.accountName = in.readString();
        this.username = in.readString();
        this.accountPassword  = in.readString();
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getAccountName() {
        return this.accountName;
    }


    public String getAccountPassword() {
        return this.accountPassword;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(accountName);
        dest.writeString(username);
        dest.writeString(accountPassword);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "id: " + id + ", AccountName: " + accountName + ", Username: " + username + ", accountPassword: " + accountPassword + "\n";
    }
}


