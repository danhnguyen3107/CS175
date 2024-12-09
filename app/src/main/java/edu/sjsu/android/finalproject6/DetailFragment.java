package edu.sjsu.android.finalproject6;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import edu.sjsu.android.finalproject6.databinding.FragmentDetailBinding;

public class DetailFragment extends Fragment {
    // Private variable the detail fragment is based on
    private Account account;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the account from the arguments
        if (getArguments() != null) {
            String key = requireContext().getString(R.string.argument_key);
            account = getArguments().getParcelable(key);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentDetailBinding binding = FragmentDetailBinding.inflate(inflater);

        // Set the account details
        binding.accountName.setText(account.getAccountName());
        binding.name.setText(account.getUsername());
        binding.password.setText(account.getAccountPassword());

        // On click listeners for edit and delete
        binding.editBtn.setOnClickListener(this::goEdit);
        binding.deleteBtn.setOnClickListener(this::showWarning);

        // Toggle password visibility button
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

        binding.name.setOnClickListener(v -> copyToClipboard(binding.name.getText().toString(), "Username copied to clipboard"));
        binding.password.setOnClickListener(v -> copyToClipboard(binding.password.getText().toString(), "Password copied to clipboard"));

        return binding.getRoot();
    }

    // Method to navigate to edit fragment
    private void goEdit(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(requireContext().getString(R.string.argument_key), account);
        NavController controller = NavHostFragment.findNavController(this);
        controller.navigate(R.id.action_detailFragment_to_editDetailFragment2, bundle);
    }

    // Method to show warning dialog for deleting an account
    public void showWarning(View view) {
        // Create a warning dialog to confirm deletion
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Warning");
        builder.setMessage("Delete this account?");
        builder.setPositiveButton("Yes", (dialog, id) -> deleteAccount());
        builder.setNegativeButton("No", (dialog, id) -> {
            // Do nothing when user clicks No
        });
        builder.create().show();
    }

    // Method to delete the current shown account
    private void deleteAccount() {
        // Try resource to delete current account
        try (DatabaseHelper db = new DatabaseHelper(requireContext())) {
            db.deleteAccount(account);
            Bundle bundle = new Bundle();
            bundle.putParcelable(requireContext().getString(R.string.argument_key), account);
            NavController controller = NavHostFragment.findNavController(this);
            controller.navigate(R.id.detail_to_list);
        } catch (Exception e) {
            Log.wtf("DetailFragment", e.getMessage());
        }
    }


    private void copyToClipboard(String text, String message) {
        ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

}