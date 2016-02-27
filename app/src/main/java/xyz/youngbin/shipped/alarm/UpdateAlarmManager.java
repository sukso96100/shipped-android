package xyz.youngbin.shipped.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;

import xyz.youngbin.shipped.service.DataSyncService;

/**
 * Created by youngbin on 16. 2. 27.
 */
public class UpdateAlarmManager {
    public static void setupAlarm(Context mContext){

        Log.d("setupAlarm","Setting Up Alarm");
        AlarmManager mAM = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        SharedPreferences mSP = PreferenceManager.getDefaultSharedPreferences(mContext);
        String mFreq = mSP.getString("update_frequency","120");
        Intent intent = new Intent(mContext, DataSyncService.class);
        PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, intent, 0);

        mAM.cancel(pendingIntent);
        Log.d("setupAlarm","Alarm Canceled");

        if(!mFreq.equals("no")){
            mAM.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                    Integer.parseInt(mFreq) * 6000, pendingIntent);
            Log.d("setupAlarm","Alarm set up");
        }
    }
}
