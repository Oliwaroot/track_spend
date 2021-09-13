package com.example.track_spend;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.track_spend.adapters.ExpenseAdapter;
import com.example.track_spend.fragments.ExpensesFragment;
import com.example.track_spend.fragments.HomeFragment;
import com.example.track_spend.room.Expense;
import com.example.track_spend.viewmodel.ExpenseViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_EXPENSE_REQUEST = 1;
    public static final int EDIT_EXPENSE_REQUEST = 2;

    private ExpenseViewModel expenseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton = findViewById(R.id.floating_add_expense);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddOrEditExpense.class);
                startActivityForResult(intent, ADD_EXPENSE_REQUEST);
            }
        });

       ExpenseAdapter adapter = new ExpenseAdapter();
       // recyclerView.setAdapter(adapter);

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseViewModel.getAllExpenses().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expense) {
                adapter.setExpense(expense);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("TagThis","R: "+requestCode);

        if(requestCode == ADD_EXPENSE_REQUEST && resultCode == RESULT_OK){
            assert data != null;
            String name = data.getStringExtra(AddOrEditExpense.EXTRA_NAME);
            String category = data.getStringExtra(AddOrEditExpense.EXTRA_CATEGORY);
            String description = data.getStringExtra(AddOrEditExpense.EXTRA_DESCRIPTION);
            String amount = data.getStringExtra(AddOrEditExpense.EXTRA_AMOUNT);
            String currentDate = data.getStringExtra(AddOrEditExpense.EXTRA_DATE);

            Expense expense = new Expense(name, category, description, Integer.parseInt(amount), currentDate);
            expenseViewModel.insert(expense);

            Toast.makeText(this, "Expense Added", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode == EDIT_EXPENSE_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            int id = data.getIntExtra(AddOrEditExpense.EXTRA_ID, -1);

            if (id == -1){
                Toast.makeText(this, "Expense not updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddOrEditExpense.EXTRA_NAME);
            String category = data.getStringExtra(AddOrEditExpense.EXTRA_CATEGORY);
            String description = data.getStringExtra(AddOrEditExpense.EXTRA_DESCRIPTION);
            String amount = data.getStringExtra(AddOrEditExpense.EXTRA_AMOUNT);
            String currentDate = data.getStringExtra(AddOrEditExpense.EXTRA_DATE);

            Expense expense = new Expense(name, category, description, Integer.parseInt(amount), currentDate);
            expense.setId(id);
            expenseViewModel.update(expense);

            Toast.makeText(this, "Expense Updated", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this, "Expense not added", Toast.LENGTH_SHORT).show();
        }
    }

    private final NavigationBarView.OnItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        break;

                    case R.id.nav_expenditures:
                        selectedFragment = new ExpensesFragment();
                        break;
                }
                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                return true;
            };
}