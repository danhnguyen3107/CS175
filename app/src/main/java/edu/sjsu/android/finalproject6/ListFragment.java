package edu.sjsu.android.finalproject6;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;


public class ListFragment extends Fragment {
    private ArrayList<Account> accountList;

    // onCreate method
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get all accounts from the database
        try (DatabaseHelper db = new DatabaseHelper(requireContext())) {
            accountList = db.getAllAccounts();
        } catch (Exception e) {
            Log.wtf("ListFragment", e.getMessage());
        }

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

    // Method to navigate to the detail fragment
    public void onClick(int position) {
        goDetail(position);
    }

    // Uses the NavController to navigate to the detail fragment
    public void goDetail(int position){
        Account account = accountList.get(position);
        Bundle bundle = new Bundle();

        bundle.putParcelable(requireContext().getString(R.string.argument_key), account);
        NavController controller = NavHostFragment.findNavController(this);
        controller.navigate(R.id.list_to_detail, bundle);
    }

    // Filter accounts based on search
    public void filterAccounts(String text){
        try (DatabaseHelper db = new DatabaseHelper(requireContext())) {
            accountList = db.searchAccounts(text);
            updateRecyclerView(accountList);
        } catch (Exception e) {
            Log.wtf("ListFragment", e.getMessage());
        }
    }

    // Method to update recycler view
    private void updateRecyclerView(ArrayList<Account> filteredList) {
        MyAdapter adapter = new MyAdapter(filteredList);
        adapter.setListener(this::onClick);
        RecyclerView recyclerView = requireView().findViewById(R.id.list); // Adjust ID as necessary
        recyclerView.setAdapter(adapter);
    }
}