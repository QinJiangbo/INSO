/**  
 * @description create index for the data
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 30, 2015 1:27:37 PM
 */

package Util;

import java.util.Map;

import Database.IDbHelper;
import Database.IDbHelperImpl;

public interface IndexCreator {
	
	public static IDbHelper db = new IDbHelperImpl();
	
	/**
	 * create index
	 */
	public abstract void createIndex();
	
	/**
	 * fetch data from Database
	 */
	@SuppressWarnings("rawtypes")
	public abstract Map[] fetchDataFromDB();
}
