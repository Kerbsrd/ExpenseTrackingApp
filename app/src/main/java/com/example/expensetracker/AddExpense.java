package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddExpense extends AppCompatActivity {
    user U1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        Intent current = getIntent();
        U1 = current.getParcelableExtra("Add Expense");
    }

    public void saveExpense(View v) {
        EditText desriptionbox = findViewById(R.id.expenseDescAdd);
        EditText costbox = findViewById(R.id.expenseCostAdd);
        String description = desriptionbox.getText().toString();
        String costget = costbox.getText().toString();
        if (description.equals("") || costget.equals("")) {
            Toast.makeText(this, "Input ALL fields!", Toast.LENGTH_LONG).show();
        } else {
            Intent AddExToDash = new Intent(AddExpense.this, dashboard.class /*from, to*/);

            double costRound = Math.round(Double.parseDouble(costget) * 100);
            Expense e1 = new Expense(description, costRound / 100);
            List<Expense> expenseList = U1.getExpenseList();
            expenseList.add(e1);
            user updatedU1 = new user(U1.getUsername(),U1.getPassword(),expenseList);
            AddExToDash.putExtra("USER",updatedU1);
            Log.i("Add Expense","DashFromAddExpense");
            setResult(Activity.RESULT_OK, AddExToDash);
            finish();
        }
    }

    public void exitAddExpense(View v) {
        Intent AddExToDash = new Intent(AddExpense.this, dashboard.class /*from, to*/);
        //AddExToDash.putExtra("USER",U1);
        //setResult(1, AddExToDash);
        Log.i("USER","AddExToDash");
        finish();
    }
}
