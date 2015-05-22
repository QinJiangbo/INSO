/**  
 * @description Extract the HTML contents from the pages
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 23, 2015 11:48:58 AM
 */

package Extractor;

import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TextExtractingVisitor;

import Database.IDbHelper;
import Database.IDbHelperImpl;
import Util.Config;

public class KickAssExtractor extends Extractor {

	@Override
	public void extract() {
		
		File htmlDir = new File(Config.KickAssDownloadCache);
		if(!htmlDir.exists()) {
			htmlDir.mkdir();
		}
		File[] fileList = htmlDir.listFiles();
		
		File KickAssExtraction = new File(Config.KickAssExtractionCache);
		if(!KickAssExtraction.exists()) {
			KickAssExtraction.mkdirs();
		}
		
		for(int i = 0; i < fileList.length; i++) {
			String fileName = fileList[i].getName();
			if(!fileExist(Config.KickAssDownloadCache + fileList[i].getName())) {
				System.out.println(Config.KickAssDownloadCache + fileList[i].getName());
				fileName = fileName.substring(0, fileName.length()-5);
				String originalUrl = Config.KickAssURL + fileList[i].getName();
				String filmPath = Config.KickAssDownload + fileList[i].getName();
				if(this.extractContent(Config.KickAssDownloadCache + fileList[i].getName()) != "") {
					String result = originalUrl + "\n" + filmPath + "\n" + this.extractContent(Config.KickAssDownloadCache + fileList[i].getName());
					FileIO.writeFile(Config.KickAssExtractionCache + fileName + ".txt", result);
				}
				else{
					FileIO.deleteFile(Config.KickAssDownloadCache + fileList[i].getName());
				}
			}else {
				System.out.println(Config.KickAssExtractionCache + fileName.substring(0, fileName.length()-5) + ".txt already exist!");
			}
		}
		FileIO.copyFiles(Config.KickAssDownloadCache, Config.KickAssDownload);
		FileIO.deleteFiles(Config.KickAssDownloadCache);
	}
	
	/**
	 * extract main content from the HTML files
	 * @param path
	 * @return extraction contents
	 */
	private String extractContent(String path) {

		String title = "";
		String publishDate = "";
		String content = "";
		
		try {
			Parser parser = new Parser(path);
			parser.setEncoding("utf-8");
			
			//title filter
			NodeFilter titleFilter = new TagNameFilter("h1");
			
			//publish date time filter
			NodeFilter publishFilter = new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "font11px lightgrey line160perc"));
			
			//content filter
			NodeFilter contentFilter = new AndFilter(new TagNameFilter("div"),  new HasAttributeFilter("class", "torrentMediaInfo"));
			
			//movie image filter
			NodeFilter imgFilter = new AndFilter(new TagNameFilter("a"), new HasAttributeFilter("class", "movieCover"));
			
			//extract the movie cover images
			NodeList nodeList = parser.extractAllNodesThatMatch(imgFilter);
			if(nodeList.size() == 0){
				return "";
			}
			parser.reset();
			
			//extract the title information
			nodeList = parser.extractAllNodesThatMatch(titleFilter);
			if(nodeList.size() == 0){
				return "";
			}
			else{
				Node nodeTitle = nodeList.elementAt(0);
				title = nodeTitle.toPlainTextString();
			}
			parser.reset();
			
			//extract the publish date information
			nodeList = parser.extractAllNodesThatMatch(publishFilter);
			if(nodeList.size() == 0){
				return "";
			}
			else{
				Node nodePublish = nodeList.elementAt(0);
				publishDate = nodePublish.toHtml();
				//use regular expression to extract the date and time
				Pattern pattern = Pattern.compile("([a-zA-Z]{1,4})( {1,2})([0-9]{1,2}), ([0-9]{4})");
				Matcher matcher = pattern.matcher(publishDate);
				while(matcher.find()) {
					publishDate = matcher.group();
				}
			}
			parser.reset();
			
			//extract the content information
			nodeList = parser.extractAllNodesThatMatch(contentFilter);
			
			if(nodeList.size() != 0) {
				Node nodeContent = nodeList.elementAt(0);
				//get a new parser to extract the plain text
				Parser contentParser = new Parser(nodeContent.toHtml());
				//create a text extracting visitor object
				TextExtractingVisitor visitor = new TextExtractingVisitor();
				//traverse all the nodes
				contentParser.visitAllNodesWith(visitor);
				//get the content information
				content = visitor.getExtractedText();
			}else {
				content = " ";
			}
			
			return title + "\n" + publishDate + content;
			
		} catch (ParserException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * check whether the file exist
	 * @param path
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean fileExist(String path) {
		String sql = "select count(*) n from kickass where Path = ?";
		Object[] params = {path};
		IDbHelper db = new IDbHelperImpl();
		Map map = db.runSelect(sql, params)[0];
		int n = Integer.parseInt(map.get("n").toString());
		return n == 1;
	}
}
