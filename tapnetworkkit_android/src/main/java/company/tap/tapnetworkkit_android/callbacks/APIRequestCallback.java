package company.tap.tapnetworkkit_android.callbacks;

import androidx.annotation.RestrictTo;

import com.google.gson.JsonObject;

import company.tap.tapnetworkkit_android.exceptionEngine.GoSellError;
import company.tap.tapnetworkkit_android.response.BaseResponse;
import okhttp3.ResponseBody;
import retrofit2.Response;


/**
 * Base callback to process API responses
 *
 * @param <T> {@link Class} implementing {@link BaseResponse} interface
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface APIRequestCallback<T extends BaseResponse> {
    /**
     * Success callback. Request is considered as successful when response code is between 200 and 299
     *  @param responseCode       Response code, from 200 to 299
     * @param serializedResponse Serialized response of {@link T} type or null in case when response could not be serialized into {@link T} type
     */
    void onSuccess(int responseCode, Response<T> serializedResponse);

    /**
     * General failure callback
     *
     * @param errorDetails {@link GoSellError} representing a failure reason
     */
    void onFailure(GoSellError errorDetails);
}