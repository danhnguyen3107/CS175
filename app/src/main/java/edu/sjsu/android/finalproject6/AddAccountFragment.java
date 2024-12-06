package edu.sjsu.android.finalproject6;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddAccountBinding.inflate(inflater);
        binding.saveBtn.setOnClickListener(this::addAccount);


        ImageButton togglePasswordButton = binding.togglePasswordVisibilityBtn;
        TextView passwordTextView = binding.insertPassword;

        passwordTextView.setTransformationMethod(new PasswordTransformationMethod());

        // Toggle the password visibility when the button is clicked
        togglePasswordButton.setOnClickListener(v -> {
            if (passwordTextView.getTransformationMethod() instanceof PasswordTransformationMethod) {
                // Show password
                passwordTextView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                togglePasswordButton.setImageResource(android.R.drawable.ic_menu_view); // Change icon to 'eye open'
            } else {
                // Hide password
                passwordTextView.setTransformationMethod(new PasswordTransformationMethod());
                togglePasswordButton.setImageResource(android.R.drawable.ic_secure); // Change icon to 'eye closed'
            }
        });


        return binding.getRoot();
    }


    public void addAccount(View view) {
        DatabaseHelper db = new DatabaseHelper(getContext());

        String accountName = binding.insertAccountName.getText().toString();
        String username = binding.insertName.getText().toString();
        String password = binding.insertPassword.getText().toString();

        if (accountName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        Account account = new Account();
        account.setAccountName(accountName);
        account.setUsername(username);
        account.setAccountPassword(password);

        db.addAccount(account);

        Toast.makeText(requireContext(), "Account added successfully.", Toast.LENGTH_SHORT).show();


        NavController controller = NavHostFragment.findNavController(this);
        controller.navigate(R.id.action_addAccountFragment_to_listFragment);
    }
}
