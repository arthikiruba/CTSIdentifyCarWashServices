package testSuite;

import org.testng.annotations.Test;

import pageClass.CarWashPage;
import pageClass.FreeListingPage;
import pageClass.GymPage;

public class RegressionTest {

	@Test
	public void regressionTesting() throws Exception{
		
		CarWashPage car = new CarWashPage();
		FreeListingPage free = new FreeListingPage();
		GymPage gym = new GymPage();
		
		car.openURL();
		car.autoCarMenu();
		car.selectCarWash();
		car.location();
		car.ratingIsDisplayed();
		car.voteIsDisplayed();
		car.locationResultTest();
		car.displayDetails();
		
		gym.fitness();
		gym.gym();
		gym.gymMenu();
		
		free.freeListing();
		free.reader();
	}
}
