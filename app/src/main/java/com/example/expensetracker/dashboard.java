package com.example.expensetracker;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextWatcher;

import java.util.List;

public class dashboard extends AppCompatActivity {
    double budgetamnt = 0.0;
    user U1;

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        U1 = data.getParcelableExtra("USER");
                        setDisplay(U1);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

       Intent current = getIntent();
       //Setting up user welcome message
        U1 = current.getParcelableExtra("USER");
        TextView welcome = findViewById(R.id.DashWelc);
        welcome.setText(getString(R.string.welcome) +" "+ U1.getUsername());
        Log.i("USER","Welcome, "+ U1.getUsername());

        //Budget displays
        EditText budgetinput = findViewById(R.id.Budget);

        // textWatcher is for watching any changes in editText
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                // this function is called after text is edited
                if(budgetinput.getText().toString().equals("")){//empty case
                    budgetamnt = 0.00;
                    budgetinput.setHint("0.00");
                   // budgetinput.setText("0.00");
                }
                if(!budgetinput.getText().toString().equals("")) {//non empty case
                    budgetamnt = Double.parseDouble(budgetinput.getText().toString());
                    setDisplay(U1);
                }
            }
        };
        // set the TextChange Listener for
        // the edit text field
        budgetinput.addTextChangedListener(textWatcher);
    }


    public void AddExpense(View v){
        Intent DashToAddExpense = new Intent(this, AddExpense.class);
        DashToAddExpense.putExtra("Add Expense", U1);
        Log.i("Add Expense","AddExpenseFromDash");
        someActivityResultLauncher.launch(DashToAddExpense);
    }

    public void goExpenseList(View v){
        Intent DashToExpenseList = new Intent(this, ExpenseListView.class);
        DashToExpenseList.putExtra("Expense List", U1);
        Log.i("Expense List","DashToExpenseList");
        someActivityResultLauncher.launch(DashToExpenseList);
    }

    public double expenseSum(List<Expense> expenseList){
        double sum = 0;
        for (Expense e: expenseList) {
            sum = sum + e.getCost();
        }
        return sum;
    }

    private void setDisplay(user u){
        TextView eCost = findViewById(R.id.dashCost);
        TextView eBal = findViewById(R.id.dashBal);

        eCost.setText(Double.toString(expenseSum(u.getExpenseList())));

        double calc = (Math.round(
                (budgetamnt * 100.00) -
                        ((expenseSum(u.getExpenseList())) * 100.00)));

        eBal.setText(Double.toString(calc/100));

    }

    public void signOut(View v) {
        Intent DashToMain = new Intent(dashboard.this, MainActivity.class /*from, to*/);
        //AddExToDash.putExtra("USER",U1);
        //setResult(1, AddExToDash);
        Log.i("USER","DashToMain");
        finish();
    }
}