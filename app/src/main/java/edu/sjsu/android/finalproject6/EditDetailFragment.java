package edu.sjsu.android.accman;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.sjsu.android.accman.databinding.FragmentDetailBinding;

public class EditDetailFragment extends Fragment {

    private Account account;
    private Bundle bundle;

    public EditDetailFragment() {
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
        //View view = inflater.inflate(R.layout.fragment_edit_detail, container, false);

        FragmentDetailBinding binding = FragmentDetailBinding.inflate(inflater);
        //binding.insertEmail.setText(account.getEmailID());
        //binding.insertName.setText(account.getNameID());
        //binding.insertPhone.setText(account.getPhoneID());
        //binding.insertNPassword.setText(account.getPasswordID());
        //binding.saveBtn.setOnClickListener(this::returnDetail);

        return binding.getRoot();
    }

    //TODO: Saving the edits made
    private void returnDetail(View view) {
        //Bundle bundle = new Bundle();
        bundle.putParcelable(getContext().getString(R.string.argument_key), account);
        NavController controller = NavHostFragment.findNavController(this);
        controller.navigate(R.id.editDetail_to_detail);
    }
}