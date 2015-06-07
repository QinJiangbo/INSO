/**  
 * @description film indexer class for indexing films
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date May 23, 2015 5:16:26 PM
 */

package Index;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.wltea.analyzer.lucene.IKAnalyzer;

import Database.IDbHelper;
import Database.IDbHelperImpl;
import Util.Config;

public class FilmIndexer {
	
	private int size = 0;
	private int count = 0;
	
	private IDbHelper db = new IDbHelperImpl();
	private String name = "";
	
	public Analyzer analyzer = new IKAnalyzer();
	
	public FilmIndexer() {
		//no argu constructor
	}
	
	public FilmIndexer(String name) {
		this.name = name;
	}
	
	/**
	 * create the index 
	 */
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public void createIndex() {
		try {
			String indexPath = getIndexPath(this.name);
			IndexWriter writer = new IndexWriter(indexPath, analyzer, true);
			Map[] results = fetchDataFromDB();
			for(Map data : results) {
				Document doc = new Document();
				Field URL = new Field("URL", data.get("URL").toString(), Field.Store.YES, Field.Index.UN_TOKENIZED);
				Field Path = new Field("Path", data.get("Path").toString(), Field.Store.YES, Field.Index.NO);
				Field Title = new Field("Title", data.get("Title").toString(), Field.Store.YES, Field.Index.TOKENIZED);
				Field PublishTime = new Field("PublishTime", data.get("PublishTime").toString(), Field.Store.YES, Field.Index.NO);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Field UpdateTime = new Field("UpdateTime", sdf.format(new Date()), Field.Store.YES, Field.Index.NO);
				Field Content = new Field("Content", data.get("Content").toString(), Field.Store.YES, Field.Index.TOKENIZED);
				
				doc.add(URL);
				doc.add(Path);
				doc.add(Title);
				doc.add(PublishTime);
				doc.add(UpdateTime);
				doc.add(Content);
				
				writer.addDocument(doc);
				count = count + 1;
			}
			//optimize the index
			writer.optimize();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * delete the document by the field
	 * @param fieldName field name
	 * @param value field value
	 */
	public void deleteIndex(String fieldName, String value) {
		try {
			String indexPath = getIndexPath(this.name);
			IndexReader reader = IndexReader.open(indexPath);
			
			Term term = new Term(fieldName, value);
			reader.deleteDocuments(term);
			reader.close();
			System.out.println("Delete successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	/**
	 * get the data from the correspond database
	 * @return
	 */
	public Map[] fetchDataFromDB() {
		String sql = "select * from " + this.name;
		Map[] results = this.db.runSelect(sql);
		return results;
	}
	
	/**
	 * get the index path by the name
	 * @param name indexer name
	 * @return the index path of the film
	 */
	public String getIndexPath(String name){
		String indexPath = "";
		switch(name){
		case "BtChina":
			indexPath = Config.BtChinaIndex;
			break;
		case "BtTianTang":
			indexPath = Config.BtTianTangIndex;
			break;
		case "DM1080p":
			indexPath = Config.DM1080pIndex;
			break;
		case "DMM":
			indexPath = Config.DMMIndex;
			break;
		case "DYTT":
			indexPath = Config.DYTTIndex;
			break;
		case "DYXZ":
			indexPath = Config.DYXZIndex;
			break;
		case "OurRelease":
			indexPath = Config.OurReleaseIndex;
			break;
		case "ThreeMu":
			indexPath = Config.ThreeMuIndex;
			break;
		case "TorrentBar":
			indexPath = Config.TorrentbarIndex;
			break;
		case "XiXiHD":
			indexPath = Config.XiXiHDIndex;
			break;
		case "XiXiZhan":
			indexPath = Config.XiXiZhanIndex;
			break;
		case "Yify":
			indexPath = Config.YifyIndex;
			break;
		case "Yify2":
			indexPath = Config.Yify2Index;
			break;
		case "YifyM":
			indexPath = Config.YifyMIndex;
			break;
		case "YifyM2":
			indexPath = Config.YifyM2Index;
			break;
		case "YS":
			indexPath = Config.YSIndex;
			break;
		case "ZeroDM":
			indexPath = Config.ZerodmIndex;
			break;
		case "ZiMuKu":
			indexPath = Config.ZiMuKuIndex;
			break;
		}
		
		return indexPath;
	}
	
	/**
	 * get the size of the files
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int getSize(){
		String sql = "select count(*) n from " + this.name;
		Map map = this.db.runSelect(sql)[0];
		this.size = Integer.parseInt(map.get("n").toString());
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
