package com.example.reminderlyfinalproject;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reminderlyfinalproject.databinding.FragmentItemBinding;
import com.example.reminderlyfinalproject.model.Reminder;

import java.util.List;


public class MyReminderRecyclerViewAdapter extends RecyclerView.Adapter<MyReminderRecyclerViewAdapter.ViewHolder> {

    private final List<Reminder> mValues;

    public MyReminderRecyclerViewAdapter(List<Reminder> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mIdView.setText(String.format("%d", mValues.get(position).reminderId));
        holder.mContentView.setText(mValues.get(position).reminderName);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public Reminder mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}