/**  
 * @description product class for film jdbcs
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date May 23, 2015 12:39:24 AM
 */

package Database;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import Util.Config;
import Util.FetchData;
import Util.FileIO;

public class FilmJDBC {
	
	private int size = 0;
	private int count = 0;
	
	IDbHelper db = new IDbHelperImpl();
	private String name = "";
	
	public FilmJDBC(String name){
		this.name = name;
	}
	/**
	 * insert data of henbt.com to the database
	 */
	@SuppressWarnings("rawtypes")
	public void insertDB() {
		String[] paths = getExtractionPaths(this.name);
		String cachePath = paths[0];
		String path = paths[1];
		File dir = new File(cachePath);
		if(dir.exists()) {
			File[] fileList = dir.listFiles();
			int length = fileList.length;
			for(int i = 0; i < length; i++) {
				Map data = FetchData.fetchData(cachePath + fileList[i].getName());
				if(!URLExist(data.get("URL").toString())) {
					String sql = "insert into "+ this.name +"(Title, Path, URL, Content, PublishTime, UpdateTime) values(?,?,?,?,?,?)";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Object[] params = { data.get("Title"), data.get("Path"), data.get("URL"), data.get("Content"), data.get("PublishTime"), sdf.format(new Date())};
					this.db.runUpdate(sql, params);
					System.out.println((i+1)+"th row inserted!");
				}else{
					System.out.println("The URL " + data.get("URL").toString() + " already inserted!");
				}
				count = count + 1;
			}
			copyFiles(cachePath, path);
			FileIO.deleteFiles(cachePath);
		}
	}
	
	/**
	 * Fetch the URLs from the database
	 * @return map of URLs
	 */
	@SuppressWarnings("rawtypes")
	public Map[] selectURL() {
		String sql = "select URL from " + this.name;
		Map[] rows = this.db.runSelect(sql);
		return rows;
	}
	
	/**
	 * delete the records stored in the table
	 */
	public void deleteRecord() {
		String sql = "delete from " + this.name;
		this.db.runUpdate(sql);
		System.out.println("Delete successfully!");
	}
	
	/**
	 * check the whether the URL exist in the database
	 * @param URL
	 * @return true or false
	 */
	@SuppressWarnings("rawtypes")
	public boolean URLExist(String URL) {
		//long start = System.currentTimeMillis();
		String sql = "select count(*) n from "+ this.name +" where URL = ?";
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
			Map map = FetchData.fetchData(srcDir + fileName);
			String data = map.get("URL").toString()+"\n"+map.get("Path").toString()+"\n"+map.get("Title").toString()+"\n"+map.get("PublishTime").toString()+"\n"+map.get("Content").toString();
			FileIO.writeFile(destDir + fileName, data);
		}
	}
	
	/**
	 * get the cache paths based on the name
	 * @param name
	 * @return a String array of path
	 */
	public String[] getExtractionPaths(String name) {
		String[] paths = new String[2];
		String cachePath = "";
		String path = "";
		switch(name){
		case "BtChina":
			cachePath = Config.BtChinaExtractionCache;
			path = Config.BtChinaExtraction;
			break;
		case "BtTianTang":
			cachePath = Config.BtTianTangExtractionCache;
			path = Config.BtTianTangExtraction;
			break;
		case "DM1080p":
			cachePath = Config.DM1080pExtractionCache;
			path = Config.DM1080pExtraction;
			break;
		case "DMM":
			cachePath = Config.DMMExtractionCache;
			path = Config.DMMExtraction;
			break;
		case "DYTT":
			cachePath = Config.DYTTExtractionCache;
			path = Config.DYTTExtraction;
			break;
		case "DYXZ":
			cachePath = Config.DYXZExtractionCache;
			path = Config.DYXZExtraction;
			break;
		case "OurRelease":
			cachePath = Config.OurReleaseExtractionCache;
			path = Config.OurReleaseExtraction;
			break;
		case "ThreeMu":
			cachePath = Config.ThreeMuExtractionCache;
			path = Config.ThreeMuExtraction;
			break;
		case "TorrentBar":
			cachePath = Config.TorrentbarExtractionCache;
			path = Config.TorrentbarExtraction;
			break;
		case "XiXiHD":
			cachePath = Config.XiXiHDExtractionCache;
			path = Config.XiXiHDExtraction;
			break;
		case "XiXiZhan":
			cachePath = Config.XiXiZhanExtractionCache;
			path = Config.XiXiZhanExtraction;
			break;
		case "Yify":
			cachePath = Config.YifyExtractionCache;
			path = Config.YifyExtraction;
			break;
		case "Yify2":
			cachePath = Config.Yify2ExtractionCache;
			path = Config.Yify2Extraction;
			break;
		case "YifyM":
			cachePath = Config.YifyMExtractionCache;
			path = Config.YifyMExtraction;
			break;
		case "YifyM2":
			cachePath = Config.YifyM2ExtractionCache;
			path = Config.YifyM2Extraction;
			break;
		case "YS":
			cachePath = Config.YSExtractionCache;
			path = Config.YSExtraction;
			break;
		case "ZeroDM":
			cachePath = Config.ZerodmExtractionCache;
			path = Config.ZerodmExtraction;
			break;
		case "ZiMuKu":
			cachePath = Config.ZiMuKuExtractionCache;
			path = Config.ZiMuKuExtraction;
			break;
		}
		
		paths[0] = cachePath;
		paths[1] = path;
		
		return paths;
	}
	
	/**
	 * get the size of the files
	 * @return
	 */
	public int getSize(){
		String[] paths = getExtractionPaths(this.name);
		String cachePath = paths[0];
		File dir = new File(cachePath);
		if(!dir.exists()){
			dir.mkdir();
		}
		File[] fileList = dir.listFiles();
		this.size = fileList.length;
		return this.size;
	}
	
	/**
	 * get the current num
	 * @return
	 */
	public int getCurrent() {
		return this.count;
	}
}
