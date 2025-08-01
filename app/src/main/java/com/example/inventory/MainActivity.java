package com.example.inventory;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTextProductId, editTextProductName, editTextQuantity, editTextExpireDate;
    Button buttonInsert, buttonUpdate, buttonDelete, buttonDisplay;
    DatabaseHelper databaseHelper;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        editTextProductId = findViewById(R.id.editTextProductId);
        editTextProductName = findViewById(R.id.editTextProductName);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextExpireDate = findViewById(R.id.editTextExpireDate);
        buttonInsert = findViewById(R.id.buttonInsert);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonDisplay = findViewById(R.id.buttonDisplay);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList, new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                loadProductDataIntoFields(product);
            }
        });
        recyclerView.setAdapter(productAdapter);

        buttonInsert.setOnClickListener(v -> insertData());
        buttonUpdate.setOnClickListener(v -> updateData());
        buttonDelete.setOnClickListener(v -> deleteData());
        buttonDisplay.setOnClickListener(v -> displayAllData());
    }

    private void loadProductDataIntoFields(Product product) {
        editTextProductId.setText(String.valueOf(product.getId()));
        editTextProductName.setText(product.getName());
        editTextQuantity.setText(String.valueOf(product.getQuantity()));
        editTextExpireDate.setText(product.getExpireDate());
    }

    private void insertData() {
        String idText = editTextProductId.getText().toString().trim();
        String name = editTextProductName.getText().toString().trim();
        String quantityText = editTextQuantity.getText().toString().trim();
        String expireDate = editTextExpireDate.getText().toString().trim();

        if (idText.isEmpty() || name.isEmpty() || quantityText.isEmpty() || expireDate.isEmpty()) {
            showToast("All fields must be filled");
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            int quantity = Integer.parseInt(quantityText);


            boolean isInserted = databaseHelper.insertData(id, name, quantity, expireDate);
            showToast(isInserted ? "Data Inserted" : "Insertion Failed");

            if (isInserted) {
                clearInputFields();
                displayAllData();
            }

        } catch (NumberFormatException e) {
            showToast("Please enter valid Product ID and Quantity");
        }
    }

    private void updateData() {
        String idText = editTextProductId.getText().toString().trim();
        String name = editTextProductName.getText().toString().trim();
        String quantityText = editTextQuantity.getText().toString().trim();
        String expireDate = editTextExpireDate.getText().toString().trim();

        if (idText.isEmpty()) {
            showToast("Product ID is required for update");
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            int quantity = Integer.parseInt(quantityText);

            boolean isUpdated = databaseHelper.updateData(id, name, quantity, expireDate);
            showToast(isUpdated ? "Data Updated" : "Update Failed");

            if (isUpdated) {
                clearInputFields();
                displayAllData();
            }

        } catch (NumberFormatException e) {
            showToast("Please enter valid Product ID and Quantity");
        }
    }

    private void deleteData() {
        String idText = editTextProductId.getText().toString().trim();
        if (idText.isEmpty()) {
            showToast("Please enter the Product ID to delete");
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            Integer deletedRows = databaseHelper.deleteData(id);
            showToast(deletedRows > 0 ? "Data Deleted" : "Deletion Failed");

            if (deletedRows > 0) {
                clearInputFields();
                displayAllData();
            }

        } catch (NumberFormatException e) {
            showToast("Please enter a valid Product ID");
        }
    }

    private void displayAllData() {
        Cursor cursor = databaseHelper.getAllData();
        productList.clear();

        if (cursor.getCount() == 0) {
            showToast("No Data Found");
        } else {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int quantity = cursor.getInt(2);
                String expireDate = cursor.getString(3);
                productList.add(new Product(id, name, quantity, expireDate));
            }
            productAdapter.notifyDataSetChanged();
        }
    }

    private void clearInputFields() {
        editTextProductId.setText("");
        editTextProductName.setText("");
        editTextQuantity.setText("");
        editTextExpireDate.setText("");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
