package company.tap.tapnetworkkit.controller;

import android.content.Context;

import company.tap.tapnetworkkit.connection.RetrofitHelper;
import company.tap.tapnetworkkit.enums.TapMethodType;
import company.tap.tapnetworkkit.interfaces.APIRequestCallback;
import company.tap.tapnetworkkit.interfaces.APIRequestInterface;
import company.tap.tapnetworkkit.interfaces.TapRequestBodyBase;
import company.tap.tapnetworkkit.request.TapRequest;

public class NetworkController {

    private APIRequestInterface apiRequestInterface;

    /**
     * Required to set baseURL on retrofit
     */
    public void setBaseUrl(String baseURL, Context context) {
        apiRequestInterface = RetrofitHelper.getApiHelper(baseURL, context);
    }

    public void processRequest(TapMethodType method, String apiName, TapRequestBodyBase requestBody, APIRequestCallback callback, int requestCode) {
        switch (method) {
            case GET:
                new TapRequest(apiRequestInterface.getRequest(apiName), callback, requestCode).run();
                break;
            case POST:
                new TapRequest(apiRequestInterface.postRequest(apiName, requestBody), callback, requestCode).run();
                break;
            case PUT:
                new TapRequest(apiRequestInterface.putRequest(apiName), callback, requestCode).run();
                break;
            case DELETE:
                new TapRequest(apiRequestInterface.delete(apiName), callback, requestCode).run();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + method);
        }
    }

    private static class SingletonCreationAdmin {
        private static final NetworkController INSTANCE = new NetworkController();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static NetworkController getInstance() {
        return SingletonCreationAdmin.INSTANCE;
    }

}


