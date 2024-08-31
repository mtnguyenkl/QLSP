package com.example.ass_ad2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Product> listP;
    private RecyclerView recyclerView;
    private ProductAdapterRe productAdapterRe;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton btnAdd;
    private ProductData productData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BingGUI();
        NavOnListener();
//        GetData();
//        AddProduct();
        ReplaceFragment(new Product_Management_Fragment());
    }

    private void AddProduct() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_add_update, null);


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
                            Toast.makeText(MainActivity.this, "Add Failed", Toast.LENGTH_SHORT).show();
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapterRe.notifyDataSetChanged();
    }

    private void NavOnListener() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);

        drawerLayout.addDrawerListener(toggle);

        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_Logout) {
                   startActivity(new Intent(MainActivity.this,Login.class));
                }
                if(item.getItemId() == R.id.nav_management){
                    ReplaceFragment(new Product_Management_Fragment());
                }
                    return true;
            }
        });
    }

    private void BingGUI() {
        productData = new ProductData(this);
        listP = new ArrayList<>();
        productAdapterRe = new ProductAdapterRe(this);

        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.rv_Product);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
    }

    private void ReplaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_layout,fragment).commit();
    }
}
