package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class PaymentFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void paymentDetailPage_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/detail");

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Payment Detail"));
    }

    @Test
    void paymentDetailByIdPage_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/detail/payment-1");

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Payment ID"));
    }

    @Test
    void paymentAdminListPage_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/admin/list");

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Payment Admin List"));
    }

    @Test
    void paymentAdminDetailPage_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/admin/detail/payment-1");

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Payment Admin Detail"));
    }

    @Test
    void paymentAdminSetStatusPost_updatesStatus(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/admin/detail/payment-1");

        driver.findElement(By.id("statusInput")).sendKeys("SUCCESS");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("SUCCESS"));
    }
}
