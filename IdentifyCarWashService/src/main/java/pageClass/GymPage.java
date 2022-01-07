package pageClass;

import static org.testng.Assert.assertTrue;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseClass.BaseUI;

public class GymPage extends BaseUI {

	// XPath for Fitness Page
	By fitnessXpath = By.xpath("//a[@id='ContextualHotkey_27']");
	By gymXpath = By.xpath("(//ul[@class='mm-listview mm-lstex']/li/a)[3]");
	By gymMenu = By.xpath("//span[@class='meditle1 lng_commn']");

	WebDriverWait wait = new WebDriverWait(driver, 30);

	// Automate Fitness
	public void fitness() {
		try {
			logger = report.createTest("Fitness -Click Fitness");
			driver.get(prop.getProperty("url"));
			WebElement fitness = driver.findElement(fitnessXpath);
			if (fitness.isDisplayed() && fitness.isEnabled()) {
				reportInfo("Fitness functionality available and clicked");
				fitness.click();
			} else {
				assertTrue(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Verify Fitness Functionality
	public void fitnessMenu() {
		try {
			logger = report.createTest("Fitness -Verify Fitness menu page");
			String title = driver.getTitle();
			if (title.contains("Fitness")) {
				reportInfo("Fitness menu available and page verified");
				takeScreenshot();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Automate Gym
	public void gym() throws Exception {
		try {
			logger = report.createTest("Fitness -Click Gym");
			WebElement gym = wait.until(ExpectedConditions.visibilityOfElementLocated(gymXpath));
			if (gym.isEnabled()) {
				reportInfo("Gym button is visible and selected");
				takeScreenshot();
				gym.click();
				Thread.sleep(3000);
			} else {
				assertTrue(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Verify Gym Functionality
	public void gymMenu() {
		try {
			logger = report.createTest("Fitness -Print all the sub-menu available under Gym menu");
			List<WebElement> lst = driver.findElements(gymMenu);
			for (int j = 1; j < lst.size(); j++) {
				reportInfo(lst.get(j).getText());
			}
			reportPass("Gym sub-menu displayed successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
