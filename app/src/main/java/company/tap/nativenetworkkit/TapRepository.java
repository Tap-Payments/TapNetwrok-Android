package company.tap.nativenetworkkit;


import android.content.Context;

import com.google.gson.JsonObject;

import company.tap.tapnetworkkit.controller.NetworkController;
import company.tap.tapnetworkkit.enums.TapMethodType;
import company.tap.tapnetworkkit.interfaces.APIRequestCallback;

public class TapRepository {
   private NetworkController networkController;

    private TapRepository() {
        initializeNetworkKit();
    }

    public void init(APIRequestCallback callback, Context context) {
        networkController.processRequest(TapMethodType.GET, API_Methods.INIT, null, callback, context);

    }

    public void getPaymentOptions(JsonObject jsonObject, APIRequestCallback callback, Context context) {
        networkController.processRequest(TapMethodType.POST, API_Methods.PAYMENT_TYPES, jsonObject, callback, context);
    }



    private void initializeNetworkKit() {
        networkController = NetworkController.getInstance();
        networkController.setBaseUrl(API_Methods.BASE_URL);
    }

    public void deleteCard(String customerId, String cardId, APIRequestCallback callback, Context context) {
        networkController.processRequest(TapMethodType.DELETE, API_Methods.DELETE_CARD+"/"+customerId+"/"+cardId, null, callback, context);

    }


    private static class SingletonCreationAdmin {
        private static final TapRepository INSTANCE = new TapRepository();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static TapRepository getInstance() {
        return TapRepository.SingletonCreationAdmin.INSTANCE;
    }
}

