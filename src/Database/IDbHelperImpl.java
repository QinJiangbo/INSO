/**  
 * @description Implementation of the DBhelper interface
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 12, 2015 16:19:01 PM
 */

package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

public class IDbHelperImpl implements IDbHelper {

	//driver
	private final String DRIVER="com.mysql.jdbc.Driver";
	//the databse's URL
	private final String URL="jdbc:mysql://localhost:3306/inso";
	
	//get the connection
	private Connection getConnection(){		
		try{
			Class.forName(DRIVER); 
			Connection conn=DriverManager.getConnection(URL, "Richard", "cq08010907");
			return conn;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	

	//run the select SQL with parameters
	@SuppressWarnings("rawtypes")
	public Map[] runSelect(String sql,Object[] params){
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{			
			conn=getConnection();
			ps=conn.prepareStatement(sql);
			//get the parameters array
			int i=1;//really easy to be wrong!!!
			for(Object o:params){
				if(o ==null){
				   ps.setObject(i++,o,java.sql.Types.VARCHAR); 
				}else{
				   ps.setObject(i++,o); 
				}
			}		 	
			rs=ps.executeQuery();
			Result result=ResultSupport.toResult(rs);
			Map[] rows=result.getRows();			
			return rows;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{ //no matter how the program goes, this line will be executed
			try{
				rs.close();
				ps.close();				
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}				
	}
		
	
	//run the select SQL without parameters
	@SuppressWarnings("rawtypes")
	public Map[] runSelect(String sql){
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{			
			conn=getConnection();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			Result result=ResultSupport.toResult(rs);
			Map[] rows=result.getRows();			
			return rows;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{ 
			try{
				rs.close();
				ps.close();				
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}				
	}
	
	//run the update SQL without parameters
	public void runUpdate(String sql){
		Connection conn=null;
		PreparedStatement ps=null;
		try{			
			conn=getConnection();
			ps=conn.prepareStatement(sql);
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				ps.close();				
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}				
	}
	
	//run the select SQL with parameters
	public void runUpdate(String sql,Object[] params){
		Connection conn=null;
		PreparedStatement ps=null;
		try{			
			conn=getConnection();
			ps=conn.prepareStatement(sql);
			//get the parameters array
			int i=1;//really easy to be wrong!!!
			for(Object o:params){
				if(o ==null){
				   ps.setObject(i++,o,java.sql.Types.VARCHAR); 
				}else{
				   ps.setObject(i++,o); 
				}
			}	
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				ps.close();				
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}				
	}
		
}
