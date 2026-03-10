package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/create")
    public String createOrderPage(Model model) {
        model.addAttribute("order", createDraftOrder());
        return "createOrder";
    }

    @PostMapping("/create")
    public String createOrderPost(@RequestParam("author") String author,
                                  @RequestParam("orderTime") Long orderTime) {
        Order order = new Order(
                UUID.randomUUID().toString(),
                createDraftProducts(),
                orderTime,
                author
        );
        orderService.createOrder(order);
        return "redirect:history";
    }

    @GetMapping("/history")
    public String orderHistoryPage() {
        return "orderHistory";
    }

    @PostMapping("/history")
    public String orderHistoryPost(@RequestParam("author") String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        return "orderList";
    }

    private Order createDraftOrder() {
        return new Order(
                UUID.randomUUID().toString(),
                createDraftProducts(),
                0L,
                ""
        );
    }

    private List<Product> createDraftProducts() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setProductName("Draft Product");
        product.setProductQuantity(1);
        products.add(product);
        return products;
    }
}
