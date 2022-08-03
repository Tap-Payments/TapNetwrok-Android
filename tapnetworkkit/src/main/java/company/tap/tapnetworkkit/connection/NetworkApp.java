package company.tap.tapnetworkkit.connection;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.annotation.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

import company.tap.tapnetworkkit.controller.NetworkController;
import company.tap.tapnetworkkit.logger.lo;
//import company.tap.nativenetworkkit.BuildConfig;


/**
 * The type App info.
 */
public class NetworkApp {
    //auth information for headers
    private static String authToken;
    private static String headerToken;
    private static LinkedHashMap<Object, Object> applicationInfo;
    private static String localeString = "en";
    private static TelephonyManager manager;
    private static String deviceName;

    /**
     * Sets auth token.
     *
     * @param context   the context
     * @param authToken the auth token
     */
    public static void initNetwork(Context context, String authToken, String appId, String baseUrl , @Nullable String sdkIdentifier) {
        NetworkApp.authToken = authToken;
        initApplicationInfo(appId,sdkIdentifier);

        if (manager != null)
            manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        deviceName = Settings.Global.getString(context.getContentResolver(), Settings.Global.DEVICE_NAME);

        NetworkController.getInstance().setBaseUrl(baseUrl, context);
        lo.init(context);
    }
    /**
     * Sets header token.
     *
     * @param _headerToken   the headertoken
     * */

    public static void initNetworkToken(String _headerToken,Context context, String baseUrl) {
        NetworkApp.headerToken =_headerToken;
        RetrofitHelper.getApiHelper(baseUrl,context);
        lo.init(context);
    }

    /**
     * Gets header token.
     *
     * @return the _header token
     */
    static String getHeaderToken() {
        return headerToken;
    }


    /**
     * Gets auth token.
     *
     * @return the auth token
     */
    static String getAuthToken() {
        return authToken;
    }

    /**
     * Sets locale.
     *
     * @param locale the locale
     */
    public static void setLocale(String locale) {
        NetworkApp.localeString = locale.length() < 2 ? locale : locale.substring(0, 2);
        NetworkApp.applicationInfo.put("app_locale", SupportedLocales.findByString(localeString).language);
    }

    private static void initApplicationInfo(String applicationId , String sdkIdentifier) {
        applicationInfo = new LinkedHashMap<>();

        applicationInfo.put("app_id", applicationId);
        applicationInfo.put("requirer", sdkIdentifier);
        applicationInfo.put("requirer_version","1.0");
        applicationInfo.put("requirer_os", "Android");
        applicationInfo.put("requirer_os_version", Build.VERSION.RELEASE);
        applicationInfo.put("app_locale", SupportedLocales.findByString(localeString).language);
        applicationInfo.put("requirer_device_name", deviceName);
        applicationInfo.put("requirer_device_type", Build.BRAND);
        applicationInfo.put("requirer_device_model", Build.MODEL);
        applicationInfo.put("requirer_device_manufacturer", Build.MANUFACTURER);
        if (manager != null) {
            applicationInfo.put("requirer_sim_network_name", manager.getSimOperatorName());
            applicationInfo.put("requirer_sim_country_iso", manager.getSimCountryIso());
        }
    }

    /**
     * Gets locale string.
     *
     * @return the locale string
     */
    public static String getLocaleString() {
        return localeString;
    }

    /**
     * Gets application info.
     *
     * @return the application info
     */
    public static String getApplicationInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry entry : applicationInfo.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("|");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    private enum SupportedLocales {
        /**
         * En supported locales.
         */
        EN("en"), AR("ar");

        private String language;

        SupportedLocales(String language) {
            this.language = language;
        }

        /**
         * Find by string supported locales.
         *
         * @param localeString the locale string
         * @return the supported locales
         */
        static SupportedLocales findByString(String localeString) {
            for (SupportedLocales locale : values()) {
                if (locale.language.equalsIgnoreCase(localeString)) {
                    return locale;
                }
            }

            return EN;
        }

    }
}
 