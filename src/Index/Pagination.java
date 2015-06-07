/**  
 * @description paginate the results
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 18, 2015 12:52:27 AM
 */

package Index;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pagination {
	
	public static final int PAGESIZE = 10;
	public static boolean hasNextPage = true;
	public static boolean hasPreviousPage = true;
	
	/**
	 * Get a interval of the data in the Hits result
	 * @param hits the Hits result
	 * @param startIndex start position
	 * @param endIndex end position
	 * @return the group of data in the interval
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List processHits(List hits, int startIndex, int endIndex) {
		if(endIndex >= hits.size()) {
			endIndex = hits.size();
		}
		ArrayList filmList = new ArrayList<Map>();
		for(int i = startIndex; i < endIndex; i++) {
			Map map = (Map) hits.get(i);
			filmList.add(map);
		}
		return filmList;
	}
	
	/**
	 * Get the results of the pagination
	 * @param hits the total results of the search
	 * @param currentPage current page number
	 * @param totalCount the total number of the results
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List paginate(List hits, int currentPage, int totalCount) {
		int startIndex = PAGESIZE * ( currentPage - 1 );
		int endIndex = Math.min(startIndex + PAGESIZE, totalCount);
		return processHits(hits, startIndex, endIndex);
	}
	
	/**
	 * Go to next page if it does exist
	 * @param hits the results
	 * @param currentPage current page number
	 * @param totalCount total numbers of the results
	 * @return the results of the next page
	 */
	@SuppressWarnings("rawtypes")
	public List nextPage(List hits, int currentPage, int totalCount) {
		if(currentPage >= getTotalPages(totalCount)) {
			hasNextPage = false;
			currentPage = getTotalPages(totalCount);
		}
		else {
			currentPage = currentPage + 1;
		}
		return processHits(hits, currentPage, totalCount);
	}
	
	/**
	 * Go to previous page if it does exist
	 * @param hits the results
	 * @param currentPage current page number
	 * @param totalCount total numbers of the results
	 * @return the results of the previous page
	 */
	@SuppressWarnings("rawtypes")
	public List previousPage(List hits, int currentPage, int totalCount) {
		if(currentPage <= 1) {
			hasPreviousPage = false;
			currentPage = 1;
		}
		else {
			currentPage = currentPage - 1;
		}
		return processHits(hits, currentPage, totalCount);
	}
	
	/**
	 * Get the number of total pages of the results
	 * @param totalCount the total results' number
	 * @return the number of total pages
	 */
	public int getTotalPages(int totalCount) {
		int totalPages = 0;
		if(totalCount % PAGESIZE == 0) {
			totalPages = totalCount / PAGESIZE;
		}
		else {
			totalPages = totalCount / PAGESIZE + 1;
		}
		return totalPages;
	}
}
