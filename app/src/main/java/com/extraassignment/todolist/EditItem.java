package com.extraassignment.todolist;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditItem extends AppCompatActivity {
    EditText editItem;
    Button updateButton;
    Button moveUp;
    Button moveDown;
    public static final int UPDATE_CODE = 10;
    public static final int MOVE_UP_CODE = 11;
    public static final int MOVE_DOWN_CODE = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        editItem = findViewById(R.id.editItem);
        updateButton = findViewById(R.id.button3);
        moveUp = findViewById(R.id.moveUp);
        moveDown = findViewById(R.id.moveDown);

        getSupportActionBar().setTitle("Edit the item");

        editItem.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, editItem.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
                intent.putExtra("requestCode", UPDATE_CODE);


                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        moveUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.putExtra(MainActivity.KEY_ITEM_TEXT, editItem.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
                intent.putExtra("requestCode", MOVE_UP_CODE);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        moveDown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, editItem.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
                intent.putExtra("requestCode", MOVE_DOWN_CODE);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}