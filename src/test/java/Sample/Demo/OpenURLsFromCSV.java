package Sample.Demo;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.logging.FileHandler;

public class OpenURLsFromCSV extends BaseTest
{
	//WebDriver driver;
	@Test
	public void demoTest()
	{
    //public static void main(String[] args) {
        // Set the system property for ChromeDriver (Make sure the path is correct)

        // Create a new ChromeDriver instance

        // Path to your CSV file
        String csvFilePath = "C:\\Users\\Admin\\OneDrive\\Desktop\\url's.csv";
        try {
            Reader reader = new FileReader(csvFilePath);
            CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(reader);

            for (CSVRecord record : csvParser) {
                String url = record.get("url"); // Assuming your header in the CSV file is "url"

                // Open the URL in the browser
               driver.get(url);
               Thread.sleep(3000);

               JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
               jsExecutor.executeScript("window.scrollTo(250, document.body.scrollHeight);");

                // Wait for a few seconds to observe the opened URL (optional)
                Thread.sleep(3000); // Adjust the time as needed
               // JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

                // Set the height of each screenshot section (adjust as needed)
                int sectionHeight = 1000;

                // Get the total height of the page
                Long totalHeight = (Long) jsExecutor.executeScript("return Math.max( document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight );");

                // Scroll down and take screenshots of each section
                for (int scrollY = 0; scrollY < totalHeight; scrollY += sectionHeight) {
                    // Scroll down to the next section
                    jsExecutor.executeScript("window.scrollTo(0, " + scrollY + ");");

                    // Wait for a short time to let the page settle (optional)
                    Thread.sleep(500);
                    // Take a screenshot of the visible area
                    String screenshotDirectory = "C:\\HairHousescreenshot";

                    File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                    // Save the screenshot to a specific location with a unique name
                    String screenshotFileName = "screenshot_" + scrollY + ".png";
                //  FileHandler.copy(screenshotFile, new File(screenshotFileName));
                 
                }
            }

            csvParser.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the WebDriver after all URLs have been processed
            driver.quit();
        }
    }
}