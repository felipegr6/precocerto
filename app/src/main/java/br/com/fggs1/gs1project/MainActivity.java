package br.com.fggs1.gs1project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @SuppressWarnings("unused") @OnClick(R.id.btn_camera) public void goToCamera() {
        //Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        ////intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        //intent.putExtra("SCAN_MODE", "EAN-13");
        //startActivityForResult(intent, 0);

        Intent intent = new Intent();

    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                Toast.makeText(this, String.format("%s", contents), Toast.LENGTH_SHORT)
                    .show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
