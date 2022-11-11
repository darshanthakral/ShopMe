package com.darshanthakral.shopme;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "ShoppingDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_SHOPPING_LIST = "shopping_list";

    SQLiteDatabase db;

    //Table Columns
    private static final String ITEM_ID = "id";
    private static final String ITEM_NAME = "name";
    private static final String ITEM_QUANTITY = "quantity";
    private static final String ITEM_COST = "cost";

    public MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_SHOPPING_LIST +
                "(" + ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ITEM_NAME + " TEXT NOT NULL,"
                + ITEM_QUANTITY + " INTEGER NOT NULL,"
                + ITEM_COST + " INTEGER NOT NULL" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST);
        onCreate(db);
    }

    public void addItem(String name, String quantity, String cost) {

        db = this.getWritableDatabase();

        //Implementing Content Values From Parcelable
        ContentValues values = new ContentValues();
        values.put(ITEM_NAME, name);
        values.put(ITEM_QUANTITY, quantity);
        values.put(ITEM_COST, cost);

        db.insert(TABLE_SHOPPING_LIST, null, values);
    }

    public ArrayList<ShopItemModel> fetchItem() {

        db = this.getReadableDatabase();
        //Implementing Cursor
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SHOPPING_LIST, null);

        ArrayList<ShopItemModel> arrayList = new ArrayList<>();

        while (cursor.moveToNext()) {

            ShopItemModel model = new ShopItemModel();

            model.setId(cursor.getInt(0));
            model.setName(cursor.getString(1));
            model.setQuantity(cursor.getString(2));
            model.setCost(cursor.getString(3));

            arrayList.add(model);
        }
        return arrayList;
    }

    @SuppressLint("Range")
    public String calTotalCost() {

        db = this.getWritableDatabase();
        int sum = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + ITEM_QUANTITY + " * " + ITEM_COST + ") AS total FROM " + TABLE_SHOPPING_LIST, null);

        if (cursor.moveToFirst()) {
            sum = cursor.getInt(cursor.getColumnIndex("total"));
        }
        return String.valueOf(sum);
    }

    public void updateItem(ShopItemModel model) {

//        TODO: Try this again
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ITEM_NAME, model.getName());

        db.update(TABLE_SHOPPING_LIST, values, ITEM_ID + " = " + model.getId(), null);
    }


    public void deleteData(int itemID) {

        db = this.getWritableDatabase();
        db.delete(TABLE_SHOPPING_LIST, ITEM_ID + " = ? ", new String[]{String.valueOf(itemID)});
    }
}
