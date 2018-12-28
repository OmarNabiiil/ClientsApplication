package com.example.ahmedragab.clientsapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
    }

    public void Office(View view) {
        Intent i = new Intent(CategoriesActivity.this, ProductsActivity.class);
        i.putExtra("tag","office");
        startActivity(i);
    }

    public void Chairs(View view) {
        Intent i = new Intent(CategoriesActivity.this, ProductsActivity.class);
        i.putExtra("tag","chairs");
        startActivity(i);
    }

    public void Cupbords(View view) {
        Intent i = new Intent(CategoriesActivity.this, ProductsActivity.class);
        i.putExtra("tag","cupbords");
        startActivity(i);
    }

    public void Tables(View view) {
        Intent i = new Intent(CategoriesActivity.this, ProductsActivity.class);
        i.putExtra("tag","tables");
        startActivity(i);
    }
}
