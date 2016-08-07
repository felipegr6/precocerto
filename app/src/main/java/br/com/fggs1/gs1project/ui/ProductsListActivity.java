package br.com.fggs1.gs1project.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import br.com.fggs1.gs1project.R;
import br.com.fggs1.gs1project.adapter.ProductAdapter;
import br.com.fggs1.gs1project.model.Product;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ProductsListActivity extends AppCompatActivity
    implements GoogleApiClient.OnConnectionFailedListener {

    public static final String ARG_PRODUCT_CODE = "productCode";

    private String productCode;
    private List<Product> products;
    private ProductAdapter adapter;

    private ProgressDialog dialog;
    @Bind(R.id.list) RecyclerView list;

    private DatabaseReference myRef;
    private ValueEventListener listener;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        ButterKnife.bind(this);

        products = new ArrayList<>();
        adapter = new ProductAdapter(this, products);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        dialog = ProgressDialog.show(this, "", "Carregando...", false, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("varejos").child("0").child("products");

        if (getIntent() != null) {
            productCode = getIntent().getStringExtra(ARG_PRODUCT_CODE);
        }

        listener = new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Product>> t = new GenericTypeIndicator<List<Product>>() {
                };

                List<Product> mProducts = dataSnapshot.getValue(t);
                Product firstProduct = null;

                for (int i = 0; i < mProducts.size(); i++) {
                    if (mProducts.get(i).getCode().equals(productCode)) {
                        firstProduct = mProducts.remove(i);
                        break;
                    }
                }

                ListIterator<Product> iterator = mProducts.listIterator();

                if (firstProduct != null) {

                    while (iterator.hasNext()) {
                        Product next = iterator.next();
                        if (!next.getCategory().equals(firstProduct.getCategory())) {
                            iterator.remove();
                        }
                    }

                    mProducts.add(0, firstProduct);

                    products.clear();
                    products.addAll(mProducts);
                    adapter.notifyDataSetChanged();
                } else {
                    products.clear();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ProductsListActivity.this,
                        "Não foi possível encontrar o produto.", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }

                if (dialog.isShowing()) dialog.dismiss();
            }

            @Override public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.e("Firebase", "Failed to read value.", databaseError.toException());
                if (dialog.isShowing()) dialog.dismiss();
            }
        };

        Log.d("code", String.valueOf(productCode));
    }

    @SuppressWarnings("unused") @OnClick(R.id.fab) public void onFabClick() {
        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
    }

    @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override protected void onResume() {
        super.onResume();
        myRef.addValueEventListener(listener);
    }

    @Override protected void onStop() {
        myRef.removeEventListener(listener);
        super.onStop();
    }
}
