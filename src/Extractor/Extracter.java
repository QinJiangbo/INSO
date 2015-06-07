/**  
 * @description manage the extraction of the HTML files
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 16, 2015 4:14:03 PM
 */

package Extractor;


public class Extracter implements Runnable{

	private int totalCount = 0;
	private int currentNum = 0;
	private int j = 0;
	private FilmExtractor[] extractors = null;
	private int[] sizeArr = new int[18];
	
	public Extracter() {
		String[] names = {"BtChina","BtTianTang","DM1080p","DMM","DYTT","DYXZ","OurRelease","ThreeMu","TorrentBar",
				"XiXiHD","XiXiZhan","Yify","Yify2","YifyM","YifyM2","YS","ZeroDM","ZiMuKu"};
		extractors = ExtractorFactory.getBashExtractors(names);
		int len = extractors.length;
		for(int i = 0; i < len; i++) {
			totalCount = totalCount + extractors[i].getSize();
			sizeArr[i] = extractors[i].getSize();
		}
	}

	@Override
	public void run() {
		
		int len = extractors.length;
		for(j = 0; j < len; j++ ) {
			extractors[j].extract();
		}
	}
	
	/**
	 * get the currenr number
	 * @return
	 */
	public int getCurrentNum() {
		if(j == 0) {
			currentNum = extractors[j].getCurrent();
		}
		
		if(j > 0 && j < 18) {
			int total = 0;
			for(int i = 0; i < j; i++) {
				total = total + sizeArr[i];
			}
			currentNum = total + extractors[j].getCurrent();
		}
		
		return currentNum;
	}

	/**
	 * get all the records
	 * @return
	 */
	public int getTotalCount() {
		return this.totalCount;
	}
}
