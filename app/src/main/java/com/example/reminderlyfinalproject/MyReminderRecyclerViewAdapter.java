package com.example.reminderlyfinalproject;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reminderlyfinalproject.databinding.FragmentItemBinding;
import com.example.reminderlyfinalproject.model.Reminder;

import java.util.List;


public class MyReminderRecyclerViewAdapter extends RecyclerView.Adapter<MyReminderRecyclerViewAdapter.ViewHolder> {

    private final List<Reminder> mValues;
    private SelectListener mListener;

    public MyReminderRecyclerViewAdapter(List<Reminder> items, SelectListener listener) {
        mValues = items;
        mListener = listener;
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
         holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClicked(mValues.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;

        public CardView cardView;

        public ConstraintLayout constraintLayout;
        public final TextView mContentView;
        public Reminder mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;

            //constraintLayout = mIdView.findViewById(R.id.layoutId);
           cardView = mIdView.findViewById(R.id.main_container);
            //cardView = mContentView.findViewById(R.id.main_container);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}