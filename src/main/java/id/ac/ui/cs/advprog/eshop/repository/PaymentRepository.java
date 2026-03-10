package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {
    private final List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        int existingIndex = findPaymentIndexById(payment.getId());
        if (existingIndex >= 0) {
            paymentData.set(existingIndex, payment);
            return payment;
        }

        paymentData.add(payment);
        return payment;
    }

    public Payment findById(String paymentId) {
        int existingIndex = findPaymentIndexById(paymentId);
        if (existingIndex >= 0) {
            return paymentData.get(existingIndex);
        }
        return null;
    }

    public List<Payment> findAll() {
        return new ArrayList<>(paymentData);
    }

    private int findPaymentIndexById(String paymentId) {
        for (int i = 0; i < paymentData.size(); i++) {
            if (paymentData.get(i).getId().equals(paymentId)) {
                return i;
            }
        }
        return -1;
    }
}
