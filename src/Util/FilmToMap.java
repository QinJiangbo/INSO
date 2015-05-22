/**  
 * @description Transfer the film object to map
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 20, 2015 8:22:47 AM
 */

package Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Pojo.Film;

public class FilmToMap {
	
	/**
	 * convert the film list to map list
	 * @param filmList
	 * @return the map list
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List Convert(ArrayList<Film> filmList) {
		List mapList = new ArrayList<Map>();
		for(int i = 0; i < filmList.size(); i++) {
			Map map = new HashMap();
			map.put("Title", filmList.get(i).getTitle());
			map.put("URL", filmList.get(i).getURL());
			map.put("Path", filmList.get(i).getPath());
			map.put("PublishTime", filmList.get(i).getPublishTime());
			map.put("Content", filmList.get(i).getContent());
			//System.out.println(filmList.get(i).getURL());
			mapList.add(map);
		}
		
		return mapList;
	}
}
