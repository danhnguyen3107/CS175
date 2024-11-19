package edu.sjsu.android.finalproject6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    protected static String DATABASE_NAME = "Accounts";
    protected static String TABLE_ACCOUNT = "Accounts";
    protected static String ID = "id";
    protected static String ACCOUNT_NAME = "accountName";
    protected static String USERNAME = "username";
    protected static String ACCOUNT_PASSWORD = "password";
    protected static int VERSION = 1;

    public DatabaseHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createAccountTable = String.format("CREATE TABLE %s " +
                "(%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL);", TABLE_ACCOUNT, ID, ACCOUNT_NAME, USERNAME, ACCOUNT_PASSWORD);
        sqLiteDatabase.execSQL(createAccountTable);


        Log.d("DatabaseHelper", "onCreate called - creating database");
        populateData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d("DatabaseHelper", "onUpgrade called - upgrading database");

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        onCreate(sqLiteDatabase);
    }

    public  void populateData(SQLiteDatabase sqLiteDatabase) {
        ContentValues values = new ContentValues();

        values.put(ACCOUNT_NAME, "Google");
        values.put(USERNAME, "test1");
        values.put(ACCOUNT_PASSWORD, "password");
        sqLiteDatabase.insert(TABLE_ACCOUNT, null, values);

        values.put(ACCOUNT_NAME, "Microsoft");
        values.put(USERNAME, "test2");
        values.put(ACCOUNT_PASSWORD, "password");
        sqLiteDatabase.insert(TABLE_ACCOUNT, null, values);

        values.put(ACCOUNT_NAME, "Linkedin");
        values.put(USERNAME, "test3");
        values.put(ACCOUNT_PASSWORD, "password");
        sqLiteDatabase.insert(TABLE_ACCOUNT, null, values);
    }



    public void deleteAccount(Account account) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_ACCOUNT, ID +" =?",
                new String[] {String.valueOf(account.getId())});
        database.close();
    }

    public void deleteAllAccounts() {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM " + TABLE_ACCOUNT);
        database.close();
    }

    public ArrayList<Account> getAllAccounts() {
        ArrayList<Account> accountList = new ArrayList<Account>();

        String query = "SELECT * FROM " + TABLE_ACCOUNT;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Account account = new Account();
                account.setId(cursor.getInt(0));
                account.setAccountName(cursor.getString(1));
                account.setUsername(cursor.getString(2));
                account.setAccountPassword(cursor.getString(3));
                accountList.add(account);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return accountList;
    }




    public ArrayList<Account> searchAccounts(String keyword) {
        ArrayList<Account> accountList = new ArrayList<Account>();

        String query = "SELECT * FROM " + TABLE_ACCOUNT + " WHERE " +
                "accountName LIKE ?";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, new String[] { "%" + keyword + "%" });

        if (cursor.moveToFirst()) {
            do {
                Account account = new Account();
                account.setId(cursor.getInt(0));
                account.setAccountName(cursor.getString(1));
                account.setUsername(cursor.getString(2));
                account.setAccountPassword(cursor.getString(3));
                accountList.add(account);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return accountList;
    }
}
