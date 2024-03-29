package company.tap.nativenetworkkit;


import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import company.tap.tapnetworkkit.controller.NetworkController;
import company.tap.tapnetworkkit.enums.TapMethodType;
import company.tap.tapnetworkkit.interfaces.APIRequestCallback;

class TapRepository {

    @RequiresApi(api = Build.VERSION_CODES.N)
    void init(APIRequestCallback callback, Context context,String jsonString) {
        NetworkController.getInstance().processRequest(TapMethodType.POST, APIMethods.INIT, jsonString, callback, 1);


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
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

