package com.viveksb007.pzldemo;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity {

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    DBHelper myDb;

    @BindView(R.id.btnScanBarCode)
    Button mBtnScanCode;
    @BindView(R.id.tvBarCodeResult)
    TextView mTvBarCodeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myDb = new DBHelper(this);
        mBtnScanCode.setOnClickListener(new View.OnClickListener() {
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
    protected void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarCodeActivity.BarcodeObject);
                    mTvBarCodeResult.setText(barcode.displayValue);
                    myDb.insertCode(barcode.displayValue);
                    Log.d(TAG,"Barcode value: "+barcode.displayValue);
                } else {
                    mTvBarCodeResult.setText(R.string.barcode_failure);
                }
            } else {
                mTvBarCodeResult.setText(R.string.barcode_error);
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_history){
            DialogFragment history = new History();
            history.show(getSupportFragmentManager(),TAG);
        }else if(id == R.id.about){
            DialogFragment aboutDialog = new About();
            aboutDialog.show(getSupportFragmentManager(),TAG);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
