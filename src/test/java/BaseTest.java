import com.pdrewa.PropertyManager;
import com.pdrewa.drivers.DriverManager;
import com.pdrewa.listeners.TestReporter;
import com.pdrewa.listeners.TestResultsListener;
import com.pdrewa.pageobject.amazon.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.*;

@Listeners({TestResultsListener.class, TestReporter.class})
public abstract class BaseTest {

    public static final Logger logger = LogManager.getLogger("Assert");
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected DriverManager driverManager;
    protected PropertyManager propertyManager;

    protected ProductDetailsPage productDetailsPage;
    protected CartActionSummaryPage cartActionSummaryPage;
    protected CartPage cartPage;
    protected HomePage homePage;
    protected SearchResultsPage searchResultsPage;

    @Parameters({"browserName", "testDataFileName"})
    @BeforeClass
    public void setup(@Optional("Chrome") String browserName, @Optional("default.properties") String testDataFileName) {
        driverManager = new DriverManager();
        driver.set(driverManager.getDriver(browserName, "Grid"));

        ITestContext context = Reporter.getCurrentTestResult().getTestContext();
        context.setAttribute("WebDriver", driver.get());

        propertyManager = new PropertyManager(testDataFileName);

        homePage = new HomePage(driver.get(), propertyManager);
        searchResultsPage = new SearchResultsPage(driver.get(), propertyManager);
        productDetailsPage = new ProductDetailsPage(driver.get(), propertyManager);
        cartActionSummaryPage = new CartActionSummaryPage(driver.get(), propertyManager);
        cartPage = new CartPage(driver.get(), propertyManager);
    }

    @AfterMethod
    public void browserReset() {
        driver.get().manage().deleteAllCookies();
    }

    @AfterClass
    public void cleanUp() {
        driver.get().quit();
    }
}
