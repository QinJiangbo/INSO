/**  
 * @description admin servlet used to maintain the search engine
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 17, 2015 10:41:35 PM
 */

package Engine;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Database.AdminJDBC;

public class AdminServlet extends HttpServlet {
	
	AdminJDBC db = new AdminJDBC();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public AdminServlet() {
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
				
		String method = request.getParameter("method");
		switch(method){
		case "Login":
			Login(request, response);
			break;
		case "Quit":
			Quit(request,response);
			break;
		case "GetLogger":
			getLogger(request,response);
			break;
		case "DeleteLogger":
			deleteLogger(request, response);
			break;
		case "SortLogger":
			sortLogger(request, response);
			break;
		}
	}

	/**
	 * sort the results by the field
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@SuppressWarnings("rawtypes")
	private void sortLogger(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String field = request.getParameter("field");
		//get the results from the database
		Map[] results = this.db.sortLogsByField(field);
		request.setAttribute("hotsearchs", results);
		request.getRequestDispatcher("logger.jsp").forward(request, response);
	}

	/**
	 * delete the log records in the database
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void deleteLogger(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		String city = request.getParameter("city");
		String type = request.getParameter("type");
		this.db.deleteLog(keyword, city, type);
		getLogger(request, response);
	}

	/**
	 * get the logger statistics from the database
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@SuppressWarnings("rawtypes")
	private void getLogger(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get the results from the database
		Map[] results = this.db.getLogs();
		request.setAttribute("hotsearchs", results);
		request.getRequestDispatcher("logger.jsp").forward(request, response);
	}

	/**
	 * Safely quit the session
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void Quit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("loginState", null);
		session.setAttribute("userName", null);
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	/**
	 * Login method for the admin
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void Login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userName = request.getParameter("userName");
		String userPassword = request.getParameter("userPassword");
		boolean result = this.db.isUserLegal(userName, userPassword);
		if(result == true) {
			session.setAttribute("userName", userName);
			//store the user level in session
			session.setAttribute("level", this.db.getLevel(userName));
			session.setAttribute("loginState", true);
			request.getRequestDispatcher("admin.jsp").forward(request, response);
		} else {
			request.setAttribute("userName", userName);
			request.setAttribute("state", false);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

}
