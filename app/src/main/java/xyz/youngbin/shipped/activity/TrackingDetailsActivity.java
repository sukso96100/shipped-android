package xyz.youngbin.shipped.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import xyz.youngbin.shipped.R;
import xyz.youngbin.shipped.data.DataModel;
import xyz.youngbin.shipped.data.DataTool;
import xyz.youngbin.shipped.data.DetailsAdapter;
import xyz.youngbin.shipped.data.Util;
import xyz.youngbin.shipped.net.ShippedServer;

public class TrackingDetailsActivity extends AppCompatActivity {

    Context mContext = TrackingDetailsActivity.this;
    DataModel mData;
    DataTool mDataTool;

    String mTypeVal;
    String mCarrier;
    String mCarrierVal;
    String mNum;

    String[] mStatus;
    String[] mTime;

    ListView mListView;
    View mHeader;

    String TAG = mContext.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_only);

        mListView = (ListView)findViewById(R.id.listView);

        mDataTool = new DataTool(mContext);

        mTypeVal = getIntent().getStringExtra("typeval");
        mCarrier = getIntent().getStringExtra("carrier");
        mCarrierVal = getIntent().getStringExtra("carrierval");
        mNum = getIntent().getStringExtra("num");

        ShippedServer.updateItem(mContext, mTypeVal, mCarrierVal, mNum);

        mData = mDataTool.getItem(mTypeVal, mCarrierVal, mNum);

        setupListView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d(TAG, "onActivityResult");
        if(requestCode==7 && resultCode==RESULT_OK){
            Log.d(TAG, "onActivityResult : RESULT_OK");
            mTypeVal = intent.getStringExtra("typeval");
            mCarrier = intent.getStringExtra("carrier");
            mCarrierVal = intent.getStringExtra("carrierval");
            mNum = intent.getStringExtra("num");

            ShippedServer.updateItem(mContext, mTypeVal, mCarrierVal, mNum);

            mData = mDataTool.getItem(mTypeVal, mCarrierVal, mNum);

            setupListView();
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
                try{
                    editIntent.putExtra("name", mData.getName());
                }catch (Exception e){}
                editIntent.putExtra("typeval", mTypeVal);
                editIntent.putExtra("carrier", mCarrier);
                editIntent.putExtra("carrierval", mCarrierVal);
                editIntent.putExtra("num", mNum);
                editIntent.putExtra("isprevdata", true);
                startActivityForResult(editIntent, 7);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    void setupListView(){

        try{mListView.removeHeaderView(mHeader);}catch (Exception e){}

        LayoutInflater mLayoutInflaer = (LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        try{
            mStatus = Util.convertStringToArray(mData.getStatusArray());
            mTime = Util.convertStringToArray(mData.getTimeArray());
        }catch (Exception e){
            mStatus = new String[0];
            mTime = new String[0];
        }

        DetailsAdapter adapter = new DetailsAdapter(mStatus, mTime, mContext);
        mListView.setAdapter(adapter);


        switch (mTypeVal){
            case "mail":
                mHeader = mLayoutInflaer.inflate(R.layout.header_mails, null);
                TextView txtName = (TextView)mHeader.findViewById(R.id.name);
                TextView txtCarrier = (TextView)mHeader.findViewById(R.id.carrier);
                TextView txtNum = (TextView)mHeader.findViewById(R.id.num);
                TextView txtSender = (TextView)mHeader.findViewById(R.id.sender);
                TextView txtReceiver = (TextView)mHeader.findViewById(R.id.receiver);

                try{
                    txtName.setText(mData.getName());
                }catch (Exception e){
                    e.printStackTrace();
                    txtName.setText(mNum);
                }
                txtCarrier.setText(mCarrier);
                txtNum.setText(mNum);
                if(mData.getSender()==null||mData.getSender().equals("")){
                    txtSender.setText(" : "+mContext.getResources().getString(R.string.unknown));
                    }else{
                    txtSender.setText(" : "+mData.getSender());
                    }
                if(mData.getReceiver()==null||mData.getReceiver().equals("")){
                    txtReceiver.setText(" : "+mContext.getResources().getString(R.string.unknown));
                    }else{
                    txtReceiver.setText(" : "+mData.getReceiver());
                    }

                break;
        }
        if(mStatus.length==0){
            Log.d(TAG, "Adding EmptyView");
            TextView mInfo = (TextView)mHeader.findViewById(R.id.info);
            mInfo.setText(mContext.getResources().getString(R.string.no_info));
        }else{
            mHeader.findViewById(R.id.empty).setVisibility(View.GONE);
        }
        mListView.addHeaderView(mHeader);
    }
}
