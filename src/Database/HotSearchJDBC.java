/**  
 * @description hot search jdbc is mainly used to count the times of a search keyword
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date May 5, 2015 7:59:56 PM
 */

package Database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class HotSearchJDBC {
	
	IDbHelper db = new IDbHelperImpl();
	
	/**
	 * judge whether the keyword does exist in the database
	 * @param keyword
	 * @return exist for true, nonexist for false
	 */
	@SuppressWarnings("rawtypes")
	public boolean isExist(String keyword, String city, String type) {
		String sql = "select count(*) n from hotsearch where keyword = ? and city = ? and type = ?";
		Object[] params = { keyword, city, type };
		Map result = this.db.runSelect(sql, params)[0];
		int n = Integer.parseInt(result.get("n").toString());
		return n != 0;
	}
	
	/**
	 * update the times of the keyword
	 * @param keyword
	 */
	public void updateTimes(String keyword, String city, String type) {
		String sql = "update hotsearch set times = times + 1 where keyword = ? and city = ? and type = ?";
		Object[] params = { keyword, city, type };
		this.db.runUpdate(sql, params);
	}
	
	/**
	 * insert the keyword into the database
	 * @param keyword
	 */
	public void insertKeyword(String keyword, String province, String city, String type) {
		String sql = "insert into hotsearch(keyword, times, province, city, type, updatetime) values(?,1,?,?,?,?)";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String updateDate = sdf.format(new Date());
		Object[] params = {keyword, province, city, type, updateDate};
		this.db.runUpdate(sql, params);
	}
	
	/**
	 * get the hot keywords from the database
	 * @return hot keywords
	 */
	@SuppressWarnings("rawtypes")
	public Map[] getHotKeyword() {
		String sql = "select keyword from hotsearch where Updatetime > DATE_SUB(CURDATE(), INTERVAL 1 WEEK) order by times desc limit 5";
		Map[] result = this.db.runSelect(sql);
		return result;
	}
}
