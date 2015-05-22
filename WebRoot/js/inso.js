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
			console.log(dict[9]);
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
