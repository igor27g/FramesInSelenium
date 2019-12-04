import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Frames {

    WebDriver driver;
    WebDriverWait wait;
    By demoStoreBar = By.cssSelector("a[class*='dismiss-link']");
    
    @BeforeEach
    public void driverSetup()
    {
        String fakeStore = "https://fakestore.testelka.pl/";
        String fakeStoreRamki = "https://fakestore.testelka.pl/cwiczenia-z-ramek/";

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.navigate().to(fakeStoreRamki);
        driver.findElement(demoStoreBar).click();

        wait = new WebDriverWait(driver,10);
    }

    @AfterEach
    public void driverClose()
    {
        driver.close();
        driver.quit();
    }


   @Test
    public void frameExamples() {
       WebElement frame = driver.findElement(By.cssSelector("iframe#twitter-widget-0"));
       driver.switchTo().frame(frame);
       //driver.switchTo().frame("twitter-widget-0");
       driver.switchTo().frame(0);
       driver.findElement(By.cssSelector("a[data-scribe*='twitter_url']")).click();
       driver.switchTo().defaultContent();
       // driver.switchTo().parentFrame();
       driver.findElement(By.cssSelector("div.navbar-header>a.logo"));
   }


   @Test
    public void frameMyExamples() {
        WebElement frameId = driver.findElement(By.cssSelector("#iPerceptionsFrame"));
        driver.switchTo().frame(frameId);
        //nasa website
   }

   By mainButton = By.cssSelector("input[name='main-page']");


    // 1. Potwierdź, że pierwszy przycisk „Strona główna” jest nieaktywny.

   @Test
    public void mainPageButtonIsNotActive() {
       driver.switchTo().frame("main-frame");
       WebElement mainButtonStatus = driver.findElement(mainButton);
       Assertions.assertTrue(!mainButtonStatus.isEnabled());
       System.out.println(driver.findElement(mainButton).isEnabled());
    }


    // 2. Potwierdź, że obrazek kieruje do strony głównej (sprawdź bez klikania w element).

    By aLinkToMainPage = By.cssSelector("article[id='post-292']>div>p>a");

    @Test
    public void imgGoToMainPage() {
        driver.switchTo().frame("main-frame");
        WebElement nameFrame = driver.findElement(By.cssSelector("iframe[name='image']"));
        driver.switchTo().frame(nameFrame);
        WebElement hrefLink = driver.findElement(aLinkToMainPage);
        Assertions.assertEquals("https://fakestore.testelka.pl/",hrefLink.getAttribute("href"), "Wrong href");
    }

    // 3. Potwierdź, że ostatni przycisk „>>Strona główna” jest aktywny.

    @Test
    public void mainPageButtonEnabledTest() {
        driver.switchTo().frame("main-frame")
                .switchTo().frame("image")
                .switchTo().frame(0);
        WebElement mainPageButton = driver.findElement(By.cssSelector("a.button"));
        Assertions.assertTrue(mainPageButton.isEnabled(), "Main Page button is not enabled");
    }

    // 4. Kliknij w przycisk „Wspinaczka” i potwierdź, że po przejściu na stronę widoczne jest logo (w tej ramce).


    @Test
    public void mainPageIsActive() {
        driver.switchTo().frame("main-frame")
                .switchTo().frame("image")
                .switchTo().frame(0);
        WebElement mainPageButton = driver.findElement(By.cssSelector("a.button"));
        mainPageButton.click();
        driver.switchTo().parentFrame()
                .switchTo().parentFrame();
        WebElement climbingButton = driver.findElement(By.cssSelector("a[name='climbing']"));
        climbingButton.click();
        WebElement logo = driver.findElement(By.cssSelector("img.custom-logo"));
        Assertions.assertTrue(logo.isDisplayed(), "Logo is not displayed");
    }
}
