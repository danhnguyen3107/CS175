package edu.sjsu.android.finalproject6;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.sjsu.android.finalproject6.databinding.FragmentAddAccountBinding;

public class AddAccountFragment extends Fragment {

    FragmentAddAccountBinding binding;
    Account account;
    //private Database database;

    public AddAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentAddAccountBinding.inflate(getLayoutInflater());
        account = new Account();

        //database = Database.getInstance(requireContext());

        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAccount();
            }
        });
    }

    public void addAccount() {
        String accountName = binding.accountName.getText().toString().trim();
        String username = binding.userName.getText().toString().trim();
        String password = binding.password.getText().toString().trim();

        if (accountName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        account.setAccountName(accountName);
        account.setUsername(username);
        account.setAccountPassword(password);

        // TODO: Add account to database

        Toast.makeText(requireContext(), "Account added successfully", Toast.LENGTH_SHORT).show();

        binding.accountName.setText("");
        binding.userName.setText("");
        binding.password.setText("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_account, container, false);
    }
}