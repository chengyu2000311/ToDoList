package com.extraassignment.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItem extends AppCompatActivity {
    EditText editItem;
    Button updateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        editItem = findViewById(R.id.editItem);
        updateButton = findViewById(R.id.button3);

        getSupportActionBar().setTitle("Edit the item");

        editItem.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));
        updateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, editItem.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}