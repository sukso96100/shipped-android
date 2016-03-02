package xyz.youngbin.shipped.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import io.realm.RealmResults;
import xyz.youngbin.shipped.alarm.InfoUpdateNotification;
import xyz.youngbin.shipped.data.DataModel;
import xyz.youngbin.shipped.data.DataTool;
import xyz.youngbin.shipped.net.ShippedServer;

public class DataSyncService extends Service {

    String TAG = getClass().getSimpleName();
    Context mContext = DataSyncService.this;
    ShippedServer mServer;
    DataTool mDataTool;
    RealmResults<DataModel> mResults;

    public DataSyncService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "Sync Started");

        SharedPreferences mSP = PreferenceManager.getDefaultSharedPreferences(mContext);
        final Boolean mSwitch = mSP.getBoolean("update_notification", true);

        mDataTool = new DataTool(mContext);
        mResults = mDataTool.getAllItems();
        for(final DataModel item : mResults){
            mServer.updateItem(mContext, item.getTypeVal(),
                    item.getCarrierVal(), item.getNumber(),
                    new ShippedServer.ShippedServerCallback() {
                        @Override
                        public void onFinished(Boolean hasStatusChanges) {
                            if(hasStatusChanges&&mSwitch){
                                // Display Notification
                                InfoUpdateNotification.notify(mContext,
                                        item.getTypeVal(), item.getCarrierVal(), item.getNumber());
                            }
                        }
                    });
        }



        stopSelf();
    }
}
