package xyz.youngbin.shipped.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import xyz.youngbin.shipped.alarm.UpdateAlarmManager;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d("BootReceiver", "Setting up alarm on boot");
        UpdateAlarmManager.setupAlarm(context);
    }
}
