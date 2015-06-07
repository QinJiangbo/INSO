<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
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
		<div class="pages">
			<div class="panel" id="main">
				<div id="inso">
                    INSO
                </div>
                <form action="MobileEngineServlet?method=CommonSearch" method="post" onsubmit="return checkValues()" id="index-form">
	                <div id="input-group">
	                	<input type="text" class="search-input" id="search-input" placeholder="动漫、电影、明星、字幕" name="search_Text">
	                	<input type="hidden" name="province" id="province">
						<input type="hidden" name="city" id="city">
						<input type="hidden" name="type" id="type">
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
