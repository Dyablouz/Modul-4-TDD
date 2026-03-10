package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Payment {
    private static final String METHOD_VOUCHER_CODE = "VOUCHER_CODE";
    private static final String METHOD_BANK_TRANSFER = "BANK_TRANSFER";

    private static final String STATUS_SUCCESS = "SUCCESS";
    private static final String STATUS_REJECTED = "REJECTED";

    private static final String KEY_VOUCHER_CODE = "voucherCode";
    private static final String KEY_BANK_NAME = "bankName";
    private static final String KEY_REFERENCE_CODE = "referenceCode";

    private static final int VOUCHER_LENGTH = 16;
    private static final int VOUCHER_REQUIRED_DIGITS = 8;
    private static final String VOUCHER_PREFIX = "ESHOP";

    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = new HashMap<>(paymentData);
        this.status = evaluateInitialStatus(method, paymentData);
    }

    private String evaluateInitialStatus(String method, Map<String, String> paymentData) {
        if (METHOD_VOUCHER_CODE.equals(method)) {
            String voucherCode = paymentData.get(KEY_VOUCHER_CODE);
            return isVoucherCodeValid(voucherCode) ? STATUS_SUCCESS : STATUS_REJECTED;
        }

        if (METHOD_BANK_TRANSFER.equals(method)) {
            String bankName = paymentData.get(KEY_BANK_NAME);
            String referenceCode = paymentData.get(KEY_REFERENCE_CODE);
            if (isNullOrEmpty(bankName) || isNullOrEmpty(referenceCode)) {
                return STATUS_REJECTED;
            }
            return STATUS_SUCCESS;
        }

        return STATUS_REJECTED;
    }

    private boolean isVoucherCodeValid(String voucherCode) {
        if (voucherCode == null || voucherCode.length() != VOUCHER_LENGTH
                || !voucherCode.startsWith(VOUCHER_PREFIX)) {
            return false;
        }

        int digitCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }
        return digitCount == VOUCHER_REQUIRED_DIGITS;
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }
}
