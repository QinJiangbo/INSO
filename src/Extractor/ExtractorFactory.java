/**  
 * @description factory class for creating extractor objects
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date May 23, 2015 6:21:38 PM
 */

package Extractor;

public class ExtractorFactory {
	
	/**
	 * get the extractor object of the film
	 * @param name
	 * @return
	 */
	public static FilmExtractor getFilmExtractor(String name) {
		//uprising type objects
		FilmExtractor extractor = null;
		switch(name){
		case "BtChina":
			extractor = new BtChinaExtractor();
			break;
		case "BtTianTang":
			extractor = new BtTianTangExtractor();
			break;
		case "DM1080p":
			extractor = new DM1080pExtractor();
			break;
		case "DMM":
			extractor = new DMMExtractor();
			break;
		case "DYTT":
			extractor = new DYTTExtractor();
			break;
		case "DYXZ":
			extractor = new DYXZExtractor();
			break;
		case "OurRelease":
			extractor = new OurReleaseExtractor();
			break;
		case "ThreeMu":
			extractor = new ThreeMuExtractor();
			break;
		case "TorrentBar":
			extractor = new TorrentbarExtractor();
			break;
		case "XiXiHD":
			extractor = new XiXiHDExtractor();
			break;
		case "XiXiZhan":
			extractor = new XiXiZhanExtractor();
			break;
		case "Yify":
			extractor = new YifyExtractor();
			break;
		case "Yify2":
			extractor = new Yify2Extractor();
			break;
		case "YifyM":
			extractor = new YifyMExtractor();
			break;
		case "YifyM2":
			extractor = new YifyM2Extractor();
			break;
		case "YS":
			extractor = new YSExtractor();
			break;
		case "ZeroDM":
			extractor = new ZerodmExtractor();
			break;
		case "ZiMuKu":
			extractor = new ZiMuKuExtractor();
			break;
		}
		
		return extractor;
	}
	
	/**
	 * get the bash extractors of the films
	 * @param names a set of names
	 * @return an array of extratcor objects
	 */
	public static FilmExtractor[] getBashExtractors(String[] names) {
		int length = names.length;
		FilmExtractor[] extractors = new FilmExtractor[length];
		for(int i = 0; i < length; i++) {
			FilmExtractor extractor = getFilmExtractor(names[i]);
			extractors[i] = extractor;
		}
		return extractors;
	}
}
