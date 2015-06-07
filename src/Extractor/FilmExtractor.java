/**  
 * @description Extractor base class for extracting contents from the HTML files
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 11, 2015 2:38:23 PM
 */

package Extractor;

public abstract class FilmExtractor {
	
	/**
	 * extract the content of the files
	 */
	public abstract void extract();
	
	/**
	 * get the size of the extraction
	 * @return
	 */
	public abstract int getSize();
	
	/**
	 * get the current number of extraction
	 */
	public abstract int getCurrent();
}
