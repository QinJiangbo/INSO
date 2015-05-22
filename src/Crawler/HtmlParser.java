/**  
 * @description 
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 10, 2015 10:45:00 PM
 */

package Crawler;

import java.util.HashSet;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlParser {
	
	/**
	 * get a link from the web site, Filter used to filter the links
	 * @param url
	 * @param filter
	 * @return links set got from the web site
	 */
	@SuppressWarnings("unused")
	public static Set<String> extractLinks(String url, LinkFilter filter) {
		
		//links set
		Set<String> links = new HashSet<String>();
		
		try{
			//get a parser instance by URL
			Parser parser = new Parser(url);
			parser.setEncoding("utf-8");
			
			//filter <frame> tags, used to extract links of the src attributes in frame tag
			NodeFilter frameFilter = new NodeFilter() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public boolean accept(Node node) {
					
					if(node.getText().startsWith("iframe") && node.getText().contains("src=")) {
						return true;
					}
					else {
						return false;
					}
				}
			};
			
			//OrFilter to set the filters of <a> and <frame> tag
			OrFilter linkFilter = new OrFilter(new NodeClassFilter(LinkTag.class), frameFilter);
			
			//get all filtered tags
			NodeList nodeList = parser.extractAllNodesThatMatch(linkFilter);
			for(int i = 0; i < nodeList.size(); i++) {
				Node tag = nodeList.elementAt(i);
				
				//<a> tag
				if( tag instanceof LinkTag)
				{
					LinkTag link = (LinkTag) tag;
					String linkUrl = link.getLink(); //URL
					String text = link.getLinkText(); //Get link text
					if(filter.accept(linkUrl)) {
						links.add(linkUrl);
					}
				}
//				//<frame> tag
//				else
//				{
//					//extract the links of the src attributes in frame eg. <frame src="Index.html" />
//					String frame = tag.getText();
//					int start = frame.indexOf("src=\"");
//					//System.out.println("start index is " + start);
//					frame = frame.substring(start);
//					int end = frame.indexOf(" ");
//					if(end == -1) {
//						end = frame.indexOf("\">");
//					}
//					String frameUrl = frame.substring(5, end-1);
//					if(filter.accept(frameUrl)) {
//						links.add(frameUrl);
//					}
//					
//				}
			}
		}catch (ParserException ex) {
			ex.printStackTrace();
		}
		
		return links;
	}
}
