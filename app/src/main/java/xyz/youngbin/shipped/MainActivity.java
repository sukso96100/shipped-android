package xyz.youngbin.shipped;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import io.realm.RealmResults;
import xyz.youngbin.shipped.activity.AddNewItemActivity;
import xyz.youngbin.shipped.activity.TrackingDetailsActivity;
import xyz.youngbin.shipped.data.DataModel;
import xyz.youngbin.shipped.data.DataTool;
import xyz.youngbin.shipped.data.MainItemAdapter;
import xyz.youngbin.shipped.data.Util;

public class MainActivity extends AppCompatActivity
implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
    Context mContext = MainActivity.this;
    ListView mListView;
    DataTool mDataTool;

    String[] mTypeVal;
    String[] mType;
    String[] mName;
    String[] mNum;
    String[] mCarrierVal;
    String[] mCarrier;
    String[] mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListView = (ListView)findViewById(R.id.listView);

        mDataTool = new DataTool(mContext);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, AddNewItemActivity.class));
            }
        });

        setupListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public View getEmptyView(Context c, String info){
        LayoutInflater LI = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View EmptyView = LI.inflate(R.layout.emptyview, null);
        TextView InfoTxt = (TextView)EmptyView.findViewById(R.id.info);
        InfoTxt.setText(info);

        return EmptyView;
    }

    public void setupListView(){
        RealmResults<DataModel> results = mDataTool.getAllItems();

        for(int i=0; i<results.size(); i++){

            //initialize Arrays
            mTypeVal = new String[results.size()];
            mType = new String[results.size()];
            mName = new String[results.size()];
            mNum = new String[results.size()];
            mCarrierVal = new String[results.size()];
            mCarrier = new String[results.size()];
            mStatus = new String[results.size()];

            //put data
            mTypeVal[i] = results.get(i).getTypeVal();
            mType[i] = Util.typeValToTypeString(mContext, results.get(i).getTypeVal());
            mName[i] = results.get(i).getName();
            mNum[i] = results.get(i).getNumber();
            mCarrierVal[i] = results.get(i).getCarrierVal();
            mCarrier[i] = Util.carrierValtoCarrierString(mContext, results.get(i).getTypeVal(), results.get(i).getCarrierVal());

            try {
                String[] tmpStatusArray = Util.convertStringToArray(results.get(i).getStatusArray());
                mStatus[i] = tmpStatusArray[0];
            }catch (Exception e){
                mStatus[i] = mContext.getResources().getString(R.string.no_status);
            }

            MainItemAdapter adapter = new MainItemAdapter(mContext, mTypeVal, mType, mName, mNum, mCarrier, mStatus);
            mListView.setAdapter(adapter);

            mListView.setEmptyView(getEmptyView(mContext,
                    mContext.getResources().getString(R.string.no_item)));
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent detailIntent = new Intent(mContext, TrackingDetailsActivity.class);
        detailIntent.putExtra("type", mTypeVal);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
