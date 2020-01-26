package company.tap.tapnetworkkit_android.request_manager;

import android.content.Context;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;

import company.tap.tapnetworkkit_android.R;
import company.tap.tapnetworkkit_android.interfaces.APIRequestCallback;
import company.tap.tapnetworkkit_android.callbacks.BaseCallback;
import company.tap.tapnetworkkit_android.interfaces.APIRequestInterface;
import company.tap.tapnetworkkit_android.interfaces.BaseResponse;
import company.tap.tapnetworkkit_android.utils.NetworkUtils;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;

/**
 * The type Request manager.
 */

public class RequestManager{
    private APIRequestInterface apiRequestInterface;

    private ArrayList<TapRequest> tapRequests;

    /**
     * Instantiates a new Request manager.
     *
     * @param apiRequestInterface the api helper
     */
    public RequestManager(APIRequestInterface apiRequestInterface) {
        this.apiRequestInterface = apiRequestInterface;
        tapRequests = new ArrayList<>();


    }

    /**
     * Request.
     *
     * @param tapRequest the delayed request
     */
    public void request(TapRequest tapRequest,Context context) {
        tapRequests.add(tapRequest);

        if(NetworkUtils.isNetworkConnected(context)){
            tapRequest.run();
        }else{

            Toast.makeText(context, context.getResources().getString(R.string.internet_connectivity_message), Toast.LENGTH_LONG).show();

        }


    }

    /**
     * The type Delayed request.
     *
     * @param <T> the type parameter
     */
    public static class TapRequest<T extends BaseResponse> {
        private Call<T> request;
        private APIRequestCallback<T> requestCallback;



        /**
         * Instantiates a new Delayed request.
         *
         * @param request         the request
         * @param requestCallback the request callback
         */
       public   TapRequest(Call<T> request, APIRequestCallback<T> requestCallback) {
            this.request = request;
            this.requestCallback = requestCallback;

        }

        private String bodyToString(final RequestBody request){
            try {
                final RequestBody copy = request;
                final Buffer buffer = new Buffer();
                if(copy != null)
                    copy.writeTo(buffer);
                else
                    return "body null";
                return buffer.readUtf8();
            }
            catch (final IOException e) {
                return "did not work";
            }

        }


        /**
         * Run.
         */
        void run() {
            request.enqueue(new BaseCallback<>(requestCallback));
        }
        
    }


}
