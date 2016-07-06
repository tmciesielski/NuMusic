package executable;

import internetFunctions.FillMusicTable;
import internetFunctions.GetYoutubeLinks;
import internetFunctions.BpmTwitterScraper;

public class IncrementalLoad {

	public static void main(String[] args) throws Exception {
		
		// XM twitter scraper [done]
		//BpmTwitterScraper.scrapeSongInstances();
		
		// XM play Count consolidater [done]
		FillMusicTable.consolidateSongInstances();
		
		// Song name to Youtube converter [done]
		GetYoutubeLinks.convertSongNames();
		
		// Youtube song downloader [not started]
		
		// Ranked playlist creator [testing]

	}

}
