/**  
 * @description JDBC methods for www.xixihd.com
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 21, 2015 6:59:16 PM
 */

package Database;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import Extractor.FetchData;
import Extractor.FileIO;
import Util.Config;
import Util.DBOperator;

public class XiXiHDJDBC implements DBOperator {
	
	IDbHelper db = new IDbHelperImpl();
	
	/**
	 * insert data of www.xixihd.com to the database
	 */
	@SuppressWarnings("rawtypes")
	public void insertDB() {
		String path = Config.XiXiHDExtractionCache;
		File dir = new File(path);
		if(dir.exists()) {
			File[] fileList = dir.listFiles();
			
			for(int i = 0; i < fileList.length; i++) {
				Map data = FetchData.fetchDataXiXiHD(path + fileList[i].getName());
				if(!XiXiHDURLExist(data.get("URL").toString())) {
					String sql = "insert into xixihd(Title, Path, URL, Content, PublishTime, UpdateTime) values(?,?,?,?,?,?)";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Object[] params = {data.get("Title"), data.get("Path"), data.get("URL"), data.get("Content"), data.get("PublishTime"), sdf.format(new Date())};
					this.db.runUpdate(sql, params);
					System.out.println((i+1)+"th row inserted!");
				}else{
					System.out.println("The URL " + data.get("URL").toString() + " already inserted!");
				}
			}
			copyFiles(Config.XiXiHDExtractionCache, Config.XiXiHDExtraction);
			FileIO.deleteFiles(Config.XiXiHDExtractionCache);
		}
	}
	
	/**
	 * Fetch the URLs from the database
	 * @return map of URLs
	 */
	@SuppressWarnings("rawtypes")
	public Map[] selectURL() {
		String sql = "select URL from xixihd";
		Map[] rows = this.db.runSelect(sql);
		return rows;
	}
	
	/**
	 * delete the records stored in the xixihd table
	 */
	public void deleteXiXiHDRecord() {
		String sql = "delete from xixihd";
		this.db.runUpdate(sql);
		System.out.println("Delete successfully!");
	}
	
	/**
	 * check the whether the URL exist in the database
	 * @param URL
	 * @return true or false
	 */
	@SuppressWarnings("rawtypes")
	public boolean XiXiHDURLExist(String URL) {
		//long start = System.currentTimeMillis();
		String sql = "select count(*) n from xixihd where URL = ?";
		Object[] params = { URL };
		Map map = this.db.runSelect(sql, params)[0];
		int n = Integer.parseInt(map.get("n").toString());
		//long end = System.currentTimeMillis();
		//System.out.println("Search costs " + (end - start) + " milliseconds!");
		return n == 1;
	}
	
	/**
	 * Copy files from source directory to destination directory
	 * @param srcDir source directory
	 * @param destDir destination directory
	 */
	@SuppressWarnings("rawtypes")
	public static void copyFiles(String srcDir, String destDir) {
		File src = new File(srcDir);
		File[] files = src.listFiles();
		for(int i = 0; i < files.length; i++) {
			String fileName = files[i].getName();
			Map map = FetchData.fetchDataXiXiHD(srcDir + fileName);
			String data = map.get("URL").toString()+"\n"+map.get("Path").toString()+"\n"+map.get("Title").toString()+"\n"+map.get("PublishTime").toString()+"\n"+map.get("Content").toString();
			FileIO.writeFile(destDir + fileName, data);
		}
	}
}
