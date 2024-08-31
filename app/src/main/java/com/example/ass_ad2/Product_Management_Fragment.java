package com.example.ass_ad2;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
public class Product_Management_Fragment extends Fragment {
    private ArrayList<Product> listP;
    private RecyclerView recyclerView;
    private ProductAdapterRe productAdapterRe;
    private ProductData productData;
    private FloatingActionButton btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product__management_, container, false);
        // Inflate the layout for this fragment
        listP = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rv_Product);
        productAdapterRe = new ProductAdapterRe(getContext());
        productData = new ProductData(getContext());
        btnAdd = view.findViewById(R.id.btnAdd);
        GetData();
        AddProduct();
        return view;
    }
    private void AddProduct() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_update, null);


                builder.setView(view);
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();

                EditText edtName = view.findViewById(R.id.edtName);
                EditText edtPrice = view.findViewById(R.id.edtPrice);
                EditText edtQuantity = view.findViewById(R.id.edtQuantity);
                Button btnAdd = view.findViewById(R.id.btnAddM);


                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = edtName.getText().toString().trim();
                        String price = edtPrice.getText().toString().trim();
                        String quantity = edtQuantity.getText().toString().trim();
                        boolean check = productData.InsertData(new Product(1, name, price, quantity));
                        if (check) {
                            GetData();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Add Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
    }
    private void GetData() {
        listP.clear();
        listP.addAll(productData.GetDataProductList());
        productAdapterRe.setList(listP);
        recyclerView.setAdapter(productAdapterRe);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productAdapterRe.notifyDataSetChanged();
    }

}