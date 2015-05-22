/**  
 * @description create a servlet to handle the searching events
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 16, 2015 3:08:41 PM
 */

package Engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.HotSearchJDBC;
import Index.Pagination;
import Index.Searcher;
import Pojo.Film;
import Util.FilmToMap;

public class EngineServlet extends HttpServlet {
	
	//searcher of the engine
	Searcher searcher = new Searcher();
	//pagination of the engine
	Pagination paginate = new Pagination();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public EngineServlet() {
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
		//set the page encodings to avoid messy code
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		//get the process method for each method
		String method = request.getParameter("method");
		switch(method){
		case "CommonSearch":
			CommonSearch(request,response);
			break;
		case "SearchByPageNum":
			SearchByPageNum(request,response);
			break;
		case "PreviousPage":
			PreviousPage(request,response);
			break;
		case "NextPage":
			NextPage(request,response);
			break;
		}
	}

	/**
	 * Go to next page
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void NextPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int currentPage = Integer.parseInt(session.getAttribute("currentPage").toString()) + 1;
		session.setAttribute("currentPage", currentPage);
		String searchText = session.getAttribute("searchText").toString();
		int totalCount = Integer.parseInt(session.getAttribute("totalCount").toString());
		List hits = searcher.searchMultipleIndexes(searchText);
		if(hits != null) {
			List filmList = new ArrayList<Film>();
			filmList = paginate.paginate(hits, currentPage, totalCount);
			List mapList = FilmToMap.Convert((ArrayList<Film>) filmList);
			request.setAttribute("filmList", mapList);
		} else {
			request.setAttribute("filmList", null);
		}
		request.getRequestDispatcher("search.jsp").forward(request, response);
	}

	/**
	 * Go to previous page
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void PreviousPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int currentPage = Integer.parseInt(session.getAttribute("currentPage").toString())- 1;
		session.setAttribute("currentPage", currentPage);
		String searchText = session.getAttribute("searchText").toString();
		int totalCount = Integer.parseInt(session.getAttribute("totalCount").toString());
		List hits = searcher.searchMultipleIndexes(searchText);
		if(hits != null) {
			List filmList = new ArrayList<Film>();
			filmList = paginate.paginate(hits, currentPage, totalCount);
			List mapList = FilmToMap.Convert((ArrayList<Film>) filmList);
			request.setAttribute("filmList", mapList);
		} else {
			request.setAttribute("filmList", null);
		}
		request.getRequestDispatcher("search.jsp").forward(request, response);
	}

	/**
	 * Search the result by page number
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void SearchByPageNum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int pageNum = Integer.parseInt(request.getParameter("pageNum").toString());
		session.setAttribute("currentPage", pageNum);
		String searchText = session.getAttribute("searchText").toString();
		int totalCount = Integer.parseInt(session.getAttribute("totalCount").toString());
		List hits = searcher.searchMultipleIndexes(searchText);
		if(hits != null) {
			List filmList = new ArrayList<Film>();
			filmList = paginate.paginate(hits, pageNum, totalCount);
			List mapList = FilmToMap.Convert((ArrayList<Film>) filmList);
			request.setAttribute("filmList", mapList);
		} else {
			request.setAttribute("filmList", null);
		}
		request.getRequestDispatcher("search.jsp").forward(request, response);
	}

	/**
	 * The common search of the films
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void CommonSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String searchText = request.getParameter("search_Text");
		HotSearchJDBC hotjdbc = new HotSearchJDBC();
		if(hotjdbc.isExist(searchText)){
			hotjdbc.updateTimes(searchText);
		}
		else{
			hotjdbc.insertKeyword(searchText);
		}
		session.setAttribute("searchText", searchText);
		int totalCount = searcher.getTotalRecords(searchText);
		int currentPage = 1;
		int totalPages = paginate.getTotalPages(totalCount);
		session.setAttribute("totalPages", totalPages);
		session.setAttribute("currentPage", currentPage);
		session.setAttribute("totalCount", totalCount);
		List hits = searcher.searchMultipleIndexes(searchText);
		if(hits != null) {
			List filmList = new ArrayList<Film>();
			filmList = paginate.paginate(hits, currentPage, totalCount);
			List mapList = FilmToMap.Convert((ArrayList<Film>) filmList);
			request.setAttribute("filmList", mapList);
		} else {
			request.setAttribute("filmList", null);
		}
		request.getRequestDispatcher("search.jsp").forward(request, response);
	}

}
