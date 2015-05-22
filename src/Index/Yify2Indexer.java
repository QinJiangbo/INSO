/**  
 * @description Indexer for www.yify-torrent.org
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 25, 2015 9:15:06 PM
 */

package Index;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import Util.Config;
import Util.IndexCreator;

@SuppressWarnings("deprecation")
public class Yify2Indexer implements IndexCreator {
	/**
	 * create the index for the Yify
	 */
	@SuppressWarnings("rawtypes")
	public void createIndex() {
		try {
			String indexPath = Config.Yify2Index;
			IndexWriter writer = new IndexWriter(indexPath, new MMAnalyzer(), true);
			Map[] results = fetchDataFromDB();
			int i = 0;
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
				System.out.println((++i) + "th record indexed!");
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
	public void deleteYify2Index(String fieldName, String value) {
		try {
			String indexPath = Config.Yify2Index;
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
	@Override
	public Map[] fetchDataFromDB() {
		String sql = "select * from yify2";
		Map[] results = IndexCreator.db.runSelect(sql);
		return results;
	}
}
