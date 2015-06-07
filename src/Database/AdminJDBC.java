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
	 * @return Map[] type results
	 */
	@SuppressWarnings("rawtypes")
	public Map[] getLogs(){
		String sql = "select * from hotsearch";
		Map[] result = this.db.runSelect(sql);
		return result;
	}
	
	/**
	 * delete the log records
	 * @param keyword
	 * @param city
	 * @param type
	 */
	public void deleteLog(String keyword, String city, String type) {
		String sql = "delete from hotsearch where keyword = ? and city = ? and type = ?";
		Object[] params = { keyword, city, type };
		this.db.runUpdate(sql, params);
	}
	
	/**
	 * sort the results by fields
	 * @return sorted Map[] type results
	 */
	@SuppressWarnings("rawtypes")
	public Map[] sortLogsByField(String field) {
		String sql = "select * from hotsearch order by "+ field +" desc";
		Map[] result = this.db.runSelect(sql);
		return result;
	}
	
	/**
	 * get all the admin users
	 * @return Map[] type results
	 */
	@SuppressWarnings("rawtypes")
	public Map[] getUsers() {
		String sql = "select * from admin";
		Map[] result = this.db.runSelect(sql);
		return result;
	}
	
	/**
	 * delete the user by the name
	 * @param name
	 */
	public void deleteUser(String name) {
		String sql = "delete from admin where userName = ?";
		Object[] params = { name };
		this.db.runUpdate(sql, params);
	}
	
	/**
	 * add the user
	 * @param params
	 */
	public void addUser(Object[] params) {
		String sql = "insert into admin(userName, userPassword, email, telephone, userLevel) values(?,?,?,?,?)";
		this.db.runUpdate(sql, params);
	}
	
	/**
	 * judge whether the user exists
	 * @param name
	 * @return bool type value
	 */
	@SuppressWarnings("rawtypes")
	public boolean existedUser(String name) {
		String sql = "select count(*) n from admin where userName = ?";
		Object[] params = { name };
		Map map = this.db.runSelect(sql, params)[0];
		int n = Integer.parseInt(map.get("n").toString());
		return n == 1;
	}
	
	/**
	 * get the user level
	 * @param name
	 * @return user level
	 */
	@SuppressWarnings("rawtypes")
	public int getLevel(String name) {
		String sql = "select userLevel from admin where userName = ?";
		Object[] params = { name };
		Map map = this.db.runSelect(sql, params)[0];
		int level = Integer.parseInt(map.get("userLevel").toString());
		return level;
	}
	
	/**
	 * update the user information
	 * @param params
	 */
	public void modifyUser(Object[] params) {
		String sql = "update admin set userPassword = ? , email = ? , telephone = ? , userLevel = ? where userName = ?";
		this.db.runUpdate(sql, params);
	}
	
	/**
	 * get the user's information
	 * @param name
	 * @return user information
	 */
	@SuppressWarnings("rawtypes")
	public Map getUserInfo(String name) {
		String sql = "select * from admin where userName = ?";
		Object[] params = { name };
		Map map = this.db.runSelect(sql, params)[0];
		return map;
	}
	
	/**
	 * select with the input SQL statement
	 * @param sql
	 * @return result map array
	 */
	@SuppressWarnings("rawtypes")
	public Map[] selectWithSQL(String sql) {
		Map[] result = this.db.runSelect(sql);
		return result;
	}
	
	/**
	 * update with the input SQL statement
	 * @param sql
	 */
	public void updateWithSQL(String sql) {
		this.db.runUpdate(sql);
	}
	
	/**
	 * sort the table by updatetime
	 * @param tableName
	 * @return the results
	 */
	@SuppressWarnings("rawtypes")
	public Map[] sortTable(String tableName, String limitCondition) {
		String sql = "select * from " + tableName + " order by updatetime desc limit " + limitCondition;
		Map[] results = this.db.runSelect(sql);
		return results;
	}
	
	/**
	 * delete the records based on the url
	 * @param tableName
	 * @param url
	 */
	public void deleteRecords(String tableName, String url) {
		String sql = "delete from " + tableName + " where url = " + url;
		this.db.runUpdate(sql);
	}
	
	/**
	 * get the records of the specified table
	 * @param tableName
	 * @return the records
	 */
	@SuppressWarnings("rawtypes")
	public Map[] getRecords(String tableName) {
		String sql = "select * from " + tableName + " limit 2000";
		Map[] results = this.db.runSelect(sql);
		return results;
	}
}
