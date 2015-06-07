/**  
 * @description servlet fotr dealing with the database of all tables
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date May 29, 2015 5:08:34 PM
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

public class DatabaseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AdminJDBC db = new AdminJDBC();
	
	/**
	 * Constructor of the object.
	 */
	public DatabaseServlet() {
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
		switch(method) {
		case "SQL":
			dealWithSQL(request, response);
			break;
		case "DeleteRecord":
			deleteRecord(request, response);
			break;
		case "SortRecord":
			sortRecord(request, response);
			break;
		}
	}

	/**
	 * sort the table by the updatetime
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	private void sortRecord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String tableName = session.getAttribute("tableName").toString();
		Map[] results = null;
		String message = "";
		if(tableName.isEmpty()) {
			message = "记录为空，无法进行排序！";
			session.setAttribute("message", message);
			request.getRequestDispatcher("database.jsp").forward(request, response);
		}
		else {
			String limitCondition = session.getAttribute("limitCondition").toString();
			results = this.db.sortTable(tableName, limitCondition);
			request.setAttribute("results", results);
			message = "select * from " + tableName + " order by updatetime desc limit " + limitCondition + ";";
			session.setAttribute("message", message);
			request.getRequestDispatcher("database.jsp").forward(request, response);
		}
	}

	/**
	 * delete the records of the table
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	private void deleteRecord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String url = request.getParameter("url");
		String tableName = session.getAttribute("tableName").toString();
		String message = "";
		this.db.deleteRecords(tableName, url);
		message = "delete from " + tableName + " where url = " + url;
		session.setAttribute("message", message);
		Map[] results = this.db.getRecords(tableName);
		request.setAttribute("results", results);
		request.getRequestDispatcher("database.jsp").forward(request, response);
	}

	/**
	 * deal with the sql statements
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes"})
	private void dealWithSQL(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//set the page encodings to avoid messy code
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		HttpSession session = request.getSession();
		String sqlText = request.getParameter("sqlText");
		String sqlTextLCS = sqlText.toLowerCase().trim();
		//judge the statement ends with semicolon
		if(!sqlTextLCS.endsWith(";")) {
			sqlTextLCS = sqlTextLCS + ";";
		}
		String message = "";
		Map[] results = null;
		String limitCondition = "";
		if(sqlTextLCS.startsWith("select")){
			if(sqlTextLCS.contains("count") || sqlTextLCS.contains("avg")) {
				message = sqlText + " 不符合输入要求！";
			}
			else {
				if(isInDB(sqlTextLCS)){
					int index1 = 0;
					int index2 = 0;
					if(sqlTextLCS.contains("where")) {
						index1 = sqlTextLCS.indexOf("from");
						index2 = sqlTextLCS.indexOf("where");
					}
					else if(!sqlTextLCS.contains("where") && sqlTextLCS.contains("order")) {
						index1 = sqlTextLCS.indexOf("from");
						index2 = sqlTextLCS.indexOf("order");
					}
					else if(!sqlTextLCS.contains("where") && !sqlTextLCS.contains("order") && sqlTextLCS.contains("limit")){
						index1 = sqlTextLCS.indexOf("from");
						index2 = sqlTextLCS.indexOf("limit");
						limitCondition = sqlTextLCS.substring(index2 + 5, sqlTextLCS.length() - 1).trim();
					}
					else {
						index1 = sqlTextLCS.indexOf("from");
						index2 = sqlTextLCS.indexOf(";");
						sqlTextLCS = sqlTextLCS.replace(";", " limit 2000;");
						index2 = sqlTextLCS.indexOf("limit");
						limitCondition = sqlTextLCS.substring(index2 + 5, sqlTextLCS.length() - 1).trim();
					}
					results = this.db.selectWithSQL(sqlTextLCS);
					session.setAttribute("limitCondition", limitCondition);
					String tableName = sqlTextLCS.substring(index1 + 4, index2).trim();
					session.setAttribute("tableName", tableName);
					message = sqlText;
				}
				else {
					message = "你查询的表不存在！";
				}
			}
		}
		else if(sqlTextLCS.startsWith("delete")) {
			if(sqlTextLCS.contains("where") && sqlTextLCS.contains("=")) {
				this.db.updateWithSQL(sqlTextLCS);
				message = sqlText;
			}
			else if(sqlTextLCS.contains("true")) {
				message = sqlText + " 不符合输入要求！";
			}
			else {
				message = sqlText + " 被系统拒绝！";
			}
		}
		else {
			message = sqlText + " 被系统拒绝！";
		}
		
		session.setAttribute("message", message);
		request.setAttribute("results", results);
		request.getRequestDispatcher("database.jsp").forward(request, response);
	}
	
	/**
	 * judge the tables
	 * @param sql
	 * @return true or false
	 */
	private boolean isInDB(String sql) {
		if(sql.contains("btchina") || sql.contains("bttiantang") || sql.contains("dm1080p") || 
		   sql.contains("dmm") || sql.contains("dytt") || sql.contains("dyxz") ||
		   sql.contains("ourrelease") || sql.contains("threemu") || sql.contains("torrentbar") ||
		   sql.contains("xixihd") || sql.contains("xixizhan") || sql.contains("yify") ||
		   sql.contains("yify2") || sql.contains("yifym") || sql.contains("yifym2") ||
		   sql.contains("ys") || sql.contains("zerodm") || sql.contains("zimuku")) {
			return true;
		}
		return false;
	}

}
