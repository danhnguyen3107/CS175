package edu.sjsu.android.finalproject6;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditDetailBinding.inflate(inflater);
        binding.insertAccName.setText(account.getAccountName());
        binding.insertUserName.setText(account.getUsername());
        binding.insertAccPassword.setText(account.getAccountPassword());
        binding.saveBtn.setOnClickListener(this::editAccount);

        return binding.getRoot();
    }



    private void editAccount(View view) {
        DatabaseHelper db =new DatabaseHelper(getContext());

        String accountName = binding.insertAccName.getText().toString();
        String username = binding.insertUserName.getText().toString();
        String accountPassword = binding.insertAccPassword.getText().toString();


        //Todo: edit Account in database


        Bundle bundle = new Bundle();
        bundle.putParcelable(getContext().getString(R.string.argument_key), account);
        NavController controller = NavHostFragment.findNavController(this);
        controller.navigate(R.id.action_editDetailFragment_to_detailFragment, bundle);
    }
}