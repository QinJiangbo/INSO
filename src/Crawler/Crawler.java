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

import Database.FilmJDBC;
import Database.JDBCFactory;

public class Crawler implements Runnable{
	
	private String[] seeds = null;
	private volatile boolean isRunning = true;
	public int downloadNum = 0;
	
	public Crawler(){
		//initialize the queue
		LinkDB.clearUnVisitedUrl();
		LinkDB.clearVisitedUrl();
	}
	
	String[] names = {"BtChina","BtTianTang","DM1080p","DMM","DYTT","DYXZ","OurRelease","ThreeMu","TorrentBar",
			"XiXiHD","XiXiZhan","Yify","Yify2","YifyM","YifyM2","YS","ZeroDM","ZiMuKu"};
	
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
	@Override
	public void run() {
			
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
		
		int limit = URLs.size() + 2000;
		//loop condition: links to be grasped are not null and the maximum value of web page is 20000
		while(isRunning && !LinkDB.unVisitedUrlIsEmpty() && LinkDB.getVisitedUrlNum()<=limit)
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
					this.downloadNum++;
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
		FilmJDBC[] jdbcs = JDBCFactory.getBashJDBCs(names);
		Map Results = new HashMap();
		int count = 0;
		
		for(FilmJDBC jdbc: jdbcs) {
			Map[] urls = jdbc.selectURL();
			for(int i = 0; i < urls.length; i++) {
				Results.put(count, urls[i].get("URL").toString());
				count++;
			}
		}
		return Results;
	}
	
	/**
	 * stop the crawler
	 */
	public void stopCrawl() {
		this.isRunning = false;
	}
	
	/**
	 * set the seeds
	 * @param seeds
	 */
	public void setSeeds(String[] seeds) {
		this.seeds = seeds;
	}
}
