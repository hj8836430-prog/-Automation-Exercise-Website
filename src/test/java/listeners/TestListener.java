package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import base.BaseTest;
import utils.ExtentReportManager;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentReportManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        String displayName = (description != null && !description.isEmpty()) ? description : testName;

        ExtentTest extentTest = extent.createTest(displayName);
        test.set(extentTest);
        test.get().info("Test Started: <b>" + testName + "</b>");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS,
            MarkupHelper.createLabel("<b>PASSED</b> — " + result.getMethod().getMethodName(), ExtentColor.GREEN));
        attachScreenshot(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL,
            MarkupHelper.createLabel("<b>FAILED</b> — " + result.getMethod().getMethodName(), ExtentColor.RED));
        test.get().log(Status.FAIL, "<pre>" + result.getThrowable().getMessage() + "</pre>");
        attachScreenshot(result);
    }

    private void attachScreenshot(ITestResult result) {
        try {
            // Get the test instance (which extends BaseTest)
            Object testInstance = result.getInstance();
            if (!(testInstance instanceof BaseTest)) {
                test.get().log(Status.WARNING, "Test instance is not BaseTest");
                return;
            }

            BaseTest baseTest = (BaseTest) testInstance;
            WebDriver driver = baseTest.getDriver();
            if (driver == null) {
                test.get().log(Status.WARNING, "Driver is null");
                return;
            }

            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String testName = result.getMethod().getMethodName();
            String screenshotPath = System.getProperty("user.dir") + "/test-output/screenshots/" + testName + "_" + timestamp + ".png";

            File dest = new File(screenshotPath);
            dest.getParentFile().mkdirs();
            FileUtils.copyFile(src, dest);

            test.get().addScreenCaptureFromPath(screenshotPath, testName + " Screenshot");
        } catch (Exception e) {
            test.get().log(Status.WARNING, "Screenshot capture failed: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP,
            MarkupHelper.createLabel("<b>SKIPPED</b> — " + result.getMethod().getMethodName(), ExtentColor.YELLOW));
        if (result.getThrowable() != null) {
            test.get().log(Status.SKIP, result.getThrowable().getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        int passed = context.getPassedTests().size();
        int failed = context.getFailedTests().size();
        int skipped = context.getSkippedTests().size();
        System.out.println("\n========================================");
        System.out.println("  TEST SUMMARY: " + passed + " passed, " + failed + " failed, " + skipped + " skipped");
        System.out.println("  Report: test-output/ExtentReport.html");
        System.out.println("========================================\n");
    }
}
