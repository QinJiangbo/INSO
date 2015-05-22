<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<!--HTML5 doctype-->
<html>
	<%@ include file="mobileHeader.jsp" %>
<body>
	<div class="view" id="mainview">
		<div class="pages">
			<div class="panel" id="main">
				<div id="inso">
                    INSO
                </div>
                <form action="MobileEngineServlet?method=CommonSearch" method="post" onsubmit="return checkValues()" id="index-form">
	                <div id="input-group">
	                	<input type="text" class="search-input" id="search-input" placeholder="动漫、电影、明星、字幕" name="search_Text">
	                	<span class="input-group-btn">
					        <button class="btn btn-default btn-search" type="submit">应搜搜索</button>
					     </span>
	                </div>
                </form>
			</div>
		</div>
		<footer>
			<p>&copy;2015-2016&nbsp;本搜索引擎由秦江波独立设计并研发&nbsp;保留所有权利</p>
		</footer>
	</div>
</body>
</html>
