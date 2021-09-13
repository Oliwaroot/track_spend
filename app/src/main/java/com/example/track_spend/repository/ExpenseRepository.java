package com.example.track_spend.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.track_spend.pojo.TotalsPojo;
import com.example.track_spend.room.Expense;
import com.example.track_spend.room.ExpenseDao;
import com.example.track_spend.room.ExpenseDb;

import java.util.List;

public class ExpenseRepository {

    private ExpenseDao expenseDao;
    private LiveData<List<Expense>> allExpenses;
    private LiveData<TotalsPojo> totalsPojo;

    public ExpenseRepository(Application application){
        ExpenseDb expenseDb = ExpenseDb.getInstance(application);
        expenseDao = expenseDb.expenseDao();
        allExpenses = expenseDao.getAllExpenses();
        totalsPojo = expenseDao.getTotal();
    }

    public void insert(Expense expense){
        new InsertExpenditureAsyncTask(expenseDao).execute(expense);
    }

    public void update(Expense expense){
        new UpdateExpenditureAsyncTask(expenseDao).execute(expense);
    }
    public LiveData<List<Expense>> getAllExpenses(){
        return allExpenses;
    }

    public LiveData<TotalsPojo> getTotal(){
        return totalsPojo;
    }

    private static class InsertExpenditureAsyncTask extends AsyncTask<Expense, Void, Void> {
        private ExpenseDao expenseDao;

        private InsertExpenditureAsyncTask(ExpenseDao expenseDao){
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(Expense... expense) {
            expenseDao.insert(expense[0]);
            return null;
        }
    }

    private static class UpdateExpenditureAsyncTask extends AsyncTask<Expense, Void, Void>{
        private ExpenseDao expenseDao;

        private UpdateExpenditureAsyncTask(ExpenseDao expenseDao){
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(Expense... expense) {
            expenseDao.update(expense[0]);
            return null;
        }
    }
    
}
