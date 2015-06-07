/**
 * Javascript for INSO mobile version
 */

/**
 * check the input values
 */
function checkValues() {
	var searchText = $("#search-input").val();
	if(searchText == "") {
		window.location.href = "mobileIndex.jsp";
		return false;
	}
	return true;
}

/**
 * get the next 10 pages in order
 */
function fetchNextPage(currentPage, totalPages) {
	$("#btn-next").css("display","none");
	$("#wave-effect").css("display","block");
	var length = 0;
	$.ajax({
		type: "POST",
		url:"MobileEngineServlet?method=NextPage",
		success: function(data){
			$("#btn-next").css("display","block");
			$("#wave-effect").css("display","none");
			// Here, remeber to notify the case sensitive!
			var list = eval("("+data+")");
			length = list.length;
			var startIndex = currentPage * 10;
			var endIndex = startIndex + length;
			startIndex = startIndex + 1;
			
			currentPage = currentPage + 1;
			if(currentPage == totalPages){
				var refreshStr = '<div id="next-page"><div class="no-more">哦都尅！只能搜到这些了诶~^_^</div></div>';
				$("#next-page").replaceWith(refreshStr);
				if(length != 0){
					var appendStr = '<div class="indicator">'+startIndex+'-'+endIndex+'条记录</div>';
					$("#content").append(appendStr);
				}
			}
			else{
				var appendStr = '<div class="indicator">'+startIndex+'-'+endIndex+'条记录</div>';
				$("#content").append(appendStr);
				$("#btn-next").attr("onclick","fetchNextPage("+ currentPage + "," + totalPages +")");
			}
			
			$.each(list, function(i, item){
				var title = item.Title;
				var content = item.Content;
				var publishTime = item.PublishTime;
				var URL = item.URL;
				appendStr = '<div class="unit"><div class="title"><a href="'+URL+'" target="_blank" data-ignore="true">'+title+'</a></div> <div class="abstract">'+content+'</div><div class="publish-time"><b>更新日期</b>&nbsp;'+publishTime+'</div><div class="original-url"><b>来源地址</b>&nbsp;'+URL+'</div> </div>';
				$("#content").append(appendStr);
			});
			
		},
		error: function(){
			alert("Network Error!");
		}
	});
}