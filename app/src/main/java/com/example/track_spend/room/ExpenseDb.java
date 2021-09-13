package com.example.track_spend.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Expense.class}, version = 1)
public abstract class ExpenseDb extends RoomDatabase {
    private static ExpenseDb instance;

    public abstract ExpenseDao expenseDao();

    public static synchronized ExpenseDb getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
            ExpenseDb.class, "expense_table")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new AddDataToDbAsyncTask(instance).execute();
        }
    };

    private static class AddDataToDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExpenseDao expenseDao;

        private AddDataToDbAsyncTask(ExpenseDb exDb){
            expenseDao = exDb.expenseDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            expenseDao.insert(new Expense("Water","Foodstuff","Bottle of water", 100, "2021-10-8"));
            expenseDao.insert(new Expense("Juice","Foodstuff","5 litres of juice", 1000, "2021-10-9"));
            expenseDao.insert(new Expense("Leather Jacket","Wearable","Black leather jacket", 5000, "2021-10-10"));
            expenseDao.insert(new Expense("Fuel","Automotive","Fuel for vehicle", 4000, "2021-10-11"));
            expenseDao.insert(new Expense("Electricity Bill","Utilities","Electricity bill payment", 2000,"2021-10-12"));
            return null;
        }
    }

}
