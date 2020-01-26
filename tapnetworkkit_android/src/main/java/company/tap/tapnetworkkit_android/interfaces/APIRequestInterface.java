package company.tap.tapnetworkkit_android.interfaces;


import androidx.annotation.RestrictTo;

import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import retrofit2.http.Url;


@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface APIRequestInterface {

    @POST()
    Call<JsonObject> postRequest(@Url String apiName ,@Body JsonObject jsonObject);

    @GET()
     Call<JsonObject> getRequest(@Url String apiName);

    @PUT()
    Call<JsonObject> putRequest(@Url String apiName);

    @DELETE()
    Call<JsonObject> delete(@Url String apiName);


}
