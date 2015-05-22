/**  
 * @description start the crawler
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 16, 2015 10:44:30 PM
 */

package Engine;

import java.util.Map;

import Util.Config;
import Crawler.DynamicCrawler;
import Crawler.Crawler;

@SuppressWarnings("unused")
public class StartCrawler {

	
	public static void main(String[] args) {
		Crawler crawler = new Crawler();
//		DynamicCrawler crawler2 = new DynamicCrawler();
//		crawler2.getSpecificUrl("http://dm1080p.com/archives/9417");
//		String[] seeds = { Config.BtChinaURL, Config.BtTianTangURL, Config.DYTTURL, Config.OurReleaseURL, 
//						   Config.ZiMuKuURL, Config.YSURL, Config.DYXZURL, Config.ThreeMuURL, Config.XiXiHDURL, Config.XiXiZhanURL,
//						   Config.YifyMURL, Config.YifyURL, Config.DM1080pURL, Config.DMMURL, Config.ZerodmURL, Config.TorrentbarURL };
		String[] seeds = { Config.YifyM2URL, Config.Yify2URL, Config.YSURL};
		crawler.crawl(seeds);
//		crawler2.crawl("d.dmm.hk", "http://dmxz.89dm.com/xiazai/", 1, 2100);
//		crawler2.getSpecificUrl("http://www.dyxz.org/thread-38170-1-1.html");
		
		//boolean flag = false && false && true;
		//System.out.println(flag);
	}
}
