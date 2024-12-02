package edu.sjsu.android.finalproject6;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.sjsu.android.finalproject6.databinding.FragmentAddAccountBinding;


public class AddAccountFragment extends Fragment {

    FragmentAddAccountBinding binding;


    public AddAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //database = Database.getInstance(requireContext());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddAccountBinding.inflate(inflater);
        binding.saveBtn.setOnClickListener(this::addAccount);

        return binding.getRoot();
    }


    public void addAccount(View view) {

        DatabaseHelper db = new DatabaseHelper(getContext());

        String accountName = binding.insertAccountName.getText().toString();
        String username = binding.insertName.getText().toString();
        String password = binding.insertPassword.getText().toString();

        if (accountName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Account account = new Account();
        account.setAccountName(accountName);
        account.setUsername(username);
        account.setAccountPassword(password);

        // TODO: Add account to database
        db.addAccount(account);


        Toast.makeText(requireContext(), "Account added successfully", Toast.LENGTH_SHORT).show();


        NavController controller = NavHostFragment.findNavController(this);
        controller.navigate(R.id.action_addAccountFragment_to_listFragment);
    }
}