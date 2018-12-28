package com.example.ahmedragab.clientsapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsActivity extends AppCompatActivity implements ProductsAdapter.ButtonClickListener {

    private RecyclerView recyclerView;
    private List<Product> products_list;
    private ProductsAdapter mAdapter;
    public ImageLoader imageLoader;
    public static String category = "";
    private Product p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        imageLoader = CustomVolleyRequest.getInstance(getApplicationContext()).getImageLoader();
        recyclerView = findViewById(R.id.recycler_view);
        category= (String) getIntent().getExtras().get("tag");

        products_list = new ArrayList<>();
        mAdapter = new ProductsAdapter( products_list,this);
        mAdapter.setImageLoader(imageLoader);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getAllProducts();
    }

    public void getAllProducts(){

        String tag_string_req = "req_register";

        String url = Config.PRODUCTS_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test",response);

                try {
                    //JSONObject jObji = new JSONObject(response);
                    JSONArray a=new JSONArray(response);
                    int sizeofarray=a.length();
                    for(int i=0;i<sizeofarray;i++){
                        JSONObject jObj = a.getJSONObject(i);//all the users in the database
                        Product c=new Product(jObj.get("product name").toString(), jObj.get("product_description").toString(),jObj.get("customers price").toString());
                        c.setImgPath(jObj.get("photo").toString());
                        products_list.add(c);
                    }

                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("test", "Registration Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url

                Map<String, String> params = new HashMap<>();
                params.put("category", ""+category);

                return params;
            }

        };
        // Adding request to request queue
        ApplicationActivity.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    public void onDetailsClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition) {
        Product p = products_list.get(viewHolder.getAdapterPosition());
        ProductDetailsFragment fr = new ProductDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", p);
        fr.setArguments(bundle);
        fr.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onOrderClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition) {
        if(LoginActivity.loggedIn){
            p = products_list.get(viewHolder.getAdapterPosition());
            showDialog();
        }else{
            Intent i=new Intent(ProductsActivity.this,LoginActivity.class);

            startActivity(i);
        }

    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter the quantity");
        // I'm using fragment here so I'm using getView() to provide ViewGroup
        // but you can provide here any other instance of ViewGroup from your Fragment / Activity
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.text_input_password, (ViewGroup) findViewById(android.R.id.content), false);
        // Set up the input
        final EditText input = viewInflated.findViewById(R.id.input);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(viewInflated);

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                if(!m_Text.isEmpty() && !m_Text.equals(" ")){
                    p.setQuantity(m_Text);
                    addToCartDB();
                    dialog.dismiss();
                }else{
                    Toast.makeText(ProductsActivity.this,"please enter a quantity", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void addToCartDB(){
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.ADDTOCART_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "GetCards Response: " + response.toString());
                if(response.equals("success")){
                    Toast.makeText(ProductsActivity.this,"successfully added", Toast.LENGTH_LONG).show();
                }
                if(response.equals("exceeded")){
                    Toast.makeText(ProductsActivity.this,"quantity exceeded the available!", Toast.LENGTH_LONG).show();
                }
                if(response.equals("failed")){
                    Toast.makeText(ProductsActivity.this,"quantity exceeded the available!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("test", "Registration Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url

                Map<String, String> params = new HashMap<>();
                params.put("userID", ""+LoginActivity.USER_ID);
                params.put("productName", ""+p.getName());
                params.put("productQuantity", ""+p.getQuantity());
                params.put("price", ""+p.getPrice());

                return params;
            }

        };

        // Adding request to request queue
        ApplicationActivity.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void ViewCart(View view) {
        Intent i=new Intent(ProductsActivity.this, ViewCartActivity.class);
        startActivity(i);
    }
}
