package xyz.youngbin.shipped.net;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import xyz.youngbin.shipped.R;
import xyz.youngbin.shipped.data.DataTool;
import xyz.youngbin.shipped.data.Util;

/**
 * Created by youngbin on 16. 2. 23.
 */
public class NetworkTasks {
    String TAG = getClass().getSimpleName();

    private static String baseurl = "http://shipped.c.youngbin.xyz";
    DataTool mDataTool;
    Context mContext;
    Retrofit mRetrofit;
    ShippedServer.ShippedREST mREST;

    public NetworkTasks(Context context){
        this.mContext = context;
        mDataTool = new DataTool(mContext);
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .build();
        mREST = mRetrofit.create(ShippedServer.ShippedREST.class);
    }

    public void updateItem(final String Type, final String Carrier, final String Number){
        Log.d(TAG, "updateItem");
        // Set i18n String
        String I18n;
        switch (Carrier){
            default:
                I18n = "";
                break;
            case "ecms":
                I18n = mContext.getResources().getString(R.string.i18n_ecms);
                break;
            case "ups":
                I18n = mContext.getResources().getString(R.string.i18n_ups);
                break;
        }

        // Get Data from the server
        Call<JsonObject> call = mREST.getData(Type, Carrier, Number, I18n);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "updateItem : Got Data from the server");
                JsonObject JsonData= response.body();
                String Receiver = JsonData.get("receiver").getAsString();
                String Sender = JsonData.get("sender").getAsString();
                String Url = JsonData.get("url").getAsString();
                String Status = Util.JsonArrayProcesser(JsonData.getAsJsonArray("status"), "location");
                String Time = Util.JsonArrayProcesser(JsonData.getAsJsonArray("status"), "time");
                mDataTool.syncItem(Type, Carrier, Number, Receiver, Sender, Url, Status, Time);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "updateItem : Fail");

            }
        });
    }
}
