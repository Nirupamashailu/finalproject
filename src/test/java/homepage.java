import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.Keys;
import io.qameta.allure.Description;

public class homepage {

	WebDriver driver;

	@BeforeMethod
	@Parameters("browserName")
	public void setDriver(String browserName) {
		driver = new crossbrowsing().getDriver(browserName);
	};

	@Test
	@Description("Test to verify navigation bar") 
	public void verifyNavigationBar() {

		driver.get("https://westfloridaahec.org/");
		WebElement navbar = driver.findElement(By.xpath("//nav[@class=\"fusion-main-menu\"]"));
		if (navbar.isDisplayed()) {
			System.out.println("navbar is present");
		} else {
			System.out.println("navbar is not present");
		}

	};

	@Test
	@Description("Test to verify all health programs dropdown") 
	public void verifyhealthProgramDropdown() {
		driver.get("https://westfloridaahec.org/");

		// Expected dropdown text values
		List<String> expectedValues = List.of("Tobacco", "AHEC Scholars", "Healthy Aging", "Covering Florida");

		// Locate the dropdown menu and fetch all <a> tags inside it
		WebElement menuOption = driver
				.findElement(By.xpath("//li[@id='menu-item-264']/a/span[contains(text(),'PROGRAMS')]"));
		menuOption.click();
		List<WebElement> listOfOptions = driver.findElements(By.xpath("//*[@id=\"menu-item-264\"]/ul/li/a/span"));
		List<String> actualValues = new ArrayList<>();

		// Iterate through all the links, fetch the text, and add it to the List
		for (WebElement element : listOfOptions) {
			String option = element.getText();
			actualValues.add(option);
		}
		// Print actual dropdown values for debugging
		System.out.println("Actual dropdown values: " + actualValues);

		// Compare actual values with expected values
		Assert.assertEquals(actualValues, expectedValues, "Dropdown values do not match!");
	}

	@Test
	@Description("Test to verify health program resource links on homepage ") 
	public void verifyHealthProgramResourcelinks() {
		driver.get("https://westfloridaahec.org/");
		WebElement container = driver.findElement(By.cssSelector(".fusion-content-boxes"));

		// Get all <a> tags within the container
		List<WebElement> links = container.findElements(By.tagName("a"));

		// Expected URLs
		List<String> expectedLinks = List.of("https://westfloridaahec.org/tobacco/quit-tobacco/",
				"https://westfloridaahec.org/healthy-aging/", "https://westfloridaahec.org/navigators/",
				"https://westfloridaahec.org/ahec-scholars/");

		// Verify links
		List<String> actualLinks = new ArrayList<>();
		for (WebElement link : links) {
			String href = link.getAttribute("href"); // Extract the href attribute
			System.out.println("Found link: " + href);
			actualLinks.add(href);

			// Validate if the link is not null or empty
			Assert.assertNotNull(href, "Link is null!");
			Assert.assertFalse(href.isEmpty(), "Link is empty!");

			// Optionally, navigate to the link to verify it loads
			driver.navigate().to(href);
			Assert.assertTrue(driver.getTitle().length() > 0, "Page did not load for link: " + href);
			driver.navigate().back();
		}

		// Compare actual links with expected links
		Assert.assertEquals(actualLinks, expectedLinks, "Links do not match the expected values!");
	}

	@Test
	@Description("Test to verify search bar") 
	public void VerifysearchBarFunctionality() {
		driver.get("https://westfloridaahec.org/");
		WebElement searchbox = driver.findElement(By.xpath(
				"//div[@class=\"fusion-search-field search-field\"]/label/input[@type=\"search\" and @class=\"s\"][1]"));
		searchbox.sendKeys("Healthy Aging Programs");
		searchbox.sendKeys(Keys.ENTER);
		WebElement searchresult = driver.findElement(By.xpath("//*[@id='wrapper']/section/div/div/div/div/h1"));
		String extractedtext = searchresult.getText();
		Assert.assertEquals(extractedtext, "Search results for: Healthy Aging Programs", "Search results appear!");

	}

	@Test
	@Description("Test to verify health program content") 
	public void verifyeachHealthProgramPageContent() {

		driver.get("https://westfloridaahec.org/");
		driver.findElement(By.xpath("//li[@id='menu-item-264']/a/span[contains(text(),'PROGRAMS')]")).click();
		WebElement tobaccoprogram = driver.findElement(By.xpath("//*[@id=\"menu-item-344\"]/a/span"));
		tobaccoprogram.click();
		driver.findElement(By.xpath("//h2[text()='SYSTEMS CHANGE']")).isDisplayed();
		driver.findElement(By.xpath("//h2[text()='QUIT TOBACCO']")).isDisplayed();
		driver.findElement(By.xpath("//h2[text()='TRAINING']")).isDisplayed();
		System.out.println("Tobacco program is verified");

		driver.get("https://westfloridaahec.org/");
		driver.findElement(By.xpath("//li[@id='menu-item-264']/a/span[contains(text(),'PROGRAMS')]")).click();
		WebElement ahecprogram = driver.findElement(By.xpath("//*[@id='menu-item-280']/a/span"));
		ahecprogram.click();
		WebElement Verificationtext = driver.findElement(By.xpath("//*[@id='post-266']/div/div[1]/div/div[1]/div/div[1]/h3"));
		String actualtext = Verificationtext.getText();
		String expectedtext = "The Scholars Program is nationally recognized and only 150 students in the state of Florida are able to join this elite program.";
		Assert.assertEquals(actualtext, expectedtext, "text doesn't matches");
		System.out.println("AHEC Scholars program is verified");


		driver.get("https://westfloridaahec.org/");
		driver.findElement(By.xpath("//li[@id='menu-item-264']/a/span[contains(text(),'PROGRAMS')]")).click();
		WebElement healthyagingprogram = driver.findElement(By.xpath("//*[@id='menu-item-534']/a/span"));
		healthyagingprogram.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement Verifysignupsection = driver.findElement(By.xpath("//h1[contains(text(),'Sign Up for Healthy Aging Classes')]"));
		String text = Verifysignupsection.getText();
		System.out.println(text);
		Assert.assertEquals(text, "Sign Up for Healthy Aging Classes", "text doesn't match");
		System.out.println("Healthy Aging program is verified");


		

		driver.get("https://westfloridaahec.org/");
		driver.findElement(By.xpath("//li[@id='menu-item-264']/a/span[contains(text(),'PROGRAMS')]")).click();
		WebElement coveringflprogram = driver.findElement(By.xpath("//*[@id='menu-item-1572']/a/span"));
		coveringflprogram.click();
		WebElement Verifyintrosection = driver.findElement(By.xpath("//h3[contains(text(),'Counties we cover: Walton, Okaloosa, Santa Rosa and Escambia')]"));
		Verifyintrosection.isDisplayed();
		System.out.println("Covering Florida program is verified");

	}

	@Test
	@Description("Test to verify User registration and login") 
	public void userRegistrationandlogin() {
		driver.get("https://westfloridaahec.org/my-account/");
		driver.findElement(By.xpath("//*[@id='reg_username']")).sendKeys("Nupsu4");
		driver.findElement(By.xpath("//*[@id='reg_email']")).sendKeys("niru125@gmail.com");
		driver.findElement(By.xpath("//*[@id='reg_password']")).sendKeys("Password@123");
		driver.findElement(By.xpath("//*[@name='register']")).click();

//verify login
		driver.get("https://westfloridaahec.org/my-account/");
		driver.findElement(By.xpath("//*[@id='username']")).sendKeys("niru125@gmail.com");
		driver.findElement(By.xpath("//*[@id='password']")).sendKeys("Password@123");
		driver.findElement(By.xpath("//*[@name='login']")).click();
		String username = driver.findElement(By.xpath("//span[@class='hello']/strong")).getText();
		if (username.equals("Nupsu4")) {
			System.out.println("Registration and Login is successfull- user name is " + username);

		}
		;
	}

	@AfterMethod
	public void quitBrowser() {
		driver.quit();
	};

}
