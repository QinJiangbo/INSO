/**  
 * @description Fetch data from the extractor files
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 15, 2015 7:03:31 PM
 */

package Extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class FetchData {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	/**
	 * fecth data from the XiXiZhan file folder
	 * @param fileName
	 * @return Map type object
	 * @throws IOException
	 */
	public static Map fetchDataXiXiZhan(String fileName){
		Map result = new HashMap();
		String s = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
			s = br.readLine();
			result.put("URL", s);
			s = br.readLine();
			result.put("Path", s);
			s = br.readLine();
			result.put("Title", s);
			s = br.readLine();
			result.put("PublishTime", s);
			String content = "";
			while((s = br.readLine()) != null) {
				content += s;
				s = br.readLine();
			}
			result.put("Content", content);
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	/**
	 * fecth data from the XiXiHD file folder
	 * @param fileName
	 * @return Map type object
	 * @throws IOException
	 */
	public static Map fetchDataXiXiHD(String fileName){
		Map result = new HashMap();
		String s = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
			s = br.readLine();
			result.put("URL", s);
			s = br.readLine();
			result.put("Path", s);
			s = br.readLine();
			result.put("Title", s);
			s = br.readLine();
			result.put("PublishTime", s);
			String content = "";
			while((s = br.readLine()) != null) {
				content += s;
				s = br.readLine();
			}
			result.put("Content", content);
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
