package baseClass;

import java.io.File;

import java.io.FileInputStream;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseUI {

	public static WebDriver driver;
	public static Properties prop;
	public static ExtentHtmlReporter extenthtml;
	public static ExtentReports report;
	public static ExtentTest logger;

	// Initialize Extent Report
	@BeforeTest
	@Parameters({ "suiteName" })
	public void reportinitialize(String suiteName) {
		extenthtml = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "//Extent-Reports//" + suiteName + ".html");
		report = new ExtentReports();
		report.attachReporter(extenthtml);
		report.setSystemInfo("Group", "QEA21QE026-Team4");
		report.setSystemInfo("OS", "Windows 10");
		report.setSystemInfo("Arthi Kiruba", "2063220");
		report.setSystemInfo("Chandhu M U", "2063252");
		report.setSystemInfo("Bharanidharan K", "2063488");

		extenthtml.config().setDocumentTitle("Hackathon Project");
		extenthtml.config().setReportName("Identify Car Wash Services");
		extenthtml.config().setTestViewChartLocation(ChartLocation.TOP);
		extenthtml.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
		extenthtml.config().setTheme(Theme.DARK);
	}

	// Name of Report and Screenshot
	public static String FileName() {
		Date d = new Date();
		return d.toString().replaceAll(":", "_").replaceAll(" ", "_");
	}

	// Invoke Browser
	@BeforeTest
	public void invokeBrowser() throws Exception {
		prop = new Properties();
		FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "\\utilities\\config.properties");
		prop.load(file);

		String browser = prop.getProperty("browserName");
		if (browser.equalsIgnoreCase("Chrome")) {
			
			WebDriverManager.chromedriver().setup();
			
			ChromeOptions option = new ChromeOptions();
			option.addArguments("--disable-notifications");
			option.addArguments("--disable-blink-features=AutomationControlled");
			
			driver = new ChromeDriver(option);
			
		} else if (browser.equalsIgnoreCase("firefox")) {
			
			WebDriverManager.firefoxdriver().setup();
			
			FirefoxOptions option = new FirefoxOptions();
			option.setProfile(new FirefoxProfile());
			option.addPreference("dom.webnotifications.enabled", false);
			option.addArguments("--disable-blink-features=AutomationControlled");
			
			driver = new FirefoxDriver(option);
			
		} else if (browser.equalsIgnoreCase("edge")) {
			
			WebDriverManager.edgedriver().setup();	
			driver = new EdgeDriver();
			
		} else {
			reportInfo("Missing Driver name");
			System.exit(0);
		}

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}

	// Open Website
	public void openURL() throws Exception {
		driver.get(prop.getProperty("url"));
		Thread.sleep(2000);
	}

	// Take Screenshot
	public void takeScreenshot() {
		TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
		File sourceFile = takeScreenshot.getScreenshotAs(OutputType.FILE);
		File destinationFile = new File(System.getProperty("user.dir") + "//Screenshots//" + FileName() + ".png");

		try {
			FileUtils.copyFile(sourceFile, destinationFile);
			logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "//Screenshots//" + FileName() + ".png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Report for Test Case Failure
	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenshot();
	}

	// Report for Test Case Pass
	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);
		takeScreenshot();
	}

	// Information of a Test Case
	public void reportInfo(String reportInfo) {
		logger.log(Status.INFO, reportInfo);
	}

	// Closing Browser
	@AfterTest
	public void closeBrowser() {
		driver.close();
	}

	// Save Extent-Report
	@AfterTest
	public void tearDown() {
		report.flush();
	}
}
