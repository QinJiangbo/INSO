/**  
 * @description manage the extraction of the HTML files
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 16, 2015 4:14:03 PM
 */

package Engine;

import Extractor.BtChinaExtractor;
import Extractor.BtTianTangExtractor;
import Extractor.DM1080pExtractor;
import Extractor.DMMExtractor;
import Extractor.DYTTExtractor;
import Extractor.DYXZExtractor;
import Extractor.Extractor;
import Extractor.OurReleaseExtractor;
import Extractor.ThreeMuExtractor;
import Extractor.TorrentbarExtractor;
import Extractor.XiXiHDExtractor;
import Extractor.XiXiZhanExtractor;
import Extractor.YSExtractor;
import Extractor.Yify2Extractor;
import Extractor.YifyExtractor;
import Extractor.YifyM2Extractor;
import Extractor.YifyMExtractor;
import Extractor.ZerodmExtractor;
import Extractor.ZiMuKuExtractor;

public class StartExtract {

	public static void main(String[] args) {
		Extractor extractor1 = new XiXiZhanExtractor();
		Extractor extractor2 = new XiXiHDExtractor();
		Extractor extractor3 = new YifyExtractor();
		Extractor extractor4 = new OurReleaseExtractor();
		Extractor extractor5 = new BtTianTangExtractor();
		Extractor extractor6 = new YSExtractor();
		Extractor extractor7 = new DYTTExtractor();
		Extractor extractor8 = new BtChinaExtractor();
		Extractor extractor9 = new ThreeMuExtractor();
		Extractor extractor10 = new DYXZExtractor();
		Extractor extractor11 = new ZiMuKuExtractor();
		Extractor extractor12 = new YifyMExtractor();
		Extractor extractor13 = new TorrentbarExtractor();
		Extractor extractor15 = new DMMExtractor();
		Extractor extractor14 = new DM1080pExtractor();
		Extractor extractor16 = new ZerodmExtractor();
		Extractor extractor17 = new Yify2Extractor();
		Extractor extractor18 = new YifyM2Extractor();
		
		Object[] extractors = { extractor1, extractor2, extractor3, extractor4, extractor5, extractor6,
								extractor7, extractor8, extractor9, extractor10, extractor11, extractor12,
								extractor13, extractor14, extractor15, extractor16, extractor17, extractor18 };
		
		for(Object o: extractors) {
			Extractor extractor = (Extractor) o;
			extractor.extract();
		}
	}

}
