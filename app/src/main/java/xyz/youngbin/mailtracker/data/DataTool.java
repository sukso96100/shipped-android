package xyz.youngbin.mailtracker.data;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;

/**
 * Created by youngbin on 16. 2. 10.
 */
public class DataTool {
    String TAG = "DataTool";
    Realm mRealm;
    Context mContext;
    public DataTool(Context c){
        mContext = c;
        mRealm = Realm.getInstance(mContext);
        Log.d(TAG, "Got Realm Instance");
    }

    public void addNewItem(String Name, String Nat, String Carrier, String Number){

    }

    public void checkIfExists(String Nat, String Carrier, String Number){}
}
