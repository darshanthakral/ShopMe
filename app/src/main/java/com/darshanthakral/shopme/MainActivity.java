package com.darshanthakral.shopme;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView itemsTotalCount;
    FloatingActionButton fabAddItem;
    RecyclerView listItems;
    ItemsDBAdapter adapter;

    ArrayList<ShopItemModel> list = new ArrayList<>();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fabAddItem = findViewById(R.id.fab_add_item);
        itemsTotalCount = findViewById(R.id.items_total_count);

        fabAddItem.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AddItem.class)));

        listItems = findViewById(R.id.recycler_view);
        listItems.setHasFixedSize(true);
        listItems.setLayoutManager(new LinearLayoutManager(this));

        MyDBHelper dbHelper = new MyDBHelper(this);

        list = dbHelper.fetchItem();

        adapter = new ItemsDBAdapter(this, MainActivity.this, list);

        listItems.setAdapter(adapter);

        String total = dbHelper.calTotalCost();

        itemsTotalCount.setText(total + " Rs");

//       //Fetch Data
//        MyDBHelper dbHelper = new MyDBHelper(this);
//
//        ArrayList<ShopItemModel> arrayList = dbHelper.fetchItem();
//
//        for (int i = 0; i < arrayList.size(); i++) {
//            Log.d("ITEM_INFO",
//                    "Name: " + arrayList.get(i).getName()
//                            + "\n Quantity: " + arrayList.get(i).getQuantity()
//                            + "\n Cost: " + arrayList.get(i).getCost());
//        }
//
//
////        Delete Data
//
//        dbHelper.deleteData(3);


    }
}