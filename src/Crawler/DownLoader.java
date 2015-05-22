/**  
 * @description Download the HTML files based on URL
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 9, 2015 11:02:24 PM
 */

package Crawler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;

import Util.Config;

public class DownLoader {
	
	private String htmlFolder = null;
	private final String PrefixPath = Config.DownLoadPath;
	
	/**
	 * Get the file name by URL
	 * @param url
	 * @return file name
	 */
	public String getFileNameByUrl(String url) {
		String name = null;
		name = url.substring(7); //remove http://
		int index = name.indexOf('/');
		if(index != -1) {
			//get the htmlFolder name
			htmlFolder = name.substring(0, index);
			name = name.substring(index+1);
		}else {
			htmlFolder = name;
		}
		File folder = new File(PrefixPath + htmlFolder + "\\cache\\");
		if(!folder.exists()) {
			folder.mkdirs(); //create the directories recursively
		}
		//replace unnecessary symbols
		name = name.replaceAll("[\\?/:*|<>\"]", "_");
		index = name.indexOf(".htm");
		if(index == -1){
			name = name + ".html";
		}
		return PrefixPath + htmlFolder + "\\cache\\" + name;
	}
	
	/**
	 * Save file to local disk
	 * @param data
	 * @param filePath
	 */
	private void saveLocation(InputStream data, String filePath) {
		try{
			File result = new File(filePath);
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(result));
			BufferedInputStream in = new BufferedInputStream(data);
			
			int nextByte = 0; //the next byte to be read
			while((nextByte = in.read()) != -1) {
				output.write((byte) nextByte);
			}
			
			//close the stream
			in.close();
			output.close();
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Download html file based on the URL
	 * @param url
	 * @return path of the html file
	 */
	public String downloadHtml(String url) {
		
		String filePath = null;
		
		HttpRequestRetryHandler hanlder = new HttpRequestRetryHandler() {

			@Override
			public boolean retryRequest(IOException arg0, int tryTimes,
					HttpContext arg2) {
				//Try 5 times, or we define it fails
				if(tryTimes > 1) {
					return false;
				}
				return true;
			}
		};
		
		//build the HTTP client
		CloseableHttpClient httpClient = HttpClients.custom().setRetryHandler(hanlder).build();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).build();
		HttpGet httpGet = new HttpGet(url);
		
		httpGet.setConfig(requestConfig);
		
		//build the HTTP response
		CloseableHttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			
			if(statusLine.getStatusCode() != HttpStatus.SC_OK) {
				System.err.println("Getting data fails: " + statusLine);
				filePath = null;
			}
			
			HttpEntity entity = response.getEntity();
			
			//process the stream content
			if(entity != null) {
				InputStream entityContent = entity.getContent();
				filePath = getFileNameByUrl(url);
				saveLocation(entityContent, filePath);
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
		System.out.println("Downloaded HTML file " + getFileNameByUrl(url) + " successfully!");
		return filePath;
	}
}
