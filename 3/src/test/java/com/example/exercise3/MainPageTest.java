package com.example.exercise3;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.Assert.*;

public class MainPageTest {

    private WebDriver driver;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Users\\badys\\Downloads\\STUDIA\\7 semestr\\Testowanie Automatyczne (TAU)\\Chrome exe for ex no. 3\\chromedriver.exe"
                );
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

//    @Test
//    public void testPepperSearch() throws Exception {
//        driver.get("https://www.pepper.pl/");
//        driver.findElement(By.name("q")).click();
//        driver.findElement(By.name("q")).clear();
//        driver.findElement(By.name("q")).sendKeys("buty nike");
//        driver.findElement(By.cssSelector(".search")).submit();
//        driver.findElement(By.xpath("//main[@id='main']/div/section/div/h1")).click();
//        try {
//            assertEquals("Szukaj \"buty nike\"", driver.findElement(By.xpath("//main[@id='main']/div/section/div/h1")).getText());
//        } catch (Error e) {
//            verificationErrors.append(e.toString());
//        }
//    }

    @Test
    public void testingWicikPageForTextVerificationOnOtherPages() throws Exception {
        driver.get("https://www.wicik24.pl/");
        try {
            assertEquals("PANEL HURTOWY", driver.findElement(By.xpath("//a[@id='headlink5']/span")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.xpath("//a[@id='headlink5']/span")).click();
        try {
            assertEquals("CBD", driver.findElement(By.xpath("//a[contains(text(),'CBD')]")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.xpath("//a[contains(text(),'CBD')]")).click();
        try {
            assertEquals("CBD", driver.findElement(By.xpath("//div[@id='box_mainproducts']/div/h1")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

//    @Test
//    public void NSFW() throws Exception {
//        driver.get("https://9gag.com/trending");
//        driver.findElement(By.linkText("Fresh")).click();
//        driver.get("https://9gag.com/fresh");
//        try {
//            assertEquals("Fresh", driver.findElement(By.linkText("Fresh")).getText());
//        } catch (Error e) {
//            verificationErrors.append(e.toString());
//        }
//        driver.findElement(By.linkText("Top")).click();
//        driver.get("https://9gag.com/top");
//        try {
//            assertEquals("📰News", driver.findElement(By.linkText("📰News")).getText());
//        } catch (Error e) {
//            verificationErrors.append(e.toString());
//        }
//    }

    @Test
    public void testingBowlingSiteForPricesOnSaturday() throws Exception {
        driver.get("http://chilloutbowling.pl/");
        driver.findElement(By.xpath("//a[@id='nav-toggle']/span")).click();
        driver.findElement(By.linkText("Cennik")).click();
        try {
            assertEquals("Sobota", driver.findElement(By.xpath("//article[@id='post-76']/div/div/table/tbody/tr[4]/td/strong")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        try {
            assertEquals("64zł", driver.findElement(By.xpath("//article[@id='post-76']/div/div/table/tbody/tr[4]/td[3]")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}
