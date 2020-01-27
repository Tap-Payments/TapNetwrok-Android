package company.tap.nativenetworkkit;


import android.content.Context;

import com.google.gson.JsonObject;

import company.tap.tapnetworkkit.connection.RetrofitHelper;
import company.tap.tapnetworkkit.interfaces.APIRequestCallback;
import company.tap.tapnetworkkit.interfaces.APIRequestInterface;
import company.tap.tapnetworkkit.request_manager.RequestManager;

public class NetworkClient {
    /**
     * Intermediate interface between the Network Layer
     * and the ViewModel. Here we pass API method name , Request body and set Base_URL
     * via API Request Interface.
     **/
    RequestManager requestManager;
    private APIRequestInterface apiRequestInterface;

     private NetworkClient() {
         /**
          *Required to set baseURL on retrofit
          */
         apiRequestInterface = RetrofitHelper.getApiHelper(API_Methods.BASE_URL);
        requestManager = new RequestManager(apiRequestInterface);

    }

    public void init(APIRequestCallback callback,Context context){
         requestManager.request(new RequestManager.TapRequest(apiRequestInterface.getRequest(API_Methods.INIT),callback),context);
    }

    public void getPaymentOptions(JsonObject jsonObject, APIRequestCallback callback,Context context) {
         requestManager.request(new RequestManager.TapRequest(apiRequestInterface.postRequest(API_Methods.PAYMENT_TYPES,jsonObject), callback),context );
    }
    public void deleteCard(String customerId, String cardId, final APIRequestCallback requestCallback,Context context){
        requestManager.request(new RequestManager.TapRequest(apiRequestInterface.delete(API_Methods.DELETE_CARD+"/"+customerId+"/"+cardId),requestCallback),context);
    }
    public void updateCharge(String chargeId, final APIRequestCallback requestCallback,Context context){
        requestManager.request(new RequestManager.TapRequest(apiRequestInterface.putRequest(API_Methods.CHARGE_ID+"/"+chargeId),requestCallback),context);
    }

    private static class SingletonCreationAdmin {
        private static final NetworkClient INSTANCE = new NetworkClient();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static NetworkClient getInstance() {
        return SingletonCreationAdmin.INSTANCE;
    }

}
