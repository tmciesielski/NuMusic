package executable;

import internetFunctions.BpmTwitterScraper;
import internetFunctions.FillMusicTable;
import internetFunctions.GetYoutubeLinks;
import internetFunctions.YoutubeToMp3;

public class InitialLoad {

	public static void main(String[] args) throws Exception {
		
		// XM twitter scraper [done]
		//BpmTwitterScraper.initialLoad();
		
		// XM play Count consolidater [done]
		//FillMusicTable.consolidateSongInstances();
		
		// Song name to Youtube converter [done]
		//GetYoutubeLinks.convertSongNames();
		
		// Youtube song downloader [testing]
		YoutubeToMp3.downloadSongs();
		
		// Ranked playlist creator [testing]

	}

}
