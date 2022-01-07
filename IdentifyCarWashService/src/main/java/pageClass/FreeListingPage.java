package pageClass;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import baseClass.BaseUI;

public class FreeListingPage extends BaseUI {

	//XPath to Register for Free Listing
	By freelist = By.xpath("//div[@class='rightfixed']/a[1]");
	By companyName = By.xpath("//input[@id='fcom']");
	By fName = By.xpath("//input[@id='fname']");
	By lName = By.xpath("//input[@id='lname']");

	By nameTitle = By.xpath("//div/div[@class='drop']");
	By selectNameTitle = By.xpath("//div[@class='drop']/ul/li[1]");

	By mobile = By.xpath("//input[@type='mobile' and @id='fmb0']");
	By landline = By.xpath("//input[@type='mobile' and @id='fph0']");
	By city = By.xpath("//input[@name='fcity']");

	By submit = By.xpath("//button[@class='bbtn subbtn']");

	By errorMessage = By.xpath("//span[contains(@class,'almsg')]");

	WebDriverWait wait = new WebDriverWait(driver, 30);

	//Automate Free Listing
	public void freeListing() {
		try {
			logger = report.createTest("Free Listing -Click Free Listing");
			driver.get(prop.getProperty("url"));
			WebDriverWait wait = new WebDriverWait(driver, 30);
			WebElement listing = wait.until(ExpectedConditions.visibilityOfElementLocated(freelist));
			listing.click();
			reportPass("Free Listing functionality selected");
		} catch (Exception e) {
			e.printStackTrace();
			reportFail("Free Listing not selected");
		}
	}

	//Verify Free Listing Page
	public void verifyTitle() {
		try {
			logger = report.createTest("Free Listing -Verify Free Listing page title");
			String actualTitle = driver.getTitle();
			if (actualTitle.contains("Free Listing")) {
				reportInfo("Title matched for the Free Listing Page");
			} else {
				reportInfo("Title not matched for the Free Listing Page");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Read and invoke data from Excel
	public void reader() {
		ExcelReader reader = new ExcelReader(System.getProperty("user.dir") + "\\utilities\\Hackathon-TestData.xlsx");
		int row = reader.getRowCount("FreeListing");
		for (int count = 2; count <= row; count++) {
			String str1 = reader.getCellData("FreeListing", 0, count);
			String str2 = reader.getCellData("FreeListing", 1, count);
			String str3 = reader.getCellData("FreeListing", 2, count);
			String str4 = reader.getCellData("FreeListing", 3, count);
			String str5 = reader.getCellData("FreeListing", 4, count);
			registration(str1, str2, str3, str4, str5);
		}
	}

	// Enter the details for Registeration
	public void registration(String companyname, String firstname, String lastname, String mobileno,
			String landlineno) {

		try {
			//Enter Company Name
			driver.findElement(companyName).sendKeys(companyname);
			
			//Enter First Name
			driver.findElement(fName).sendKeys(firstname);
			
			//Enter Last Name
			driver.findElement(lName).sendKeys(lastname);
			
			//Select Name Title
			WebElement drop = driver.findElement(nameTitle);
			Actions act = new Actions(driver);
			act.moveToElement(drop).click().perform();
			driver.findElement(selectNameTitle).click();
			
			//Enter Mobile Number
			driver.findElement(mobile).sendKeys(mobileno);
			
			//Enter Landline Number
			driver.findElement(landline).sendKeys(landlineno);
			
			//Click Submit
			driver.findElement(submit).click();
			Thread.sleep(4000);
			
			int i = 0;
			List<WebElement> lst = driver.findElements(errorMessage);
			for (WebElement x : lst) {
				if (x.getText().isBlank()) {
					i++;
				} else {
					reportInfo(x.getText());
				}
			}
			if (i == 0) {
				reportInfo("Valid data");
			} else {
				reportInfo("Ivalid data");
			}
			driver.get(prop.getProperty("url"));
			WebElement listing = wait.until(ExpectedConditions.visibilityOfElementLocated(freelist));
			listing.click();
			
		} catch (Exception e) {
		}
	}
}
