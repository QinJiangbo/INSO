/**  
 * @description Static crawler used to get static HTML data from the static websites
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 10, 2015 10:42:57 PM
 */

package Crawler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Util.DBOperator;
import Database.BtChinaJDBC;
import Database.BtTianTangJDBC;
import Database.DM1080pJDBC;
import Database.DMMJDBC;
import Database.DYTTJDBC;
import Database.DYXZJDBC;
import Database.KickAssJDBC;
import Database.OurReleaseJDBC;
import Database.ThreeMuJDBC;
import Database.TorrentbarJDBC;
import Database.XiXiHDJDBC;
import Database.XiXiZhanJDBC;
import Database.YSJDBC;
import Database.Yify2JDBC;
import Database.YifyJDBC;
import Database.YifyM2JDBC;
import Database.YifyMJDBC;
import Database.ZerodmJDBC;
import Database.ZiMuKuJDBC;

public class Crawler {
	
	XiXiZhanJDBC xxzjdbc = new XiXiZhanJDBC();
	XiXiHDJDBC xxhdjdbc = new XiXiHDJDBC();
	KickAssJDBC kajdbc = new KickAssJDBC();
	OurReleaseJDBC orjdbc = new OurReleaseJDBC();
	BtTianTangJDBC btjdbc = new BtTianTangJDBC();
	YSJDBC ysjdbc = new YSJDBC();
	DYTTJDBC dyttjdbc = new DYTTJDBC();
	BtChinaJDBC btchinajdbc = new BtChinaJDBC();
	ThreeMuJDBC tmjdbc = new ThreeMuJDBC();
	DYXZJDBC dyxzjdbc = new DYXZJDBC();
	ZiMuKuJDBC zmkjdbc = new ZiMuKuJDBC();
	YifyJDBC yifyjdbc = new YifyJDBC();
	YifyMJDBC yifymjdbc = new YifyMJDBC();
	Yify2JDBC yify2jdbc = new Yify2JDBC();
	YifyM2JDBC yifym2jdbc = new YifyM2JDBC();
	TorrentbarJDBC torrentbarjdbc = new TorrentbarJDBC();
	DMMJDBC dmmjdbc = new DMMJDBC();
	DM1080pJDBC dm1080pjdbc = new DM1080pJDBC();
	ZerodmJDBC zerodmjdbc = new ZerodmJDBC();
	
	/**
	 * initialize the crawler with seeds
	 * @param seeds
	 */
	private void init(String[] seeds) {
		
		for(int i = 0; i < seeds.length; i++)
		{
			LinkDB.addUnVisitedUrl(seeds[i]);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void crawl(String[] seeds) {
		
		LinkFilter filter = new LinkFilter() {

			@Override
			public boolean accept(String url) {
				if(FilterCondition.XiXiZhanFilm(url)
						|| FilterCondition.XiXiHDFilm(url)
						|| FilterCondition.OurReleaseFilm(url)
						|| FilterCondition.BtTianTangFilm(url)
						|| FilterCondition.YSFilm(url)
						|| FilterCondition.DYTTFilm(url)
						|| FilterCondition.BtChinaFilm(url)
						|| FilterCondition.ThreeMuFilm(url)
						|| FilterCondition.DYXZFilm(url)
						|| FilterCondition.ZiMuKuFilm(url)
						|| FilterCondition.YifyFilm(url)
						|| FilterCondition.YifyMFilm(url)
						|| FilterCondition.Yify2Film(url)
						|| FilterCondition.YifyM2Film(url)
						|| FilterCondition.TorrentbarFilm(url)
						|| FilterCondition.DM1080pFilm(url)
						|| FilterCondition.ZerodmFilm(url)
						|| FilterCondition.DMMFilm(url))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		};
		
		//initialize the queue
		init(seeds);
		
		//add all urls into the visited list
		Map URLs = getAllURLs();
		for(int i = 0; i < URLs.size(); i++) {
			LinkDB.addVisitedUrl(URLs.get(i).toString());
		}
		
		int limit = URLs.size() + 20000;
		//loop condition: links to be grasped are not null and the maximum value of web page is 20000
		while(!LinkDB.unVisitedUrlIsEmpty() && LinkDB.getVisitedUrlNum()<=limit)
		{
			//queue head out
			String visitUrl = (String) LinkDB.unVisitedUrlDeQueue();
			if(visitUrl == null)
			{
				continue;
			}
			else{
				//download the web pages from the Internet
				DownLoader downloader = new DownLoader();
				boolean flag = visitUrl.contains("thread-")  //xixizhan, dyxz
						|| visitUrl.contains("content-") //xixihd
						|| (visitUrl.endsWith(".html") && visitUrl.contains("/torrent/") && !visitUrl.contains("list_")) //ourrelease
						|| visitUrl.contains("/subject/") //bttiantang
						|| (!visitUrl.contains("/index") && visitUrl.endsWith(".htm")) //66ys[dygang] 
						|| ((visitUrl.contains("/i/") || (visitUrl.contains("/html/"))) && !visitUrl.contains("/index") && visitUrl.endsWith(".html")) //dy2018
						|| (visitUrl.contains("show") && !visitUrl.contains("index")) //henbt
						|| (visitUrl.contains("UFODY") && visitUrl.contains(".html")) //3mu
						|| (visitUrl.contains("detail") && visitUrl.contains(".html")) //zimuku
						|| (visitUrl.endsWith(".html") && visitUrl.contains("-yify-torrent")) //yify-torrent.org
						|| (visitUrl.endsWith(".html") && visitUrl.contains("/movies/") && visitUrl.contains("yify-torrents.tv")) //yify-torrents.tv
						|| (visitUrl.endsWith(".html") && visitUrl.contains("/movies/") && visitUrl.contains("-yify-")) //yify-movies
						|| (visitUrl.endsWith(".html") && visitUrl.contains("/movies/") && visitUrl.contains("-movie-download")) //torrentbar
						|| (visitUrl.contains("/movie/")) //yify-movie.com
						|| (visitUrl.contains("/archives/") && !visitUrl.contains("/page/") && !visitUrl.contains("/tag/") && !visitUrl.contains("/search/") && !visitUrl.contains("/category/") && !visitUrl.contains("/author/")) //dm1080p
						|| (visitUrl.contains("/xiazai/") && !visitUrl.contains("_") && !visitUrl.contains("/list/") //89dm
						|| (visitUrl.contains("/v/") && visitUrl.endsWith(".html")));//dmm
				if(flag)
				{
					downloader.downloadHtml(visitUrl);
				}
				
				//put the visited URL into visited URL queue
				LinkDB.addVisitedUrl(visitUrl);
				
				//extract the URLs from the download HTML files
				Set<String> links = HtmlParser.extractLinks(visitUrl, filter);
				
				//new unVisited URL into queue
				for(String link: links)
				{
					//System.out.println(link);
					LinkDB.addUnVisitedUrl(link);
				}
			}
		}
		
	}
	
	/**
	 * get all URLs from the database
	 * @return URLs
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public Map getAllURLs() {
		Object[] JDBCs = { xxzjdbc, xxhdjdbc, kajdbc, orjdbc, btjdbc, ysjdbc, dyttjdbc, 
						   btchinajdbc, tmjdbc, dyxzjdbc, zmkjdbc, yifyjdbc, yifymjdbc,
						   torrentbarjdbc,dmmjdbc, dm1080pjdbc, zerodmjdbc, yify2jdbc, yifym2jdbc };
		Map Results = new HashMap();
		int count = 0;
		
		for(Object o: JDBCs) {
			DBOperator selector = (DBOperator) o;
			Map[] urls = selector.selectURL();
			for(int i = 0; i < urls.length; i++) {
				Results.put(count, urls[i].get("URL").toString());
				count++;
			}
		}
		return Results;
	}
}
