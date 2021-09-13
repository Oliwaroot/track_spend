package com.example.track_spend.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.track_spend.R;
import com.example.track_spend.adapters.ExpenseAdapter;
import com.example.track_spend.room.Expense;
import com.example.track_spend.viewmodel.ExpenseViewModel;

import java.util.List;

public class ExpensesFragment extends Fragment {
    private ExpenseViewModel expenseViewModel;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.expenses_fragment, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.expenses_recycler2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        ExpenseAdapter adapter = new ExpenseAdapter();
        recyclerView.setAdapter(adapter);

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseViewModel.getAllExpenses().observe(getViewLifecycleOwner(), new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expense) {
                adapter.setExpense(expense);
            }
        });

        return v;
    }
}
