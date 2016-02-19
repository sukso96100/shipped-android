package xyz.youngbin.shipped.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import xyz.youngbin.shipped.R;

public class TrackingDetailsActivity extends AppCompatActivity {
    Context mContext = TrackingDetailsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_details);
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
                startActivityForResult(editIntent, 7);
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
