/**  
 * @description pojo class for http://www.xixizhan.com
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 18, 2015 7:02:03 PM
 */

package Pojo;

public class Film {
	private String Title;
	private String Path;
	private String URL;
	private String Content;
	private String PublishTime;
	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getPath() {
		return Path;
	}
	public void setPath(String path) {
		Path = path;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getPublishTime() {
		return PublishTime;
	}
	public void setPublishTime(String publishTime) {
		PublishTime = publishTime;
	}
}
