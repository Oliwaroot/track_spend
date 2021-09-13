package com.example.track_spend.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense_table")
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String category;
    private String description;
    private String currentDate;

    public String getCurrentDate() {
        return currentDate;
    }

    public Expense(String name, String category, String description, int amount, String currentDate) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.currentDate = currentDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    private int amount;
}
