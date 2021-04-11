package listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.*;

public class TestReporter implements IReporter {

    static final Logger logger = LogManager.getLogger("");

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
                               String outputDirectory) {

        for (ISuite iSuite : suites) {
            //Get a map of result of a single suite at a time

            Map<String, ISuiteResult> results = iSuite.getResults();

            //Get the key of the result map

            Set<String> keys = results.keySet();

            //Go to each map value one by one

            for (String key : keys) {

                //The Context object of current result

                ITestContext context = results.get(key).getTestContext();

                //Print Suite detail in Console

                logger.info("Suite Name->" + context.getName()

                        + "::Report output Ditectory->" + context.getOutputDirectory()

                        + "::Suite Name->" + context.getSuite().getName()

                        + "\n     Start Date Time for execution->" + context.getStartDate()

                        + "::End Date Time for execution->" + context.getEndDate());

                //Get Map for only failed test cases

                IResultMap resultMap = context.getFailedTests();

                //Get method detail of failed test cases

                Collection<ITestNGMethod> failedMethods = resultMap.getAllMethods();

                //Loop one by one in all failed methods

                logger.info("--------FAILED TEST CASE---------");

                for (ITestNGMethod iTestNGMethod : failedMethods) {

                    //Print failed test cases detail

                    logger.info("TESTCASE NAME->" + iTestNGMethod.getMethodName()

                            + "\nDescription->" + iTestNGMethod.getDescription()

                            + "\nPriority->" + iTestNGMethod.getPriority()

                            + "\n:Date->" + new Date(iTestNGMethod.getDate()));

                }
            }
        }
    }
}
