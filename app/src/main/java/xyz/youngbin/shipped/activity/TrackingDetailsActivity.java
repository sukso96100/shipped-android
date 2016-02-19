package xyz.youngbin.shipped.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;

import xyz.youngbin.shipped.R;
import xyz.youngbin.shipped.data.DataModel;
import xyz.youngbin.shipped.data.DataTool;
import xyz.youngbin.shipped.data.Util;

public class TrackingDetailsActivity extends AppCompatActivity {
    Context mContext = TrackingDetailsActivity.this;
    DataModel mData;
    DataTool mDataTool;

    String mType;
    String mCarrier ;
    String mCarrierVal;
    String mNum ;

    String[] mStatus;
    String[] mTime;

    ListView mListView;

    String TAG = mContext.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_details);

        mListView = (ListView)findViewById(R.id.listView);

        mDataTool = new DataTool(mContext);

        mType = getIntent().getStringExtra("type");
        mCarrier = getIntent().getStringExtra("carrier");
        mCarrierVal = getIntent().getStringExtra("carrierval");
        mNum = getIntent().getStringExtra("num");

        mData = mDataTool.getItem(mType, mCarrierVal, mNum);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d(TAG, "onActivityResult");
        if(requestCode==7 && resultCode==RESULT_OK){
            Log.d(TAG, "onActivityResult : RESULT_OK");
            mType = intent.getStringExtra("type");
            mCarrier = intent.getStringExtra("carrier");
            mCarrierVal = intent.getStringExtra("carrierval");
            mNum = intent.getStringExtra("num");

            mData = mDataTool.getItem(mType, mCarrierVal, mNum);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tracking_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_edit:
                Intent editIntent = new Intent(mContext, AddNewItemActivity.class);
                editIntent.putExtra("type", mType);
                editIntent.putExtra("carrier", mCarrier);
                editIntent.putExtra("carrierval", mCarrierVal);
                editIntent.putExtra("num", mNum);
                startActivityForResult(editIntent, 7);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    void setupListView(){
        LayoutInflater mLayoutInflaer = (LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout mHeader;
        switch (mType){
            case "mail":
                mHeader = (LinearLayout)mLayoutInflaer.inflate(R.layout.header_mails, null);
                break;
        }

        mStatus = Util.convertStringToArray(mData.getStatusArray());
        mTime = Util.convertStringToArray(mData.getTimeArray());

    }
}
