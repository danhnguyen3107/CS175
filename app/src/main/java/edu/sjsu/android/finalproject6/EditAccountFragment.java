package edu.sjsu.android.finalproject6;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.sjsu.android.finalproject6.databinding.FragmentEditAccountBinding;

public class EditAccountFragment extends Fragment {

    private Account account;
    private FragmentEditAccountBinding binding;
    //private Database database;

    public EditAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            account = args.getParcelable("account");
        }

        //accountDatabase = database.getInstance(requireContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditAccountBinding.inflate(inflater, container, false);
        if (account != null) {
            binding.accountName.setText(account.getAccountName());
            binding.userName.setText(account.getUsername());
            binding.password.setText(account.getAccountPassword());
        }

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAccount();
            }
        });

        return binding.getRoot();
    }

    private void saveAccount() {
        String accountName = binding.accountName.getText().toString().trim();
        String username = binding.userName.getText().toString().trim();
        String password = binding.password.getText().toString().trim();

        if (TextUtils.isEmpty(accountName) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        account.setAccountName(accountName);
        account.setUsername(username);
        account.setAccountPassword(password);

        // TODO: Add to database after merge
    }
}
