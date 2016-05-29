package com.viveksb007.pzldemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity {

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";

    @BindView(R.id.btnScanBarCode)
    Button btnScanCode;
    @BindView(R.id.tvBarCodeResult)
    TextView tvBarCodeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            Toast.makeText(MainActivity.this, "Some Data is Coming", Toast.LENGTH_SHORT).show();
        }
        btnScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BarCodeActivity.class);
                i.putExtra(BarCodeActivity.AutoFocus,false);
                i.putExtra(BarCodeActivity.UseFlash,false);
                startActivityForResult(i, RC_BARCODE_CAPTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarCodeActivity.BarcodeObject);
                    tvBarCodeResult.setText(barcode.displayValue);
                    Log.d(TAG,"Barcode value: "+barcode.displayValue);
                } else {
                    tvBarCodeResult.setText(R.string.barcode_failure);
                }
            } else {
                tvBarCodeResult.setText(R.string.barcode_error);
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
