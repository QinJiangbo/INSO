<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<!--HTML5 doctype-->
<html>
	<%@ include file="mobileHeader.jsp" %>
	<script>
		$(document).ready(function(){
			$("#type").val("Mobile");
		});
	</script>
	<script type="text/javascript" src="http://whois.pconline.com.cn/ipJson.jsp?callback=myip"></script>
	<script>
        $.getJSON("http://ip-api.com/json/?callback=?", function(data) {
            var city = data["city"];
            var province = data["regionName"];
            $("#province").val(province);
      	 	$("#city").val(city);
        });
  	</script>
<body>
	<div class="view" id="mainview">
		<header>
			<a href="mobileIndex.jsp">INSO</a>
		</header>

		<div class="pages" id="pages">
			<div class="panel" id="main2">
				<form action="MobileEngineServlet?method=CommonSearch" onsubmit="return checkValues()" method="post" id="search-form">
	                <div id="input-group">
	                	<c:choose>
			              	<c:when test="${ empty searchText }">
			              		<input type="text" class="search-input" id="search-input" placeholder="动漫、电影、明星、字幕" name="search_Text" >
			              		<input type="hidden" name="province" id="province">
								<input type="hidden" name="city" id="city">
								<input type="hidden" name="type" id="type">
			              	</c:when>
			              	<c:otherwise>
			              		<input type="text" class="search-input" id="search-input" placeholder="动漫、电影、明星、字幕" name="search_Text" value="${ searchText }" >
			              		<input type="hidden" name="province" id="province">
								<input type="hidden" name="city" id="city">
								<input type="hidden" name="type" id="type">
			              	</c:otherwise>
			             </c:choose>
	                	<span class="input-group-btn">
					        <button class="btn btn-default btn-search" type="submit">应搜搜索</button>
					     </span>
	                </div>
                </form>
                <div id="content">
			  		<c:choose>
			  			<c:when test="${ empty searchText }">
			  				<div id="noReuslt">
			  					输入你喜欢的动漫，明星或者是电影，按“回车”键发起检索
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
						  	 	 	<div class="title"><a href="${ film.URL}" target="_blank" data-ignore="true">${ film.Title }</a></div>
						  	 	 	<div class="abstract">${ film.Content }</div>
						  	 	 	<div class="publish-time"><b>更新日期</b>&nbsp;${ film.PublishTime }</div>
						  	 	 	<div class="original-url"><b>来源地址</b>&nbsp;${ film.URL }</div>
					  	 	 	</div>
					  		</c:forEach>
			  			</c:otherwise>
			  		</c:choose>
  				</div>
  				<div id="next-page">
  					<c:choose>
			  			<c:when test="${ empty searchText }">
			  			</c:when>
			  			<c:when test="${ empty filmList }">
			  			</c:when>
			  			<c:when test="${ totalPages eq currentPage }">
			  			</c:when>
			  			<c:otherwise>
			  				<a class="btn btn-default btn-next" href="javascript:void(0)" onclick="fetchNextPage(${currentPage}, ${ totalPages })" id="btn-next">下一页</a>
			  				<div class="sk-spinner sk-spinner-wave" id="wave-effect">
						      	<div class="sk-rect1"></div>
						      	<div class="sk-rect2"></div>
						      	<div class="sk-rect3"></div>
						      	<div class="sk-rect4"></div>
						      	<div class="sk-rect5"></div>
						    </div>
			  			</c:otherwise>
			  		</c:choose>
  				</div>
			</div>
		</div>
		<footer>
			<p>&copy;2015-2016&nbsp;本搜索引擎由秦江波独立设计并研发&nbsp;保留所有权利</p>
		</footer>
	</div>
</body>
</html>
