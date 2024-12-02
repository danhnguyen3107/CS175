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

    private DatabaseHelper db;
    private View mainKeyboardView;
    private View accountSelectionView;

    @Override
    public void onCreate() {
        super.onCreate();
        db = new DatabaseHelper(this);
    }

    @Override
    public View onCreateInputView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout parent = new LinearLayout(this);
        mainKeyboardView = inflater.inflate(R.layout.keyboard_layout, parent, false);
        setupMainKeyboardView(mainKeyboardView);
        return mainKeyboardView;
    }

    private void setupMainKeyboardView(View view) {
        Button switchButton = view.findViewById(R.id.switch_to_accounts);
        switchButton.setOnClickListener(v -> showAccountSelectionView());
    }

    private void showAccountSelectionView() {
        if (accountSelectionView == null) {
            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout parent = new LinearLayout(this);
            accountSelectionView = inflater.inflate(R.layout.account_selection_layout, parent, false);

            LinearLayout accountList = accountSelectionView.findViewById(R.id.account_list);
            List<Account> accounts = db.getAllAccounts();

            for (Account account : accounts) {
                LinearLayout accountEntry = new LinearLayout(this);
                accountEntry.setOrientation(LinearLayout.VERTICAL);

                TextView accountNameTextView = new TextView(this);
                accountNameTextView.setText(getString(R.string.accountKeyboard, account.getAccountName()));
                accountEntry.addView(accountNameTextView);

                Button usernameButton = new Button(this);
                usernameButton.setText(getString(R.string.usernameKeyboard, account.getUsername()));
                usernameButton.setOnClickListener(v -> {
                    pasteCredential(account.getUsername());
                    switchToMainKeyboardView();
                });
                accountEntry.addView(usernameButton);

                // Password button
                Button passwordButton = new Button(this);
                passwordButton.setText(R.string.passwordKeyboard);
                passwordButton.setOnClickListener(v -> {
                    pasteCredential(account.getAccountPassword());
                    switchToMainKeyboardView();
                });
                accountEntry.addView(passwordButton);

                accountList.addView(accountEntry);
            }

            // Back button
            Button backButton = accountSelectionView.findViewById(R.id.back_button);
            backButton.setOnClickListener(v -> switchToMainKeyboardView());
        }

        setInputView(accountSelectionView);
    }

    private void pasteCredential(String value) {
        if (getCurrentInputConnection() != null) {
            getCurrentInputConnection().commitText(value, 1);
            Toast.makeText(this, "Credential pasted", Toast.LENGTH_SHORT).show();
        }
    }

    private void switchToMainKeyboardView() {
        setInputView(mainKeyboardView);
    }

}
