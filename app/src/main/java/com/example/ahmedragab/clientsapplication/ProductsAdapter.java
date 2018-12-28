package com.example.ahmedragab.clientsapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    List<Product> productsList;
    public ImageLoader imageLoader;

    private ProductsAdapter.ButtonClickListener monClickListener;

    public interface ButtonClickListener{
        void onDetailsClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition);
        void onOrderClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name, price;
        private Button details, order;
        public NetworkImageView courseImg;

        public MyViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.txt_name);
            price=view.findViewById(R.id.txt_cost);
            details=view.findViewById(R.id.btn_details);
            order=view.findViewById(R.id.btn_order);
            courseImg= view.findViewById(R.id.imgItem);
            details.setOnClickListener(this);
            order.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedItemPosition=getAdapterPosition();
            if(view.equals(details)){
                monClickListener.onDetailsClickListener(this, clickedItemPosition);
            }

            if(view.equals(order)){
                monClickListener.onOrderClickListener(this, clickedItemPosition);
            }
        }
    }

    public void setImageLoader(ImageLoader img){
        this.imageLoader=img;
    }

    public ProductsAdapter( List<Product> productsList, ProductsAdapter.ButtonClickListener listener) {
        this.productsList = productsList;
        monClickListener=listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);

        return new ProductsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product p = productsList.get(position);
        holder.name.setText(p.getName());
        holder.price.setText(p.getPrice());
        imageLoader.get(p.getImgPath(), ImageLoader.getImageListener(holder.courseImg ,0 ,android.R.drawable.ic_dialog_alert));

        holder.courseImg.setImageUrl(p.getImgPath() , imageLoader);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

}
