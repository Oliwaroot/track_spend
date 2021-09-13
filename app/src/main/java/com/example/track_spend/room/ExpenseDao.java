package com.example.track_spend.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.track_spend.pojo.TotalsPojo;

import java.util.List;

@Dao
public interface ExpenseDao {

    @Insert
    void insert(Expense expense);

    @Update
    void update(Expense expense);

    @Query("SELECT * FROM expense_table")
    LiveData<List<Expense>> getAllExpenses();

    @Query("SELECT SUM(amount) as total FROM expense_table")
    LiveData<TotalsPojo> getTotal();

    @Query("SELECT SUM(amount) as total FROM expense_table WHERE category = 'Transport'")
    LiveData<TotalsPojo> getTotalTransport();

    @Query("SELECT SUM(amount) as total FROM expense_table WHERE category = 'Automotive'")
    LiveData<TotalsPojo> getTotalAutomotive();

    @Query("SELECT SUM(amount) as total FROM expense_table WHERE category = 'Foodstuff'")
    LiveData<TotalsPojo> getTotalFoods();

    @Query("SELECT SUM(amount) as total FROM expense_table WHERE category = 'Wearable'")
    LiveData<TotalsPojo> getTotalWearable();

    @Query("SELECT SUM(amount) as total FROM expense_table WHERE category = 'Utilities'")
    LiveData<TotalsPojo> getTotalUtilities();


}
