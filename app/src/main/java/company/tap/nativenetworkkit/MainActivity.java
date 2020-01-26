package company.tap.nativenetworkkit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.JsonObject;

import company.tap.tapnetworkkit_android.connection.AppInfo;
import company.tap.tapnetworkkit_android.interfaces.APIRequestCallback;
import company.tap.tapnetworkkit_android.exception_handling.GoSellError;
import company.tap.tapnetworkkit_android.logger.lo;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements APIRequestCallback, View.OnClickListener {
    Button buttonPay;
    Button buttonDel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonPay = findViewById(R.id.button_Pay);
        buttonDel = findViewById(R.id.button_Delete);

        /**
         * Setting token to API Info
         **/
        AppInfo.setAuthToken(this,"sk_test_kovrMB0mupFJXfNZWx6Etg5y","company.tap.goSellSDKExample");

        /**
         * Calling init API
         **/
        NetworkClient.getInstance().init(this,getBaseContext());
        buttonPay.setOnClickListener(this);
        buttonDel.setOnClickListener(this);
        /**
         * Dummy values sent to check PUT request
         **/
        NetworkClient.getInstance().updateCharge("test2w123",this,getBaseContext());


    }


    @Override
    public void onSuccess(int responseCode, Response response) {
        lo.g(response.body().toString());

    }

    @Override
    public void onFailure(GoSellError errorDetails) {

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button_Pay) {
            /**
             * Passing post request body as JsonObject to obtain
             * response for Payment options
             **/
            JsonObject jsonObject = null;
            jsonObject = new JsonObject();
            jsonObject.addProperty("currency", "kwd");
            jsonObject.addProperty("customer", "");

            jsonObject.addProperty("payment_type", "KNET");

            jsonObject.addProperty("shipping", "");
            jsonObject.addProperty("taxes", "");
            jsonObject.addProperty("total_amount", "1");
            jsonObject.addProperty("transaction_mode", "PURCHASE");
            NetworkClient.getInstance().getPaymentOptions(jsonObject, this,getBaseContext());
        }
        if(v.getId()==R.id.button_Delete){
            /**
             * Sending dummy values to check delete request*/
            NetworkClient.getInstance().deleteCard("cus_10000","83921741382", this,getBaseContext());

        }
    }
}
