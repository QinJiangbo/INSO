/**  
 * @description Extract files from www.bttiantang.com
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 25, 2015 4:18:24 PM
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
import Util.FileIO;

public class YSExtractor extends FilmExtractor {

	private int size = 0;
	public int count = 0;
	
	/**
	 * get the size of the files
	 * @return
	 */
	public int getSize(){
		File htmlDir = new File(Config.YSDownloadCache);
		if(!htmlDir.exists()) {
			htmlDir.mkdir();
		}
		File[] fileList = htmlDir.listFiles();
		this.size = fileList.length;
		return this.size;
	}
	
	@Override
	public void extract() {
		File htmlDir = new File(Config.YSDownloadCache);
		if(!htmlDir.exists()) {
			htmlDir.mkdir();
		}
		File[] fileList = htmlDir.listFiles();
		
		File YSExtraction = new File(Config.YSExtractionCache);
		if(!YSExtraction.exists()) {
			YSExtraction.mkdir();
		}
		
		for(int i = 0; i < fileList.length; i++) {
			String fileName = fileList[i].getName();
			if(!fileExist(Config.YSDownloadCache + fileList[i].getName())) {
				System.out.println(Config.YSDownloadCache + fileList[i].getName());
				fileName = fileName.substring(0, fileName.length()-4);
				String originalUrl = (Config.YSURL + fileList[i].getName()).replaceAll("_", "/");
				String filmPath = Config.YSDownload + fileList[i].getName();
				if(this.extractContent(Config.YSDownloadCache + fileList[i].getName()) != "") {
					String result = originalUrl + "\n" + filmPath + "\n" + this.extractContent(Config.YSDownloadCache + fileList[i].getName());
					FileIO.writeFile(Config.YSExtractionCache + fileName + ".txt", result);
				}
				else {
					FileIO.deleteFile(Config.YSDownloadCache + fileList[i].getName());
				}
			}else {
				System.out.println(Config.YSExtractionCache + fileName.substring(0, fileName.length()-5) + ".txt already exist!");
			}
			count = count + 1;
		}
		
		FileIO.copyFiles(Config.YSDownloadCache, Config.YSDownload);
		FileIO.deleteFiles(Config.YSDownloadCache);
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
			NodeFilter titleFilter = new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "title"));
			
			//publish date time filter
			NodeFilter publishFilter = new AndFilter(new TagNameFilter("table"), new HasAttributeFilter("width", "91%"));
			
			//content filter
			NodeFilter contentFilter = new AndFilter(new TagNameFilter("td"),  new HasAttributeFilter("id", "dede_content"));
			
			//extract the title information
			NodeList nodeList = parser.extractAllNodesThatMatch(titleFilter);
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
			else {
				Node nodePublish = nodeList.elementAt(0);
				publishDate = nodePublish.toHtml();
				//use regular expression to extract the date and time
				Pattern pattern = Pattern.compile("([0-9]{4})-([0-9]{1,2})-([0-9]{1,2})");
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
				content = visitor.getExtractedText().trim();
			}else {
				content = " ";
			}
			
			return title + "\n" + publishDate+ "\n" + content;
			
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
		String sql = "select count(*) n from ys where Path = ?";
		Object[] params = {path};
		IDbHelper db = new IDbHelperImpl();
		Map map = db.runSelect(sql, params)[0];
		int n = Integer.parseInt(map.get("n").toString());
		return n == 1;
	}
	
	@Override
	public int getCurrent() {
		return this.count;
	}
}