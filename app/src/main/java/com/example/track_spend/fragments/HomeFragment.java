package com.example.track_spend.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.track_spend.AddOrEditExpense;
import com.example.track_spend.R;
import com.example.track_spend.adapters.ExpenseAdapter;
import com.example.track_spend.pojo.TotalsPojo;
import com.example.track_spend.room.Expense;
import com.example.track_spend.viewmodel.ExpenseViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    View v;
    private ExpenseViewModel expenseViewModel;
    public static final int EDIT_EXPENSE_REQUEST = 2;
    public static int counter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.home_fragment, container, false);

        counter = 0;

        RecyclerView recyclerView = v.findViewById(R.id.expenses_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        TextView textView = v.findViewById(R.id.totals);

        ExpenseAdapter adapter = new ExpenseAdapter();
        recyclerView.setAdapter(adapter);

        PieChart pieChart = v.findViewById(R.id.pie_chart);
        ArrayList<PieEntry> entries  = new ArrayList<>();

        PieDataSet pieDataSet = new PieDataSet(entries, "Entries");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Example");
        pieChart.animateY(1000);
//        pieChart.animate();

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseViewModel.getAllExpenses().observe(getViewLifecycleOwner(), new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expense) {
                adapter.setExpense(expense);
            }
        });

        expenseViewModel.getTotal().observe(getViewLifecycleOwner(), new Observer<TotalsPojo>() {
            @Override
            public void onChanged(TotalsPojo totalsPojo) {
                adapter.setTotalsPojo(totalsPojo);
                textView.setText(String.valueOf(totalsPojo.total));
            }
        });

        expenseViewModel.getTotalTransport().observe(getViewLifecycleOwner(), new Observer<TotalsPojo>() {
            @Override
            public void onChanged(TotalsPojo totalsPojo) {
                if(counter >= 5){
                    pieDataSet.removeFirst();
                }
                counter++;
                adapter.setTotalsPojo(totalsPojo);
                PieEntry pieEntry = new PieEntry(totalsPojo.total, "Transport");
                pieDataSet.addEntry(pieEntry);
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
                pieChart.animateY(1000);
            }
        });

        expenseViewModel.getTotalAutomotive().observe(getViewLifecycleOwner(), new Observer<TotalsPojo>() {
            @Override
            public void onChanged(TotalsPojo totalsPojo) {
                if(counter >= 5){
                    pieDataSet.removeFirst();
                }
                counter++;
                adapter.setTotalsPojo(totalsPojo);
                PieEntry pieEntry = new PieEntry(totalsPojo.total, "Automotive");
                pieDataSet.addEntry(pieEntry);
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
                pieChart.animateY(1000);
            }
        });

        expenseViewModel.getTotalFoods().observe(getViewLifecycleOwner(), new Observer<TotalsPojo>() {
            @Override
            public void onChanged(TotalsPojo totalsPojo) {
                if(counter >= 5){
                    pieDataSet.removeFirst();
                }
                counter++;
                adapter.setTotalsPojo(totalsPojo);
                PieEntry pieEntry = new PieEntry(totalsPojo.total, "Foodstuff");
                pieDataSet.addEntry(pieEntry);
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
                pieChart.animateY(1000);
            }
        });

        expenseViewModel.getTotalWearable().observe(getViewLifecycleOwner(), new Observer<TotalsPojo>() {
            @Override
            public void onChanged(TotalsPojo totalsPojo) {
                if(counter >= 5){
                    pieDataSet.removeFirst();
                }
                counter++;
                adapter.setTotalsPojo(totalsPojo);
                PieEntry pieEntry = new PieEntry(totalsPojo.total, "Wearable");
                pieDataSet.addEntry(pieEntry);
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
                pieChart.animateY(1000);
            }
        });

        expenseViewModel.getTotalUtilities().observe(getViewLifecycleOwner(), new Observer<TotalsPojo>() {
            @Override
            public void onChanged(TotalsPojo totalsPojo) {
                if(counter >= 5){
                    pieDataSet.removeFirst();
                }
                counter++;
                adapter.setTotalsPojo(totalsPojo);
                PieEntry pieEntry = new PieEntry(totalsPojo.total, "Utilities" );
                pieDataSet.addEntry(pieEntry);
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
                pieChart.animateY(1000);
            }
        });

        adapter.setOnItemClickListener(new ExpenseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Expense expense) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
                TextView textView;
                TextView textView2;
                TextView textView3;
                TextView textView4;
                TextView textView5;
                Button editButton;
                Button backButton;

                View dialogView = inflater.inflate(R.layout.view_expense_item_details, null);
                dialogBuilder.setView(dialogView);
                textView = dialogView.findViewById(R.id.expense_title);
                textView2 = dialogView.findViewById(R.id.expense_category_name);
                textView3 = dialogView.findViewById(R.id.expense_description);
                textView4 = dialogView.findViewById(R.id.expense_amount);
                textView5 = dialogView.findViewById(R.id.date_created);
                editButton = dialogView.findViewById(R.id.edit_expense_button);
                backButton = dialogView.findViewById(R.id.back_button);

                textView.setText(expense.getName());
                textView2.setText(expense.getCategory());
                textView3.setText(expense.getDescription());
                textView4.setText(String.valueOf(expense.getAmount()));
                textView5.setText(expense.getCurrentDate());


                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), AddOrEditExpense.class);
                        intent.putExtra(AddOrEditExpense.EXTRA_ID, expense.getId());
                        intent.putExtra(AddOrEditExpense.EXTRA_NAME, expense.getName());
                        intent.putExtra(AddOrEditExpense.EXTRA_CATEGORY, expense.getCategory());
                        intent.putExtra(AddOrEditExpense.EXTRA_DESCRIPTION, expense.getDescription());
                        intent.putExtra(AddOrEditExpense.EXTRA_AMOUNT, String.valueOf(expense.getAmount()));
                        intent.putExtra(AddOrEditExpense.EXTRA_DATE, expense.getCurrentDate());
                        requireActivity().startActivityForResult(intent, EDIT_EXPENSE_REQUEST);
                        alertDialog.dismiss();
                    }
                });

                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        return v;
    }
}
