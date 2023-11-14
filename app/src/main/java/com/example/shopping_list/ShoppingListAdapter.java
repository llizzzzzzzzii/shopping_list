package com.example.shopping_list;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    private List<String> shoppingItems;

    private OnEditButtonClickListener editButtonClickListener;


    public ShoppingListAdapter(List<String> shoppingItems, OnEditButtonClickListener editButtonClickListener) {
        this.shoppingItems = shoppingItems;
        this.editButtonClickListener = editButtonClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String item = shoppingItems.get(position);
        holder.textViewName.setText(item);

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
                Toast.makeText(v.getContext(), "Товар удален", Toast.LENGTH_SHORT).show();
            }
        });

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editButtonClickListener.onEditButtonClick(item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingItems.size();
    }


    public void removeItem(int position) {
        shoppingItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, shoppingItems.size());
    }

    public interface OnEditButtonClickListener {
        void onEditButtonClick(String item, int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ImageButton buttonDelete;
        CheckBox checkBox;

        ImageButton buttonEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.item_name);
            checkBox = itemView.findViewById(R.id.checkBox);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked){
                    textViewName.setPaintFlags(textViewName.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else{
                    textViewName.setPaintFlags(textViewName.getPaintFlags()&(~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            });

        }
    }
}



