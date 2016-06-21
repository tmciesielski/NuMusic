package test;

import java.io.File;
import java.io.IOException;

/*
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import database.MusicDB;
import tables.ControlTable;
import tables.RawXmTable;

public class test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
    	// Get database connections
		Connection conn = MusicDB.getConnection();
		ControlTable controlTable = new ControlTable(conn);
		
    	Date startDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Get today's date
        c.add(Calendar.DATE, -1); // Start day = 1 day ago
        startDate = c.getTime();
        
        String source = "bpm_playlist";
        Date newDate = controlTable.getNewestDate(source);
        System.out.println("Newest date 1: "+sdf.format(newDate));
		
		controlTable.update("bpm_playlist", startDate);
		
		newDate = controlTable.getNewestDate(source);
		System.out.println("Newest date 2: "+sdf.format(newDate));

	}

}
*/


import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
public class test {

    	public static void main(String[]args)
        {
    		Document document = null;
			try {
				document = Jsoup.connect("https://www.youtube.com/results?search_query=cedric+gervais+feat+jack+wilby+-+with+you").get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Elements currentData = document.select("h3.yt-lockup-title > a");
			int results = currentData.size();
			
			Elements playCount = document.select("ul.yt-lockup-meta-info > li");
			int result = playCount.size();
			System.out.println(result);
			
			for (int i=0; i<results; i++) {
				System.out.println(i);
				String stuff = playCount.get(i).childNode(0).toString();
				System.out.println(stuff);
			}
			
			/*
			if(results != 0) {
				for (int i=0; i<results; i++) {
					String link = currentData.get(i).attr("href");
					String title = currentData.get(i).attr("title");
					if(link.contains("list")) {
						System.out.println("ERROR: First Youtube link is a list of videos");
						continue;
					} else if(link.contains("user")){
						System.out.println("ERROR: First Youtube link is a user account");
						continue;
					}
					else {
						System.out.println(title + " " + link);
						break;
					}	
				}
			}
			*/
        }

    	
    }




