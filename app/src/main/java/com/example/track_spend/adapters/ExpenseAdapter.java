package com.example.track_spend.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.track_spend.R;
import com.example.track_spend.pojo.TotalsPojo;
import com.example.track_spend.room.Expense;


import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder> {

    private List<Expense> expense = new ArrayList<>();
    private OnItemClickListener listener;
    private TotalsPojo totalsPojo = new TotalsPojo();

    @NonNull
    @Override
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_item, parent, false);
        return new ExpenseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHolder holder, int position) {
        Expense currentExpense = expense.get(position);
        holder.tvName.setText(currentExpense.getName());
        holder.tvCategory.setText(currentExpense.getCategory());
        holder.tvAmount.setText(String.valueOf(currentExpense.getAmount()));
    }

    @Override
    public int getItemCount() {
        return expense.size();
    }

    public void setTotalsPojo(TotalsPojo totalsPojo){
        this.totalsPojo = totalsPojo;
    }

    public void setExpense(List<Expense> expense) {
        this.expense = expense;
        notifyDataSetChanged();
    }

    class ExpenseHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvCategory;
        private TextView tvAmount;

        public ExpenseHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            tvCategory = itemView.findViewById(R.id.category);
            tvAmount = itemView.findViewById(R.id.amount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(expense.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Expense expense);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
