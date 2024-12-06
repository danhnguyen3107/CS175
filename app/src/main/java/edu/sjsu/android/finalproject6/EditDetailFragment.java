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

import edu.sjsu.android.finalproject6.databinding.FragmentEditDetailBinding;


public class EditDetailFragment extends Fragment {

    private Account account;

    private FragmentEditDetailBinding binding;

    public EditDetailFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String key = getContext().getString(R.string.argument_key);
            account = getArguments().getParcelable("Account");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditDetailBinding.inflate(inflater);
        binding.insertAccName.setText(account.getAccountName());
        binding.insertUserName.setText(account.getUsername());
        binding.insertAccPassword.setText(account.getAccountPassword());
        binding.saveBtn.setOnClickListener(this::editAccount);


        ImageButton togglePasswordButton = binding.togglePasswordVisibilityBtn;
        TextView passwordTextView = binding.insertAccPassword;

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



    private void editAccount(View view) {
        DatabaseHelper db = new DatabaseHelper(getContext());

        String accountName = binding.insertAccName.getText().toString();
        String username = binding.insertUserName.getText().toString();
        String accountPassword = binding.insertAccPassword.getText().toString();

        if (accountName.isEmpty() || username.isEmpty() || accountPassword.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (account != null) {
            account.setAccountName(accountName);
            account.setUsername(username);
            account.setAccountPassword(accountPassword);
        }

        assert account != null;
        db.editAccount(account);

        Toast.makeText(requireContext(), "Successfully saved changes.", Toast.LENGTH_SHORT).show();
        
        Bundle bundle = new Bundle();
        bundle.putParcelable(getContext().getString(R.string.argument_key), account);
        NavController controller = NavHostFragment.findNavController(this);
        controller.navigate(R.id.action_editDetailFragment_to_detailFragment, bundle);
    }
}
