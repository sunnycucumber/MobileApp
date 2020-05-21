package com.vogella.android.recyclerview.presentation.model.view;

import java.util.List;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vogella.android.recyclerview.R;
import com.vogella.android.recyclerview.presentation.model.Pokemon;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<Pokemon> values;
    private OnItemClickListener listener;

 public interface OnItemClickListener{
     void onItemClick(Pokemon item);
 }
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
         TextView txtHeader;
        TextView txtFooter;
        View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = v.findViewById(R.id.firstLine);
            txtFooter = v.findViewById(R.id.secondLine);
        }
    }

    public void add(int position, Pokemon item) {

        values.add(position, item);
        notifyItemInserted(position);
    }

     private void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Adapter(List<Pokemon> myDataset, OnItemClickListener listener) {

         this.values = myDataset;
        this.listener = listener;
    }

    public void setListener(OnItemClickListener listener){
     this.listener = listener;
    }


    // Create new views (invoked by the layout manager)

    @Override
    public Adapter.ViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent,false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Pokemon currentPokemon = values.get(position);
        holder.txtHeader.setText(currentPokemon.getName());
        holder.txtFooter.setText(currentPokemon.getUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(currentPokemon);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)

    @Override
    public int getItemCount() {
        return values.size();
    }

}