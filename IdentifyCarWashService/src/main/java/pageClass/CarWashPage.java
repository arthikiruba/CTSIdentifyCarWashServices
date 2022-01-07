package pageClass;

import java.util.List;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseClass.BaseUI;

public class CarWashPage extends BaseUI {

	WebDriverWait wait = new WebDriverWait(driver, 30);
	static int count = 0;

	// XPath to Select Car Wash Functionality
	By autoCare = By.xpath("//span[text() = 'Auto care']");
	By carWash = By.xpath("//span[text() = 'Car Wash']");

	// XPath to filter by Location, Ratings and Votes
	By location = By.xpath("//a[@class = ' lng_srtfltr'] [text() = 'Location']");
	By enterLocation = By.xpath("//input[@id = 'sortbydist'] [@name = 'sortbydist']");
	By clickGo = By.xpath("//button[@id = 'sortbydistbtn'] [text() = 'Go']");
	By rating = By.xpath("//li[@id = 'rating']");

	// XPath for Location Verification
	By verifyLocation = By.xpath("(//span[@class='jcn'])[2]");
	By verifyLocationPage = By.xpath("(//span[@class='lng_add'])[1]");

	// XPath to print the details
	By shopNameXpath = By.xpath("//*[contains(@class, 'lng_cont_name')]");
	By starXpath = By.xpath("//*[contains(@class, 'green-box')]");
	By voteXpath = By.xpath("//p[@class='newrtings ']/child::a/span[@class='rt_count lng_vote']");

	// Automate Auto Care
	public void autoCarMenu() {
		try {
			// Creating Test report for "autoCarMenu" function
			logger = report.createTest("Car Wash -Click Auto Car");
			driver.findElement(autoCare).click();
			reportPass("Auto Car Functionality selected from the left menu of justdial website.");

		} catch (Exception e) {
			
			// Print if the Test Case Failed
			e.printStackTrace();
			reportFail("Auto Car functionality not Selected" + e.getMessage());
		}
	}

	// Automate Car Wash
	public void selectCarWash() {
		try {
			// Creating Test report for "selectCarWash" function
			logger = report.createTest("Car Wash -Click Car Wash");
			wait.until(ExpectedConditions.elementToBeClickable(carWash));
			driver.findElement(carWash).click();
			Thread.sleep(3000);
			reportPass("Car Wash Functionality selected from the 'Auto Care' Menu");

		} catch (Exception e) {
			
			// Print if the Test Case Failed
			e.printStackTrace();
			reportFail("Car Wash functionality not Selected");
		}
	}

	// Check Title of Car Wash
	public void carWashTitleCheck() {
		try {
			logger = report.createTest("Car Wash -Verify title");
			String title = driver.getTitle();
			if (title.contains("Car Washing Services")) {
				// Print if the Test Case Passed
				reportInfo("Title matched for the Car Wash Services Page");
			} else {
				reportInfo("Title not matched for the Car Wash Services Page");
			}
		} catch (Exception e) {
			// Print if the Test Case Failed
			e.printStackTrace();
		}
	}

	// Enter Location
	public void location() {
		try {
			logger = report.createTest("Car Wash -Click Location");
			wait.until(ExpectedConditions.elementToBeClickable(location));
			driver.findElement(location).click();
			wait.until(ExpectedConditions.elementToBeClickable(enterLocation));
			WebElement location = driver.findElement(enterLocation);
			location.clear();
			location.sendKeys("Guindy");
			// Click "Go" button
			driver.findElement(clickGo).click();
			// Print if the Test Case Passed
			reportPass("Location functionality clicked to enter nearest location");

		} catch (Exception e) {
			// Print if the Test Case Failed
			e.printStackTrace();
			reportFail("Location functionality not selected");
		}
	}

	// Verify Ratings
	public void ratingIsDisplayed() {
		try {
			logger = report.createTest("Car Wash -Ratings");
			driver.findElement(rating).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement rateCheck = driver.findElement(starXpath);
			String rateStr = rateCheck.getText();
			if (rateStr.isBlank()) {
				// Print if the Test Case Passed
				reportInfo("Ratings not displayed for the shops");
			} else {
				reportInfo("Ratings displayed for the shops");
				driver.navigate().back();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Verify Votes
	public void voteIsDisplayed() {
		try {
			logger = report.createTest("Car Wash -Votes");
			WebElement voteCheck = driver.findElement(voteXpath);
			String voteStr = voteCheck.getText();
			if (voteStr.isBlank()) {
				reportInfo("Vote not displayed for the shops");
			} else {
				reportInfo("Vote displayed for the shops");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Verify whether location is displayed correctly
	public void locationResultTest() {
		try {
			logger = report.createTest("Car Wash -Verify Location Page");

			driver.findElement(verifyLocation).click();
			WebElement location_check = wait.until(ExpectedConditions.visibilityOfElementLocated(verifyLocationPage));
			String location = location_check.getText();
			if (location.contains("Guindy")) {
				reportInfo("Shops are displayed for the given location");
			} else {
				reportInfo("Shops aren't displayed for the given location");
			}
			driver.navigate().back();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Print all the required details
	public void displayDetails() {
		try {
			logger = report.createTest("Car Wash -Print Shop names filtered by more then 4 ratings and 20 votes");
			List<WebElement> shopName = driver.findElements(shopNameXpath);
			List<WebElement> star = driver.findElements(starXpath);
			List<WebElement> vote = driver.findElements(voteXpath);
			for (int i = 0; i < shopName.size(); i++) {
				float ratings = Float.parseFloat(star.get(i).getText());
				String number = vote.get(i).getText().split(" ")[0];
				int votes = Integer.parseInt(number);
				if (count < 5) {
					if (ratings > 4 && votes > 20) {
						reportInfo(i + 1 + ". " + shopName.get(i).getText() + "||" + ratings + "||"
								+ vote.get(i).getText());
						count++;
					}
				}
			}
			// Print if the Test Case Passed
			reportPass("Car Wash Service shop name has been displayed successfully");

		} catch (Exception e) {
			// Print if the Test Case Failed
			e.printStackTrace();
			reportFail("Car Wash Service shop names not displayed");
		}
	}
}