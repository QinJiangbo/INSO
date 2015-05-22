/**  
 * @description Conditions for the HTML filters
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 11, 2015 8:29:20 PM
 */

package Crawler;

public class FilterCondition {
	
	/**
	 * Filter for the films in www.xixizhan.com
	 * @param url
	 * @return true or false
	 */
	public static boolean XiXiZhanFilm(String url) {
		return url.startsWith("http://www.xixizhan.com")
					&& (url.endsWith(".html")
					&& (!url.contains("#")
					&& (!url.contains("?")
					&& (!url.contains("space-")))));
	}
	
	public static boolean XiXiHDFilm(String url) {
		return url.startsWith("http://www.xixihd.com")
					&& (!url.contains("mod=bbs")
					&& (!url.contains("user")
					&& (!url.contains("mod=download")
					&& (!url.contains("mod=content")
					&& (!url.endsWith("#"))))));
	}
	
	public static boolean KickAssFilm(String url) {
		return url.startsWith("http://kickass.to")
					&& (!url.contains("#")
					&& (!url.contains("rss")
					&& (!url.contains("search")
					&& (!url.contains("community")
					&& (!url.contains("faq")
					&& (!url.contains("blog")
					&& (!url.contains("auth")
					&& (!url.contains(".txt")))))))));
	}
	
	public static boolean OurReleaseFilm(String url) {
		return url.startsWith("http://www.ourrelease.org/torrent")
					&& (!url.contains("#")
					&& (!url.contains("contacts")
					&& (!url.contains("about")
					&& (!url.contains("Privacy-Policy")
					&& (!url.contains("DMCA")
					&& (!url.contains("thankyou")
					&& (!url.contains("tvshow"))))))));
	}
	
	public static boolean BtTianTangFilm(String url) {
		return url.startsWith("http://www.bttiantang.com")
					&& (!url.contains("#")
					&& (!url.contains("jumpto")));
	}
	
	public static boolean YSFilm(String url) {
		return url.startsWith("http://www.dygang.com")
					&& (!url.contains("#")
					&& (!url.contains("tool")
					&& (!url.contains("help")
					&& (!url.contains("search")
					&& (!url.contains("SCR"))))));
	}
	
	public static boolean DYTTFilm(String url) {
		return url.startsWith("http://www.dy2018.com")
					&& (!url.contains("support")
					&& (!url.contains("#")
					&& (!url.contains("sitemap"))));
	}
	
	public static boolean BtChinaFilm(String url) {
		return url.startsWith("http://henbt.com")
					&& (!url.contains("#")
					&& (!url.contains("search")
					&& (!url.contains("user")
					&& (!url.contains("down")
					&& (!url.contains("rss"))))));
	}
	
	public static boolean ThreeMuFilm(String url) {
		return url.startsWith("http://www.3mu.cc")
					&& (!url.contains("#")
					&& (!url.contains("gbook")
					&& (!url.contains("pan")
					&& (!url.contains("search")))));
	}
	
	public static boolean DYXZFilm(String url) {
		return url.startsWith("http://www.dyxz.org")
					&& (!url.contains("member")
					&& (!url.contains("space-")
					&& (!url.contains("forum-7-1")
					&& (!url.contains("thread-1-1-1")
					&& (!url.contains("thread-38100-1-1")
					&& (!url.contains("thread-29282-1-1")
					&& (!url.contains("thread-29282-2-1")
					&& (!url.contains("thread-29282-3-1")
					&& (!url.contains("mobile=yes")
					&& (!url.contains("archiver")
					&& (!url.contains("showdarkroom")
					&& (!url.contains("rss")
					&& (!url.contains("#")
					&& (!url.contains("search")
					&& (!url.contains("?mod="))))))))))))))));
	}
	
	public static boolean ZiMuKuFilm(String url) {
		return url.startsWith("http://www.zimuku.net")
					&& (!url.contains("user")
					&& (!url.contains("#")));
	}
	
	public static boolean YifyFilm(String url) {
		return url.startsWith("http://www.yify-torrent.org")
					&& (!url.contains("contact.html")
					&& (!url.contains("sitemap.html")
					&& (!url.contains("#"))));
	}
	
	public static boolean Yify2Film(String url) {
		return url.startsWith("http://yify-torrents.tv")
					&& (!url.contains("#"));
	}
	
	public static boolean YifyMFilm(String url) {
		return url.startsWith("http://www.yify-movies.net")
					&& (!url.contains("help.html")
					&& (!url.contains("#")));
	}
	
	public static boolean YifyM2Film(String url) {
		return url.startsWith("http://yify-movie.com")
					&& (!url.contains("watch")
					&& (!url.contains("#")
					&& (!url.contains("blog")
					&& (!url.contains("contact")
					&& (!url.contains("images"))))));
	}
	
	public static boolean TorrentbarFilm(String url) {
		return url.startsWith("http://www.torrentbar.com")
					&& (!url.contains("sitemap.html")
					&& (!url.contains("#")
					&& (!url.contains("tos.html"))));
	}
	
	public static boolean DM1080pFilm(String url) {
		return url.startsWith("http://dm1080p.com")
					&& (!url.contains("#")
					&& (!url.contains("?action")
					&& (!url.contains("5594")
					&& (!url.contains("动漫1080p-投稿教程")))));
	}
	
	public static boolean ZerodmFilm(String url) {
		return url.startsWith("http://dmxz.89dm.com")
					&& (!url.contains("wenti")
					&& (!url.contains("hudong")
					&& (!url.contains("Help")
					&& (!url.contains("Rss")
					&& (!url.contains("#"))))));
	}
	
	public static boolean DMMFilm(String url) {
		return url.startsWith("http://d.dmm.hk")
					&& (!url.contains("gbook")
					&& (!url.contains("help")
					&& (!url.contains("search")
					&& (!url.contains("chat")
					&& (!url.contains("#"))))));
	}
}
