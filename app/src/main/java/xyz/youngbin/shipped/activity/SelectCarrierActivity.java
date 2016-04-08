package xyz.youngbin.shipped.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import xyz.youngbin.shipped.R;

public class SelectCarrierActivity extends AppCompatActivity {
    Context mContext = SelectCarrierActivity.this;
    String mTypeVal;
    ListView mListView;
    ProgressBar mProgressBar;
    String[] mCarrierArray;
    String[] mCarriersValArray;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_only);

        mProgressBar = (ProgressBar)findViewById(R.id.progress);
        mProgressBar.setVisibility(View.GONE);
        mListView = (ListView)findViewById(R.id.listView);

        mTypeVal = getIntent().getStringExtra("typeval");

        switch (mTypeVal){
            default:
                setUpListView(R.array.carriers_mails, R.array.carriers_mails_val);
                break;
            case "mail":
                setUpListView(R.array.carriers_mails, R.array.carriers_mails_val);
                break;
            case "aircargo":
                setUpListView(R.array.carriers_aircargo, R.array.carriers_aircargo_val);
                break;
        }
    }

    void setUpListView(int Carriers, int CarriersVals){
        mCarrierArray = mContext.getResources().getStringArray(Carriers);
        mCarriersValArray = mContext.getResources().getStringArray(CarriersVals);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_dropdown_item, mCarrierArray);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("CARRIER",mCarrierArray[position]);
                Log.d("CARRIERVAL",mCarriersValArray[position]);
                Intent intent = new Intent();
                intent.putExtra("carrier",mCarrierArray[position]);
                intent.putExtra("carrierval",mCarriersValArray[position]);

                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
