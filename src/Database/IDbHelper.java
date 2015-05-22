/**  
 * @description Interface for the database operation
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 12, 2015 16:09:01 PM
 */

package Database;

import java.util.Map;

@SuppressWarnings("rawtypes")
public interface IDbHelper {

	//run the select SQL with parameters
	public Map[] runSelect(String sql,Object[] params);
	//run the select SQL without parameters
	public Map[] runSelect(String sql);
	//run the update SQL without parameters
	public void runUpdate(String sql);
	//run the update SQL with parameters
	public void runUpdate(String sql,Object[] params);
}
