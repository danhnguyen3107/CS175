package edu.sjsu.android.accman;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Account implements Parcelable {
    private int emailID;
    private int nameID;
    private int phoneID;
    private int passwordID;

    // alt + enter (option + enter on Mac) for faster setup

    public Account(int imageID, int nameID, int phoneID, int passwordID) {
        this.emailID = imageID;
        this.nameID = nameID;
        this.phoneID = phoneID;
        this.passwordID = passwordID;
    }

    protected Account(Parcel in) {
        emailID = in.readInt();
        nameID = in.readInt();
        phoneID = in.readInt();
        passwordID = in.readInt();
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

    public int getEmailID() {
        return emailID;
    }
    public int getNameID() {
        return nameID;
    }
    public int getPhoneID() {
        return phoneID;
    }
    public int getPasswordID() {
        return passwordID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(emailID);
        parcel.writeInt(nameID);
        parcel.writeInt(phoneID);
        parcel.writeInt(passwordID);
    }
}
