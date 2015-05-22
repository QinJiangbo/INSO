/**  
 * @description create a result model to store the result
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 17, 2015 12:43:15 PM
 */

package Pojo;

import org.apache.lucene.document.Document;

public class ResultModel {
	
	int pageNum;
	Document data;
	
	public Document getData() {
		return data;
	}
	public void setData(Document data) {
		this.data = data;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
}
