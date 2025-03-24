package com.demo;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

@Disabled("Test UI désactivé en CI, exécuter uniquement en local")
public class SeleniumLoginTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
       
        ChromeOptions options = new ChromeOptions();

        // Mode headless si en CI
        if (System.getenv("CI") != null) {
            options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
        }

        driver = new ChromeDriver(options);
        driver.get("http://localhost:8080/login");
    }

    @Test
    void testLoginSuccess() {
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("pass");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Attendre que la page se charge
        try {
            Thread.sleep(2000); // Attendre 2 secondes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String bodyText = driver.getPageSource();
        System.out.println("Page content (succès login) :");
        System.out.println(bodyText);

        assertTrue(bodyText.contains("Connexion réussie !"));
    }

    @Test
    void testLoginFailure() {
        driver.findElement(By.name("username")).sendKeys("wrong");
        driver.findElement(By.name("password")).sendKeys("wrong");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Attendre que la page se charge
        try {
            Thread.sleep(2000); // Attendre 2 secondes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String bodyText = driver.getPageSource();
        System.out.println("Page content (échec login) :");
        System.out.println(bodyText);

        assertTrue(bodyText.contains("Identifiants invalides."));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
