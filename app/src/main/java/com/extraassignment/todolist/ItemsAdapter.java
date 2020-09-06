package com.extraassignment.todolist;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnClickerListener {
        void onItemClicked(int position);
    }
    public interface OnLongClickerListener {
        void onItemLongClicked(int position);
    }


    private ArrayList<String> items;
    private OnLongClickerListener longClickerListener;
    private OnClickerListener clickerListener;

    public ItemsAdapter(ArrayList<String> items, OnLongClickerListener longClickerListener, OnClickerListener clickerListener) {
        this.items = items;
        this.longClickerListener = longClickerListener;
        this.clickerListener = clickerListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        return new ViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //Container of to-do list
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView allItems;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            allItems = itemView.findViewById(android.R.id.text1);
            allItems.setBackgroundColor(0xffff0000);
        }
        public void bind(String item) {
            allItems.setText(item);
            allItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickerListener.onItemClicked(getAdapterPosition());
                }
            });

            allItems.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longClickerListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
            allItems.setBackgroundColor(MainActivity.setColor);
        }
    }
}
