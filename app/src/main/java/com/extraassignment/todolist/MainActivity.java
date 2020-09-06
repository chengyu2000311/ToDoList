package com.extraassignment.todolist;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;
    public static int setColor = 0xffff0000;

    private ArrayList<String> items;
    private Button addButton;
    private EditText addingItem;
    private RecyclerView allItems;
    private ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        addingItem = findViewById(R.id.addingItem);
        allItems = findViewById(R.id.allItems);

        loadItems();

        ItemsAdapter.OnLongClickerListener onLongClickerListener = new ItemsAdapter.OnLongClickerListener() {
            @Override
            public void onItemLongClicked(int position) {
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed!", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

        ItemsAdapter.OnClickerListener onClickerListener = new ItemsAdapter.OnClickerListener() {
            @Override
            public void onItemClicked(int position) {
                //Log.d("MainActivity", "single click at position: " + position);
                Intent i = new Intent(MainActivity.this, EditItem.class);
                i.putExtra(KEY_ITEM_TEXT, items.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);

                startActivityForResult(i, EDIT_TEXT_CODE);
            }
        };



        itemsAdapter = new ItemsAdapter(items, onLongClickerListener, onClickerListener);
        allItems.setAdapter(itemsAdapter);
        allItems.setLayoutManager(new LinearLayoutManager(this));


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toDoItem = addingItem.getText().toString();
                items.add(toDoItem);
                itemsAdapter.notifyItemInserted(items.size()-1);
                addingItem.setText("");
                Toast.makeText(getApplicationContext(), "Item was added!", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });

    }
    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_TEXT_CODE && resultCode == RESULT_OK) {
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);
            if (data.getExtras().getInt("requestCode") == EditItem.UPDATE_CODE) {
                    items.set(position, itemText);
                    itemsAdapter.notifyItemChanged(position);
                    saveItems();
                    Toast.makeText(getApplicationContext(), "Item was updated!", Toast.LENGTH_SHORT).show();
            }else if (data.getExtras().getInt("requestCode") == EditItem.MOVE_UP_CODE) {
                    if (position==0) {
                        Toast.makeText(getApplicationContext(), "It is already at the first!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    items.remove(position);
                    items.add(position-1, itemText);
                    itemsAdapter.notifyItemMoved(position, position-1);
                    saveItems();
                    Toast.makeText(getApplicationContext(), "Moved Up!", Toast.LENGTH_SHORT).show();
            } else if (data.getExtras().getInt("requestCode") == EditItem.MOVE_DOWN_CODE) {
                    if (position==items.size()-1) {
                        Toast.makeText(getApplicationContext(), "It is already at the end!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    items.remove(position);
                    items.add(position+1, itemText);
                    itemsAdapter.notifyItemMoved(position, position+1);
                    saveItems();
                    Toast.makeText(getApplicationContext(), "Moved Down!", Toast.LENGTH_SHORT).show();
            } else if (data.getExtras().getInt("requestCode") == EditItem.MARK_AS_COMPLETED) {
                    setColor = 0xff00ff00;
                    itemsAdapter.notifyItemChanged(position);
                    Toast.makeText(getApplicationContext(), "Successfully Marked as Completed!", Toast.LENGTH_SHORT).show();
            } else if (data.getExtras().getInt("requestCode") == EditItem.MARK_AS_INCOMPLETED) {
                    setColor = 0xffff0000;
                    itemsAdapter.notifyItemChanged(position);
                    Toast.makeText(getApplicationContext(), "Successfully Marked as Uncompleted!", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "requestCode: "+requestCode, Toast.LENGTH_SHORT).show();
            Log.w("MainActivity", "unknown call on activity result");
        }
    }

    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading the items", e);
            items = new ArrayList<>();
        }
    }

    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing the items", e);
            items = new ArrayList<>();
        }
    }

}