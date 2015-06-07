<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">
  <%@ include file="header.jsp" %>
  <script type="text/javascript">
	  $(window).load(function(){
	  	  var loginState = $("#loginState").val();
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
	            <li class="active"><a href="#home">后台首页</a></li>
	            <li><a href="UserServlet?method=GetUsers">管理员管理</a></li>
	            <li><a href="AdminServlet?method=GetLogger">搜索记录</a></li>
	            <li><a href="crawler.jsp">索引管理</a></li>
	            <li><a href="database.jsp">数据库管理</a></li>
	          </ul>
	          <ul class="nav navbar-nav navbar-right">
	            <li><a href="#user">欢迎你！${ userName }</a></li>
	            <li><a href="AdminServlet?method=Quit">安全退出</a></li>
	          </ul>
	        </div>
	      </div>
	    </div>
  		<div id="admin_content">
	      <div class="jumbotron">
	        <h3>INSO 后台管理</h3>
	        <p><b>INSO</b>是本搜索引擎开发者秦江波于2015年05月开发完成，主要用于学习目的，目前该搜索引擎的主要爬取网站是一些影视论坛，对他们的数据进行爬取和分析以及管理是一个非常重要的过程，所以开发者单独开发这个后台管理系统进行搜索引擎的维护。</p>
	        <p>目前该搜索引擎后台维护的主要方面是以下几点：</p>
	        <div class="items">
	        	<div class="item"><a href="UserServlet?method=GetUsers">管理员管理</a></div>
		        <div class="item"><a href="AdminServlet?method=GetLogger">搜索记录</a></div>
		        <div class="item"><a href="crawler.jsp">索引管理</a></div>
		        <div class="item"><a href="database.jsp">数据库管理</a></div>
		    </div>
	        <p>后续我们会继续对网站进行优化，以便给用户更加优良的用户体验！</p>
	        <p class="sign">秦江波&nbsp;&nbsp;&nbsp;&nbsp;2015年05月10日&nbsp;&nbsp;&nbsp;&nbsp;武汉大学</p>
	      </div>
	    </div> 
  		<%@ include file="footer.jsp" %>
  </body>
</html>
