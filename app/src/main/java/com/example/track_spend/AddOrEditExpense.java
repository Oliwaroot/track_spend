package com.example.track_spend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddOrEditExpense extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.track_spend.EXTRA_ID";
    public static final String EXTRA_NAME =
            "com.example.track_spend.EXTRA_NAME";
    public static final String EXTRA_DESCRIPTION =
            "com.example.track_spend.EXTRA_DESCRIPTION";
    public static final String EXTRA_CATEGORY =
            "com.example.track_spend.EXTRA_CATEGORY";
    public static final String EXTRA_AMOUNT =
            "com.example.track_spend.EXTRA_AMOUNT";
    public static final String EXTRA_DATE =
            "com.example.track_spend.EXTRA_DATE";

    private EditText addName;
    private Spinner addCategory;
    private EditText addDescription;
    private EditText addAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        addName = findViewById(R.id.add_expense_name);
        addCategory = findViewById(R.id.add_expense_category);
        addDescription = findViewById(R.id.add_expense_description);
        addAmount = findViewById(R.id.add_expense_amount);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Expense");
            addName.setText(intent.getStringExtra(EXTRA_NAME));
            int position = getIndex(addCategory, intent.getStringExtra(EXTRA_CATEGORY));
            addCategory.setSelection(position);
            addDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            Log.i("TagThis", intent.getStringExtra(EXTRA_AMOUNT));
            addAmount.setText(intent.getStringExtra(EXTRA_AMOUNT));
        }
        else{
            setTitle("Add Expense");
        }
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        // Check for this when you set the position.
        return -1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_expense_menu_button, menu);
        return true;
    }

    private void saveExpense(){
        String name = addName.getText().toString();
        String category = addCategory.getSelectedItem().toString();
        String description = addDescription.getText().toString();
        String amount = addAmount.getText().toString();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        if(name.trim().isEmpty() || description.trim().isEmpty() || amount.trim().isEmpty() || category.equals("Select Category")){
            Toast.makeText(this, "Please insert all values", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_CATEGORY, category);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_AMOUNT, amount);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        String initialDate = getIntent().getStringExtra(EXTRA_DATE);
        if(id != -1){
            data.putExtra(EXTRA_ID, id);
        }
        if(initialDate == null){
            data.putExtra(EXTRA_DATE, currentDate);
        }
        else{
            data.putExtra(EXTRA_DATE, initialDate);
        }

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_expense) {
            saveExpense();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}