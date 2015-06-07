<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
  	  	  	$("#type").val("PC");
  	  	  }
  	  	  
  	  	  if(!localStorage.alert){
 	  	  	alert("INSO温馨提示:如果你遭遇了很恶心的广告，那是ISP服务商比如电信搞的鬼，手机用户建议开启UC浏览器设置中的广告强力拦截，PC用户建议你使用Chrome或Firefox并安装广告终结者插件。么么哒！");
 	  	  	localStorage.alert = true;
 	  	  }
  	  });
  </script>
  <script>
        $.getJSON("http://ip-api.com/json/?callback=?", function(data) {
            var city = data["city"];
            var province = data["regionName"];
            $("#province").val(province);
      	 	$("#city").val(city);
        });
  </script>
  <body>
  	<div id="topbar">
		<nav class="navbar navbar-default" role="navigation">
	      <div class="navbar-header">
	        <a class="navbar-brand" href="index.jsp">INSO</a>
	      </div>
	      <div class="collapse navbar-collapse" id="navbar-collapse-5">
	        <form class="navbar-form navbar-left" action="EngineServlet?method=CommonSearch" method="post" onsubmit="return checkValues()" role="search">
	          <div class="form-group">
	            <div class="input-group">
	              <c:choose>
	              	<c:when test="${ empty searchText }">
	              		<input class="form-control search_bar" id="search_input" name="search_Text" type="search" placeholder="动漫、电影、明星、字幕" />
	              		<input type="hidden" name="province" id="province">
						<input type="hidden" name="city" id="city">
						<input type="hidden" name="type" id="type">
	              	</c:when>
	              	<c:otherwise>
	              		<input class="form-control search_bar" id="search_input" name="search_Text" type="search" placeholder="动漫、电影、明星、字幕" value="${ searchText }" />
	              		<input type="hidden" name="province" id="province">
						<input type="hidden" name="city" id="city">
						<input type="hidden" name="type" id="type">
	              	</c:otherwise>
	              </c:choose>
	              <span class="input-group-btn">
	              	<button class="btn btn-default bar_btn" type="submit">应搜搜索</button>
	              </span>
	            </div>
	          </div>
	        </form>
	      </div>
	   	</nav>
  	</div>
  	<div id="content">
  		<c:choose>
  			<c:when test="${ empty searchText }">
  				<div id="noReuslt">
  					输入你喜欢的明星或者是电影，按“回车”键发起检索
  				</div>
  			</c:when>
  			<c:when test="${ empty filmList }">
  				<div id="noReuslt">
  					对不起，没有搜索到你想要查找的电影，请换一个关键词再试试吧！
  				</div>
  			</c:when>
  			<c:otherwise>
  				<c:forEach items="${ filmList }" var="film">
		  			<div class="unit">
			  	 	 	<div class="title"><a href="${ film.URL}" target="_blank">${ film.Title }</a></div>
			  	 	 	<div class="abstract">${ film.Content }</div>
			  	 	 	<div class="link">
			  	 	 		<div class="publish_Time">更新于&nbsp;&nbsp;&nbsp;${ film.PublishTime }</div>
			  	 	 		<div class="original_URL">来源:&nbsp;&nbsp;&nbsp;${ film.URL }</div>
			  	 	 	</div>
		  	 	 	</div>
		  		</c:forEach>
  			</c:otherwise>
  		</c:choose>
  	</div>
  	<c:if test="${ !empty searchText && totalPages ne 0 }">
  		<div id="page">
	  		<div class="pagination pagination-success">
	  		<c:choose>
	  			<c:when test="${ currentPage eq 1 }">
	  				<a href="#fakelink" class="btn btn-primary previous disabled">
		              <i class="fui-arrow-left"></i>
		             	 上一页
		            </a>
	  			</c:when>
	  			<c:otherwise>
	  				<a href="javascript:" onclick="previousPage()" class="btn btn-primary previous">
		              <i class="fui-arrow-left"></i>
		             	 上一页
		            </a>
	  			</c:otherwise>
	  		</c:choose>
	            <ul>
	            <c:choose>
	            	<c:when test="${ totalPages ge 10 }">
	            		<c:choose>
	            			<c:when test="${ currentPage ge 7 && (currentPage + 4) le totalPages }">
	            				<c:forEach begin="${ currentPage - 5 }" end="${ currentPage }" step="1" varStatus="status">
					            	<c:choose>
					            		<c:when test="${ status.index eq currentPage }">
					            			<li class="active"><a href="javascript:">${ currentPage }</a></li>
					            		</c:when>
					            		<c:otherwise>
					            			<li><a href="javascript:" onclick="searchByPageNum(${ status.index })">${ status.index }</a></li>
					            		</c:otherwise>
					            	</c:choose>
					            </c:forEach>
					            <c:forEach begin="${ currentPage + 1 }" end="${ currentPage + 4 }" step="1" varStatus="status">
					            	<c:choose>
					            		<c:when test="${ status.index eq currentPage }">
					            			<li class="active"><a href="javascript:">${ currentPage }</a></li>
					            		</c:when>
					            		<c:otherwise>
					            			<li><a href="javascript:" onclick="searchByPageNum(${ status.index })">${ status.index }</a></li>
					            		</c:otherwise>
					            	</c:choose>
					            </c:forEach>
	            			</c:when>
	            			<c:when test="${ currentPage ge 7 && (currentPage + 4) ge totalPages }">
	            				<c:forEach begin="${ totalPages - 9 }" end="${ currentPage }" step="1" varStatus="status">
					            	<c:choose>
					            		<c:when test="${ status.index eq currentPage }">
					            			<li class="active"><a href="javascript:">${ currentPage }</a></li>
					            		</c:when>
					            		<c:otherwise>
					            			<li><a href="javascript:" onclick="searchByPageNum(${ status.index })">${ status.index }</a></li>
					            		</c:otherwise>
					            	</c:choose>
					            </c:forEach>
					            <c:forEach begin="${ currentPage + 1 }" end="${ totalPages }" step="1" varStatus="status">
					            	<c:choose>
					            		<c:when test="${ status.index eq currentPage }">
					            			<li class="active"><a href="javascript:">${ currentPage }</a></li>
					            		</c:when>
					            		<c:otherwise>
					            			<li><a href="javascript:" onclick="searchByPageNum(${ status.index })">${ status.index }</a></li>
					            		</c:otherwise>
					            	</c:choose>
					            </c:forEach>
	            			</c:when>
	            			<c:otherwise>
	            				<c:forEach begin="1" end="10" step="1" varStatus="status">
					            	<c:choose>
					            		<c:when test="${ status.index eq currentPage }">
					            			<li class="active"><a href="javascript:">${ currentPage }</a></li>
					            		</c:when>
					            		<c:otherwise>
					            			<li><a href="javascript:" onclick="searchByPageNum(${ status.index })">${ status.index }</a></li>
					            		</c:otherwise>
					            	</c:choose>
					            </c:forEach>
	            			</c:otherwise>
	            		</c:choose>
	            	</c:when>
	            	<c:otherwise>
	            		<c:forEach begin="1" end="${ totalPages }" step="1" varStatus="status">
			            	<c:choose>
			            		<c:when test="${ status.index eq currentPage }">
			            			<li class="active"><a href="javascript:">${ currentPage }</a></li>
			            		</c:when>
			            		<c:otherwise>
			            			<li><a href="javascript:" onclick="searchByPageNum(${ status.index })">${ status.index }</a></li>
			            		</c:otherwise>
			            	</c:choose>
			            </c:forEach>
	            	</c:otherwise>
	            </c:choose>
	            </ul>
	            <c:choose>
		  			<c:when test="${ currentPage eq totalPages }">
		  				<a href="#fakelink" class="btn btn-primary next disabled">
			              	下一页
			              <i class="fui-arrow-right"></i>
			            </a>
		  			</c:when>
		  			<c:otherwise>
		  				<a href="javascript:" onclick="nextPage()" class="btn btn-primary next">
			              	下一页
			              <i class="fui-arrow-right"></i>
			            </a>
		  			</c:otherwise>
		  		</c:choose>
	          </div>
  		</div>
  	</c:if>
  	<%@ include file="footer.jsp" %>
  </body>
</html>
