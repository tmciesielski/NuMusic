package internetFunctions;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import tables.GarbageTable;
import tables.ControlTable;
import tables.RawXmTable;
import database.MusicDB;

public class BpmTwitterScraper {
	
    public static void initialLoad() throws Exception {
    	
    	// Get database connections
		Connection conn = MusicDB.getConnection();
		RawXmTable rawXmTable = new RawXmTable(conn);
		ControlTable controlTable = new ControlTable(conn);
		GarbageTable garbageTable = new GarbageTable(conn);
		
		// Set pre-loop variables
        String xmChannel = "bpm_playlist";
    	
		// Set start date as yesterday, today's tweets won't finish until EOD
    	Date startDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Get today's date
        c.add(Calendar.DATE, -1); // Start day = 1 day ago
        startDate = c.getTime();
        
        // Set final date as oldest tweet, researched from internet 
        //Date finalEndDate = sdf.parse("2011-02-04");
        Date finalEndDate = sdf.parse("2015-06-01");
        
        // While loop scrapes 1 day of tweets at a time
        while(true) {  
        	
        	// Need to set an interval 1 day before start date
        	Date tempEndDate = null;
            c.add(Calendar.DATE, -1); // temp end day = 2 days ago
            tempEndDate = c.getTime();            
            System.out.println("\n\n"+"Searching between "+sdf.format(tempEndDate)+" and "+sdf.format(startDate));
            
            // Get control table dates to skip
            Date newestDate = controlTable.getNewestDate(xmChannel);
            Date oldestDate = controlTable.getOldestDate(xmChannel);
            
            // Check for initialLoad end conditions
            if(startDate.equals(finalEndDate)) {
            	System.out.println("End of twitter history reached");
            	break;
        	// if startDate is between newestDate and oldestDate skip
        	// need to be grabbing stuff older than oldestDate and newer 
        	//		than finalEndDate
            } else if(startDate.before(newestDate) &&
            		startDate.after(oldestDate)) {
            	System.out.println("Already scraped this date range");
            	//continue;
            }
            
            // Open a firefox page to twitter search page
            WebDriver driver = new FirefoxDriver();
            boolean reachedBottom = false;
            String url = "https://twitter.com/search?f=realtime&q=from%3A"+xmChannel+
            		"%20since%3A"+sdf.format(tempEndDate)+
            		"%20until%3A"+sdf.format(startDate)+"&src=typd";
            driver.get(url);
            
            // Check for 1 tweet
            List<WebElement> tweets = driver.findElements(By.className("tweet"));
	        int numTweets = tweets.size();
	        if(numTweets <= 1) {
	        	driver.quit();
	        	System.out.println("    No tweets in this range");
	        	continue;
	        }

	        // Scroll to bottom
	        JavascriptExecutor javascript = (JavascriptExecutor) driver;
	        int scrollCount = 0;
	        while(!reachedBottom) {
	        	javascript.executeScript("window.scrollTo(0, document.body.scrollHeight)", "");
	        	WebElement bottom = driver.findElement(By.className("back-to-top"));            
	        	
	            if(bottom.isDisplayed()) {
	            	reachedBottom = true;
	            }
	            Thread.sleep(500);
	            scrollCount++;
	            
	            if(scrollCount == 200) {
	            	break;
	            }
	        }
	        
	        // Get the full list of the day's tweets
	        tweets = driver.findElements(By.className("tweet"));
	        
	        for(int x=0; x<tweets.size() - 1; x++) {
	        	WebElement tweet = tweets.get(x);
	        	
	        	String song = null;
	        	Date date = null;
	        	String idString = null;
	        	long id = 0;
	        	
	        	song = tweet.findElement(By.className("tweet-text")).getText();
	        	
	        	long dateInMs = Long.parseLong(tweet.findElement(By.className("_timestamp")).getAttribute("data-time-ms"));
	        	date = new Date(dateInMs);
	        	
	        	idString = tweet.getAttribute("data-tweet-id");
	        	id = Long.parseLong(idString);
	        	
	        	// Clean the garbage strings out of the tweet
	        	song = cleanGarbage(song);
		    	
	        	// Check if the tweet is not a song
		    	int atCheck = song.length() - song.replace("@", "").length();
		    	int hashCheck = song.length() - song.replace("#", "").length();
		    	int httpCheck = song.length() - song.replace("http:", "").length();
		    	
		    	song = song.replace("'", "''");
		    	// if the song contains any flagged characters add it to garbage table
		    	if(atCheck > 0 || hashCheck > 0 || httpCheck > 0) {
					garbageTable.addSong(xmChannel, song, date, id);
				// else add it to the XM song table
		    	} else {
					rawXmTable.addSong(xmChannel, song, date, id);
		    	}
		    	
		    	// update the control table		    	
		    	controlTable.update(xmChannel, date);
	        }
	        
	        // close the browser
	        driver.quit();
	        
	        // set startDate to tempEndDate to move 1 day backwards
	        startDate = tempEndDate;
        }
    }
    
	private static String cleanGarbage(String song) {
		
		String split = " playing on ";
    	if(song.contains(split)) {
    		String[] split1 = song.split(split);
    		song = split1[0];
    	}
    	
		String[] garbageStrings = {"#BpmBreaker", "#Debut", "#Ultra2014", "#AandBSXM"};
    	for(String garbage : garbageStrings) {
    		if(song.contains(garbage)) {
    			String garbage1 = " "+garbage+" ";
        		String garbage2 = " "+garbage;
        		String garbage3 = garbage;
        		if (song.contains(garbage1)) {
        			song = song.replace(garbage1, "");
        		} else if (song.contains(garbage2)) {
        			song = song.replace(garbage2, "");
        		} else if (song.contains(garbage3)) {
        			song = song.replace(garbage3, "");
        		} else {
        			song = "ERROR cleaning string: "+garbage;
        		}
    		}
    	}
    	
		return song;
	}
}