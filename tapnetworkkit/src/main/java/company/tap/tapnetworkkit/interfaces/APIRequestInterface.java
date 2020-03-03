package company.tap.tapnetworkkit.interfaces;



import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import retrofit2.http.Url;



public interface APIRequestInterface {

    @POST()
    Call<JsonObject> postRequest(@Url String apiName ,@Body TapRequestBodyBase requestBody);

    @GET()
     Call<JsonObject> getRequest(@Url String apiName);

    @PUT()
    Call<JsonObject> putRequest(@Url String apiName);

    @DELETE()
    Call<JsonObject> delete(@Url String apiName);


}
