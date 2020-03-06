package company.tap.nativenetworkkit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

import company.tap.tapnetworkkit.interfaces.TapRequestBodyBase;

class PaymentOptionsRequest implements TapRequestBodyBase {

    @SerializedName("transaction_mode")
    @Expose
    @NonNull
    private TransactionMode transactionMode;

    @SerializedName("currency")
    @Expose
    @NonNull private String currency;

    @SerializedName("total_amount")
    @Expose
    @NonNull private Double totalAmount;

    @SerializedName("payment_type")
    @Expose
    @NonNull private String payment_type;

    PaymentOptionsRequest(@NonNull TransactionMode transactionMode, @NonNull String currency, @NonNull Double totalAmount, @NonNull String payment_type) {
        this.transactionMode = transactionMode;
        this.currency = currency;
        this.totalAmount = totalAmount;
        this.payment_type = payment_type;
    }
}
