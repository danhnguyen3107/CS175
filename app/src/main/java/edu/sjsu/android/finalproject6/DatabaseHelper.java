package edu.sjsu.android.finalproject6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Variables with database information
    protected static String DATABASE_NAME = "Accounts";
    protected static String TABLE_ACCOUNT = "Accounts";
    protected static String ID = "id";
    protected static String ACCOUNT_NAME = "accountName";
    protected static String USERNAME = "username";
    protected static String ACCOUNT_PASSWORD = "password";
    protected static int VERSION = 1;

    // Constructor
    public DatabaseHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    // onCreate method that creates the database table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createAccountTable = String.format("CREATE TABLE %s " +
                "(%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL);", TABLE_ACCOUNT, ID, ACCOUNT_NAME, USERNAME, ACCOUNT_PASSWORD);
        sqLiteDatabase.execSQL(createAccountTable);

        // Populate the database with sample data when the database is created
        populateData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Drop the table if it exists and create a new one
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        onCreate(sqLiteDatabase);
    }

    // Method to populate the database with sample data
    public  void populateData(SQLiteDatabase sqLiteDatabase) {
        ContentValues values = new ContentValues();

        // Sample data with Google account
        values.put(ACCOUNT_NAME, "Google");
        values.put(USERNAME, "test1");
        values.put(ACCOUNT_PASSWORD, "password");
        sqLiteDatabase.insert(TABLE_ACCOUNT, null, values);

        // Sample Microsoft account
        values.put(ACCOUNT_NAME, "Microsoft");
        values.put(USERNAME, "test2");
        values.put(ACCOUNT_PASSWORD, "password");
        sqLiteDatabase.insert(TABLE_ACCOUNT, null, values);

        // Sample Linkedin account
        values.put(ACCOUNT_NAME, "Linkedin");
        values.put(USERNAME, "test3");
        values.put(ACCOUNT_PASSWORD, "password");
        sqLiteDatabase.insert(TABLE_ACCOUNT, null, values);
    }

    // Delete the specified account
    public void deleteAccount(Account account) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_ACCOUNT, ID +" =?",
                new String[] {String.valueOf(account.getId())});
        database.close();
    }

    // Get all accounts from the database and return them as an ArrayList
    public ArrayList<Account> getAllAccounts() {
        ArrayList<Account> accountList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_ACCOUNT;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Account account = new Account(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3));
                accountList.add(account);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return accountList;
    }

    // Search the accounts with a specified keyword and return them as an ArrayList
    public ArrayList<Account> searchAccounts(String keyword) {
        ArrayList<Account> accountList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_ACCOUNT + " WHERE " +
                "accountName LIKE ?";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, new String[] { "%" + keyword + "%" });

        if (cursor.moveToFirst()) {
            do {
                Account account = new Account(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3));
                accountList.add(account);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return accountList;
    }

    // Add an account to the database with an Account object passed in
    public void addAccount(Account account) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_NAME, account.getAccountName());
        values.put(USERNAME, account.getUsername());
        values.put(ACCOUNT_PASSWORD, account.getAccountPassword());
        database.insert(TABLE_ACCOUNT, null, values);
        database.close();
    }

    // Edit account based on Account passed in
    public void editAccount(Account account) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_NAME, account.getAccountName());
        values.put(USERNAME, account.getUsername());
        values.put(ACCOUNT_PASSWORD, account.getAccountPassword());
        database.update(TABLE_ACCOUNT, values, ID + " = ?",
                new String[] {String.valueOf(account.getId())});
        database.close();
    }
}
