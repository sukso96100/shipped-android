package xyz.youngbin.shipped.activity;


import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

import xyz.youngbin.shipped.R;
import xyz.youngbin.shipped.alarm.UpdateAlarmManager;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_only);

        PreferenceFragment mFragment = new SettingsFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.container, mFragment).commit();
    }

    public static class SettingsFragment extends PreferenceFragment{


        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
            getPreferenceManager().findPreference("update_frequency")
                    .setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    UpdateAlarmManager.setupAlarm(getActivity());
                    return true;
                }
            });

        }

    }



}