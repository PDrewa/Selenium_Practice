import com.pdrewa.DriverManager;
import com.pdrewa.PropertyManager;
import com.pdrewa.listeners.TestReporter;
import com.pdrewa.listeners.TestResultsListener;
import com.pdrewa.pageobject.amazon.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

@Listeners({TestResultsListener.class, TestReporter.class})
public abstract class BaseTest {

    public static final Logger logger = LogManager.getLogger("Assert");
    public static WebDriver driver;
    protected DriverManager driverManager;
    protected PropertyManager propertyManager;
    protected ProductDetailsPage productDetailsPage;
    protected CartActionSummaryPage cartActionSummaryPage;
    protected CartPage cartPage;


    protected HomePage homePage;
    protected SearchResultsPage searchResultsPage;

    @Parameters({"testDataFileName"})
    @BeforeClass
    public void setup(@Optional("default.properties") String testDataFileName
            , ITestContext context) {
        driverManager = new DriverManager();
        driver = driverManager.getDriver();
        context.setAttribute("WebDriver", driver);
        propertyManager = new PropertyManager(testDataFileName);

        homePage = new HomePage(driver, propertyManager);
        searchResultsPage = new SearchResultsPage(driver, propertyManager);
        productDetailsPage = new ProductDetailsPage(driver, propertyManager);
        cartActionSummaryPage = new CartActionSummaryPage(driver, propertyManager);
        cartPage = new CartPage(driver, propertyManager);


    }

    @AfterMethod
    public void browserReset() {
        driver.manage().deleteAllCookies();
    }

    @AfterClass
    public void cleanUp() {
        driver.quit();
    }


}
