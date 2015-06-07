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
  <script type="text/javascript">
  	  function loadInfo(userName) {
		
		//el expression gets the userName in session
		var name = "${ userName }";

		$.ajax({
			type: "POST",
			url: "UserServlet?method=GetUserInfo&name="+userName,
			contentType: "application/json",
			dataType: "json",
			success: function(data) {
				var info = data;
				$("#name").val(info.userName);
				$("#password").val(info.userPassword);
				$("#email").val(info.email);
				$("#telephone").val(info.telephone);
				$("#level").val(info.userLevel);
				if(info.userName == name) {
					$("#level").attr("disabled", true);
				}
				else{
					$("#level").attr("disabled", false);
				}
			}, 
			error: function(data) {
				alert("Network error!");
			}
		});
	  }
  </script>
  <body>
  ${msg }
	    <input type="hidden" value= ${ loginState } id="loginState" />
  		<div class="navbar navbar-default navbar-inverse navbar-fixed-top" role="navigation">
	      <div class="container">
	        <div class="navbar-header">
	          <a class="navbar-brand" href="index.jsp">INSO</a>
	        </div>
	        <div class="navbar-collapse collapse">
	          <ul class="nav navbar-nav">
	            <li><a href="admin.jsp">后台首页</a></li>
	            <li class="active"><a href="#about">管理员管理</a></li>
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
  		<div id="table_content">
  			<div class="table_head">
		      	<table class="table table-hover table-bordered hot_table">
		      		<thead>
		      			<tr>
		      				<th class="u-col-1">#</th>
		      				<th class="u-col-2">名称</th>
		      				<th class="u-col-3">密码</th>
		      				<th class="u-col-4">邮箱</th>
		      				<th class="u-col-5">号码</th>
		      				<th class="u-col-6">权限</th>
		      				<th class="u-col-7">编辑操作</th>
		      				<th class="u-col-7">删除操作</th>
		      			</tr>
		      		</thead>
		      	</table>
	      	</div>
	      	<div class="table_body">
	      		<table class="table table-hover table-bordered hot_table">
	      		<tbody>
	      		<c:forEach items = "${ users }" var = "row" varStatus="status">
	      			<tr>
	      				<td class="u-col-1">${ status.count }</td>
	      				<td class="u-col-2">${ row.userName }</td>
	      				<td class="u-col-3">${ row.userPassword }</td>
	      				<td class="u-col-4">${ row.email }</td>
	      				<td class="u-col-5">${ row.telephone }</td>
	      				<td class="u-col-6">${ row.userLevel }</td>
	      				<td class="u-col-7">
	      					<c:choose>
	      						<c:when test="${ level gt 1 }">
	      							<button disabled class="btn btn-primary btn_modify" data-toggle="modal" data-target="#modifyModal" onclick="loadInfo('${ row.userName }')">修改</button>
	      						</c:when>
	      						<c:otherwise>
	      							<button class="btn btn-primary btn_modify" data-toggle="modal" data-target="#modifyModal" onclick="loadInfo('${ row.userName }')">修改</button>
	      						</c:otherwise>
	      					</c:choose>
	      				</td>
	      				<td class="u-col-7">
	      					<c:choose>
	      						<c:when test="${ level gt 1 || row.userName eq userName}">
	      							<a disabled href="UserServlet?method=DeleteUser&name=${ row.userName }" class="btn btn-danger btn_delete">删除</a>
	      						</c:when>
	      						<c:otherwise>
	      							<a href="UserServlet?method=DeleteUser&name=${ row.userName }" class="btn btn-danger btn_delete" onclick="return confirm('你确定要删除吗？');">删除</a>
	      						</c:otherwise>
	      					</c:choose>
	      				</td>
	      			</tr>
	      		</c:forEach>
	      		</tbody>
	      	</table>
	      	</div>
	      	<c:if test="${ level eq 1 }">
	      		<div id="add_area">
				    <div class="btn_row">
				    	<button class="btn btn-primary btn_add" onclick="addUser()">新建用户</button>
				    </div>
				    <div id="register_area">
				    	<form action="UserServlet?method=AddUser" method="post" onsubmit="return checkInputs2();">
				           <div class="form-group">
				            <label for="message-text" class="control-label">管理员名称:</label>
				            <input type="text" class="form-control" id="name1" name="name">
				          </div>
				          <div class="form-group">
				            <label for="message-text" class="control-label">管理员密码:</label>
				            <input type="password" class="form-control" id="password1" name="password">
				          </div>
				          <div class="form-group">
				            <label for="message-text" class="control-label">邮箱:</label>
				            <input type="email" class="form-control" id="email1" name="email">
				          </div>
				          <div class="form-group">
				            <label for="message-text" class="control-label">手机号码:</label>
				            <input type="tel" class="form-control" id="telephone1" name="telephone">
				          </div>
				          <div class="form-group">
				            <label for="message-text" class="control-label">权限:</label>
				            <input type="number" class="form-control" id="level1" name="level" placeholder="输入1 或 2">
				          </div>
				          <div class="modal-footer">
					        <button type="button" class="btn btn-danger btn-footer" onclick="hideUser()">取消</button>
					        <button type="submit" class="btn btn-primary btn-footer">添加</button>
					      </div>
				        </form>
				    </div>
			    </div>
	      	</c:if>
	    </div>
  		<%@ include file="footer.jsp" %>
  		<div class="modal fade" id="modifyModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="exampleModalLabel">修改管理员信息</h4>
		      </div>
		      <div class="modal-body">
		        <form action="UserServlet?method=ModifyUser" method="post" onsubmit="return checkInputs();">
		           <input type="hidden" class="form-control" id="name" name="name">
		          <div class="form-group">
		            <label for="message-text" class="control-label">管理员密码:</label>
		            <input type="text" class="form-control" id="password" name="password">
		          </div>
		          <div class="form-group">
		            <label for="message-text" class="control-label">邮箱:</label>
		            <input type="email" class="form-control" id="email" name="email">
		          </div>
		          <div class="form-group">
		            <label for="message-text" class="control-label">手机号码:</label>
		            <input type="tel" class="form-control" id="telephone" name="telephone">
		          </div>
		          <div class="form-group">
		            <label for="message-text" class="control-label">权限:</label>
		            <input type="number" class="form-control" id="level" name="level" placeholder="输入1 或 2">
		          </div>
		          <div class="modal-footer">
			        <button type="button" class="btn btn-danger" data-dismiss="modal">关闭</button>
			        <button type="submit" class="btn btn-primary">更改</button>
			      </div>
		        </form>
		      </div>
		    </div>
		  </div>
		</div>
  </body>
</html>
