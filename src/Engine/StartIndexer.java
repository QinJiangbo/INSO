/**  
 * @description manage the index proceedure
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 16, 2015 4:03:43 PM
 */

package Engine;

import Index.BtChinaIndexer;
import Index.BtTianTangIndexer;
import Index.DM1080pIndexer;
import Index.DMMIndexer;
import Index.DYTTIndexer;
import Index.DYXZIndexer;
import Index.OurReleaseIndexer;
import Index.ThreeMuIndexer;
import Index.TorrentbarIndexer;
import Index.XiXiHDIndexer;
import Index.XiXiZhanIndexer;
import Index.Yify2Indexer;
import Index.YifyIndexer;
import Index.YSIndexer;
import Index.YifyM2Indexer;
import Index.YifyMIndexer;
import Index.ZerodmIndexer;
import Index.ZiMuKuIndexer;
import Util.IndexCreator;

public class StartIndexer {

	public static void main(String[] args) {
		XiXiZhanIndexer indexer1 = new XiXiZhanIndexer();
		XiXiHDIndexer indexer2 = new XiXiHDIndexer();
		YifyIndexer indexer3 = new YifyIndexer();
		OurReleaseIndexer indexer4 = new OurReleaseIndexer();
		BtTianTangIndexer indexer5 = new BtTianTangIndexer();
		YSIndexer indexer6 = new YSIndexer();
		DYTTIndexer indexer7 = new DYTTIndexer();
		BtChinaIndexer indexer8 = new BtChinaIndexer();
		ThreeMuIndexer indexer9 = new ThreeMuIndexer();
		DYXZIndexer indexer10 = new DYXZIndexer();
		ZiMuKuIndexer indexer11 = new ZiMuKuIndexer();
		YifyMIndexer indexer12 = new YifyMIndexer();
		TorrentbarIndexer indexer13 = new TorrentbarIndexer();
		DM1080pIndexer indexer14 = new DM1080pIndexer();
		DMMIndexer indexer15 = new DMMIndexer();
		ZerodmIndexer indexer16 = new ZerodmIndexer();
		YifyM2Indexer indexer17 = new YifyM2Indexer();
		Yify2Indexer indexer18 = new Yify2Indexer();
		
		Object[] indexers = { indexer1, indexer2, indexer3, indexer4, indexer5, indexer6, 
							  indexer7, indexer8, indexer9, indexer10, indexer11, indexer12,
							  indexer13, indexer14, indexer15, indexer16, indexer17, indexer18 };
//		Object[] indexers = { indexer3,indexer6, indexer12, indexer13};
		
		for(Object o : indexers) {
			IndexCreator index = (IndexCreator) o;
			index.createIndex();
		}
	}

}
