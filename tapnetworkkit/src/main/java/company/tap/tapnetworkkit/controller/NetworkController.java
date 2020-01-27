package company.tap.tapnetworkkit.controller;


import android.content.Context;

import com.google.gson.JsonObject;

import company.tap.tapnetworkkit.connection.RetrofitHelper;
import company.tap.tapnetworkkit.enums.TapMethodType;
import company.tap.tapnetworkkit.interfaces.APIRequestCallback;
import company.tap.tapnetworkkit.interfaces.APIRequestInterface;
import company.tap.tapnetworkkit.request_manager.RequestManager;

import static company.tap.tapnetworkkit.request_manager.RequestManager.*;

public class NetworkController {


    private  RequestManager requestManager;
    private   APIRequestInterface apiRequestInterface;

     private NetworkController() {
        requestManager = new RequestManager(apiRequestInterface);
    }

    /**
     *Required to set baseURL on retrofit
     */
    public  void setBaseUrl(String baseURL){
        apiRequestInterface = RetrofitHelper.getApiHelper(baseURL);
    }

    public void processRequest(TapMethodType method, String apiName,JsonObject requestBody ,APIRequestCallback callback, Context context){
        switch(method){
            case GET:
                requestManager.request( new TapRequest(apiRequestInterface.getRequest(apiName), callback),context);
                break;
            case POST:
                requestManager.request( new TapRequest(apiRequestInterface.postRequest(apiName,requestBody), callback),context);
            case PUT:
                requestManager.request( new TapRequest(apiRequestInterface.putRequest(apiName), callback),context);
                break;
            case DELETE:
                requestManager.request( new TapRequest(apiRequestInterface.delete(apiName), callback),context);
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


