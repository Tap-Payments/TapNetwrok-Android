package company.tap.tapnetworkkit_android.connection;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import company.tap.tapnetworkkit_android.BuildConfig;
import company.tap.tapnetworkkit_android.exception_handling.NoAuthTokenProvidedException;
import company.tap.tapnetworkkit_android.interfaces.APIRequestInterface;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The type Retrofit helper.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class RetrofitHelper {
    private static Retrofit retrofit;
    private static APIRequestInterface helper;

    /**
     * Gets api helper.
     *
     * @return the api helper
     */
    @Nullable
    public static APIRequestInterface getApiHelper(String baseUrl) {
        /**
         * Lazy loading
         */
        if (retrofit == null) {
            if (AppInfo.getAuthToken() == null) {
                throw new NoAuthTokenProvidedException();
            }

            OkHttpClient okHttpClient = getOkHttpClient();
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

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        // add application interceptor to httpClientBuilder
        httpClientBuilder.addInterceptor(chain -> {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader(API_Constants.AUTH_TOKEN_KEY, API_Constants.AUTH_TOKEN_PREFIX + AppInfo.getAuthToken())
                    .addHeader(API_Constants.APPLICATION, AppInfo.getApplicationInfo())
                    .addHeader(API_Constants.ACCEPT_KEY,API_Constants.ACCEPT_VALUE)
                    .addHeader(API_Constants.CONTENT_TYPE_KEY, API_Constants.CONTENT_TYPE_VALUE).build();
            return chain.proceed(request);
        });
        httpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(!BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BODY));

        return httpClientBuilder.build();
    }
}