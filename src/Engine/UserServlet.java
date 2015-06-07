/**  
 * @description 
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date May 25, 2015 2:34:31 PM
 */

package Engine;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;

import Database.AdminJDBC;

public class UserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AdminJDBC db = new AdminJDBC();

	/**
	 * Constructor of the object.
	 */
	public UserServlet() {
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
		case "GetUsers":
			getUsers(request,response);
			break;
		case "DeleteUser":
			deleteUser(request,response);
			break;
		case "AddUser":
			addUser(request,response);
			break;
		case "ModifyUser":
			modifyUser(request, response);
			break;
		case "GetUserInfo":
			getUserInfo(request, response);
		}
	}

	/**
	 * get the user info ajax method
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	private void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		Map map = this.db.getUserInfo(name);
		JSONObject m = JSONObject.fromObject(map);
		PrintWriter writer = response.getWriter();
		writer.println(m.toString());
		writer.flush();
		writer.close();
	}

	/**
	 * modify the user's information
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void modifyUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String level = request.getParameter("level");
		Object[] params = {password, email, telephone, level, name};
		this.db.modifyUser(params);
		getUsers(request, response);
	}

	/**
	 * add the user
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String level = request.getParameter("level");
		if(this.db.existedUser(name)) {
			request.setAttribute("msg", "<script>alert('用户名已经存在了！无法添加！');</script>");
		}
		else {
			Object[] params = {name, password, email, telephone, level};
			this.db.addUser(params);
		}
		getUsers(request, response);
	}

	/**
	 * delete the user
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		this.db.deleteUser(name);
		getUsers(request, response);
	}

	/**
	 * get the users
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void getUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map[] results = this.db.getUsers();
		HttpSession session = request.getSession();
		int level = Integer.parseInt(session.getAttribute("level").toString());
		if(level > 1) {
			Map[] newResults = new Map[results.length];
			int i = 0;
			for(Map row: results) {
				Map map = new HashMap();
				map.put("userName", row.get("userName").toString());
				byte[] encodeStr = Base64.encodeBase64(row.get("userPassword").toString().getBytes());
				map.put("userPassword", new String(encodeStr));
				map.put("email", row.get("email").toString());
				map.put("telephone", row.get("telephone").toString());
				map.put("userLevel", row.get("userLevel").toString());
				newResults[i] = map;
				i++;
			}
			request.setAttribute("users", newResults);
		}
		else{
			request.setAttribute("users", results);
		}
		request.getRequestDispatcher("user.jsp").forward(request, response);
	}

}
