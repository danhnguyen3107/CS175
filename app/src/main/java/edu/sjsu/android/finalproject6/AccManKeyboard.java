package edu.sjsu.android.finalproject6;

import android.inputmethodservice.InputMethodService;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class AccManKeyboard extends InputMethodService {

    // Local variables
    private DatabaseHelper db;
    private View mainKeyboardView;
    private View accountSelectionView;

    // onCreate method
    @Override
    public void onCreate() {
        // Call super method
        super.onCreate();
        // Initialize database
        db = new DatabaseHelper(this);
    }

    @Override
    public View onCreateInputView() {
        // Create main keyboard view
        LayoutInflater inflater = LayoutInflater.from(this);
        // Create parent layout
        LinearLayout parent = new LinearLayout(this);
        // Inflate main keyboard layout
        mainKeyboardView = inflater.inflate(R.layout.keyboard_layout, parent, false);
        // Setup main keyboard view
        setupMainKeyboardView(mainKeyboardView);
        // Return main keyboard view
        return mainKeyboardView;
    }

    // Method to setup main keyboard view
    private void setupMainKeyboardView(View view) {
        // Switch to accounts button
        Button switchButton = view.findViewById(R.id.switch_to_accounts);
        // Set on click listener for switch button to show account selection view
        switchButton.setOnClickListener(v -> showAccountSelectionView());
    }

    // Method to show account selection view
    private void showAccountSelectionView() {
        // Check if account selection view is null
        if (accountSelectionView == null) {
            // Create account selection view
            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout parent = new LinearLayout(this);
            // Inflate account selection layout
            accountSelectionView = inflater.inflate(R.layout.account_selection_layout, parent, true);
            // Setup account selection view
            LinearLayout accountList = accountSelectionView.findViewById(R.id.account_list);
            List<Account> accounts = db.getAllAccounts();
            // Loop through accounts and add them to the account list
            for (Account account : accounts) {
                // Create account entry
                LinearLayout accountEntry = new LinearLayout(this);
                accountEntry.setOrientation(LinearLayout.VERTICAL);

                // Account name text view
                TextView accountNameTextView = new TextView(this);
                accountNameTextView.setText(getString(R.string.accountKeyboard, account.getAccountName()));
                accountEntry.addView(accountNameTextView);

                // Username and password buttons
                Button usernameButton = new Button(this);
                usernameButton.setText(getString(R.string.usernameKeyboard, account.getUsername()));
                // Set on click listener for username button to paste username and switch to main keyboard view
                usernameButton.setOnClickListener(v -> {
                    pasteCredential(account.getUsername());
                    switchToMainKeyboardView();
                });
                accountEntry.addView(usernameButton);

                // Password button
                Button passwordButton = new Button(this);
                passwordButton.setText(R.string.passwordKeyboard);
                // On click listener for pasting password
                passwordButton.setOnClickListener(v -> {
                    pasteCredential(account.getAccountPassword());
                    switchToMainKeyboardView();
                });

                // Add password button to account entry
                accountEntry.addView(passwordButton);
                // Add account entry to account list
                accountList.addView(accountEntry);
            }

            // Back button
            Button backButton = accountSelectionView.findViewById(R.id.back_button);
            backButton.setOnClickListener(v -> switchToMainKeyboardView());
        }
        // Set input view to account selection view
        setInputView(accountSelectionView);
    }

    // Method to paste credential
    private void pasteCredential(String value) {
        if (getCurrentInputConnection() != null) {
            getCurrentInputConnection().commitText(value, 1);
            Toast.makeText(this, "Credential pasted", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to switch to main keyboard view
    private void switchToMainKeyboardView() {
        setInputView(mainKeyboardView);
    }

}