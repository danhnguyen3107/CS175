package edu.sjsu.android.finalproject6;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ListFragment extends Fragment {
    private ArrayList<Account> accountList;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountList = new ArrayList<>();
//        accountList.add(new Account(R.string.a1_email, R.string.a1_name,R.string.a1_phone, R.string.a1_password));
//        accountList.add(new Account(R.string.a2_email, R.string.a2_name, R.string.a2_phone, R.string.a2_password));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            MyAdapter adapter = new MyAdapter(accountList);
            adapter.setListener(this::onClick);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    public void onClick(int position) {
        /*if (position == accountList.size() - 1) {
            showWarning(position);
        }
        else {
            goDetail(position);
        }*/
        goDetail(position);
    }

    public void goDetail(int position){
        Account account = accountList.get(position);
        Bundle bundle = new Bundle();

        bundle.putParcelable(getContext().getString(R.string.argument_key), account);
        NavController controller = NavHostFragment.findNavController(this);
        controller.navigate(R.id.list_to_detail, bundle);
    }

    public void showWarning(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Warning");
        //builder.setMessage("Warning: ");
        builder.setMessage("This section is under construction. Proceed?");
        builder.setPositiveButton("Yes", (dialog, id) -> {
            // When user selects yes
            goDetail(position);
        });
        builder.setNegativeButton("No", (dialog, id) -> {
            // Do nothing when user clicks No
        });
        builder.create().show();
    }
}