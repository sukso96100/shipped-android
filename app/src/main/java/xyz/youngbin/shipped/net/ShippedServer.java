package xyz.youngbin.shipped.net;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by youngbin on 16. 2. 23.
 */
public class ShippedServer{
    public interface ShippedREST{
        @GET("{type}/{carrier}/{num}/{i18n}")
        Call<JsonObject> getData(@Path("type") String Type, @Path("carrier") String Carrier,
                                 @Path("num") String Number,@Path("i18n") String I18n);
    }
}
