package xyz.youngbin.mailtracker.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import xyz.youngbin.mailtracker.R;

public class AddNewItemActivity extends AppCompatActivity {
    Context mContext = AddNewItemActivity.this;
    EditText mEtName;
    Spinner mSpNat;
    Spinner mSpCarrier;
    EditText mEtNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        mEtName = (EditText)findViewById(R.id.et_name);
        mSpNat = (Spinner)findViewById(R.id.sp_nat);
        mSpCarrier = (Spinner)findViewById(R.id.sp_carrier);
        mEtNum = (EditText)findViewById(R.id.et_num);
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
