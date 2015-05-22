/**  
 * @description select urls from multiple tables
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 30, 2015 12:24:38 PM
 */

package Util;

import java.util.Map;

public interface DBOperator {
	
	/**
	 * select url from tables
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public abstract Map[] selectURL();
	
	/**
	 * insert into database
	 */
	public abstract void insertDB();
}
