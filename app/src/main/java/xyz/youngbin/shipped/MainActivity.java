package xyz.youngbin.shipped;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.RealmResults;
import xyz.youngbin.shipped.activity.AddNewItemActivity;
import xyz.youngbin.shipped.activity.TrackingDetailsActivity;
import xyz.youngbin.shipped.data.DataModel;
import xyz.youngbin.shipped.data.DataTool;
import xyz.youngbin.shipped.data.MainItemAdapter;
import xyz.youngbin.shipped.data.Util;
import xyz.youngbin.shipped.profit.AdMobUtil;
import xyz.youngbin.shipped.activity.SettingsActivity;

public class MainActivity extends AppCompatActivity
implements AdapterView.OnItemClickListener, AbsListView.MultiChoiceModeListener {
    Context mContext = MainActivity.this;
    ListView mListView;
    View mEmptyView;
    TextView mInfo;
    DataTool mDataTool;
    RealmResults<DataModel> mData;

    ArrayList<String> mSelection;
    MainItemAdapter mAdapter;

    View mAdBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdBanner = AdMobUtil.getAdMobBanner(mContext);
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
            startActivity(new Intent(mContext, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupListView(){

        try{mListView.removeHeaderView(mAdBanner);}catch (Exception e){}
        mData = mDataTool.getAllItems();
        mSelection = new ArrayList<>();
        mAdapter = new MainItemAdapter(mContext, mData, true, mSelection);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(mEmptyView);
        mListView.setOnItemClickListener(this);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        mListView.setMultiChoiceModeListener(this);
        mListView.addHeaderView(mAdBanner);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        int position = pos - 1;
        Intent detailsIntent = new Intent(mContext, TrackingDetailsActivity.class);
        detailsIntent.putExtra("typeval", mData.get(position).getTypeVal());
        detailsIntent.putExtra("carrier", Util.carrierValtoCarrierString(mContext,
                mData.get(position).getTypeVal(), mData.get(position).getCarrierVal()));
        detailsIntent.putExtra("carrierval", mData.get(position).getCarrierVal());
        detailsIntent.putExtra("num", mData.get(position).getNumber());
        startActivity(detailsIntent);
    }

    @Override
    public void onItemCheckedStateChanged(android.view.ActionMode mode, int pos, long id, boolean checked) {
        int position = pos - 1;
        if(checked){
            mSelection.add(String.valueOf(position));
        }else {
            mSelection.remove(String.valueOf(position));
        }
        Log.d("Main items Selected", mSelection.toString());
        mAdapter.updateSelection(mSelection);
        mode.setTitle(String.valueOf(mSelection.size()));
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
            for(String selection : mSelection){
               mDataTool.removeItem(mData.get(Integer.parseInt(selection)).getTypeVal(),
                       mData.get(Integer.parseInt(selection)).getCarrierVal(),
                       mData.get(Integer.parseInt(selection)).getNumber());
            }
            mode.finish();
            setupListView();
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(android.view.ActionMode mode) {
        mSelection = new ArrayList<>();
        mAdapter.updateSelection(mSelection);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        mDataTool.closeRealm();
    }
}
