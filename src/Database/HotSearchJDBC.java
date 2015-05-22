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
	public boolean isExist(String keyword) {
		String sql = "select count(*) n from hotsearch where keyword like '%"+ keyword +"%'";
		Map result = this.db.runSelect(sql)[0];
		int n = Integer.parseInt(result.get("n").toString());
		return n == 1;
	}
	
	/**
	 * update the times of the keyword
	 * @param keyword
	 */
	public void updateTimes(String keyword) {
		String sql = "update hotsearch set times = times + 1 where keyword like '%"+ keyword +"%'";
		this.db.runUpdate(sql);
	}
	
	/**
	 * insert the keyword into the database
	 * @param keyword
	 */
	public void insertKeyword(String keyword) {
		String sql = "insert into hotsearch(keyword, times, updatetime) values(?,1,?)";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String updateDate = sdf.format(new Date());
		Object[] params = {keyword, updateDate};
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
