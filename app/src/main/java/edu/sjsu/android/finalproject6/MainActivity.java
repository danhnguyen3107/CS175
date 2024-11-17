package edu.sjsu.android.finalproject6;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.customview.widget.Openable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    EditText accountName;
    EditText username;
    EditText password;
    EditText idInput;
    EditText searchText;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        // Set the start destination defined in nav graph as the top-level destination
        // i.e., there's no up button on the list fragment.
        NavigationUI.setupActionBarWithNavController(this, navController, (Openable) null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.unin) {
            Intent delete = new Intent(Intent.ACTION_DELETE,
                    Uri.parse("package:" + getPackageName()));
            startActivity(delete);

            //Log.d(">:{D :P XD :l :3 :( :)", "Uninstall clicked");
            return true;
        } else if (item.getItemId() == R.id.info) {
            //Log.d("EEEEEEEEEEEEEEEEE", "Information clicked");
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment);
            assert navHostFragment != null;
            NavController navController = navHostFragment.getNavController();
            navController.navigate(R.id.goto_info);
            return true;
        } else if (item.getItemId() == R.id.add) {
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment);
            assert navHostFragment != null;
            NavController navController = navHostFragment.getNavController();
            navController.navigate(R.id.goto_add);
            return true;
        }

        else return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp(){
        return navController.navigateUp() || super.onSupportNavigateUp();
    }









    public void test(){
        setContentView(R.layout.test);
        accountName = (EditText) findViewById(R.id.accountname);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        idInput = (EditText) findViewById(R.id.idInput);
        searchText = (EditText) findViewById(R.id.search);
        db = new DatabaseHelper(this);

    }
    public void btnAddPressed(View v){
        Account temp = new Account(username.getText().toString(), accountName.getText().toString(), password.getText().toString());

        db.addAccount(temp);
    }

    public void btnDeletePressed(View v){
        Account temp = new Account();
        temp.setId(Integer.parseInt(idInput.getText().toString()));
        db.deleteAccount(temp);
    }


    public void btnGetPressed(View v){
        ArrayList<Account> accounts =  db.getAllAccounts();

        for (Account acc : accounts){
            Log.i("Database Info: ", "ID: " + acc.getId() + " AccountName: " + acc.getAccountName() + " Username: " + acc.getUsername() + " Password: " + acc.getAccountPassword());
        }


    }

    public void btnDeleteAllPressed(View v){
        db.deleteAllAccounts();
    }

    public void btnEditPressed(View v){
        Account temp = new Account(
                Integer.parseInt(idInput.getText().toString()),
                username.getText().toString(),
                accountName.getText().toString(),
                password.getText().toString());
        db.editAccount(temp);
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
}