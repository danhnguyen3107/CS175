package edu.sjsu.android.finalproject6;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;




public class MainActivity extends AppCompatActivity {


    EditText searchText;
    EditText idInput;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.test);

        // delete database the old database when run app
        deleteDatabase("Accounts");

        searchText = (EditText) findViewById(R.id.search);
        idInput = (EditText) findViewById(R.id.idInput);

        db = new DatabaseHelper(this);
    }
    public void btnSearchPressed(View v){
        String text = searchText.getText().toString();
        ArrayList<Account> accounts =  db.searchAccounts(text);
        if (accounts.isEmpty()){
            Log.i("Message: ", "there is not account match");
        }
        for (Account acc : accounts){
            Log.i("Database Info: ", "ID: " + acc.getId() + " AccountName: " + acc.getAccountName() + " Username: " + acc.getUsername() + " Password: " + acc.getAccountPassword());
        }


    }

    public void btnDeletePressed(View v){
        Account temp = new Account();
        temp.setId(Integer.parseInt(idInput.getText().toString()));
        db.deleteAccount(temp);
    }
    public void btnDeleteAllPressed(View v){
        db.deleteAllAccounts();
    }


}