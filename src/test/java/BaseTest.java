import listeners.TestReporter;
import listeners.TestResultsListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

@Listeners({TestResultsListener.class, TestReporter.class})
public abstract class BaseTest {

    public static final Logger logger = LogManager.getLogger("Assert");
    public static WebDriver driver;
    static SoftAssert softAssert;
    protected DriverManager driverManager;
    protected PropertyManager propertyManager;

    @Parameters("testDataFileName")
    @BeforeClass
    public void setup(@Optional("default.properties") String testDataFileName
            , ITestContext context) {
        driverManager = new DriverManager();
        driver = driverManager.getDriver();
        context.setAttribute("WebDriver", driver);
        propertyManager = new PropertyManager(testDataFileName);
        softAssert = new SoftAssert();


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
