<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
  <%@ include file="header.jsp" %>
  <body id="login">
  <!-- <input type="hidden" value=${ state } id="hidden"/> -->
  <script type="text/javascript">
  	  $(window).load(function(){
  	  	  var state = ${ state }
  	  	  if(state == false) {
  	  	  	  $("#login-pass").val("");
  	  	  	  $("#login-pass").focus();
  	  	  }
  	  });
  	  
  	  $(document).ready(function(){
  	  	  var loginState = ${ loginState };
  	  	  if(loginState == true) {
  	  	  	 window.location.href = "admin.jsp";
  	  	  }
  	  });
  </script>
  	<div class="login-logo">
  		INSO
  	</div>
	<div class="login">
        <div class="login-screen">
          <div class="login-form">
          <form action="AdminServlet?method=Login" method="post">
            <div class="form-group">
              <input type="text" class="form-control login-field" value="${ userName }" placeholder="管理员账号" name="userName" id="login-name" />
              <label class="login-field-icon fui-user" for="login-name"></label>
            </div>
            <div class="form-group">
            <c:choose>
            	<c:when test="${ state ne false }">
            		<input type="password" class="form-control login-field" value="" placeholder="管理员密码" name="userPassword" id="login-pass" />
              		<label class="login-field-icon fui-lock" for="login-pass"></label>
            	</c:when>
            	<c:otherwise>
            		<input type="password" class="form-control error-field" value="" placeholder="管理员密码" name="userPassword" id="login-pass" />
              		<label class="login-field-icon fui-lock" for="login-pass"></label>
            	</c:otherwise>
            </c:choose>
            </div>
            <button class="btn btn-primary btn-lg btn-block" type="submit">登录</button>
            <a class="login-link" href="#">忘记密码？</a>
            </form>
          </div>
        </div>
      </div>
      <div id="footer" class="login-footer">
		<p>&copy;2015-2016&nbsp;&nbsp;&nbsp;本搜索引擎由秦江波开发&nbsp;&nbsp;保留所有权利</p>
	</div>
</body>
</html>
