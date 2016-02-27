package xyz.youngbin.shipped.profit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import xyz.youngbin.shipped.R;
import xyz.youngbin.shipped.data.DataTool;

/**
 * Created by youngbin on 16. 2. 26.
 */
public class AdMobUtil {
    public static View getAdMobBanner(Context mContext){
        LayoutInflater mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mAdBanner = mLayoutInflater.inflate(R.layout.banner_admob, null);
        AdView mAdView = (AdView)mAdBanner.findViewById(R.id.adView);
        if(isSatisfiesConditions(mContext)){
            AdRequest mAdRequest = new AdRequest.Builder().build();
            mAdView.loadAd(mAdRequest);
        }else{
            mAdView.setVisibility(View.GONE);
            mAdBanner.setVisibility(View.GONE);
        }
        return mAdBanner;
    }

    public static void loadAdInto(View mAdBanner, Context mContext){
        if(isSatisfiesConditions(mContext)){
            AdView mAdView = (AdView)mAdBanner.findViewById(R.id.adView);
            AdRequest mAdRequest = new AdRequest.Builder().build();
            mAdView.loadAd(mAdRequest);
        }else {
            mAdBanner.setVisibility(View.GONE);
        }
    }

    public static Boolean isSatisfiesConditions(Context mContext){
        Boolean mIsNetConnected = isNetworkConnected(mContext);
        DataTool mDataTool = new DataTool(mContext);
        int mItemSize = mDataTool.getAllItems().size();

        if(mItemSize > 0 && mIsNetConnected){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isNetworkConnected(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
