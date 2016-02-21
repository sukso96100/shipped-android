package xyz.youngbin.shipped.data;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

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

    public void addNewItem(String Name, String TypeVal, String CarrierVal, String Number){
        Log.d(TAG, "addNewItem");
        DataModel mItem = getItem(TypeVal, CarrierVal, Number);
        if(mItem==null){
            Log.d(TAG, "Adding New Item...");
            mRealm.beginTransaction();
            DataModel MM = mRealm.createObject(DataModel.class);
            MM.setName(Name);
            MM.setTypeVal(TypeVal);
            MM.setCarrierVal(CarrierVal);
            MM.setNumber(Number);
            mRealm.commitTransaction();
            Log.d(TAG, "addNewItem : Added new one");
        }else{
            saveItem(mItem, Name, TypeVal, CarrierVal, Number);
            Log.d(TAG, "addNewItem : saved");
        }

    }

    public void saveItem(DataModel PrevData, String Name, String TypeVal, String CarrierVal, String Number){
        Log.d(TAG, "saveItem");
        mRealm.beginTransaction();
        PrevData.setName(Name);
        PrevData.setTypeVal(TypeVal);
        PrevData.setCarrierVal(CarrierVal);
        PrevData.setNumber(Number);
        mRealm.commitTransaction();
        Log.d(TAG, "saveitem : saved");
    }

    public void syncItem(DataModel PrevData, String Receiver, String Sender, String Url, String Status, String Time){
        Log.d(TAG, "syncItem");
        mRealm.beginTransaction();
        PrevData.setReceiver(Receiver);
        PrevData.setSender(Sender);
        PrevData.setUrl(Url);
        PrevData.setStatusArray(Status);
        PrevData.setTimeArray(Time);
        mRealm.commitTransaction();
    }

    public DataModel getItem(String TypeVal, String CarrierVal, String Number){
        Log.d(TAG, "getItem");
        RealmQuery<DataModel> query = mRealm.where(DataModel.class)
                .equalTo("TypeVal",TypeVal)
                .equalTo("CarrierVal",CarrierVal)
                .equalTo("Number",Number);

        DataModel result = query.findFirst();

        if(result!=null){
            Log.d(TAG, "getItem : returning result");
            return result;
        }else{
            Log.d(TAG, "getItem : null");
            return null;
        }
    }

    public RealmResults<DataModel> getAllItems(){
        Log.d(TAG, "getAllItems");
        RealmQuery<DataModel> query = mRealm.where(DataModel.class);
        RealmResults<DataModel> results = query.findAll();
        return results;
    }

    public void removeItem(String TypeVal, String CarrierVal, String Number){
        DataModel toRemove = getItem(TypeVal, CarrierVal, Number);
        mRealm.beginTransaction();
        toRemove.removeFromRealm();
        mRealm.commitTransaction();
    }

}
