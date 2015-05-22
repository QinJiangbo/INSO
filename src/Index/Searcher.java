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
import java.util.List;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

import Pojo.Film;
import Util.Config;

@SuppressWarnings({ "deprecation" })
public class Searcher {
	
	/**
	 * search term in different fields in the same index
	 * @param queryPhrase
	 * @return result Hits type
	 */
	public Hits searchXiXiZhanIndex(String queryPhrase) {
		String[] fields = {"Title", "Content"};
		Hits hits = null;
		BooleanClause.Occur[] clauses = {
				BooleanClause.Occur.SHOULD,
				BooleanClause.Occur.SHOULD
		};
		try {
			IndexSearcher searcher = new IndexSearcher(Config.XiXiZhanIndex);
			Query query = MultiFieldQueryParser.parse(queryPhrase, fields, clauses, new MMAnalyzer());
			hits = searcher.search(query, Sort.RELEVANCE);
			//System.out.println(query);
			for(int i = 0; i < hits.length(); i++) {
				Document doc = hits.doc(i);
				String title = highlightWords(query, "Title", doc.get("Title"));
				doc.removeField("Title");
				Field Title = new Field("Title", title, Field.Store.YES, Field.Index.TOKENIZED);
				String content = highlightWords(query, "Content", doc.get("Content"));
				doc.removeField("Content");
				Field Content = new Field("Content", content, Field.Store.YES, Field.Index.TOKENIZED);
				doc.add(Title);
				doc.add(Content);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hits;
	}
	
	/**
	 * search term in different fields in different indexes
	 * @param queryPhrase
	 * @return result Hits type
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List searchMultipleIndexes(String queryPhrase) {
		String[] fields = {"Title", "Content"};
		Hits hits = null;
		BooleanClause.Occur[] clauses = {
				BooleanClause.Occur.SHOULD,
				BooleanClause.Occur.SHOULD
		};
		List result = new ArrayList<Film>();
		try {
			Query query = MultiFieldQueryParser.parse(queryPhrase, fields, clauses, new MMAnalyzer());
			IndexSearcher searcher1 = new IndexSearcher(Config.XiXiZhanIndex);
			IndexSearcher searcher2 = new IndexSearcher(Config.XiXiHDIndex);
			IndexSearcher searcher3 = new IndexSearcher(Config.YifyIndex);
			IndexSearcher searcher4 = new IndexSearcher(Config.BtChinaIndex);
			IndexSearcher searcher5 = new IndexSearcher(Config.BtTianTangIndex);
			IndexSearcher searcher6 = new IndexSearcher(Config.DYTTIndex);
			IndexSearcher searcher7 = new IndexSearcher(Config.DYXZIndex);
			IndexSearcher searcher8 = new IndexSearcher(Config.OurReleaseIndex);
			IndexSearcher searcher9 = new IndexSearcher(Config.ThreeMuIndex);
			IndexSearcher searcher10 = new IndexSearcher(Config.YSIndex);
			IndexSearcher searcher11 = new IndexSearcher(Config.ZiMuKuIndex);
			IndexSearcher searcher12 = new IndexSearcher(Config.YifyMIndex);
			IndexSearcher searcher13 = new IndexSearcher(Config.TorrentbarIndex);
			IndexSearcher searcher14 = new IndexSearcher(Config.DM1080pIndex);
			IndexSearcher searcher15 = new IndexSearcher(Config.ZerodmIndex);
			IndexSearcher searcher16 = new IndexSearcher(Config.DMMIndex);
			IndexSearcher searcher17 = new IndexSearcher(Config.Yify2Index);
			IndexSearcher searcher18 = new IndexSearcher(Config.YifyM2Index);
			IndexSearcher[] searchers = { searcher1, searcher2, searcher3, searcher4, searcher5, searcher6, searcher7, searcher8, searcher9, 
					searcher10, searcher11, searcher12, searcher13, searcher14, searcher15, searcher16, searcher17, searcher18 };
			MultiSearcher searcher = new MultiSearcher(searchers);
			hits = searcher.search(query, Sort.RELEVANCE);
			for(int i = 0; i < hits.length(); i++) {
				Document doc = hits.doc(i);
				Film film = new Film();
				String title = highlightWords(query, "Title", doc.get("Title"));
				String content = highlightWords(query, "Content", doc.get("Content"));
				film.setTitle(title);
				film.setContent(content);
				film.setURL(hits.doc(i).get("URL"));
				film.setPath(hits.doc(i).get("Path"));
				film.setPublishTime(hits.doc(i).get("PublishTime"));
				result.add(film);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * get the number of total records 
	 * @param queryPhrase
	 * @return the number
	 */
	public int getTotalRecords(String queryPhrase) {
		String[] fields = {"Title", "Content"};
		Hits hits = null;
		int length = 0;
		BooleanClause.Occur[] clauses = {
				BooleanClause.Occur.SHOULD,
				BooleanClause.Occur.SHOULD
		};
		try {
			IndexSearcher searcher = new IndexSearcher(Config.XiXiZhanIndex);
			Query query = MultiFieldQueryParser.parse(queryPhrase, fields, clauses, new MMAnalyzer());
			hits = searcher.search(query, Sort.RELEVANCE);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(hits != null) {
			return hits.length();
		}else{
			return length;
		}
		
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
			MMAnalyzer analyzer = new MMAnalyzer();
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
