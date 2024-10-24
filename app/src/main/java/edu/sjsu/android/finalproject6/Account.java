package edu.sjsu.android.finalproject6;

public class Account {

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



}
