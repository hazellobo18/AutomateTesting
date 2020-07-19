package mytestpack;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
public class AutoTest {
	WebDriver driver;
	
	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.gecko.driver", "D:\\FirefoxDriver\\geckodriver.exe");
		DesiredCapabilities capabilities=DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", true);
		driver =new FirefoxDriver(capabilities);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.navigate().to("http://automationpractice.com/index.php");

	}
	
	@Test(priority=1)
	public void searchBox() throws InterruptedException{	
		driver.findElement(By.id("search_query_top")).sendKeys("T-shirts");
		driver.findElement(By.name("submit_search")).click();
		Thread.sleep(2000);
		String actual=driver.findElement(By.xpath("//*[@id=\"center_column\"]/h1/span[1]")).getText();
		String expected="\"T-SHIRTS\"";
		Assert.assertEquals(actual,expected);
		System.out.println("Search Successful");
	}
	
	@Test(priority=2)
	public void registerTest() throws InterruptedException {
		driver.findElement(By.className("login")).click();
		Thread.sleep(2000);
		
		driver.findElement(By.id("email_create")).sendKeys("hazellobo1@gmail.com");
		driver.findElement(By.id("SubmitCreate")).click();
		Thread.sleep(3000);
		
		//registrationForm  
		driver.findElement(By.id("customer_firstname")).sendKeys("Hazel");
		driver.findElement(By.id("customer_lastname")).sendKeys("Lobo");
		driver.findElement(By.id("passwd")).sendKeys("123Hazel");
		driver.findElement(By.id("address1")).sendKeys("3113  Doctors Drive");
		driver.findElement(By.id("city")).sendKeys("Los Angeles");
		Select state= new Select(driver.findElement(By.id("id_state")));
		state.selectByValue("5");
		driver.findElement(By.id("postcode")).sendKeys("90017");
		Select country= new Select(driver.findElement(By.id("id_country")));
		country.selectByValue("21");
		driver.findElement(By.id("phone_mobile")).sendKeys("310-341-3202");
		driver.findElement(By.id("alias")).clear();
		driver.findElement(By.id("alias")).sendKeys("3113  Doctors Drive");
		driver.findElement(By.id("submitAccount")).click();	
		Thread.sleep(2000);
		String expectedTitle="My account - My Store";
		String actualTitle=driver.getTitle();
		if(expectedTitle.contentEquals(actualTitle)) {
			System.out.println("Registration Succesfull");
		}else {
			System.out.println("Registration Failed");
		}
	}
	
	@Test(priority=3)
	public void loginVerify() throws InterruptedException {
		driver.findElement(By.className("login")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("email")).sendKeys("hazellobo1@gmail.com");
		driver.findElement(By.id("passwd")).sendKeys("123Hazel");
		driver.findElement(By.id("SubmitLogin")).click();
		Thread.sleep(2000);
		
		String expectedTitle="My account - My Store";
		String actualTitle=driver.getTitle();
		if(expectedTitle.contentEquals(actualTitle)) {
			System.out.println("Login Succesfull");
		}else {
			System.out.println("Login Failed");
		}
	}
	
	@Test(priority=4)
	public void addcartTest() throws InterruptedException {

		Actions action = new Actions(driver);
		
		//adding item1
		WebElement element1=driver.findElement(By.xpath("//*[@id=\"homefeatured\"]/li[1]/div"));
		
		JavascriptExecutor js= (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true);",element1);
		Thread.sleep(1000);
		WebElement product1=driver.findElement(By.xpath("//*[@id=\"homefeatured\"]/li[1]/div/div[2]/div[2]/a[1]"));		
		action.moveToElement(element1).click(product1).build().perform();
		System.out.println("Product added");
		Thread.sleep(3000);
		
		WebElement conBtn= driver.findElement(By.xpath("//div[@id='layer_cart']/div/div[2]/div[4]/span/span"));
		js.executeScript("arguments[0].scrollIntoView(true);",conBtn);
		conBtn.click();
		Thread.sleep(1000);

		//adding item2
		WebElement element2=driver.findElement(By.xpath("//*[@id=\"homefeatured\"]/li[2]/div"));
		
		js.executeScript("arguments[0].scrollIntoView(true);",element2);
		Thread.sleep(1000);
		WebElement product2=driver.findElement(By.xpath("//*[@id=\"homefeatured\"]/li[2]/div/div[2]/div[2]/a[1]"));		
		action.moveToElement(element2).click(product2).build().perform();
		System.out.println("Product added");
		Thread.sleep(3000);
		
		WebElement checkoutBtn= driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[2]/div[4]/a"));
		js.executeScript("arguments[0].scrollIntoView(true);",checkoutBtn);
		checkoutBtn.click();		
		Thread.sleep(1000);
		
		String status= driver.findElement(By.className("ajax_cart_quantity")).getText();
		System.out.println("There are " +status+" products in your cart!");
		Assert.assertEquals(status,"2");
	}
	
	@AfterMethod
	public void close() {
		driver.quit();
	}
	
}
