package company.tap.tapnetworkkit.connection;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import company.tap.tapnetworkkit.exception.NoAuthTokenProvidedException;
import company.tap.tapnetworkkit.interfaces.APIRequestInterface;
import company.tap.tapnetworkkit_android.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The type Retrofit helper.
 */
public final class RetrofitHelper {
    private static Retrofit retrofit;
    private static APIRequestInterface helper;

    /**
     * Gets api helper.
     *
     * @return the api helper
     */
    public static APIRequestInterface getApiHelper(String baseUrl, Context context) {
        if (retrofit == null) {
            if (NetworkApp.getAuthToken() == null) {
                throw new NoAuthTokenProvidedException();
            }

            OkHttpClient okHttpClient = getOkHttpClient(context);
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(buildGsonConverter())
                    .client(okHttpClient)
                    .build();
        }

        if (helper == null) {
            helper = retrofit.create(APIRequestInterface.class);
        }

        return helper;
    }

    private static GsonConverterFactory buildGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson myGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
        // custom deserializers not added
        return GsonConverterFactory.create(myGson);
    }

    private static OkHttpClient getOkHttpClient(Context context) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new NetworkConnectionInterceptor(context));
        httpClientBuilder.addInterceptor(chain -> {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader(APIConstants.AUTH_TOKEN_KEY, APIConstants.AUTH_TOKEN_PREFIX + NetworkApp.getAuthToken())
                    .addHeader(APIConstants.APPLICATION, NetworkApp.getApplicationInfo())
                    .addHeader(APIConstants.ACCEPT_KEY, APIConstants.ACCEPT_VALUE)
                    .addHeader(APIConstants.CONTENT_TYPE_KEY, APIConstants.CONTENT_TYPE_VALUE).build();
            return chain.proceed(request);
        });
        httpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(!BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BODY));

        return httpClientBuilder.build();
    }
}