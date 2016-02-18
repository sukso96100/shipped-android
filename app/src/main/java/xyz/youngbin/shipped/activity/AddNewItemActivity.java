package xyz.youngbin.shipped.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import xyz.youngbin.shipped.R;

public class AddNewItemActivity extends AppCompatActivity {
    Context mContext = AddNewItemActivity.this;
    EditText mEtName;
    Spinner mSpType;
    Button mBtnCarrier;
    EditText mEtNum;

    String mName;
    String mType;
    String mCarrier;
    String mNum;
    String[] mCarrierVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        mEtName = (EditText)findViewById(R.id.et_name);
        mSpType = (Spinner)findViewById(R.id.sp_type);
        mBtnCarrier = (Button)findViewById(R.id.btn_carrier);
        mEtNum = (EditText)findViewById(R.id.et_num);

        mBtnCarrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SelectCarrierActivity.class);
                intent.putExtra("type",mType);
                startActivityForResult(intent,0);
            }
        });
        mSpType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    default:
                        mType = "mails";
                        break;
                    case 0:
                        mType = "mails";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);

        
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


}
