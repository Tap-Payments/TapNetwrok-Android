package company.tap.nativenetworkkit;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Transaction mode.
 */
public enum TransactionMode {

    /**
     * Purchase transaction mode.
     */
    @SerializedName("PURCHASE")             PURCHASE,
    /**
     * Authorize capture transaction mode.
     */
    @SerializedName("AUTHORIZE_CAPTURE")    AUTHORIZE_CAPTURE
}
