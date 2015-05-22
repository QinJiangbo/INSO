/**  
 * @description Files input and output operation
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 11, 2015 7:47:25 PM
 */

package Extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileIO {
	
	/**
	 * read file data
	 * @param fileName
	 * @return the string of the data stored in the file
	 */
	public static String readFile(String fileName) {
		try {
			String s = null;
			StringBuilder result = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "utf-8"));
			
			while((s = br.readLine()) != null) {
				result.append(s);
			}
			
			br.close();
			return result.toString();
		}catch(FileNotFoundException ex) {
			ex.printStackTrace();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * write the data into a file
	 * @param fileName
	 * @param data
	 */
	public static void writeFile(String fileName, String data) {
		File file = new File(fileName);
		file.getParentFile().mkdirs();
		try {
			FileOutputStream fos = new FileOutputStream(file);
			Writer out = new OutputStreamWriter(fos);
			out.write(data);
			out.flush();
			fos.close();
		}catch(FileNotFoundException ex) {
			ex.printStackTrace();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * delete all files in the specific directory
	 * @param dir
	 */
	public static void deleteFiles(String dir) {
		File src = new File(dir);
		File[] files = src.listFiles();
		for(int i = 0; i < files.length; i++) {
			files[i].delete();
		}
		src.delete();
	}
	
	/**
	 * Copy files from source directory to destination directory
	 * @param srcDir source directory
	 * @param destDir destination directory
	 */
	public static void copyFiles(String srcDir, String destDir) {
		File src = new File(srcDir);
		File[] files = src.listFiles();
		for(int i = 0; i < files.length; i++) {
			String fileName = files[i].getName();
			String data = readFile(srcDir + fileName);
			FileIO.writeFile(destDir + fileName, data);
		}
	}
	
	/**
	 * delete the file
	 * @param path
	 */
	public static void deleteFile(String path) {
		File file = new File(path);
		file.delete();
	}
	
	/**
	 * delete the file by condition string
	 * @param dir directory
	 * @param contentStr condition string
	 */
	public static void deleteByCondition(String dir, String contentStr) {
		try {
			File directory = new File(dir);
			File[] fileList  = directory.listFiles();
			for(int i = 0; i < fileList.length; i++) {
				BufferedReader br  = new BufferedReader(new InputStreamReader(new FileInputStream(new File(dir + fileList[i].getName()))));
				String word = br.readLine();
				String content = null;
				while(word != null) {
					content += word;
					word = br.readLine();
				}
				br.close();
				if(content.contains(contentStr)) {
					//System.out.println(dir + fileList[i].getName() + " nedds to be deleted!");
					System.out.println(fileList[i].delete());
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
