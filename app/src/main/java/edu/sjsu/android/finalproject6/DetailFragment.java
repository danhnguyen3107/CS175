package edu.sjsu.android.finalproject6;

import android.app.AlertDialog;
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


import edu.sjsu.android.finalproject6.databinding.FragmentDetailBinding;

public class DetailFragment extends Fragment {

    private Account account;

    private FragmentDetailBinding binding;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            String key = requireContext().getString(R.string.argument_key);
            account = getArguments().getParcelable(key);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //View view = inflater.inflate(R.layout.fragment_detail, container, false);
        binding = FragmentDetailBinding.inflate(inflater);

        binding.accountName.setText(account.getAccountName());
        binding.name.setText(account.getUsername());
        binding.password.setText(account.getAccountPassword());
//        binding.details.setMovementMethod();

        binding.editBtn.setOnClickListener(this::goEdit);
        binding.deleteBtn.setOnClickListener(this::showWarning);

        ImageButton togglePasswordButton = binding.togglePasswordVisibilityBtn;
        TextView passwordTextView = binding.password;

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

    private void goEdit(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(getContext().getString(R.string.argument_key), account);
        NavController controller = NavHostFragment.findNavController(this);
        controller.navigate(R.id.action_detailFragment_to_editDetailFragment2, bundle);
    }

    public void showWarning(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Warning");
        //builder.setMessage("Warning: ");
        builder.setMessage("Delete this account?");
        builder.setPositiveButton("Yes", (dialog, id) -> {
            // When user selects yes
            deleteAccount();
        });
        builder.setNegativeButton("No", (dialog, id) -> {
            // Do nothing when user clicks No
        });
        builder.create().show();
    }

    //TODO: delete account and return user to account list.
    private void deleteAccount() {
        DatabaseHelper db = new DatabaseHelper(getContext());
        db.deleteAccount(account);

        Bundle bundle = new Bundle();
        bundle.putParcelable(getContext().getString(R.string.argument_key), account);
        NavController controller = NavHostFragment.findNavController(this);
        controller.navigate(R.id.detail_to_list);
    }

}