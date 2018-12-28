package com.example.ahmedragab.clientsapplication;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class ProductDetailsFragment extends DialogFragment {

    NetworkImageView productImg;
    TextView productName;
    TextView productDescription;
    TextView productCost;
    public ImageLoader imageLoader;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);

        productName = view.findViewById(R.id.txt_product_name);
        productDescription = view.findViewById(R.id.txt_product_description);
        productCost = view.findViewById(R.id.txt_product_cost);
        productImg= view.findViewById(R.id.imgProduct);

        imageLoader = CustomVolleyRequest.getInstance(getActivity().getApplicationContext())
                .getImageLoader();
        Bundle bundle = getArguments();
        Product c= (Product) bundle.getSerializable("product");
        productName.setText(c.getName());
        productDescription.setText(c.getDescription());
        productCost.setText(c.getPrice());
        imageLoader.get(c.getImgPath(), ImageLoader.getImageListener(productImg ,0 ,android.R.drawable.ic_dialog_alert));

        productImg.setImageUrl(c.getImgPath() , imageLoader);
        return view;
    }

}
