/**  
 * @description manage the index proceedure
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 16, 2015 4:03:43 PM
 */

package Index;

public class Indexer implements Runnable{

	private int totalCount = 0;
	private int currentNum = 0;
	private int j = 0;
	private FilmIndexer[] indexers = null;
	private int[] sizeArr = new int[18];
	
	public Indexer() {
		String[] names = {"BtChina","BtTianTang","DM1080p","DMM","DYTT","DYXZ","OurRelease","ThreeMu","TorrentBar",
				"XiXiHD","XiXiZhan","Yify","Yify2","YifyM","YifyM2","YS","ZeroDM","ZiMuKu"};
		indexers = IndexerFactory.getBashIndexers(names);
		int len = indexers.length;
		for(int i = 0; i < len; i++) {
			totalCount = totalCount + indexers[i].getSize();
			sizeArr[i] = indexers[i].getSize();
		}
	}
	
	@Override
	public void run() {
		
		int len = indexers.length;
		for(j = 0; j < len; j++) {
			indexers[j].createIndex();
		}
	}
	
	/**
	 * get the current number
	 * @return
	 */
	public int getCurrentNum() {
		if(j == 0) {
			currentNum = indexers[j].getCurrent();
		}
		
		if(j > 0 && j < 18) {
			int total = 0;
			for(int i = 0; i < j; i++) {
				total = total + sizeArr[i];
			}
			currentNum = total + indexers[j].getCurrent();
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
