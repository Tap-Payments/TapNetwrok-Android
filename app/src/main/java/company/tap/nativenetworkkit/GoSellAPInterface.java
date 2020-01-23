package company.tap.nativenetworkkit;


import com.google.gson.JsonObject;

import org.json.JSONObject;

import company.tap.tapnetworkkit_android.connection.RetrofitHelper;
import company.tap.tapnetworkkit_android.callbacks.APIRequestCallback;
import company.tap.tapnetworkkit_android.exceptionEngine.GoSellError;
import company.tap.tapnetworkkit_android.interfaces.APIRequestInterface;
import company.tap.tapnetworkkit_android.request_manager.RequestManager;
import company.tap.tapnetworkkit_android.response.BaseResponse;
import company.tap.tapnetworkkit_android.response.GenericResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class GoSellAPInterface {
    /**
     * Intermediate interface between the Network Layer
     * and the ViewModel. Here we pass API method name , Request body and set Base_URL
     * via API Request Interface.
     **/
    RequestManager requestManager;
    private APIRequestInterface apiRequestInterface;

     private GoSellAPInterface() {
         apiRequestInterface = RetrofitHelper.getApiHelper(API_Methods.BASE_URL);
        requestManager = new RequestManager(apiRequestInterface);

    }

    public void init(APIRequestCallback callback){
         requestManager.request(new RequestManager.DelayedRequest(apiRequestInterface.getRequest(API_Methods.INIT),callback),false);
    }

    public void getPaymentOptions(JsonObject jsonObject, APIRequestCallback callback) {
         requestManager.request(new RequestManager.DelayedRequest(apiRequestInterface.postRequest(API_Methods.PAYMENT_TYPES,jsonObject), callback),false );
    }
    public void deleteCard(String customerId, String cardId, final APIRequestCallback requestCallback){
        requestManager.request(new RequestManager.DelayedRequest(apiRequestInterface.delRequest(API_Methods.DELETE_CARD,customerId,cardId),requestCallback),false);
    }

    private static class SingletonCreationAdmin {
        private static final GoSellAPInterface INSTANCE = new GoSellAPInterface();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static GoSellAPInterface getInstance() {
        return SingletonCreationAdmin.INSTANCE;
    }

}
