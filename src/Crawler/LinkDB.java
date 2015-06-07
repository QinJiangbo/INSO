/**  
 * @description To store the visited links and to be visited links
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 10, 2015 10:43:17 PM
 */

package Crawler;

import java.util.HashSet;
import java.util.Set;

public class LinkDB {
	
	//set of visited links
	private static Set<String> visitedUrl = new HashSet<String>();
	//set of to be visited links
	private static LinkQueue<String> unVisitedUrl = new LinkQueue<String>();
	
	/**
	 * get the unVisited URLs
	 * @return
	 */
	public static LinkQueue<String> getUnVisitedUrl() 
	{
		return unVisitedUrl;
	}
	
	/**
	 * add the URLs which are visited
	 * @param url
	 */
	public static void addVisitedUrl(String url)
	{
		visitedUrl.add(url);
	}
	
	/**
	 * remove the visited URL from the set
	 * @param url
	 */
	public static void removeVisitedUrl(String url)
	{
		visitedUrl.remove(url);
	}
	
	/**
	 * remove the first element of the unVisited link queue
	 * @return the first URL of the queue
	 */
	public static String unVisitedUrlDeQueue()
	{
		return unVisitedUrl.deQueue();
	}
	
	/**
	 * keep each URL visited once
	 * @param url
	 */
	public static void addUnVisitedUrl(String url) {
		
		if((url != null && !url.trim().equals("") && !visitedUrl.contains(url)) && !unVisitedUrl.contains(url))
		{
			//add this URL to the unvisited links set
			unVisitedUrl.enQueue(url);
		}
	}
	
	/**
	 * get the visited URL numbers
	 * @return visited URL numbers
	 */
	public static int getVisitedUrlNum()
	{
		return visitedUrl.size();
	}
	
	/**
	 * to know whether the queue is empty
	 * @return true or false
	 */
	public static boolean unVisitedUrlIsEmpty()
	{
		return unVisitedUrl.isEmpty();
	}
	
	/**
	 * clear all the items in the unVisitedUrl queue
	 */
	public static void clearUnVisitedUrl() 
	{
		unVisitedUrl.clearAll();
	}
	
	/**
	 * clear all the items in the visitedUrl queue
	 */
	public static void clearVisitedUrl() 
	{
		visitedUrl.clear();
	}
}
