package com.example.shopping_list;//package com.example.shopping_list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ShoppingListAdapter adapter;
    private List<String> shoppingItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shoppingItems = new ArrayList<>();

        shoppingItems.add(("Item1"));
        shoppingItems.add(("Item 2"));
        shoppingItems.add(("Item 3"));
        shoppingItems.add(("Item 4"));


        adapter = new ShoppingListAdapter(shoppingItems, this::openEditDialog);


        recyclerView.setAdapter(adapter);

        Button addButton = findViewById(R.id.buttonAdd);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInputDialog();
            }
        });

    }

    private void openInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавление нового товара");

        final EditText editText = new EditText(this);
        builder.setView(editText);

        builder.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newItem = editText.getText().toString();
                shoppingItems.add(newItem);
                adapter.notifyItemInserted(shoppingItems.size() - 1);
                Toast.makeText(MainActivity.this, "Товар добавлен", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Отмена", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void openEditDialog(String item, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Редактировать товар");

        final EditText input = new EditText(this);
        input.setText(item);
        builder.setView(input);

        builder.setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String editedText = input.getText().toString();
                shoppingItems.set(position, editedText);
                adapter.notifyItemChanged(position);
                Toast.makeText(MainActivity.this, "Новое название сохранено", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Отмена", null);

        AlertDialog dialog = builder.create();
        builder.show();
    }
}


