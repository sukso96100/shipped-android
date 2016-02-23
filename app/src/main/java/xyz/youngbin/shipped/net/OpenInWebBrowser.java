package xyz.youngbin.shipped.net;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.util.TypedValue;


import xyz.youngbin.shipped.R;

/**
 * Created by youngbin on 16. 2. 23.
 */
public class OpenInWebBrowser {
    public static void execute(final Activity mActivity, final String Url) {

        if(Url!=null&&!Url.equals("")) {
            if (Build.VERSION.SDK_INT < 18) {
                // Show Web Page In User's Browser
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
                mActivity.startActivity(intent);
            } else {
                // Show Web Page In Chrome Custom Tab
                final TypedValue value = new TypedValue();
                mActivity.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
                String pkgname = "com.android.chrome";
                CustomTabsServiceConnection mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
                    @Override
                    public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                        customTabsClient.warmup(0L);
                        CustomTabsSession customTabsSession = customTabsClient.newSession(null);
                        boolean mayLaunchUrl = customTabsSession.mayLaunchUrl(Uri.parse(Url), null, null);
                        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder(customTabsSession);
                        builder.setToolbarColor(value.data);
                        CustomTabsIntent customTabsIntent = builder.build();
                        customTabsIntent.launchUrl(mActivity, Uri.parse(Url));
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                };
                CustomTabsClient.bindCustomTabsService(mActivity, pkgname, mCustomTabsServiceConnection);
            }
        }
    }
}

