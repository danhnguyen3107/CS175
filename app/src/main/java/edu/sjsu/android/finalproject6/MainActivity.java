package edu.sjsu.android.finalproject6;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.autofill.AutofillManager;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.customview.widget.Openable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
//    EditText accountName;
//    EditText username;
//    EditText password;
//    EditText idInput;
//    EditText searchText;
//    DatabaseHelper db;

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

        checkKeyboardPermission();

    }

    InputMethodManager manager;
    private void checkKeyboardPermission() {
        manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (manager != null) {
            for (InputMethodInfo inputMethodInfo : manager.getEnabledInputMethodList()) {
                ComponentName componentName = inputMethodInfo.getComponent();
                if (TextUtils.equals(componentName.getPackageName(), getPackageName())) {
                    return;
                }
            }
            keyboardDialog();
        }
    }

    private void keyboardDialog() {
        new AlertDialog.Builder(this).setTitle("Enable custom keyboard to autofill username and passwords.")
                .setMessage("View custom keyboard settings?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS);
                    startActivity(intent);
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);

                return false;
            }
        });

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
        } else if (item.getItemId() == R.id.serv) {
            keyboardDialog();
            return true;
        } else if (item.getItemId() == R.id.home) {
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
            assert navHostFragment != null;
            NavController navController = navHostFragment.getNavController();
            navController.navigate(R.id.goto_home);
            return true;
        } else if (item.getItemId() == R.id.search) {
            //TODO: Implement searching



            return true;
        }
        else return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp(){
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    private void performSearch(String query) {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);

        if (navHostFragment != null) {
            Fragment currentFragment = navHostFragment.getChildFragmentManager().getPrimaryNavigationFragment();

            if (currentFragment instanceof ListFragment) {
                ((ListFragment) currentFragment).filterAccounts(query);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
