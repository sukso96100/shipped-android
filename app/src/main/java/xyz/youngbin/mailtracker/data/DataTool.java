package xyz.youngbin.mailtracker.data;

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

    public void addNewItem(String Name, String Nat, String Carrier, String Number){
        if(getItem(Nat, Carrier, Number)==null){
            mRealm.beginTransaction();
            MailModel MM = mRealm.createObject(MailModel.class);
            MM.setName(Name);
            MM.setNat(Nat);
            MM.setCarrier(Carrier);
            MM.setNumber(Number);
            mRealm.commitTransaction();
        }

    }

    public void saveItem(MailModel PrevData, String Name, String Nat, String Carrier, String Number){
        mRealm.beginTransaction();
        PrevData.setName(Name);
        PrevData.setNat(Nat);
        PrevData.setCarrier(Carrier);
        PrevData.setNumber(Number);
        mRealm.commitTransaction();
    }

    public void syncItem(){}

    public MailModel getItem(String Nat, String Carrier, String Number){
        RealmQuery<MailModel> query = mRealm.where(MailModel.class)
                .equalTo("Nat",Nat)
                .equalTo("Carrier",Carrier)
                .equalTo("Number",Number);

        MailModel result = query.findFirst();

        if(result!=null){
            return result;
        }else{
            return null;
        }
    }
}
