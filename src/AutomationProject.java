import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import java.io.IOException;
import java.time.Duration;
import java.util.List;


public class AutomationProject {


    public static void main(String[] args) throws InterruptedException, IOException {

        System.setProperty("webdriver.chrome.driver", "/Users/mervegurleyen/Desktop/BrowserDrivers/chromedriver-3");
        WebDriver driver = new ChromeDriver();
        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4000));


        driver.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester");
        driver.findElement(By.id("ctl00_MainContent_password")).sendKeys("test");

        driver.findElement(By.id("ctl00_MainContent_login_button")).click();

         driver.findElement(By.linkText("Order")).click();

        int quantity = (int)(Math.random() * (100 - 1)) + 1;
//
//        String strQuantity = String.valueOf(quantity);
//   driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity")).sendKeys(quantity+"");
//        driver.findElement(By.className("btn_dark")).click();

        driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).sendKeys(Keys.BACK_SPACE);

        driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).sendKeys(quantity+"");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@value='Calculate']")).click();

        double total = Double.parseDouble(driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtTotal")).getAttribute("value"));

        if (quantity >= 10) {
            Assert.assertTrue(total == (quantity * 100 * 0.92));
        } else {
            Assert.assertTrue(total == (quantity * 100));
        }

        List<String[]> fakeUsers = Utility.read("MOCK_DATA.csv");
        String[] fakeUser = fakeUsers.get(randNumber(0, fakeUsers.size()));

        driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtName")).sendKeys(fakeUser[0]);
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox2")).sendKeys(fakeUser[1]);
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox3")).sendKeys(fakeUser[2]);
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox4")).sendKeys(fakeUser[3]);
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox5")).sendKeys(fakeUser[4]);


        long AmExcard = (long) (300000000000000l + (Math.random()*99999999999999l));
        long visacard = (long) (4000000000000000l + (Math.random()*999999999999999l));
        long mastcard = (long) (5000000000000000l + (Math.random()*999999999999999l));

        String strAmExcard = String.valueOf(AmExcard);
        String strvisacard = String.valueOf(visacard);
        String strmastercard = String.valueOf(mastcard);

        int cardType = randNumber(0,3);

        if (cardType == 0){
            Thread.sleep(2000);
            driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_0")).click();
            driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6")).sendKeys( strvisacard + Keys.ENTER);
        }else if(cardType == 1){
            Thread.sleep(2000);
            driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_1")).click();
            driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6")).sendKeys( strmastercard + Keys.ENTER);
        }else if (cardType == 2){
            Thread.sleep(2000);
            driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_2")).click();
            driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6")).sendKeys( strAmExcard + Keys.ENTER);
        }
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox1")).sendKeys( "12/24" + Keys.ENTER);
        Thread.sleep(2000);
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();

        String actual = driver.findElement(By.tagName("strong")).getText();
        String expected = "New order has been successfully added.";
        Assert.assertEquals(actual, expected);

        driver.findElement(By.id("ctl00_logout")).click();


    }

    public static int randNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
