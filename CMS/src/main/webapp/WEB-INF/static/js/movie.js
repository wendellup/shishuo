$(document).ready(function () {
//	var gobalHtmlUrlVal = window.location.pathname+window.location.search;
	var currentPage = getQueryString("current_page");
	if(currentPage==null){
		currentPage = 0;
	}
	var rowsOfPage = getQueryString("page_size");
	if(rowsOfPage==null){
		rowsOfPage = 20;
	}
	loadData(currentPage, rowsOfPage);
});

function changeUrl(current_page, rows_of_page){
	var htmlUrlVal = "?current_page="+current_page+"&page_size="+rows_of_page;
	window.location.search = htmlUrlVal;
}

function loadData(current_page, rows_of_page){
//	var htmlUrlVal = "?current_page="+current_page+"&page_size="+rows_of_page;
	var jsonurlVal = "/movie/list/current_page/"+current_page+"/page_size/"+rows_of_page;
	//清空原始数据
	$("#t1").html("");
	$("#d_page1").html("");
	$("#d_page2").html("");
	
	
	//电影数据
	$.ajax({ url: jsonurlVal, dataType: "json" })
    .done(function (result) {//ajax的done解析result
        $.each(result.content, function (i,item) {
        	$("#t1").append(
                    "<tr>" +
                    	"<td>" + item.id + "</td>" + 
	                    "<td><a href='movie_detail.html?id="+item.id+"'>" + item.movieName+ "</a></td>" +
	                    "<td>" + item.doubanId + "</td>" +
                    "</tr>");
        });
    });
	
	/** 分页相关数据 */
	//1.分页插件
	$.ajax({ url: jsonurlVal, dataType: "json" })
    .done(function (result) {//ajax的done解析result
        	for(var p=0; p<result.page_count; p++){
        		if(p==result.current_page){
        			$("#d_page1").append((p+1)+"&nbsp;");
        		}else{
        			$("#d_page1").append(
//        					"<a href=/movie/list/current_page/"+p+"/page_size/"+result.rows_of_page+">"+p+"&nbsp;"+"</a>"
//        					"<a onclick='loadData("+p+","+result.rows_of_page+")' style='cursor:pointer'>"+(p+1)+"&nbsp;"+"</a>"
        					"<a onclick='changeUrl("+p+","+result.rows_of_page+")' style='cursor:pointer'>"+(p+1)+"&nbsp;"+"</a>"
        					
        			);
        		}
        	}
    });
	//2.总页数及每页条数
	$.ajax({
		url : jsonurlVal,
		dataType : "json"
	}).done(
			function(result) {// ajax的done解析result
				$("#d_page2").append(
						"总条数为:" + result.total + "&nbsp;每页条数"
								+ result.rows_of_page);
			});
	
}