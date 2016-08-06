package br.com.fggs1.gs1project.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import br.com.fggs1.gs1project.R;
import br.com.fggs1.gs1project.model.Product;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

public class ProductsListActivity extends AppCompatActivity
    implements GoogleApiClient.OnConnectionFailedListener {

    public static final String ARG_PRODUCT_CODE = "productCode";

    private String productCode;

    private ProgressDialog dialog;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        dialog = ProgressDialog.show(this, "", "Carregando...", false, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef =
            database.getReference().child("varejos").child("0").child("products");

        if (getIntent() != null) {
            productCode = getIntent().getStringExtra(ARG_PRODUCT_CODE);
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Product>> t = new GenericTypeIndicator<List<Product>>() {
                };
                List<Product> yourStringArray = dataSnapshot.getValue(t);
                for (Product p : yourStringArray) {
                    Log.w("Firebase", p.toString());
                }
                if (dialog.isShowing()) dialog.dismiss();
            }

            @Override public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.e("Firebase", "Failed to read value.", databaseError.toException());
                if (dialog.isShowing()) dialog.dismiss();
            }
        });

        Log.d("code", String.valueOf(productCode));
    }

    @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
