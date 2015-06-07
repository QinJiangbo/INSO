/**  
 * @description 
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date May 26, 2015 9:53:34 AM
 */

package Engine;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Crawler.Crawler;
import Database.JDBC;
import Extractor.Extracter;
import Index.Indexer;

public class ControllerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * crawler thread
	 */
	private static Crawler crawler = null;
	private Thread tc = null;
	
	/**
	 * extracter thread
	 */
	private static Extracter extracter = null;
	private Thread te = null;
	
	/**
	 * jdbc thread
	 */
	private static JDBC jdbc = null;
	private Thread tj = null;
	
	/**
	 * indexer thread
	 */
	private static Indexer indexer = null;
	private Thread ti = null;
	
	/**
	 * Constructor of the object.
	 */
	public ControllerServlet() {
		super();
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		switch(method){
		case "Crawl":
			crawl(request, response);
			break;
		case "Extract":
			extract(request, response);
			break;
		case "JDBC":
			jdbc(request, response);
			break;
		case "Indexer":
			indexer(request, response);
			break;
		}
	}

	/**
	 * indexer progress
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void indexer(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String op = request.getParameter("op");
		if(op.equals("start")) {
			indexer = new Indexer();
			ti = new Thread(indexer);
			ti.start();
			PrintWriter writer = response.getWriter();
			writer.println(1);
			writer.flush();
			writer.close();
		}
		else if(op.equals("progress")) {
			int totalCount = indexer.getTotalCount();
			int currentSize = indexer.getCurrentNum();
			float percentage = (float) currentSize / totalCount ;
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMaximumFractionDigits(1);
			String s = nf.format(percentage);
			PrintWriter writer = response.getWriter();
			boolean flag1 = (totalCount == currentSize);
			boolean flag2 = (ti.getState().toString().equals("TERMINATED"));
			if(flag1 || flag2) {
				writer.println(1);
			}
			else{
				writer.println(s);
			}
			writer.flush();
			writer.close();
		}
	}

	/**
	 * jdbc progress
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void jdbc(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String op = request.getParameter("op");
		if(op.equals("start")) {
			jdbc = new JDBC();
			tj =  new Thread(jdbc);
			tj.start();
			PrintWriter writer = response.getWriter();
			writer.println(1);
			writer.flush();
			writer.close();
		}
		else if(op.equals("progress")) {
			int totalCount = jdbc.getTotalCount();
			int currentSize = jdbc.getCurrentNum();
			float percentage = (float) currentSize / totalCount ;
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMaximumFractionDigits(1);
			String s = nf.format(percentage);
			PrintWriter writer = response.getWriter();
			boolean flag1 = (totalCount == currentSize);
			boolean flag2 = (tj.getState().toString().equals("TERMINATED"));
			if(flag1 || flag2) {
				writer.println(1);
			}
			else{
				writer.println(s);
			}
			writer.flush();
			writer.close();
		}
	}

	/**
	 * extract progress
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void extract(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String op = request.getParameter("op");
		if(op.equals("start")) {
			extracter = new Extracter();
			te = new Thread(extracter);
			te.start();
			PrintWriter writer = response.getWriter();
			writer.println(1);
			writer.flush();
			writer.close();
		}
		else if(op.equals("progress")) {
			int totalCount = extracter.getTotalCount();
			int currentSize = extracter.getCurrentNum();
			float percentage = (float) currentSize / totalCount ;
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMaximumFractionDigits(1);
			String s = nf.format(percentage);
			PrintWriter writer = response.getWriter();
			boolean flag1 = (totalCount == currentSize);
			boolean flag2 = (te.getState().toString().equals("TERMINATED"));
			if(flag1 || flag2) {
				writer.println(1);
			}
			else{
				writer.println(s);
			}
			writer.flush();
			writer.close();
		}
	}

	/**
	 * crawler control method
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void crawl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String op = request.getParameter("op");
		if(op.equals("start")){
			crawler = new Crawler();
			tc = new Thread(crawler);
			String[] seeds = request.getParameterValues("seeds[]");
			crawler.setSeeds(seeds);
			tc.start();
			PrintWriter writer = response.getWriter();
			writer.println(1);
			writer.flush();
			writer.close();
		}
		else if(op.equals("end")){
			crawler.stopCrawl();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) { }
			System.out.println(crawler.downloadNum +" : "+tc.getState().toString());
			if(tc.getState().toString().equals("TERMINATED")) {
				PrintWriter writer = response.getWriter();
				writer.println(1);
				writer.flush();
				writer.close();
			}
			else{
				PrintWriter writer = response.getWriter();
				writer.println(0);
				writer.flush();
				writer.close();
			}
		}
		else if(op.equals("progress")) {
			int count = crawler.downloadNum;
			int total = 2000;
			float percentage = (float) count / total ;
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMaximumFractionDigits(1);
			String s = nf.format(percentage);
			PrintWriter writer = response.getWriter();
			writer.println(s);
			writer.flush();
			writer.close();
		}
	}

}
