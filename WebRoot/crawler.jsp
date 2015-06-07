<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
  <%@ include file="header.jsp" %>
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
	            <li><a href="UserServlet?method=GetUsers">管理员管理</a></li>
	            <li><a href="AdminServlet?method=GetLogger">搜索记录</a></li>
	            <li class="active"><a href="crawler.jsp">索引管理</a></li>
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
  			<h5 class="demo-panel-title">爬虫爬取模块</h5>
  			<div class="form-group col-md-2">
               <label class="checkbox" for="checkbox1">
                  <input type="checkbox" data-toggle="checkbox" value="http://www.xixizhan.com/" id="checkbox1" name="seed">
                  www.xixizhan.com
               </label>
            </div>
            <div class="form-group col-md-2">
               <label class="checkbox" for="checkbox2">
                  <input type="checkbox" data-toggle="checkbox" value="http://www.xixihd.com/" id="checkbox2" name="seed">
                  www.xixihd.com
               </label>
            </div>
            <div class="form-group col-md-2">
               <label class="checkbox" for="checkbox3">
                  <input type="checkbox" data-toggle="checkbox" value="http://www.ourrelease.org/" id="checkbox3" name="seed">
                  www.ourrelease.org
               </label>
            </div>
            <div class="form-group col-md-2">
               <label class="checkbox" for="checkbox4">
                  <input type="checkbox" data-toggle="checkbox" value="http://www.bttiantang.com/" id="checkbox4" name="seed">
                  www.bttiantang.com
               </label>
            </div>
            <div class="form-group col-md-2">
               <label class="checkbox" for="checkbox5">
                  <input type="checkbox" data-toggle="checkbox" value="http://www.dygang.com/" id="checkbox5" name="seed">
                  www.dygang.com
               </label>
            </div>
            <div class="form-group col-md-2">
               <label class="checkbox" for="checkbox6">
                  <input type="checkbox" data-toggle="checkbox" value="http://www.dy2018.com/" id="checkbox6" name="seed">
                  www.dy2018.com
               </label>
            </div>
            <div class="form-group col-md-2">
               <label class="checkbox" for="checkbox7">
                  <input type="checkbox" data-toggle="checkbox" value="http://henbt.com/" id="checkbox7" name="seed">
                  henbt.com
               </label>
            </div>
            <div class="form-group col-md-2">
               <label class="checkbox" for="checkbox8">
                  <input type="checkbox" data-toggle="checkbox" value="http://www.3mu.cc/" id="checkbox8" name="seed">
                  www.3mu.cc
               </label>
            </div>
            <div class="form-group col-md-2">
               <label class="checkbox" for="checkbox9">
                  <input type="checkbox" data-toggle="checkbox" value="http://www.dyxz.org/" id="checkbox9" name="seed">
                  www.dyxz.org
               </label>
            </div>
            <div class="form-group col-md-2">
               <label class="checkbox" for="checkbox10">
                  <input type="checkbox" data-toggle="checkbox" value="http://www.zimuku.net/" id="checkbox10" name="seed">
                  www.zimuku.net
               </label>
            </div>
            <div class="form-group col-md-2">
               <label class="checkbox" for="checkbox11">
                  <input type="checkbox" data-toggle="checkbox" value="http://www.yify-torrent.org/" id="checkbox11" name="seed">
                  yify-torrent.org
               </label>
            </div>
            <div class="form-group col-md-2">
               <label class="checkbox" for="checkbox12">
                  <input type="checkbox" data-toggle="checkbox" value="http://yify-torrents.tv/" id="checkbox12" name="seed">
                  yify-torrents.tv
               </label>
            </div>
            <div class="form-group col-md-2">
               <label class="checkbox" for="checkbox13">
                  <input type="checkbox" data-toggle="checkbox" value="http://www.yify-movies.net/" id="checkbox13" name="seed">
                  yify-movies.net
               </label>
            </div>
            <div class="form-group col-md-2">
               <label class="checkbox" for="checkbox14">
                  <input type="checkbox" data-toggle="checkbox" value="http://yify-movie.com/" id="checkbox14" name="seed">
                  yify-movie.com
               </label>
            </div>
            <div class="form-group col-md-2">
               <label class="checkbox" for="checkbox15">
                  <input type="checkbox" data-toggle="checkbox" value="http://www.torrentbar.com/" id="checkbox15" name="seed">
                  www.torrentbar.com
               </label>
            </div>
            <div class="form-group col-md-2">
               <label class="checkbox" for="checkbox16">
                  <input type="checkbox" data-toggle="checkbox" value="http://dm1080p.com/" id="checkbox16" name="seed">
                  dm1080p.com
               </label>
            </div>
            <div class="form-group col-md-2">
               <label class="checkbox" for="checkbox17">
                  <input type="checkbox" data-toggle="checkbox" value="http://dmxz.89dm.com/" id="checkbox17" name="seed">
                  dmxz.89dm.com
               </label>
            </div>
            <div class="form-group col-md-2">
               <label class="checkbox" for="checkbox18">
                  <input type="checkbox" data-toggle="checkbox" value="http://d.dmm.hk/" id="checkbox18" name="seed">
                  d.dmm.hk
               </label>
            </div>
            <div class="button_wrapper">
			    <a href="javascript:startCrawl()" class="btn btn-primary" id="crawl-btn" onclick="return validateCheck();">开始爬取</a>
			</div>
			<label class="crawl" id="crawl-label">正在爬取...</label>
			<div class="button_wrapper button_cancel">
			    <a href="javascript:endCrawl()" class="btn btn-danger" id="end-btn" disabled>结束爬虫</a>
			</div>
			<label class="crawl" id="cancel-label">已停止</label>
			<div class="progress" id="crawl_progress">
			    <div class="progress-bar progress-bar-striped active" id="crawl_progress_bar" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 55%">
			       完成0%
			    </div>
			</div>
			<div class="clear"></div>
			<h5 class="demo-panel-title">网页解析模块</h5>
			<div class="button_extract">
			    <a href="javascript:startExtract()" class="btn btn-primary" id="extract-btn">开始解析</a>
			</div>
			<label class="crawl" id="extract-label">正在解析...</label>
			<div class="progress" id="extract_progress">
			    <div class="progress-bar progress-bar-striped active" id="extract_progress_bar" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 55%">
			       完成0%
			    </div>
			</div>
			<h5 class="demo-panel-title">数据入库模块</h5>
			<div class="button_extract">
			    <a href="javascript:startJDBC()" class="btn btn-primary" id="jdbc-btn">开始入库</a>
			</div>
			<label class="crawl" id="jdbc-label">正在入库...</label>
			<div class="progress" id="jdbc_progress">
			    <div class="progress-bar progress-bar-striped active" id="jdbc_progress_bar" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 45%">
			       完成0%
			    </div>
			</div>
			<h5 class="demo-panel-title">数据建索模块</h5>
			<div class="button_extract">
			    <a href="javascript:startIndexer()" class="btn btn-primary" id="indexer-btn">开始建索</a>
			</div>
			<label class="crawl" id="indexer-label">正在建索...</label>
			<div class="progress" id="indexer_progress">
			    <div class="progress-bar progress-bar-striped active" id="indexer_progress_bar" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 45%">
			      完成0%
			    </div>
			</div>
	    </div>
  		<%@ include file="footer.jsp" %>
  </body>
</html>
