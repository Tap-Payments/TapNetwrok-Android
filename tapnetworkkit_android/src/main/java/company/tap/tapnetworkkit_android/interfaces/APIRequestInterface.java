package company.tap.tapnetworkkit_android.interfaces;


import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import company.tap.tapnetworkkit_android.connection.API_Constants;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;


@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface APIRequestInterface {

    @POST()
    Call<JsonObject> postRequest(@Url String apiName ,@Body JsonObject jsonObject);

    @GET()
     Call<JsonObject> getRequest(@Url String apiName);

    @PUT()
    Call<JsonObject> putRequest(@Url String apiName,@Path("id")String id);

    @DELETE()
    Call<JsonObject> delRequest(@Url String apiName, @Query("/") String cusid, @Query("/") String cardid);

}
