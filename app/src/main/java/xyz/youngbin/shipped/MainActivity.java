package xyz.youngbin.shipped;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
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
implements AdapterView.OnItemClickListener, AbsListView.MultiChoiceModeListener {
    Context mContext = MainActivity.this;
    ListView mListView;
    View mEmptyView;
    TextView mInfo;
    DataTool mDataTool;

    String[] mTypeVal;
    String[] mType;
    String[] mName;
    String[] mNum;
    String[] mCarrierVal;
    String[] mCarrier;
    String[] mStatus;

    Boolean[] mSelection;
    MainItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView)findViewById(R.id.listView);
        mEmptyView = findViewById(R.id.empty);
        mInfo = (TextView)findViewById(R.id.info);

        mInfo.setText(mContext.getResources().getString(R.string.no_item));

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
    protected void onResume(){
        super.onResume();

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

            mSelection = new Boolean[mTypeVal.length];

            mAdapter = new MainItemAdapter(mContext, mTypeVal, mType, mName, mNum, mCarrier, mStatus, mSelection);
            mListView.setAdapter(mAdapter);

            mListView.setEmptyView(mEmptyView);

            mListView.setOnItemClickListener(this);

            mListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
            mListView.setMultiChoiceModeListener(this);

        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent detailsIntent = new Intent(mContext, TrackingDetailsActivity.class);
        detailsIntent.putExtra("typeval", mTypeVal[position]);
        detailsIntent.putExtra("carrier", mCarrier[position]);
        detailsIntent.putExtra("carrierval", mCarrierVal[position]);
        detailsIntent.putExtra("num", mNum[position]);
        startActivity(detailsIntent);
    }



    @Override
    public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
        mSelection[position] = checked;
        mAdapter.updateSelection(mSelection);
    }

    @Override
    public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
        getMenuInflater().inflate(R.menu.actionmode_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
        if(item.getItemId()==R.id.delete){
            for(int i=0; i<mSelection.length; i++){
                if(mSelection[i]==null||!mSelection[i]){

                }else {
                    mDataTool.removeItem(mTypeVal[i], mCarrierVal[i], mNum[i]);
                }
            }
            mode.finish();
            setupListView();
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(android.view.ActionMode mode) {
        mSelection = new Boolean[mTypeVal.length];
        mAdapter.updateSelection(mSelection);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        mDataTool.closeRealm();
    }
}
