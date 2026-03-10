package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PaymentTest {

    @Test
    void testCreateVoucherPaymentValidCodeSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("payment-1", "VOUCHER_CODE", paymentData);

        assertEquals("payment-1", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testCreateVoucherPaymentInvalidCodeRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALID");

        Payment payment = new Payment("payment-2", "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateBankTransferPaymentValidDataNotRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456789");

        Payment payment = new Payment("payment-3", "BANK_TRANSFER", paymentData);

        assertEquals("payment-3", payment.getId());
        assertEquals("BANK_TRANSFER", payment.getMethod());
        assertNotEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateBankTransferPaymentEmptyBankNameRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "REF123456789");

        Payment payment = new Payment("payment-4", "BANK_TRANSFER", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateBankTransferPaymentEmptyReferenceCodeRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "");

        Payment payment = new Payment("payment-5", "BANK_TRANSFER", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }
}
