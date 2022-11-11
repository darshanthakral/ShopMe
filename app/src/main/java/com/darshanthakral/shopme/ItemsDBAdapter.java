package com.darshanthakral.shopme;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemsDBAdapter extends RecyclerView.Adapter<ItemsDBAdapter.ViewHolder> {

    Context mContext;
    Activity activity;
    private final ArrayList<ShopItemModel> list;


    @SuppressLint("NotifyDataSetChanged")
    public ItemsDBAdapter(Context context, Activity yourActivity, ArrayList<ShopItemModel> arrayList) {
        this.mContext = context;
        this.activity = yourActivity;
        this.list = arrayList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemsDBAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_data_structure_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ItemsDBAdapter.ViewHolder holder, int position) {

        MyDBHelper dbHelper = new MyDBHelper(mContext);

        ShopItemModel model = list.get(position);

        holder.itemName.setText(model.getName());
        holder.itemQuantity.setText(model.getQuantity());

        int subTotal = Integer.parseInt(model.getQuantity()) * Integer.parseInt(model.getCost());

        holder.itemSubTotal.setText(String.valueOf(subTotal));

        holder.deleteItemButton.setOnClickListener(v ->
        {
            int pos = model.getId();
            dbHelper.deleteData(pos);

            Toast.makeText(mContext, "Item is deleted.", Toast.LENGTH_SHORT).show();

            this.notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemQuantity, itemSubTotal;
        ImageView deleteItemButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.item_name);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            itemSubTotal = itemView.findViewById(R.id.item_sub_total);

            deleteItemButton = itemView.findViewById(R.id.delete_item_button);
        }
    }
}
