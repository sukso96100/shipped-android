package xyz.youngbin.mailtracker.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.youngbin.mailtracker.R;

public class AddNewItemActivity extends AppCompatActivity {
    Context mContext = AddNewItemActivity.this;
    EditText mEtName;
    Spinner mSpNat;
    Spinner mSpCarrier;
    EditText mEtNum;

    String mName;
    String mNat;
    String mCarrier;
    String mNum;
    String[] mCarrierVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        mEtName = (EditText)findViewById(R.id.et_name);
        mSpNat = (Spinner)findViewById(R.id.sp_nat);
        mSpCarrier = (Spinner)findViewById(R.id.sp_carrier);
        mEtNum = (EditText)findViewById(R.id.et_num);

//        mEtName.setOnEditorActionListener();
        mSpCarrier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCarrier = mCarrierVal[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpNat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    default:
                        mSpCarrier.setAdapter(setUpCarriersList(
                                mContext, R.array.carrier_us, R.array.carrier_us_val, "us"));
                        break;
                    case 0:
                        mSpCarrier.setAdapter(setUpCarriersList(
                                mContext, R.array.carrier_us, R.array.carrier_us_val, "us"));
                        break;
                    case 1:
                        mSpCarrier.setAdapter(setUpCarriersList(
                                mContext, R.array.carrier_ko, R.array.carrier_ko_val, "ko"));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.done) {
            startActivity(new Intent(mContext, TrackingDetailsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayAdapter<String> setUpCarriersList(Context c, int array, int valarray, String natcode){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                c, android.R.layout.simple_spinner_dropdown_item,
                c.getResources().getStringArray(array));
        mCarrierVal = c.getResources().getStringArray(valarray);
        mNat = natcode;
        return adapter;
    }
}
