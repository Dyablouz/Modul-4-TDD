package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/detail")
    @ResponseBody
    public String paymentDetailPage() {
        return "<h3>Payment Detail</h3>";
    }

    @GetMapping("/detail/{paymentId}")
    @ResponseBody
    public String paymentDetailByIdPage(@PathVariable String paymentId) {
        Payment payment = getOrCreatePayment(paymentId);
        return "<h3>Payment ID: " + payment.getId() + "</h3><p>Status: " + payment.getStatus() + "</p>";
    }

    @GetMapping("/admin/list")
    @ResponseBody
    public String paymentAdminListPage() {
        List<Payment> payments = paymentService.getAllPayments();
        return "<h3>Payment Admin List</h3><p>Total Payments: " + payments.size() + "</p>";
    }

    @GetMapping("/admin/detail/{paymentId}")
    @ResponseBody
    public String paymentAdminDetailPage(@PathVariable String paymentId) {
        Payment payment = getOrCreatePayment(paymentId);
        return "<h3>Payment Admin Detail</h3>"
                + "<p>Payment ID: " + payment.getId() + "</p>"
                + "<p>Status: " + payment.getStatus() + "</p>"
                + "<form method='post' action='/payment/admin/set-status/" + payment.getId() + "'>"
                + "<input id='statusInput' name='status' type='text' />"
                + "<button type='submit'>Submit</button>"
                + "</form>";
    }

    @PostMapping("/admin/set-status/{paymentId}")
    @ResponseBody
    public String setPaymentStatus(@PathVariable String paymentId, @RequestParam("status") String status) {
        Payment payment = getOrCreatePayment(paymentId);
        Payment updatedPayment = paymentService.setStatus(payment, status);
        return "<h3>Payment Updated</h3><p>Status: " + updatedPayment.getStatus() + "</p>";
    }

    private Payment getOrCreatePayment(String paymentId) {
        Payment payment = paymentService.getPayment(paymentId);
        if (payment != null) {
            return payment;
        }

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        return new Payment(paymentId, "VOUCHER_CODE", paymentData);
    }
}
