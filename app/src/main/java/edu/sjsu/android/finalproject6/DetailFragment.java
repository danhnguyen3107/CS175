package edu.sjsu.android.finalproject6;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.sjsu.android.finalproject6.databinding.FragmentDetailBinding;

public class DetailFragment extends Fragment {

    private Account account;
    private Bundle bundle;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bundle argument = getArguments();
        bundle = getArguments();

        if (getArguments() != null) {
            String key = requireContext().getString(R.string.argument_key);
            account = bundle.getParcelable(key);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //View view = inflater.inflate(R.layout.fragment_detail, container, false);
        FragmentDetailBinding binding = FragmentDetailBinding.inflate(inflater);

//        binding.email.setText(account.getEmailID());
//        binding.name.setText(account.getNameID());
//        binding.phone.setText(account.getPhoneID());
//        binding.password.setText(account.getPasswordID());
        //binding.details.setMovementMethod();

        binding.editBtn.setOnClickListener(this::goEdit);
        binding.deleteBtn.setOnClickListener(this::showWarning);

        return binding.getRoot();
    }

    private void goEdit(View view) {
        //Bundle bundle = new Bundle();
        bundle.putParcelable(getContext().getString(R.string.argument_key), account);
        NavController controller = NavHostFragment.findNavController(this);
        controller.navigate(R.id.detail_to_editDetail);
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
        //Bundle bundle = new Bundle();
        bundle.putParcelable(getContext().getString(R.string.argument_key), account);
        NavController controller = NavHostFragment.findNavController(this);
        controller.navigate(R.id.detail_to_list);
    }

}