package company.tap.nativenetworkkit;


import android.content.Context;

import company.tap.tapnetworkkit.controller.NetworkController;
import company.tap.tapnetworkkit.enums.TapMethodType;
import company.tap.tapnetworkkit.interfaces.APIRequestCallback;

class TapRepository {


    void init(APIRequestCallback callback, Context context) {
        NetworkController.getInstance().processRequest(TapMethodType.GET, APIMethods.INIT, null, callback, 1);

    }

    void getPaymentOptions(String jsonString, APIRequestCallback callback) {
        NetworkController.getInstance().processRequest(TapMethodType.POST, APIMethods.PAYMENT_TYPES, jsonString, callback, 2);
    }


    void deleteCard(String customerId, String cardId, APIRequestCallback callback) {
        NetworkController.getInstance().processRequest(TapMethodType.DELETE, APIMethods.DELETE_CARD+"/"+customerId+"/"+cardId, null, callback, 3);

    }


    private static class SingletonCreationAdmin {
        private static final TapRepository INSTANCE = new TapRepository();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    static TapRepository getInstance() {
        return TapRepository.SingletonCreationAdmin.INSTANCE;
    }
}

