package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import java.util.List;

public class ModifyExpense extends AppCompatActivity {
    private Expense previous;
    private user U1;
    private int index;
    private String ExpenseName;
    private double ExpenseCost;

    private List<Expense> ExpenseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_expense);

        Intent current = getIntent();
        U1 = current.getParcelableExtra("User");
        ExpenseList = U1.getExpenseList();
        index = current.getIntExtra("ExpenseIndex", -1);

        EditText descriptionBox = findViewById(R.id.ModifyExpenseNameTextView);
        EditText costBox = findViewById(R.id.modExpenseAmount);

        if(index != -1){
            previous = ExpenseList.get(index);
            ExpenseList.remove(index);
        } else { //place holder bc this should NOT happen
            Toast.makeText(this,"Something went wrong!", Toast.LENGTH_LONG).show();
        }

        TextWatcher TWdesc = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                descriptionBox.setHint(previous.getDescription());
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                // this function is called after text is edited
                if(descriptionBox.getText().toString().equals("")){//empty case
                    descriptionBox.setHint(previous.getDescription());
                }
                if(!descriptionBox.getText().toString().equals("")) {//non empty case
                    ExpenseName= descriptionBox.getText().toString();
                }
            }
        };
        TextWatcher TWcost = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                costBox.setHint(Double.toString(previous.getCost()));
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                // this function is called after text is edited
                if(costBox.getText().toString().equals("")){//empty case
                    costBox.setHint(Double.toString(previous.getCost()));
                }
                if(!costBox.getText().toString().equals("")) {//non empty case
                    String costString = costBox.getText().toString();
                    double temp = Math.round(Double.parseDouble(costString)*100);
                    ExpenseCost = temp/100.00;
                }
            }
        };

        costBox.addTextChangedListener(TWcost);
        descriptionBox.addTextChangedListener(TWdesc);
    }

    public void saveMod(View v) {
        if (ExpenseName.equals("") || ExpenseCost < 0) {
            Toast.makeText(this, "Input ALL fields!", Toast.LENGTH_LONG).show();
        } else {
            Intent ModToList = new Intent(ModifyExpense.this, ExpenseListView.class /*from, to*/);
            Expense e1 = new Expense(ExpenseName, ExpenseCost);
            ExpenseList.add(e1);
            user updatedU1 = new user(U1.getUsername(), U1.getPassword(), ExpenseList);
            ModToList.putExtra("NEW USER", updatedU1);
            Log.i("Mod Expense", "ModToExpenseList");
            setResult(Activity.RESULT_OK, ModToList);
            finish();
        }
    }

    public void cancelMod(View v){
        Intent ModToList = new Intent(ModifyExpense.this, ExpenseListView.class /*from, to*/);
        Log.i("USER","ModToList");
        finish();
    }
}