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

import xyz.youngbin.shipped.R;

public class SelectCarrierActivity extends AppCompatActivity {
    Context mContext = SelectCarrierActivity.this;
    String mType;
    ListView mListView;
    String[] mCarriers;
    String[] mCarriersVals;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_only);

        mListView = (ListView)findViewById(R.id.listView);

        mType = getIntent().getStringExtra("type");

        switch (mType){
            default:
                setUpListView(R.array.carriers_mails, R.array.carriers_mails_val);
                break;
            case "mail":
                setUpListView(R.array.carriers_mails, R.array.carriers_mails_val);
                break;
        }
    }

    void setUpListView(int Carriers, int CarriersVals){
        mCarriers = mContext.getResources().getStringArray(Carriers);
        mCarriersVals = mContext.getResources().getStringArray(CarriersVals);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_dropdown_item, mCarriers);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("CARRIER",mCarriers[position]);
                Log.d("CARRIERVAL",mCarriersVals[position]);
                Intent intent = new Intent();
                intent.putExtra("carrier",mCarriers[position]);
                intent.putExtra("carrierval",mCarriersVals[position]);

                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
