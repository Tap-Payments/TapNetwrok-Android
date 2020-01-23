package company.tap.tapnetworkkit_android.request_manager;

import android.util.Log;

import androidx.annotation.RestrictTo;

import java.io.IOException;
import java.util.ArrayList;

import company.tap.tapnetworkkit_android.callbacks.APIRequestCallback;
import company.tap.tapnetworkkit_android.callbacks.BaseCallback;
import company.tap.tapnetworkkit_android.exceptionEngine.GoSellError;
import company.tap.tapnetworkkit_android.interfaces.APIRequestInterface;
import company.tap.tapnetworkkit_android.response.BaseResponse;
import company.tap.tapnetworkkit_android.response.GenericResponse;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Response;

/**
 * The type Request manager.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class RequestManager {
    private APIRequestInterface apiRequestInterface;

    //all requests are wrapped in DelayedRequest, until init() would be finished
    private boolean initIsRunning;
    private ArrayList<DelayedRequest> delayedRequests;

    /**
     * Instantiates a new Request manager.
     *
     * @param apiRequestInterface the api helper
     */
    public RequestManager(APIRequestInterface apiRequestInterface) {
        this.apiRequestInterface = apiRequestInterface;
        delayedRequests = new ArrayList<>();
    }

    /**
     * Request.
     *
     * @param delayedRequest the delayed request
     */
    public void request(DelayedRequest delayedRequest, boolean checkInit) {
        delayedRequests.add(delayedRequest);
        //todo add checking for secret key added or not
        if (checkInit) {
            if (!initIsRunning) {
                //init();
            }
        } else {
            runDelayedRequests();
        }
    }

    /**
     * Retrieve SDKSettings from server.
     */
    private void init() {
        initIsRunning = true;


        apiRequestInterface.getRequest("init")
                .enqueue(new BaseCallback(new APIRequestCallback<GenericResponse>() {

                    @Override
                    public void onSuccess(int responseCode, Response serializedResponse) {
                        initIsRunning = false;

                        runDelayedRequests();
                    }

                    @Override
            public void onFailure(GoSellError errorDetails) {
                        initIsRunning = false;
                        failDelayedRequests(errorDetails);
            }
        }));

    }


    private void runDelayedRequests() {
        for (DelayedRequest delayedRequest : delayedRequests) {
//            Log.d("runDelayedRequests","delayedRequest.toString() : " + delayedRequest.getRequest().request());
            try {
                final Buffer buffer = new Buffer();
                if(delayedRequest.getRequest().request().body()!=null ) {
                    delayedRequest.getRequest().request().body().writeTo(buffer);
//                System.out.println("delayedRequest.toString() :" + buffer.readUtf8().toString());
                }
            }catch (IOException s){
                Log.d("runDelayedRequests","ex : " + s.getLocalizedMessage());
            }
            delayedRequest.run();
        }
        delayedRequests.clear();
    }


    private void failDelayedRequests(GoSellError errorDetails) {
        for (DelayedRequest delayedRequest : delayedRequests) {
            delayedRequest.fail(errorDetails);
        }
        delayedRequests.clear();
    }

    /**
     * The type Delayed request.
     *
     * @param <T> the type parameter
     */
    public static class DelayedRequest<T extends BaseResponse> {
        private Call<T> request;
        private APIRequestCallback<T> requestCallback;

        /**
         * Instantiates a new Delayed request.
         *
         * @param request         the request
         * @param requestCallback the request callback
         */
       public   DelayedRequest(Call<T> request, APIRequestCallback<T> requestCallback) {
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

        /**
         * Fail.
         *
         * @param errorDetails the error details
         */
        void fail(GoSellError errorDetails) {
            requestCallback.onFailure(errorDetails);
        }

        /**
         * Get request call.
         *
         * @return the call
         */
        Call getRequest(){
            return this.request;
        }
    }


}
