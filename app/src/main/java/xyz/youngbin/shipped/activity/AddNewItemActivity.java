package xyz.youngbin.shipped.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import xyz.youngbin.shipped.R;
import xyz.youngbin.shipped.data.DataModel;
import xyz.youngbin.shipped.data.DataTool;
import xyz.youngbin.shipped.receiver.BootReceiver;

public class AddNewItemActivity extends AppCompatActivity {

    Context mContext = AddNewItemActivity.this;
    EditText mEtName;
    Spinner mSpType;
    Button mBtnCarrier;
    EditText mEtNum;

    String mName;
    String mTypeVal;
    String[] mTypeValArray;
    String mCarrier;
    String mNum;
    String mCarrierVal;

    DataTool mDataTool;
    DataModel mPrevData;
    String TAG = mContext.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        mTypeValArray = mContext.getResources().getStringArray(R.array.type_val);

        //Setup Views
        mEtName = (EditText)findViewById(R.id.et_name);
        mSpType = (Spinner)findViewById(R.id.sp_type);
        mBtnCarrier = (Button)findViewById(R.id.btn_carrier);
        mEtNum = (EditText)findViewById(R.id.et_num);

        //If user is editing existing data, show previous data on widgets.
        mDataTool = new DataTool(mContext);
        if(getIntent().getBooleanExtra("isprevdata", false)){
            Log.d(TAG, "Loading Previous Data to Edit");
            mName = getIntent().getStringExtra("name");
            mTypeVal = getIntent().getStringExtra("typeval");
            mCarrier = getIntent().getStringExtra("carrier");
            mCarrierVal = getIntent().getStringExtra("carrierval");
            mNum = getIntent().getStringExtra("num");
            mPrevData = mDataTool.getItem(mTypeVal, mCarrierVal, mNum);

            mEtName.setText(mName);
            mBtnCarrier.setText(mCarrier);
            mEtNum.setText(mNum);

            switch (mTypeVal){
                default:
                    mSpType.setSelection(0);
                    break;
                case "mail":
                    mSpType.setSelection(0);
                    break;
                case "aircargo":
                    mSpType.setSelection(1);
                    break;
            }
        }else if (getIntent().getBooleanExtra("fromclip", false)){
            //If User was navigated from snackbar
            mEtNum.setText(getIntent().getStringExtra("num"));
        }

        mBtnCarrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SelectCarrierActivity.class);
                intent.putExtra("typeval",mTypeVal);
                startActivityForResult(intent,7);
            }
        });
        mSpType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTypeVal = mTypeValArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Just do nothing.
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d(TAG, "onActivityResult");
        if(requestCode==7 && resultCode==RESULT_OK){
            Log.d(TAG, "onActivityResult : RESULT_OK");
            mBtnCarrier.setText(intent.getStringExtra("carrier"));
            mCarrier = intent.getStringExtra("carrier");
            mCarrierVal = intent.getStringExtra("carrierval");
        }
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
            mName = mEtName.getText().toString();
            mNum = mEtNum.getText().toString();

            if(mPrevData==null){
                Log.d(TAG,"Adding New Data");
                mDataTool.addNewItem(mName, mTypeVal, mCarrierVal, mNum);
                Intent detailsIntent = new Intent(mContext, TrackingDetailsActivity.class);
                detailsIntent.putExtra("typeval", mTypeVal);
                detailsIntent.putExtra("carrier", mCarrier);
                detailsIntent.putExtra("carrierval", mCarrierVal);
                detailsIntent.putExtra("num", mNum);
                mDataTool.closeRealm();
                startActivity(detailsIntent);
                finish();
            }else {
                Log.d(TAG,"Editing Existing Data");
                mDataTool.saveItem(mPrevData, mName, mTypeVal, mCarrierVal, mNum);
                Intent intent = new Intent();
                intent.putExtra("typeval", mTypeVal);
                intent.putExtra("carrier", mCarrier);
                intent.putExtra("carrierval", mCarrierVal);
                intent.putExtra("num", mNum);
                setResult(RESULT_OK,intent);
                mDataTool.closeRealm();
                finish();
            }


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
