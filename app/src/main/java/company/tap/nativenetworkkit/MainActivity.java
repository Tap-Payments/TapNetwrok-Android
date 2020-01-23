package company.tap.nativenetworkkit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import company.tap.tapnetworkkit_android.connection.AppInfo;
import company.tap.tapnetworkkit_android.callbacks.APIRequestCallback;
import company.tap.tapnetworkkit_android.exceptionEngine.GoSellError;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements APIRequestCallback, View.OnClickListener {
    Button buttonPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonPay = findViewById(R.id.button_Pay);

        /**
         * Setting token to API Info
         **/
        AppInfo.setAuthToken(this,"sk_test_kovrMB0mupFJXfNZWx6Etg5y","company.tap.goSellSDKExample");

        /**
         * Calling init API
         **/
        GoSellAPInterface.getInstance().init(this);
        buttonPay.setOnClickListener(this);


    }


    @Override
    public void onSuccess(int responseCode, Response response) {
        System.out.println("responseCode = [" + responseCode + "], response = [" + response.body()+ "]");

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
            GoSellAPInterface.getInstance().getPaymentOptions(jsonObject, this);
        }
    }
}
