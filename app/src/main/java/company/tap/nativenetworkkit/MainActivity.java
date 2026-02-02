package company.tap.nativenetworkkit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.JsonObject;

import company.tap.tapnetworkkit.connection.AppInfo;
import company.tap.tapnetworkkit.interfaces.APIRequestCallback;
import company.tap.tapnetworkkit.exception_handling.GoSellError;
import company.tap.tapnetworkkit.logger.lo;
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
         * Store authToken and bundle ID.
         **/
        AppInfo.setAuthToken(this,"XXXXXXXX","company.tap.goSellSDKExample");

        /**
         * Calling init API
         **/
        TapRepository.getInstance().init(this,getBaseContext());

        buttonPay.setOnClickListener(this);
        buttonDel.setOnClickListener(this);


    }


    @Override
    public void onSuccess(int responseCode, Response response) {
        lo.g(response.body().toString());

    }

    @Override
    public void onFailure(GoSellError errorDetails) {
        lo.g(errorDetails.getErrorBody());
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button_Pay) {
            /*
             Passing post request body to obtain
             response for Payment options
             */
            PaymentOptionsRequest requestBody = new PaymentOptionsRequest(TransactionMode.PURCHASE, "kwd", 1.0, "KNET");
            TapRepository.getInstance().getPaymentOptions(requestBody, this,getBaseContext());
        }
        if(v.getId()==R.id.button_Delete){
            //Sending dummy values to check delete request
            TapRepository.getInstance().deleteCard("cus_10000","83921741382", this,getBaseContext());

        }
    }
}
