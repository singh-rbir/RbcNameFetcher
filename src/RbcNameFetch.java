/*
 * Code Test
 * Author: Rajanbir (Raj) Singh
 * Date: Aug 5, 2020 
 * 
 * Synopsis: The program uses selenium with a head less browser to fetch and output the names of billers
 * on the first two pages of the link: http://www.rbcroyalbank.com/online/online-bill-payees.html
 * 
 * Please configure dependencies before compiling/running the program
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class RbcNameFetch {

	// log statement for removing useless warnings from Log output
	static java.util.logging.Logger Log = java.util.logging.Logger.getLogger("com.gargoylesoftware");
	
	public static void main(String[] args) {
		
		Log.setLevel(Level.OFF); // turns off useless warnings pertaining to rbc's backend-code
		
		HtmlUnitDriver driver = new HtmlUnitDriver();
		
		driver.setJavascriptEnabled(true); // used later for implicit wait
		
		// wait up a max of 10s for page to load after any clicks/navigations
		//driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		// go to the provided RBC link
		driver.get("http://www.rbcroyalbank.com/online/online-bill-payees.html");
		
		System.out.println("Page opened: " + driver.getTitle());
		
		// write 'a' in the search field
		WebElement searchField = driver.findElement(By.id("payeename_id"));
		searchField.sendKeys("a");
		searchField.submit();
		
		System.out.println("Page opened: " + driver.getTitle());
		
		/* fetch all names in the list on page 1
		 * This gets all the names in the table, in the form of a <td> WebElement
		 */
		List<WebElement> billers = new ArrayList<WebElement>();
		billers = driver.findElements(By.className("dataTableText"));
		
		// print the names on the first page
		System.out.println("\nBILLERS' NAMES ON PAGE 1: ");
		
		printNames(billers); // helper method called 
		
		
		/* Click the "Next" button to go to next page. 
		 * "Next" is a link text that is unique to this particular link on the page 
		 */
		WebElement nextPage = driver.findElement(By.linkText("Next"));
		nextPage.click();
		
		new WebDriverWait(driver, 10).until(
				webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		
		// find the names again
		billers = driver.findElements(By.className("dataTableText"));
		
		// print the names on the first page
		System.out.println("\nBILLERS' NAMES ON PAGE 2: ");
		printNames(billers);
		
		driver.quit();
		
		System.out.println("\nWork done! Driver has stopped. ");
		
	}
	
	
	/*
	 * Helper method for printing Billers' list
	 */
	private static void printNames(List<WebElement> list) {
		for(WebElement we: list) {
			System.out.println("\t" + we.getText());
		}
	}

}
