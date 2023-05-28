package com.example.expensetracker;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ExpenseListView extends AppCompatActivity {

    private user u1;
    private List<Expense> expenseList = new ArrayList<Expense>();

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        u1 = data.getParcelableExtra("NEW USER");
                        expenseList = u1.getExpenseList();
                        populateExpenseLayout();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list_view);

        Intent current = getIntent();
        u1 = current.getParcelableExtra("Expense List");
        expenseList = u1.getExpenseList();

        if(!expenseList.isEmpty()) {
            populateExpenseLayout();
        }
        
    }

    private void populateExpenseLayout() {
        ArrayAdapter<Expense> eAdapter = new MyListAdapter();
        ListView eListView = findViewById(R.id.ExListView);
        eListView.setAdapter(eAdapter);

        eListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                Intent ExpenseListToModify = new Intent(ExpenseListView.this, ModifyExpense.class);
                ExpenseListToModify.putExtra("User", u1);
                ExpenseListToModify.putExtra("ExpenseIndex", index);
                Log.i("Expense Modify","ExpenseListToModify");
                someActivityResultLauncher.launch(ExpenseListToModify);
            }
        });

    }


    private class MyListAdapter extends ArrayAdapter<Expense> {
        public MyListAdapter() {
            super(ExpenseListView.this, R.layout.item_layout, expenseList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;

            if(itemView==null)
                itemView = getLayoutInflater().inflate(R.layout.item_layout, parent, false);

            Expense currentExpense = expenseList.get(position);
            TextView exName = itemView.findViewById(R.id.listExpenseNametextView);
            exName.setText(currentExpense.getDescription());
            TextView exAmount = itemView.findViewById(R.id.AmountlisttextView);
            exAmount.setText("" + currentExpense.getCost());

            return itemView;
        }
    }


    public void exitListView(View v) {
        Intent ListViewToDash = new Intent(ExpenseListView.this, dashboard.class /*from, to*/);
        Log.i("USER","ListViewToDash");
        ListViewToDash.putExtra("USER",u1);
        setResult(Activity.RESULT_OK, ListViewToDash);
        finish();
    }
}