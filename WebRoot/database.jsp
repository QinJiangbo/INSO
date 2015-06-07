<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
  <%@ include file="header.jsp" %>
  <link rel="stylesheet" href="css/monokai_sublime.css">
  <script src="js/highlight.pack.js"></script>
  <script type="text/javascript">
	  $(window).load(function(){
	  	  var loginState = $("#loginState").val();
	  	  if(loginState == null || loginState == "") {
  	  	  	 window.location.href = "login.jsp";
  	  	  }
  	  	  else if(loginState == false) {
  	  	  	 window.location.href = "login.jsp";
  	  	  }
	  });
	  
	  //deal with the ctrl + enter key press events
	  function send(event) {
	  	  if(event.ctrlKey && event.keyCode == 13) {
	  	  	  $("#sql-form").submit();
	  	  }
	  }
	  
	  //init the highlight
	  hljs.initHighlightingOnLoad();
	  
	  /**
	 	* change to the input mode
	 	*/
	  function inputCode() {
		  $("#code").css("display", "none");
		  $("#sqlText").css("display", "block");
		  $("#sqlText").focus();
		  $("#sqlText").val("${ message }");
	  }
	  
	  /**
	  	*show the code area
	  	*/
	  function showCode() {
	  	  $("#code").css("display", "block");
		  $("#sqlText").css("display", "none");
	  }
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
	            <li><a href="UserServlet?method=GetUsers">管理员管理</a></li>
	            <li><a href="AdminServlet?method=GetLogger">搜索记录</a></li>
	            <li><a href="crawler.jsp">索引管理</a></li>
	            <li class="active"><a href="#database">数据库管理</a></li>
	          </ul>
	          <ul class="nav navbar-nav navbar-right">
	            <li><a href="#user">欢迎你！${ userName }</a></li>
	            <li><a href="AdminServlet?method=Quit">安全退出</a></li>
	          </ul>
	        </div>
	      </div>
	    </div>
  		<div id="db_content">
  			<c:choose>
  				<c:when test="${ empty message }">
	 				<form action="DatabaseServlet?method=SQL" method="post" id="sql-form">
	 					<textarea class="form-control" rows="2" name="sqlText" placeholder="请输入正确的sql语句查询,不支持使用别名和更新操作,同时按下ctrl+enter发送" title="可供操作的表名: BtChina,BtTianTang,DM1080p,DMM,DYTT,DYXZ,OurRelease,ThreeMu,TorrentBar,XiXiHD,XiXiZhan,Yify,Yify2,YifyM,YifyM2,YS,ZeroDM,ZiMuKu" onkeydown="send(event)"></textarea>
	 				</form>
  				</c:when>
  				<c:otherwise>
  					<form action="DatabaseServlet?method=SQL" method="post" id="sql-form" id="sqlText" >
	 					<textarea class="form-control" rows="2" name="sqlText" id="sqlText" placeholder="请输入正确的sql语句查询,不支持使用别名和更新操作,同时按下ctrl+enter发送" title="可供操作的表名: BtChina,BtTianTang,DM1080p,DMM,DYTT,DYXZ,OurRelease,ThreeMu,TorrentBar,XiXiHD,XiXiZhan,Yify,Yify2,YifyM,YifyM2,YS,ZeroDM,ZiMuKu" onkeydown="send(event)" onblur="showCode()"></textarea>
	 				</form>
  					<pre id="code"><code ondblclick="inputCode()">${ message }</code></pre>
  				</c:otherwise>
  			</c:choose>
  			<div class="table_head">
		      	<table class="table table-hover table-bordered hot_table">
		      		<thead>
		      			<tr>
		      				<th class="d-col-1">#</th>
		      				<th class="d-col-2">电影标题</th>
		      				<th class="d-col-2">URL地址</th>
		      				<th class="d-col-3"><a href="DatabaseServlet?method=SortRecord">更新时间</a></th>
		      				<th class="d-col-4">删除该记录</th>
		      			</tr>
		      		</thead>
		      	</table>
	      	</div>
	      	<div class="table_body">
	      		<table class="table table-hover table-bordered hot_table">
	      		<tbody>
	      		<c:forEach items = "${ results }" var = "row" varStatus="status">
	      			<tr>
	      				<td class="d-col-1">${ status.count }</td>
	      				<td class="d-col-2" title="${ row.Title }">${ row.Title }</td>
	      				<td class="d-col-2"><a href="${ row.URL }" title="${ row.URL }" target="_blank">${ row.URL }</a></td>
	      				<td class="d-col-3">${ row.UpdateTime }</td>
	      				<td class="d-col-4"><a href="DatabaseServlet?method=DeleteRecord&url=${ row.URL }" class="btn btn-danger btn_delete">删除</a></td>
	      			</tr>
	      		</c:forEach>
	      		</tbody>
	      	</table>
	      	</div>
	    </div>
  		<%@ include file="footer.jsp" %>
  </body>
</html>
