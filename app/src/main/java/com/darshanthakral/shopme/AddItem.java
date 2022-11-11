package com.darshanthakral.shopme;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class AddItem extends AppCompatActivity {

    EditText inputName, inputQuantity, inputPerItemCost;
    Button addItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Item");

        inputName = findViewById(R.id.input_name);

        inputQuantity = findViewById(R.id.input_quantity);

        inputPerItemCost = findViewById(R.id.input_per_item_cost);

        addItem = findViewById(R.id.add_button);

        addItem.setOnClickListener(v -> {

            if (inputName != null && inputQuantity != null && inputPerItemCost != null) {

                String name = String.valueOf(inputName.getText());
                String quantity = String.valueOf(inputQuantity.getText());
                String cost = String.valueOf(inputPerItemCost.getText());

                MyDBHelper dbHelper = new MyDBHelper(this);
                dbHelper.addItem(name, quantity, cost);


                inputName.setText("");
                inputQuantity.setText("");
                inputPerItemCost.setText("");
                Toast.makeText(getApplicationContext(), "Item Added successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });


    }
}