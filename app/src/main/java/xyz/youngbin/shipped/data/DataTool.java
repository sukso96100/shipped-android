package xyz.youngbin.shipped.data;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmQuery;

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

    public void syncItem(MailModel PrevData, String Receiver, String Sender, String Url, String[] Status, String[] Time){
        mRealm.beginTransaction();
        PrevData.setReceiver(Receiver);
        PrevData.setSender(Sender);
        PrevData.setUrl(Url);
        PrevData.setStatusArray(convertArrayToString(Status));
        PrevData.setTimeArray(convertArrayToString(Time));
        mRealm.commitTransaction();
    }

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

    // CONVERTER
    // http://stackoverflow.com/questions/9053685/android-sqlite-saving-string-array
    private static String strSeparator = "__,__";
    private static String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }
    private static String[] convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        return arr;
    }
}