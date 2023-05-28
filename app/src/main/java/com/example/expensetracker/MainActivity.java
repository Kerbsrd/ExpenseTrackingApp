package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<user> userList = new ArrayList<user>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateUsers(this.userList);
    }

    //Check if a username is already taken or user exists already
    private int userExists(String username){
        int result = -1;
        if(userList != null) {
            for (int i = 0; i < userList.size(); i++) {
                if (user.getUsername().equalsIgnoreCase(username)) {
                    result = i;
                }
            }
        }
        return result;
    }

    //Add in a test user
    private void populateUsers(List<user> list){
        List<Expense> kerbsExpense = new ArrayList<>();
        kerbsExpense.add(new Expense("Groceries",54.67));
        kerbsExpense.add(new Expense("Treats",20.33));
        kerbsExpense.add(new Expense("Monster Energy",6.00));
        list.add(new user("Kerbsrd", "mice123", kerbsExpense));
    }

    //Login button function
    public void Login(View v){
        Intent mainToDash = new Intent(MainActivity.this, dashboard.class);
        EditText usernameBox = findViewById(R.id.usernameEditText);
        EditText passwordBox = findViewById(R.id.PWEditText);

        String username = usernameBox.getText().toString();
        String password = passwordBox.getText().toString();

        int userExistsResult = userExists(username);

        if(username.equals("")||password.equals("")){
        Toast.makeText(this,"Input ALL fields!", Toast.LENGTH_LONG).show();
        } else if(userExistsResult==-1){ //Does this user exist?
            Toast.makeText(this,"User not found, try again", Toast.LENGTH_LONG).show();
        } //Is the password correct and the user exists?
        else if(!userList.get(userExistsResult).getPassword().equals(password)){
            Toast.makeText(this,"Incorrect password, try again", Toast.LENGTH_LONG).show();
        } else {    //Successful Login
            mainToDash.putExtra("USER", userList.get(userExistsResult));
            Log.i("USER",username + " logged in.");
            startActivity(mainToDash);
        }
    }

    //Register a user
    public void Register(View v){
        Intent mainToDash = new Intent(MainActivity.this, dashboard.class);
        EditText usernameBox = findViewById(R.id.usernameEditText);
        EditText passwordBox = findViewById(R.id.PWEditText);

        String username = usernameBox.getText().toString();
        String password = passwordBox.getText().toString();
        if(username.equals("")||password.equals("")){
            Toast.makeText(this,"Input ALL fields!", Toast.LENGTH_LONG).show();
        }else if(userExists(username)!=-1){//Is the username taken
            Toast.makeText(this,"User taken, try again", Toast.LENGTH_LONG).show();
        } else {//Add the user to the list for next time!
            List<Expense> e = new ArrayList<>();
            e.add(new Expense("Join fee",0.0));
            user newUser = new user(username, password, e);
            userList.add(newUser);
            mainToDash.putExtra("USER", newUser);
            Log.i("USER",username + " registered.");
            startActivity(mainToDash);
        }

    }
}