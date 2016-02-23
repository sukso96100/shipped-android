package xyz.youngbin.shipped.net;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import xyz.youngbin.shipped.R;
import xyz.youngbin.shipped.data.DataTool;
import xyz.youngbin.shipped.data.Util;

/**
 * Created by youngbin on 16. 2. 23.
 */
public class ShippedServer{

    private static String baseurl = "http://shipped.c.youngbin.xyz";

    public static class ShippedNetData{
        public final String postid;
        public final String url;
        public final String carrier;
        public final String sender;
        public final String receiver;
        public final JsonArray status;

        public ShippedNetData(String postid, String url, String carrier,
                              String sender, String receiver, JsonArray status){
            this.postid = postid;
            this.url = url;
            this.carrier = carrier;
            this.sender = sender;
            this.receiver = receiver;
            this.status = status;

        }
    }

    public interface ShippedREST{
        @GET("{type}/{carrier}/{num}/{i18n}")
        Call<ShippedNetData> getData(@Path("type") String Type, @Path("carrier") String Carrier,
                                 @Path("num") String Number,@Path("i18n") String I18n);
    }

    public interface ShippedServerCallback{
        public void onFinished();
    }

    public static void updateItem(Context mContext, final String Type, final String Carrier, final String Number, final ShippedServerCallback mCallback){

        final String TAG = "updateItem";
        final DataTool mDataTool = new DataTool(mContext);
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ShippedREST mREST = mRetrofit.create(ShippedServer.ShippedREST.class);

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
        Call<ShippedNetData> call = mREST.getData(Type, Carrier, Number, I18n);

//            ShippedNetData NetData = call.execute().body();
            call.enqueue(new Callback<ShippedNetData>() {
                @Override
                public void onResponse(Call<ShippedNetData> call, Response<ShippedNetData> response) {
                    ShippedNetData NetData = response.body();
                    Log.d(TAG, "updateItem : Got Data from the server");
//            Log.d(TAG, response.body().toString());
                    String Receiver = NetData.receiver;
                    String Sender = NetData.sender;
                    String Url = NetData.url;
                    String Status = Util.JsonArrayProcesser(NetData.status, "location");
                    String Time = Util.JsonArrayProcesser(NetData.status, "time");
                    mDataTool.syncItem(Type, Carrier, Number, Receiver, Sender, Url, Status, Time);
                    mCallback.onFinished();
                }

                @Override
                public void onFailure(Call<ShippedNetData> call, Throwable t) {
                    mCallback.onFinished();
                }
            });



    }
}
