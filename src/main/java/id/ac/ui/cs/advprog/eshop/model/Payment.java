package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Payment {
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
        if ("VOUCHER_CODE".equals(method)) {
            String voucherCode = paymentData.get("voucherCode");
            return isVoucherCodeValid(voucherCode) ? "SUCCESS" : "REJECTED";
        }

        if ("BANK_TRANSFER".equals(method)) {
            String bankName = paymentData.get("bankName");
            String referenceCode = paymentData.get("referenceCode");
            if (isNullOrEmpty(bankName) || isNullOrEmpty(referenceCode)) {
                return "REJECTED";
            }
            return "SUCCESS";
        }

        return "REJECTED";
    }

    private boolean isVoucherCodeValid(String voucherCode) {
        if (voucherCode == null || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            return false;
        }

        int digitCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }
        return digitCount == 8;
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }
}
