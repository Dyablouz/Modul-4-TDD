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
class OrderFunctionalTest {

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
    void createOrderPage_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/order/create");

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Create New Order"));
        assertTrue(driver.findElement(By.id("authorInput")).isDisplayed());
        assertTrue(driver.findElement(By.id("orderTimeInput")).isDisplayed());
    }

    @Test
    void historyOrderPage_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/order/history");

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Find Order History"));
        assertTrue(driver.findElement(By.id("authorInput")).isDisplayed());
    }

    @Test
    void postHistoryOrderPage_showsOrderList(ChromeDriver driver) {
        driver.get(baseUrl + "/order/history");

        driver.findElement(By.id("authorInput")).sendKeys("Safira Sudrajat");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Order List"));
    }
}
