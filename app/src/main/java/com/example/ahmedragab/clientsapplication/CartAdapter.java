package com.example.ahmedragab.clientsapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private List<Product> productsList;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name, quantity, price;
        public ImageButton remove;

        public MyViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.name);
            price=view.findViewById(R.id.price);
            quantity=view.findViewById(R.id.quantity);
        }

    }

    public CartAdapter(List<Product> productsList) {
        this.productsList = productsList;
    }

    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_item, parent, false);

        return new CartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CartAdapter.MyViewHolder holder, int position) {
        Product v=productsList.get(position);
        holder.name.setText("Name: "+v.getName());
        holder.price.setText("price: "+v.getPrice());
        holder.quantity.setText("quantity: "+v.getQuantity());
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

}