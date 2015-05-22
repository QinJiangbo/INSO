<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">
  <%@ include file="header.jsp" %>
  <script>
  	  $(window).load(function(){
  	  	  var ua = navigator.userAgent;
  	  	  if(/AppleWebKit.*Mobile/i.test(ua) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(ua))){
  	  	  	 //mobile
  	  	  	 if(!localStorage.confirmResult || localStorage.confirmResult == "NO"){
	  	  	  	 if(ua.indexOf("Android") > 0 || ua.indexOf("android") > 0){
	  	  	  	 	if(confirm("建议你切换到Android版，体验会更好！")){
	  	  	  	 		window.location.href = "mobileIndex.jsp";
	  	  	  	 		localStorage.confirmResult = "OK";
	  	  	  	 	}
	  	  	  	 	else{
	  	  	  	 		localStorage.confirmResult = "NO";
	  	  	  	 	}
	  	  	  	 }
	  	  	  	 else if(ua.indexOf("iPhone") > 0 || ua.indexOf("iphone") > 0) {
	  	  	  	 	if(confirm("建议你切换到iPhone版，体验会更好！")){
	  	  	  	 		window.location.href = "mobileIndex.jsp";
	  	  	  	 		localStorage.confirmResult = "OK";
	  	  	  	 	}
	  	  	  	 	else{
	  	  	  	 		localStorage.confirmResult = "NO";
	  	  	  	 	}
	  	  	  	 }
	  	  	  	 else {
	  	  	  	 	if(confirm("建议你切换到移动版，体验会更好！")){
	  	  	  	 		window.location.href = "mobileIndex.jsp";
	  	  	  	 		localStorage.confirmResult = "OK";
	  	  	  	 	}
	  	  	  	 	else{
	  	  	  	 		localStorage.confirmResult = "NO";
	  	  	  	 	}
	  	  	  	 }
  	  	  	 }
  	  	  	 else{
  	  	  	 	 window.location.href = "mobileIndex.jsp";
  	  	  	 }
  	  	  }
  	  	  else{
  	  	  	//pc
  	  	  }
  	  });
  </script>
  <body>
	<div class="container" id="container">
		<div class="headline">
			<h1 class="inso-logo">
				INSO
			</h1>
		</div>
		<form action="EngineServlet?method=CommonSearch" method="post" onsubmit="return checkValues()" role="search">
			<div class="form-group">
				<div class="input-group">
					<input type="text" class="form-control search-input" name="search_Text" id="search_input" placeholder="动漫、电影、明星、字幕">
					<span class="input-group-btn">
						<button class="btn btn-default btn-search" type="submit">应搜搜索</button>
					</span>
				</div>
			</div>
		</form>
	</div>
	<%@ include file="footer.jsp" %>
</body>
</html>
