 package internetFunctions;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import database.MusicDB;
import tables.YoutubeTable;

public class YoutubeToMp3 {

	public static void downloadSongs() throws InterruptedException, ClassNotFoundException, SQLException, IOException {
		
		Connection conn = MusicDB.getConnection();
		YoutubeTable youtubeTable = new YoutubeTable(conn);
		ArrayList<String[]> youtubeLinks = youtubeTable.getLinks();
		String downloadDir = "C:\\College\\Freshmen Summer\\Music";

		// Set the firefox profile settings
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.download.dir", downloadDir);
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.manager.showAlertOnComplete", false);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "audio/mp3");
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "audio/mpeg");		

		for(String[] youtubeLinkWithSongAndLink : youtubeLinks) {
			int downloadNumber = youtubeLinks.indexOf(youtubeLinkWithSongAndLink) + 1;
			
//			if(downloadNumber <= 9) {
			
//				System.out.println("SKIPPED");
//				continue;
//			}
			// Open a firefox page to twitter search page
			WebDriver driver = new FirefoxDriver(profile);
			String url = "http://www.youtube-mp3.org/";
			driver.get(url);
	
			// Input the youtube URL
			WebElement textBox = driver.findElement(By.id("youtube-url"));
			textBox.click();
			textBox.clear();
			String youtubeLink = "http://www.youtube.com"+youtubeLinkWithSongAndLink[1];
			textBox.sendKeys(youtubeLink);
	
			WebElement convertButton = driver.findElement(By.id("submit"));
			convertButton.click();
	
			WebElement statusText = driver.findElement(By.id("status_text"));
			WebElement errorText = driver.findElement(By.id("error_text"));
	
			boolean converted = false;
			boolean error = false;
			String convertedString = "Video successfully converted to mp3";
			while (!converted) {
				// look for downloaded tag
				if (statusText.getText().equals(convertedString)) {
					converted = true;
				} else if (errorText.getText().length() > 0) {
					driver.quit();
					error = true;
					break;
				} else {
					Thread.sleep(1000);
				}
			}
	
			if(!error) {
				WebElement downloadButton = driver.findElement(By.id("dl_link"));
				List<WebElement> links = downloadButton.findElements(By.linkText("Download"));
				for (WebElement link : links) {
					if (link.isDisplayed()) {
						link.click();
					}
				}
		
				// Poll download directory to see when download is done
				File dir = new File(downloadDir);
				FilenameFilter partFilter = new FilenameFilter() {
					public boolean accept(File dir, String name) {
						String lowercaseName = name.toLowerCase();
						if (lowercaseName.endsWith(".part")) {
							return true;
						} else {
							return false;
						}
					}
				};
			
				boolean downloaded = false;
				Thread.sleep(2000);
				while(!downloaded) {
					String[] fileList = dir.list(partFilter);
					if(fileList.length > 0) {
						Thread.sleep(1000);
					} else {
						renameFile(youtubeLinkWithSongAndLink[2], youtubeLinkWithSongAndLink[0], downloadDir);
						System.out.println("Downloaded ("+downloadNumber+"/"+youtubeLinks.size()+")");
						downloaded = true;
					}
				}
				
				// close the browser
				driver.quit();
			}
		}
	}
	
	public static void renameFile(String oldName, String newName, String downloadDir) throws IOException {
		newName = newName.replace("\\", ", ");
		newName = newName.replace(":", ", ");
		newName = newName.replace("/", ", ");
		newName = newName.replace("?", ", ");
		newName = newName.replace(">", ", ");
		newName = newName.replace("<", ", ");
		newName = newName.replace("*", ", ");
		newName = newName.replace("\"", "\"");
		newName = newName.replace("|", ", ");
		newName = newName.replace("/\\", ", ");
		
		System.out.println(newName);
		new File(downloadDir + "\\\\" + oldName + ".mp3").renameTo(new File(downloadDir + "\\\\" + newName + ".mp3"));
	}

}
