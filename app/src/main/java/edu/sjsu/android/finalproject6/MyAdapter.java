package edu.sjsu.android.finalproject6;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.sjsu.android.finalproject6.databinding.RowLayoutBinding;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    // Local variables
    private final ArrayList<Account> accountList;
    private OnAccountClickedListener listener;

    // Constructor
    public MyAdapter(ArrayList<Account> items) {
        accountList = items;
    }

    // Account clicked listener interface
    public void setListener(OnAccountClickedListener listener) {
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowLayoutBinding binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding, listener);
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Account account = accountList.get(position);
        holder.accountNameView.setText(account.getAccountName());
        holder.usernameView.setText(account.getUsername());
    }

    // Return the size of accountList
    @Override
    public int getItemCount() {
        return accountList.size();
    }

    // ViewHolder class for the adapter
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView accountNameView;
        public final TextView usernameView;

        // Constructor for ViewHolder to set the account name, username, and click listener
        public ViewHolder(RowLayoutBinding binding, OnAccountClickedListener listener) {
            super(binding.getRoot());
            accountNameView = binding.accountName;
            usernameView = binding.username;
            binding.getRoot().setOnClickListener(v -> listener.onClick(getLayoutPosition()));
        }
    }
}