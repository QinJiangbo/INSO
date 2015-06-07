/**  
 * @description manage the jdbc proceedure
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 16, 2015 4:04:01 PM
 */

package Database;

public class JDBC implements Runnable{
	
	private int totalCount = 0;
	private int currentNum = 0;
	private int j = 0;
	private FilmJDBC[] jdbcs = null;
	private int[] sizeArr = new int[18];
	
	public JDBC() {
		String[] names = {"BtChina","BtTianTang","DM1080p","DMM","DYTT","DYXZ","OurRelease","ThreeMu","TorrentBar",
				"XiXiHD","XiXiZhan","Yify","Yify2","YifyM","YifyM2","YS","ZeroDM","ZiMuKu"};
		jdbcs = JDBCFactory.getBashJDBCs(names);
		int len = jdbcs.length;
		for(int i = 0; i < len; i++) {
			totalCount = totalCount + jdbcs[i].getSize();
			sizeArr[i] = jdbcs[i].getSize();
		}
	}

	@Override
	public void run() {
		
		int len = jdbcs.length;
		for( j = 0; j < len; j++ ) {
			jdbcs[j].insertDB();
		}
	}
	
	/**
	 * get the current number
	 * @return
	 */
	public int getCurrentNum() {
		if(j == 0) {
			currentNum = jdbcs[j].getCurrent();
		}
		
		if(j > 0 && j < 18) {
			int total = 0;
			for(int i = 0; i < j; i++) {
				total = total + sizeArr[i];
			}
			currentNum = total + jdbcs[j].getCurrent();
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
