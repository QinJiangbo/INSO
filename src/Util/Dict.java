/**  
 * @description Create the dictionary based on http://shurufa.baidu.com/dict.html
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 13, 2015 8:51:41 AM
 */

package Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jeasy.analysis.MMAnalyzer;

public class Dict {
	
	/**
	 * method of add words to a dictionary
	 * @param inputFile the path of the HTML
	 * @param outputFile the path of the dictionary
	 */
	@SuppressWarnings("rawtypes")
	public void AddDict(String inputFile, String outputFile) {
		Set<String> set = new HashSet<String>();
		String word = null;
		
		//read file content
		try {
			System.out.println("Reading " + inputFile + "...");
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(Config.DictionaryPath + outputFile))));
			word = br.readLine();
			while(word != null) {
				set.add(word);
				word = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//validate the content, get rid of the overlap
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(Config.DictionaryPath + inputFile))));
			word = br.readLine();
			while(word != null) {
				if(!set.contains(word)) {
					set.add(word);
					word = br.readLine();
				}else{
					word = br.readLine();
				}
				System.out.println(word);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//add the words to the file
		try {
			System.out.println("Writing to Files...");
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(Config.DictionaryPath + outputFile)));
			Iterator it = set.iterator();
			word = "";
			while(it.hasNext()) {
				word += it.next().toString() + "\n";
			}
			bw.write(word);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Added Dictionary Successfully!");
	}
	
	/**
	 * method of creating a dictionary
	 */
	@SuppressWarnings("rawtypes")
	public void CreateDict() {
		String[] inputFiles = { "Hot Movie.txt", "Japanese Comics.txt", "Korea Series.txt", "Movies.txt", "Series.txt", "ZhouXingChi.txt"};
		String outputFile = "Dictionary.txt";
		Set<String> set = new HashSet<String>();
		String word = null;
		
		//read file contents
		for(String inputFile : inputFiles) {
			try {
				System.out.println("Reading " + inputFile + "...");
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(Config.DictionaryPath + inputFile))));
				word = br.readLine();
				while(word != null) {
					if(!set.contains(word)) {
						set.add(word);
						word = br.readLine();
						System.out.println(word);
					}else{
						word = br.readLine();
					}
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//write words to output file
		try {
			System.out.println("Writing to Files...");
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(Config.DictionaryPath + outputFile)));
			Iterator it = set.iterator();
			word = "";
			while(it.hasNext()) {
				word += it.next().toString() + "\n";
			}
			bw.write(word);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Created Dictionary Successfully!");
	}
	
	/**
	 * add diy words to JE analyzer
	 */
	public void AddWords() {
		String dict = "Dictionary.txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(Config.DictionaryPath + dict)));
			MMAnalyzer.addDictionary(br);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the dictionary of the input area
	 * @return the dictionary
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "resource" })
	public Map GetDict() {
		String dictName = "Dictionary.txt";
		Map dict = new HashMap();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(Config.DictionaryPath + dictName)),"utf-8"));
			String word = br.readLine();
			int i = 0;
			while(word != null){
				dict.put(i, word);
				i++;
				word = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dict;
	}
	
	/**
	 * read data stored in dictionary
	 */
	@SuppressWarnings("resource")
	public void ReadDict() {
		String dictName = "Newest Movie.txt";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(Config.DictionaryPath + dictName)),"utf-8"));
			String word = br.readLine();
			while(word != null){
				System.out.println(word);
				word = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		
		Dict dict = new Dict();
		//dict.CreateDict();
		dict.AddDict("Newest Movie.txt", "Dictionary.txt");
		//dict.ReadDict();
	}

}
