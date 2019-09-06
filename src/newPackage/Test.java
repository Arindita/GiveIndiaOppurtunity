package newPackage;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class Test {
	public static void main(String[] args) throws IOException {
		System.setProperty("webdriver.chrome.driver","E:\\SeleniumSoftware\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		String baseUrl = "https://en.wikipedia.org/wiki/Selenium";
		driver.manage().window().maximize();
		driver.get(baseUrl);
		WebElement externalLinks = driver.findElement(By.xpath(".//*[@id='toc']/ul/li/a[@href='#External_links']/span[text()='External links']"));
		externalLinks.click();
		if(driver.findElement(By.xpath(".//*[@id='External_links']")).isDisplayed()){
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			String newURL = driver.getCurrentUrl();  
		    String expectedNewUrl= "https://en.wikipedia.org/wiki/Selenium#External_links";
		    if(newURL.equals(expectedNewUrl)){
		    	System.out.println("External Link is verified.");
		    }
		    else{
		    	System.out.println("External Link is not working.");
		    }
		}
		else{
			System.out.println("Link did not go to proper section.");
		}
		WebElement oxygen = driver.findElement(By.xpath(".//*[@id='mw-content-text']//a[@title='Oxygen']/span"));
		oxygen.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement article = driver.findElement(By.xpath(".//*[@id='ca-nstab-main']/span/a/ancestor::li[@class='selected']"));
		if(article.isSelected()){
			System.out.println("This is a featured article page");
		}
		else{
			System.out.println("This is not a featured article page.");
		}
		WebElement properties = driver.findElement(By.xpath(".//*[@id='mw-content-text']/div/table"));
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        BufferedImage fullimg = ImageIO.read(screenshot);  
        Point point = properties.getLocation();
        int elewidth = properties.getSize().getWidth();
        int eleheight = properties.getSize().getHeight();
        try{
            BufferedImage elementScreenshot = fullimg.getSubimage(point.getX(), point.getY(), elewidth, eleheight);
            ImageIO.write(elementScreenshot,"JPEG",new File(System.getProperty("user.dir") +"./ScreenShots/elements.jpeg"));
        }
        catch(RasterFormatException ignoRasterFormatException)
       {
            System.out.println("Ignore Exception");
       }
       org.apache.commons.io.FileUtils.copyFile(screenshot, new File("E:\\ArinditaWSWorkspace\\GiveIndiaOppurtunity\\screenshot.png"));
		List<WebElement> referenceLinks = driver.findElements(By.xpath(".//*[@id='References']/parent::h2/following-sibling::div/ol[@class='references']/li"));
		System.out.println(referenceLinks.size());
		WebElement searchBar = driver.findElement(By.xpath(".//*[@id='searchInput']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(1000,0)");
		searchBar.sendKeys("pluto");
		List<WebElement> searchLinks = driver.findElements(By.xpath("//div[@class='suggestions-results']/a"));
		String expectedSearchResult = "Plutonium";
		String actualResult = searchLinks.get(1).getText();
		System.out.println(actualResult);
		if(expectedSearchResult.equals(actualResult)){
			System.out.println("Second Search option is coming as Plutonium");
		}
		else{
			System.out.println("Search result is not coming as plutonium");
		}
		 driver.close();
		
	}

}
