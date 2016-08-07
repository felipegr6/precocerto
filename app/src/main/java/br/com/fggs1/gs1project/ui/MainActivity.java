package br.com.fggs1.gs1project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.fggs1.gs1project.R;
import br.com.fggs1.gs1project.helper.Persistencia;
import br.com.fggs1.gs1project.model.Ad;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    private ValueEventListener listener;

    private int adsSize;
    private int actualAd;

    private List<Ad> mAds;
    private Handler mHandler;
    private Runnable mRunnable;

    @Bind(R.id.img_ads) ImageView imgAds;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mHandler = new Handler();
        mAds = new ArrayList<>();
        mRunnable = new Runnable() {
            @Override public void run() {
                changeAd(mAds);
                mHandler.postDelayed(this, 3500);
            }
        };

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("varejos").child("0").child("ads");

        listener = new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<List<Ad>> t = new GenericTypeIndicator<List<Ad>>() {
                };

                mAds.clear();
                mAds.addAll(dataSnapshot.getValue(t));

                if (mAds != null && !mAds.isEmpty()) {
                    adsSize = mAds.size();
                    actualAd = 0;
                }
            }

            @Override public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_logout) {
            Persistencia persistencia = new Persistencia(this);
            persistencia.setLogin(false);
            startActivity(new Intent(this, SplashActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override protected void onResume() {
        super.onResume();
        mHandler.post(mRunnable);
        myRef.addValueEventListener(listener);
    }

    @Override protected void onStop() {
        myRef.removeEventListener(listener);
        mHandler.removeCallbacks(mRunnable);
        super.onStop();
    }

    @SuppressWarnings("unused") @OnClick(R.id.btn_scan) public void goToCamera() {
        new IntentIntegrator(this).initiateScan();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Operação cancelada.", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(this, ProductsListActivity.class);
                intent.putExtra(ProductsListActivity.ARG_PRODUCT_CODE, result.getContents());
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void changeAd(List<Ad> ads) {
        if (ads != null && !ads.isEmpty()) {

            Ad ad = ads.get(actualAd);
            if (ad != null) {
                Glide.with(MainActivity.this).load(ad.getUrl()).into(imgAds);
            }

            actualAd++;

            if (actualAd == adsSize) {
                actualAd = 0;
            }
        }
    }
}
