package edu.sjsu.android.finalproject6;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import edu.sjsu.android.finalproject6.databinding.RowLayoutBinding;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final List<Account> accountList;
    private edu.sjsu.android.finalproject6.OnAccountClickedListener listener;

    public MyAdapter(List<Account> items) {
        accountList = items;
    }
    public void setListener(OnAccountClickedListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowLayoutBinding binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        //return new ViewHolder(RowLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        return new ViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        //holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).id);
        //holder.mContentView.setText(mValues.get(position).content);
        Account account = accountList.get(position);
        holder.nameView.setText(account.getNameID());
        holder.phoneView.setText(account.getPhoneID());
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView nameView;
        public final TextView phoneView;

        public ViewHolder(RowLayoutBinding binding, OnAccountClickedListener listener) {
            super(binding.getRoot());
            nameView = binding.name;
            phoneView = binding.phone;
            binding.getRoot().setOnClickListener(v -> listener.onClick(getLayoutPosition()));
        }
    }
}