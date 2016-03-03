package xyz.youngbin.shipped.fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import xyz.youngbin.shipped.R;
import xyz.youngbin.shipped.alarm.UpdateAlarmManager;

/**
 * Created by youngbin on 16. 3. 3.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        String VersionName = "";
        try {
            VersionName = getActivity().getPackageManager()
                    .getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        getPreferenceManager().findPreference("app_ver").setSummary(VersionName);
        getPreferenceManager().findPreference("update_frequency").setOnPreferenceChangeListener(this);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        UpdateAlarmManager.setupAlarm(getActivity());
        return true;
    }
}