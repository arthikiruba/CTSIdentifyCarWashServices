package testSuite;

import org.testng.annotations.Test;

import pageClass.CarWashPage;
import pageClass.FreeListingPage;
import pageClass.GymPage;
public class SmokeTest {
	@Test
	public void smokeTesting() throws Exception {
		
		CarWashPage car = new CarWashPage();
		FreeListingPage free = new FreeListingPage();
		GymPage gym = new GymPage();
		
		car.openURL();
		car.autoCarMenu();
		car.selectCarWash();
		car.carWashTitleCheck();
		
		gym.fitness();
		gym.fitnessMenu();
		gym.gym();
		
		free.freeListing();
		free.verifyTitle();
	}
}
