<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
	            <li><a href="admin.jsp">后台首页</a></li>
	            <li><a href="UserServlet?method=GetUsers">管理员管理</a></li>
	            <li class="active"><a href="#contact">搜索记录</a></li>
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
  		<div id="table_content">
  			<div class="table_head">
		      	<table class="table table-hover table-bordered hot_table">
		      		<thead>
		      			<tr>
		      				<th class="col-1">#</th>
		      				<th class="col-2"><a href="AdminServlet?method=SortLogger&field=keyword">检索关键字</a></th>
		      				<th class="col-3"><a href="AdminServlet?method=SortLogger&field=province">检索地区</a></th>
		      				<th class="col-4"><a href="AdminServlet?method=SortLogger&field=type">设备类型</a></th>
		      				<th class="col-5"><a href="AdminServlet?method=SortLogger&field=times">检索次数</a></th>
		      				<th class="col-6"><a href="AdminServlet?method=SortLogger&field=updatetime">历史检索时间</a></th>
		      				<th class="col-7">删除检索词</th>
		      			</tr>
		      		</thead>
		      	</table>
	      	</div>
	      	<div class="table_body">
	      		<table class="table table-hover table-bordered hot_table">
	      		<tbody>
	      		<c:forEach items = "${ hotsearchs }" var = "row" varStatus="status">
	      			<tr>
	      				<td class="col-1">${ status.count }</td>
	      				<td class="col-2">${ row.keyword }</td>
	      				<td class="col-3">${ row.province } - ${ row.city }</td>
	      				<td class="col-4">${ row.type }</td>
	      				<td class="col-5">${ row.times }</td>
	      				<td class="col-6">${ row.updatetime }</td>
	      				<td class="col-7"><a href="AdminServlet?method=DeleteLogger&keyword=${ row.keyword }&city=${ row.city }&type=${ row.type }" class="btn btn-danger btn_delete">删除</a></td>
	      			</tr>
	      		</c:forEach>
	      		</tbody>
	      	</table>
	      	</div>
	    </div> 
  		<%@ include file="footer.jsp" %>
  </body>
</html>
