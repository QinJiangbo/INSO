/**  
 * @description Filter interface
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 10, 2015 10:44:30 PM
 */

package Crawler;

public interface LinkFilter {
	
	/**
	 * whether the URL accepts the filter condition
	 * @param url
	 * @return true or false
	 */
	public boolean accept(String url);
}
