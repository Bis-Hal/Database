package com.example.database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText customerName;
    private Button add, view;
    Customers customers;
    private RecyclerView recyclerView;
    ArrayList<Customers> customersResultSet;
    Db db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customerName = findViewById(R.id.name);
        add = findViewById(R.id.add);
        view = findViewById(R.id.view);

        recyclerView = findViewById(R.id.recyclerView);
        db = new Db(MainActivity.this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    customers = new Customers(customerName.getText().toString());
                }catch(Exception e) {
                    e.printStackTrace();
                }

                if(db.addCustomer(customers)){
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customersResultSet = db.getCustomers(MainActivity.this);
              //  Toast.makeText(MainActivity.this, customersResultSet.toString(), Toast.LENGTH_LONG).show();
                RecyclerViewCustomerListItems recyclerViewCustomerListItems = new RecyclerViewCustomerListItems(MainActivity.this, customersResultSet);

                recyclerView.setAdapter(recyclerViewCustomerListItems);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            }
        });
    }
}