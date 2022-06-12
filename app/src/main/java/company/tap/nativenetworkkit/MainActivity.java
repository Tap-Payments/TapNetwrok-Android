package company.tap.nativenetworkkit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import company.tap.tapnetworkkit.connection.NetworkApp;
import company.tap.tapnetworkkit.exception.GoSellError;
import company.tap.tapnetworkkit.interfaces.APIRequestCallback;
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

        NetworkApp.initNetwork(this, "sk_test_kovrMB0mupFJXfNZWx6Etg5y", "company.tap.goSellSDKExample", APIMethods.BASE_URL,"Native");

        TapRepository.getInstance().init(this, getBaseContext());

        buttonPay.setOnClickListener(this);
        buttonDel.setOnClickListener(this);
    }

    @Override
    public void onSuccess(int responseCode, int requestCode, Response<JsonElement> response) {
        switch (requestCode) {
            case 1: // INIT
                break;
            case 2: // Payment Option
                break;
            case 3: // Delete Card
                break;
        }
    }

    @Override
    public void onFailure(int requestCode, GoSellError errorDetails) {
        lo.g(errorDetails.getErrorBody());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_Pay) {
            /*
             Passing post request body to obtain
             response for Payment options
             */
            PaymentOptionsRequest requestBody = new PaymentOptionsRequest(TransactionMode.PURCHASE, "kwd", 1.0, "KNET");
            String jsonString = new Gson().toJson(requestBody);
            System.out.println("jsonString<<<>>>>"+jsonString);
            TapRepository.getInstance().getPaymentOptions(jsonString, this);
        }
        if (v.getId() == R.id.button_Delete) {
            //Sending dummy values to check delete request
            TapRepository.getInstance().deleteCard("cus_10000", "83921741382", this);

        }
    }
}
