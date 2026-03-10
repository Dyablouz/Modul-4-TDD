package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private List<Payment> payments;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        Map<String, String> voucherPaymentData = new HashMap<>();
        voucherPaymentData.put("voucherCode", "ESHOP1234ABC5678");

        Map<String, String> bankTransferPaymentData = new HashMap<>();
        bankTransferPaymentData.put("bankName", "BCA");
        bankTransferPaymentData.put("referenceCode", "REF123456789");

        payments = List.of(
                new Payment("payment-1", "VOUCHER_CODE", voucherPaymentData),
                new Payment("payment-2", "BANK_TRANSFER", bankTransferPaymentData)
        );
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(0);

        Payment result = paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById(payment.getId());

        assertNotNull(result);
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getStatus(), findResult.getStatus());
    }

    @Test
    void testFindByIdIfIdFound() {
        paymentRepository.save(payments.get(0));
        paymentRepository.save(payments.get(1));

        Payment findResult = paymentRepository.findById("payment-2");

        assertNotNull(findResult);
        assertEquals("payment-2", findResult.getId());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        paymentRepository.save(payments.get(0));

        Payment findResult = paymentRepository.findById("non-existent");

        assertNull(findResult);
    }

    @Test
    void testFindAll() {
        paymentRepository.save(payments.get(0));
        paymentRepository.save(payments.get(1));

        List<Payment> results = paymentRepository.findAll();

        assertEquals(2, results.size());
        assertEquals("payment-1", results.get(0).getId());
        assertEquals("payment-2", results.get(1).getId());
    }
}
