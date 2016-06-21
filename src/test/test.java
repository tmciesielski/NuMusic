package test;

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
