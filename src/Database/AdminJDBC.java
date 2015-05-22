/**  
 * @description This is class mainly used for the management of the administrators
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 21, 2015 9:03:11 AM
 */

package Database;

import java.util.Map;

public class AdminJDBC {
	
	IDbHelper db = new IDbHelperImpl();
	
	/**
	 * validate the admin users
	 * @param userName
	 * @param userPassword
	 * @return the boolean result true(legal) or false(illegal)
	 */
	@SuppressWarnings("rawtypes")
	public boolean isUserLegal(String userName, String userPassword) {
		String sql = "select count(*) n from admin where userName =? and userPassword = ?";
		Object[] params = {userName, userPassword};
		Map result = this.db.runSelect(sql, params)[0];
		int n = Integer.parseInt(result.get("n").toString());
		return n == 1;
	}
	
	/**
	 * change the password based on the user name of the admin
	 * @param userName
	 * @param newPassword
	 */
	public void modifyPassword(String userName, String newPassword) {
		String sql = "update admin set userPassword = ? where userName = ?";
		Object[] params = {newPassword, userName};
		this.db.runUpdate(sql, params);
	}
	
	/**
	 * get the level of the administrator
	 * @param userName
	 * @param userPassword
	 * @return the level of the admin
	 */
	@SuppressWarnings("rawtypes")
	public int getLevel(String userName, String userPassword) {
		String sql = "select userLevel from admin where userName =? and userPassword = ?";
		Object[] params = {userName, userPassword};
		Map result = this.db.runSelect(sql, params)[0];
		int n = Integer.parseInt(result.get("userLevel").toString());
		return n;
	}
	
	/**
	 * get the logs from the hotsearch table
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map[] getLogs(){
		String sql = "select * from hotsearch";
		Map[] result = this.db.runSelect(sql);
		return result;
	}
}
