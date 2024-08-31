package com.example.ass_ad2;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapterRe extends RecyclerView.Adapter<ProductAdapterRe.ViewHolder> {

    private Context context;
    private ArrayList<Product> list = new ArrayList<>();

    public ProductAdapterRe(Context context) {
        this.context = context;
    }

    public void setList(ArrayList<Product> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(list.get(position).getName());
        holder.txtPrice.setText(list.get(position).getPrice());
        holder.txtQuantity.setText(" - " + list.get(position).getQuantity());

        holder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_update1, null);
                builder.setView(view);

                AlertDialog dialog = builder.create();

                Button btnUpdate = view.findViewById(R.id.btnAddM1);
                EditText edtName = view.findViewById(R.id.edtName1);
                EditText edtPrice = view.findViewById(R.id.edtPrice1);
                EditText edtQuantity = view.findViewById(R.id.edtQuantity1);

                edtName.setText(list.get(holder.getAdapterPosition()).getName());
                edtPrice.setText(list.get(holder.getAdapterPosition()).getPrice());
                edtQuantity.setText(list.get(holder.getAdapterPosition()).getQuantity());
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = edtName.getText().toString();
                        String price = edtPrice.getText().toString();
                        String quantity = edtQuantity.getText().toString();
                        boolean check = holder.productData.UpdateData(new Product(0, name, price, quantity));

                        if (check) {
                            Toast.makeText(context, "Update Complete!!<3", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list = holder.productData.GetDataProductList();
                            notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Update Failed!!!!1", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });
        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Notification");
                builder1.setMessage("Do you want delete " + list.get(holder.getAdapterPosition()).getName());
                builder1.setCancelable(false);
                builder1.setIcon(R.drawable.baseline_heart_broken_24);
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean check = holder.productData.DeleteData(list.get(holder.getAdapterPosition()).getId());
                        if (check) {
                            list.clear();
                            list = holder.productData.GetDataProductList();
                            notifyDataSetChanged();
                            notifyItemRemoved(holder.getAdapterPosition());
                        }

                    }
                });
                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder1.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtPrice, txtQuantity, txtEdit, txtDelete;
        private ProductData productData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productData = new ProductData(context);
            txtDelete = itemView.findViewById(R.id.item_delete);
            txtEdit = itemView.findViewById(R.id.item_edit);
            txtPrice = itemView.findViewById(R.id.item_price);
            txtName = itemView.findViewById(R.id.item_title);
            txtQuantity = itemView.findViewById(R.id.item_quantity);
        }
    }
}
