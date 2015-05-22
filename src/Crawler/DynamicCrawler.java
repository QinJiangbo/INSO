/**  
 * @description Dynamic cralwer mainly used for dynamic websites
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 22, 2015 5:00:38 PM
 */

package Crawler;

import java.util.HashMap;
import java.util.Map;

import Database.BtTianTangJDBC;
import Database.DMMJDBC;
import Database.XiXiHDJDBC;

public class DynamicCrawler {
	/**
	 * downloader for downloading html files
	 */
	DownLoader downloader = new DownLoader();
	XiXiHDJDBC xxhdjdbc = new XiXiHDJDBC();
	BtTianTangJDBC btttjdbc = new BtTianTangJDBC();
	DMMJDBC dmmjdbc = new DMMJDBC();
	
	/**
	 * crawl the dynamic websites
	 * @param sitesName
	 * @param sitesPrefix
	 * @param sitesLength
	 */
	@SuppressWarnings("rawtypes")
	public void crawl(String sitesName, String sitesPrefix, int startIndex, int lastIndex) {
		Map map = getAllURLs(sitesName);
		String url = null;
		for(int i = startIndex; i <= lastIndex; i++) {
			url = sitesPrefix + i + ".html";
			if(map.containsValue(url)) {
				System.out.println(url + " exists!");
			} else {
				downloader.downloadHtml(url);
			}
		}
	}
	
	/**
	 * get all the URLs from the specific site
	 * @param sitesName
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	private Map getAllURLs(String sitesName) {
		Map urls = new HashMap();
		switch(sitesName){
		case "www.xixihd.com": 
			urls = getXiXiHDURLs();
			break;
		case "www.bttiantang.com":
			urls = getBtTianTangURLs();
			break;
		case "d.dmm.hk":
			urls = getDMMURLs();
			break;
		}
		
		return urls;
	}
	
	/**
	 * Get the urls from the database of d.dmm.hk
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map getDMMURLs() {
		Map[] URLs = this.dmmjdbc.selectURL();
		Map urls = new HashMap();
		for(int i = 0; i < URLs.length ; i++) {
			urls.put(i, URLs[i].get("URL").toString());
		}
		return urls;
	}

	/**
	 * Get the urls from the database of www.bttiantang.com
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map getBtTianTangURLs() {
		Map[] URLs = this.btttjdbc.selectURL();
		Map urls = new HashMap();
		for(int i = 0; i < URLs.length ; i++) {
			urls.put(i, URLs[i].get("URL").toString());
		}
		return urls;
	}

	/**
	 * Get the urls from the database of www.xixihd.com
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map getXiXiHDURLs() {
		Map[] URLs = this.xxhdjdbc.selectURL();
		Map urls = new HashMap();
		for(int i = 0; i < URLs.length ; i++) {
			urls.put(i, URLs[i].get("URL").toString());
		}
		return urls;
	}
	
	public void getSpecificUrl(String url) {
		downloader.downloadHtml(url);
	}
	
}
