<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">
  <%@ include file="header.jsp" %>
  <script type="text/javascript">
	  $(window).load(function(){
	  	  var loginState = document.getElementById("loginState");
	  	  if(loginState == null) {
  	  	  	 window.location.href = "login.jsp";
  	  	  }
  	  	  else if(loginState == false) {
  	  	  	 window.location.href = "login.jsp";
  	  	  }
	  });
  </script>
  <body>
	    <input type="hidden" value= ${ loginState } id="loginState" />
  		<div class="navbar navbar-default navbar-inverse navbar-fixed-top" role="navigation">
	      <div class="container">
	        <div class="navbar-header">
	          <a class="navbar-brand" href="index.jsp">INSO</a>
	        </div>
	        <div class="navbar-collapse collapse">
	          <ul class="nav navbar-nav">
	            <li><a href="admin.jsp">后台首页</a></li>
	            <li><a href="#about">管理员管理</a></li>
	            <li class="active"><a href="#contact">搜索记录</a></li>
	            <li><a href="#contact">索引管理</a></li>
	            <li><a href="#contact">数据库管理</a></li>
	          </ul>
	          <ul class="nav navbar-nav navbar-right">
	            <li><a href="#user">欢迎你！${ userName }</a></li>
	            <li><a href="AdminServlet?method=Quit">安全退出</a></li>
	          </ul>
	        </div>
	      </div>
	    </div>
  		<div id="admin_content">
	      	<table class="table table-hover table-bordered hot_table">
	      		<thead>
	      			<tr>
	      				<th>检索关键字</th>
	      				<th>检索次数</th>
	      				<th>最近检索时间</th>
	      				<th>删除检索词</th>
	      			</tr>
	      		</thead>
	      		<tbody>
	      			<tr>
	      				<td>迷河</td>
	      				<td>2</td>
	      				<td>2015-03-07</td>
	      				<td><a href="#">删除</a></td>
	      			</tr>
	      		</tbody>
	      	</table>
	    </div> 
  		<%@ include file="footer.jsp" %>
  </body>
</html>
