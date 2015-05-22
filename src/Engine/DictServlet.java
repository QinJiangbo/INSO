/**  
 * @description servlet to transfer the dictionary to the jsp page
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 19, 2015 2:58:34 PM
 */

package Engine;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import Util.Dict;

public class DictServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public DictServlet() {
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
	@SuppressWarnings({ "rawtypes"})
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		Dict dict = new Dict();
		//the words which are picked out
		Map words = dict.GetDict();
		//transfer to json array
		JSONArray array = new JSONArray();
		for(int j = 0; j < words.size(); j++) {
			array.add(words.get(j));
		}
		String jsonDict = array.toString();
		//write back to the call from the page
		PrintWriter writer = response.getWriter();
		writer.println(jsonDict);
		writer.flush();
		writer.close();
	}

}
