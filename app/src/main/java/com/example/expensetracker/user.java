package com.example.expensetracker;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class user implements Parcelable {
    private static String username;
    private String password;
    private List<Expense> expenseList;


    public user(String username, String password, List<Expense> expenseList) {
        this.username = username;
        this.password = password;
        this.expenseList = expenseList;
    }

    protected user(Parcel in) {
        username = in.readString();
        password = in.readString();
        expenseList = in.createTypedArrayList(Expense.CREATOR);
    }

    public static final Creator<user> CREATOR = new Creator<user>() {
        @Override
        public user createFromParcel(Parcel in) {
            return new user(in);
        }

        @Override
        public user[] newArray(int size) {
            return new user[size];
        }
    };

    public static String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeTypedList(expenseList);
    }
}
