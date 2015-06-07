/**
 * Javascript for INSO
 */

/**
 * fecth the data from the background
 */
function getDict(){
	//create the dict
	var dict = new Array();
	$.ajax({
		type: "POST",
		url: "DictServlet",
		contentType : "application/json",
		dataType: "json",
		success: function(data){
			var jsonObj = eval(data);
			for(var i = 0; i < jsonObj.length; i++) {
				dict.push(jsonObj[i]);
			}
		},
		error: function(){
			console.log("Network error!");
		}
	});
	
	return dict;
}

//autocomplete javascript
$(function(){
	var availableWords = getDict();
	$("#search_input").autocomplete({
		source: availableWords
	});
	
});

//check the input values
function checkValues() {
	var searchText = $("#search_input").val();
	if(searchText == "") {
		window.location.href = "index.jsp";
		return false;
	}
	return true;
}

//search by page number
function searchByPageNum(pageNum) {
	window.location.href = "EngineServlet?method=SearchByPageNum&pageNum=" + pageNum;
}

//go to previous page
function previousPage() {
	window.location.href = "EngineServlet?method=PreviousPage";
}

//go to next page
function nextPage() {
	window.location.href = "EngineServlet?method=NextPage";
}

/**
 * check the inputs
 * @returns {Boolean}
 */
function checkInputs() {

	var password = $("#password").val();
	var email = $("#email").val();
	var telephone = $("#telephone").val();
	var level = $("#level").val();
	
	if(password == null || password == "") {
		$("#password").focus();
		$("#password").attr("class", "form-control error-field");
		return false;
	}
	else if(email == null || email == "") {
		$("#email").focus();
		$("#email").attr("class", "form-control error-field");
		return false;
	}
	else if(telephone == null || telephone == "") {
		$("#telephone").focus();
		$("#telephone").attr("class", "form-control error-field");
		return false;
	}
	else if(level == null || level == "" || ( level != 1 && level != 2 )) {
		$("#level").focus();
		$("#level").attr("class", "form-control error-field");
		return false;
	}
	
	return true;
}

/**
 * check the inputs
 * @returns {Boolean}
 */
function checkInputs2() {

	var name = $("#name1").val();
	var password = $("#password1").val();
	var email = $("#email1").val();
	var telephone = $("#telephone1").val();
	var level = $("#level1").val();
	
	if(name == null || name == "") {
		$("#name1").focus();
		$("#name1").attr("class", "form-control error-field");
		return false;
	}
	else if(password == null || password == "") {
		$("#password1").focus();
		$("#password1").attr("class", "form-control error-field");
		return false;
	}
	else if(email == null || email == "") {
		$("#email1").focus();
		$("#email1").attr("class", "form-control error-field");
		return false;
	}
	else if(telephone == null || telephone == "") {
		$("#telephone1").focus();
		$("#telephone1").attr("class", "form-control error-field");
		return false;
	}
	else if(level == null || level == "" || ( level != 1 && level != 2 )) {
		$("#level1").focus();
		$("#level1").attr("class", "form-control error-field");
		return false;
	}
	
	return true;
}

/**
 * add the user
 */
function addUser() {
	$("#register_area").css("display", "block");
}

/**
 * cancel adding
 */
function hideUser() {
	$("#register_area").css("display", "none");
}

/**
 * validate the chekc state of the checkbox
 * @returns {Boolean}
 */
function validateCheck() {
	var seeds  = $('input[name="seed"]:checked');
	var length = seeds.length;
	if(length == 0) {
		alert("请选择种子URL!");
		return false;
	}
	return true;
}

var crawlInterval = null;

/**
 * start crawler
 */
function startCrawl() {
	var seeds = [];
	$('input[name="seed"]:checked').each(function(){
		seeds.push($(this).val());
	});
	$("#crawl-btn").attr("disabled",true);
	$("#crawl-label").css("display","inline-block");
	$("#end-btn").attr("disabled",false);
	$("#cancel-label").css("display","none");
	
	$("#extract-btn").attr("disabled",true);
	$("#jdbc-btn").attr("disabled",true);
	$("#indexer-btn").attr("disabled",true);
	
	$.ajax({
		type: "POST",
		url : "ControllerServlet?method=Crawl&op=start",
		data: { "seeds": seeds},
		success: function(data) {
			if(data == 1) {
				crawlInterval = window.setInterval("crawlProgress()", 50);
			}
		},
		error: function() {
			alert("Network Error!");
		}
	});
}

/**
 * progress bar
 */
function crawlProgress() {
	$.ajax({
		type: "POST",
		url : "ControllerServlet?method=Crawl&op=progress",
		success: function(data) {
			$("#crawl_progress").css("display","block");
			$("#crawl_progress_bar").css("width",data);
			$("#crawl_progress_bar").text("已完成"+data);
		},
		error: function() {
			alert("Network Error!");
		}
	});
}

/**
 * end crawler
 */
function endCrawl() {
	
	$.ajax({
		type: "POST",
		url: "ControllerServlet?method=Crawl&op=end",
		success: function(data) {
			if(data == 1) {
				$("#crawl-btn").attr("disabled",false);
				$("#crawl-label").css("display","none");
				$("#end-btn").attr("disabled",true);
				$("#cancel-label").css("display","inline-block");
				$("#crawl_progress").css("display","none");
				window.clearInterval(crawlInterval);
				
				$("#extract-btn").attr("disabled",false);
				$("#jdbc-btn").attr("disabled",false);
				$("#indexer-btn").attr("disabled",false);
			}else if(data == 0) {
				$("#cancel-label").val("终止失败！请重试！");
				$("#cancel-label").css("display","inline-block");
			}
		}, 
		error: function() {
			alert("Network Error!");
		}
	});
}

var extracterInterval = null;

/**
 * start extracter
 */
function startExtract() {
	
	$("#extract-btn").attr("disabled",true);
	$("#extract-label").css("display","inline-block");
	
	$("#crawl-btn").attr("disabled",true);
	$("#jdbc-btn").attr("disabled",true);
	$("#indexer-btn").attr("disabled",true);
	
	$.ajax({
		type: "POST",
		url : "ControllerServlet?method=Extract&op=start",
		success: function(data) {
			if(data == 1) {
				extracterInterval = window.setInterval("extractProgress()", 50);
			}
		},
		error: function() {
			alert("Network Error!");
		}
	});
}

/**
 * progress bar
 */
function extractProgress() {
	$.ajax({
		type: "POST",
		url : "ControllerServlet?method=Extract&op=progress",
		success: function(data) {
			if(data == 1) {
				$("#extract_progress").css("display","block");
				$("#extract_progress_bar").css("width","100%");
				$("#extract_progress_bar").text("已完成100%");
				
				window.clearInterval(extracterInterval);
				
				$("#crawl-btn").attr("disabled",false);
				$("#jdbc-btn").attr("disabled",false);
				$("#indexer-btn").attr("disabled",false);
				$("#extract-btn").attr("disabled",false);
				$("#extract-label").css("display","none");
			}else{
				$("#extract_progress").css("display","block");
				$("#extract_progress_bar").css("width",data);
				$("#extract_progress_bar").text("已完成"+data);
			}
		},
		error: function() {
			alert("Network Error!");
		}
	});
}

var jdbcInterval = null;

/**
 * start jdbc
 */
function startJDBC() {
	
	$("#jdbc-btn").attr("disabled",true);
	$("#jdbc-label").css("display","inline-block");
	
	$("#crawl-btn").attr("disabled",true);
	$("#extract-btn").attr("disabled",true);
	$("#indexer-btn").attr("disabled",true);
	
	$.ajax({
		type: "POST",
		url : "ControllerServlet?method=JDBC&op=start",
		success: function(data) {
			if(data == 1) {
				jdbcInterval = window.setInterval("jdbcProgress()", 50);
			}
		},
		error: function() {
			alert("Network Error!");
		}
	});
}

/**
 * progress bar
 */
function jdbcProgress() {
	$.ajax({
		type: "POST",
		url : "ControllerServlet?method=JDBC&op=progress",
		success: function(data) {
			if(data == 1) {
				$("#jdbc_progress").css("display","block");
				$("#jdbc_progress_bar").css("width","100%");
				$("#jdbc_progress_bar").text("已完成100%");
				
				window.clearInterval(jdbcInterval);
				
				$("#crawl-btn").attr("disabled",false);
				$("#extract-btn").attr("disabled",false);
				$("#indexer-btn").attr("disabled",false);
				$("#jdbc-btn").attr("disabled",false);
				$("#jdbc-label").css("display","none");
			}else{
				$("#jdbc_progress").css("display","block");
				$("#jdbc_progress_bar").css("width",data);
				$("#jdbc_progress_bar").text("已完成"+data);
			}
		},
		error: function() {
			alert("Network Error!");
		}
	});
}

var indexerInterval = null;

/**
 * start indexer
 */
function startIndexer() {
	
	$("#indexer-btn").attr("disabled",true);
	$("#indexer-label").css("display","inline-block");
	
	$("#crawl-btn").attr("disabled",true);
	$("#extract-btn").attr("disabled",true);
	$("#jdbc-btn").attr("disabled",true);
	
	$.ajax({
		type: "POST",
		url : "ControllerServlet?method=Indexer&op=start",
		success: function(data) {
			if(data == 1) {
				indexerInterval = window.setInterval("indexerProgress()", 50);
			}
		},
		error: function() {
			alert("Network Error!");
		}
	});
}

/**
 * progress bar
 */
function indexerProgress() {
	$.ajax({
		type: "POST",
		url : "ControllerServlet?method=Indexer&op=progress",
		success: function(data) {
			if(data == 1) {
				$("#indexer_progress").css("display","block");
				$("#indexer_progress_bar").css("width","100%");
				$("#indexer_progress_bar").text("已完成100%");
				
				window.clearInterval(indexerInterval);
				
				$("#crawl-btn").attr("disabled",false);
				$("#extract-btn").attr("disabled",false);
				$("#jdbc-btn").attr("disabled",false);
				$("#indexer-btn").attr("disabled",false);
				$("#indexer-label").css("display","none");
			}else{
				$("#indexer_progress").css("display","block");
				$("#indexer_progress_bar").css("width",data);
				$("#indexer_progress_bar").text("已完成"+data);
			}
		},
		error: function() {
			alert("Network Error!");
		}
	});
}
