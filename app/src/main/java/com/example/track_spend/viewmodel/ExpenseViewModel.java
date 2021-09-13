package com.example.track_spend.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.track_spend.pojo.TotalsPojo;
import com.example.track_spend.repository.ExpenseRepository;
import com.example.track_spend.room.Expense;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {
    private ExpenseRepository expenseRepository;
    private LiveData<List<Expense>> allExpenses;
    private LiveData<TotalsPojo> totalsPojo;
    private LiveData<TotalsPojo> totalTransport;
    private LiveData<TotalsPojo> totalAutomotive;
    private LiveData<TotalsPojo> totalFood;
    private LiveData<TotalsPojo> totalWearable;
    private LiveData<TotalsPojo> totalUtilities;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        expenseRepository = new ExpenseRepository(application);
        allExpenses = expenseRepository.getAllExpenses();
        totalsPojo = expenseRepository.getTotal();
        totalTransport = expenseRepository.getTotalTransport();
        totalAutomotive = expenseRepository.getTotalAutomotive();
        totalFood = expenseRepository.getTotalFoods();
        totalWearable = expenseRepository.getTotalWearable();
        totalUtilities = expenseRepository.getTotalUtilities();
    }

    public void insert(Expense expense){
        expenseRepository.insert(expense);
    }
    public void update(Expense expense){
        expenseRepository.update(expense);
    }
    public LiveData<List<Expense>> getAllExpenses(){
        return allExpenses;
    }

    public LiveData<TotalsPojo> getTotal(){
        return totalsPojo;
    }

    public LiveData<TotalsPojo> getTotalTransport(){
        return totalTransport;
    }

    public LiveData<TotalsPojo> getTotalAutomotive(){
        return totalAutomotive;
    }

    public LiveData<TotalsPojo> getTotalFoods(){
        return totalFood;
    }

    public LiveData<TotalsPojo> getTotalWearable(){
        return totalWearable;
    }

    public LiveData<TotalsPojo> getTotalUtilities(){
        return totalUtilities;
    }
}
