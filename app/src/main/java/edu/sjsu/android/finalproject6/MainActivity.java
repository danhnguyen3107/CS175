package edu.sjsu.android.finalproject6;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.customview.widget.Openable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    // NavController for the app
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up the navigation controller
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        // Set the start destination defined in nav graph as the top-level destination
        // i.e., there's no up button on the list fragment.
        NavigationUI.setupActionBarWithNavController(this, navController, (Openable) null);
        // Check if the custom keyboard is enabled for autofill
        checkKeyboardPermission();

    }

    // Method for checking custom keyboard permission
    InputMethodManager manager;
    private void checkKeyboardPermission() {
        // Check if the custom keyboard is enabled, if not, show a dialog
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

    // Dialog to enable custom keyboard
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

    // Method to create and navigate the options menu
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
            return true;
        } else if (item.getItemId() == R.id.info) {
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
        } else return super.onOptionsItemSelected(item);
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
