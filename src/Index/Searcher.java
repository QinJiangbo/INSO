/**  
 * @description search in the indexes
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 17, 2015 10:38:08 PM
 */

package Index;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKQueryParser;
import org.wltea.analyzer.lucene.IKSimilarity;


public class Searcher {
	
	public Analyzer analyzer = new IKAnalyzer();
	private FilmIndexer indexer = new FilmIndexer();
	public int totalRecords = 0;
	
	/**
	 * search term in different fields in different indexes
	 * @param queryPhrase
	 * @return result Hits type
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List searchMultipleIndexes(String queryPhrase) {
		String[] fields = {"Title", "Content"};
		BooleanClause.Occur[] clauses = {
				BooleanClause.Occur.SHOULD,
				BooleanClause.Occur.SHOULD
		};
		List result = new ArrayList<Map>();
		try {
			//IKQueryParser to parser mutli-field query
			Query query = IKQueryParser.parseMultiField(fields, 
					queryPhrase, clauses);
			
			String[] names = {"BtChina","BtTianTang","DM1080p", 
							  "DMM","DYTT","DYXZ",
							  "OurRelease","ThreeMu","TorrentBar",
							  "XiXiHD","XiXiZhan","YS",
							  "Yify2","YifyM","YifyM2",
							  "ZeroDM","ZiMuKu","Yify"};
			int len = names.length;
			String indexPath = "";
			IndexSearcher[] searchers = new IndexSearcher[len];
			for(int i = 0; i < len; i++) {
				indexPath = indexer.getIndexPath(names[i]);
				searchers[i] = new IndexSearcher(indexPath);
				searchers[i].setSimilarity(new IKSimilarity());
			}
			
			MultiSearcher searcher = new MultiSearcher(searchers);
			//set the similarity of the searcher
			searcher.setSimilarity(new IKSimilarity());
			//here we think the most relevant records limit inside 2000 records
			TopDocs topDocs = searcher.search(query.weight(searcher), null, 2000);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			this.totalRecords = scoreDocs.length;
			for(int i = 0; i < this.totalRecords; i++) {
				Document doc = searcher.doc(scoreDocs[i].doc);
				Map map = new HashMap();
				String title = highlightWords(query, "Title", doc.get("Title"));
				String content = highlightWords(query, "Content", doc.get("Content"));
				map.put("Title", title);
				map.put("Content", content);
				map.put("URL", doc.get("URL"));
				map.put("Path", doc.get("Path"));
				map.put("PublishTime", doc.get("PublishTime"));
				result.add(map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * get the number of total records 
	 * @return the number
	 */
	public int getTotalRecords() {
		return this.totalRecords;
	}
	
	
	/**
	 * Highlighter the keywords in the results
	 * @param query query string
	 * @param fieldName the field name of the highlighter
	 * @param text the orignal text of the results
	 * @return text highlighted
	 */
	public String highlightWords(Query query, String fieldName, String text) {
		QueryScorer queryScorer = new QueryScorer(query);
		Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
		Highlighter highlighter = new Highlighter(formatter, queryScorer);
		highlighter.setTextFragmenter(new SimpleFragmenter(text.length()));
		String highlightText;
		try {
			TokenStream tokenStream = analyzer.tokenStream(fieldName, new StringReader(text));
			highlightText = highlighter.getBestFragment(tokenStream, text);
			if(highlightText != null){
				return highlightText;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}
	
}
