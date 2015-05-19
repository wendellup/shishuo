$(document).ready(function () {
	movieName = "";
//	var gobalHtmlUrlVal = window.location.pathname+window.location.search;
//	var currentPage = getQueryString("current_page");
//	if(currentPage==null){
//		currentPage = 0;
//	}
//	var rowsOfPage = getQueryString("page_size");
//	if(rowsOfPage==null){
//		rowsOfPage = 20;
//	}
//	loadData(currentPage, rowsOfPage);
	var id = getQueryString("id");
	loadData(id);
//	alert("end loadData---->"+movieName);
	if(movieName!=""){
		loadTorrent(movieName);
	}
});

function loadData(id){
	var url = "/movie/detail/id/" + id;
	$.ajax({
		url : url,
		dataType : "json",
		async:false,
	}).done(function(result) {// ajax的done解析result
		$("#d_page1").append("电影名称为:" + result.movieName);
		movieName = result.movieName;
	});
//	alert("in loadData-------->"+movieName);
}

function loadTorrent(movieName){
	var url = "/movie/resource/key/"+encodeURIComponent(movieName)+"/current_page/0/type/2";
	$.ajax({ url: url, dataType: "json" })
    .done(function (result) {//ajax的done解析result
        $.each(result, function (i,item) {
        	$("#t1").append(
                    "<tr>" +
                    	"<td><a href='"+item.torrentUrl+"'>" + item.torrentName + "</a></td>" + 
                    "</tr>");
        });
    });
}

/*
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
                    	"<td><a href='movie_detail.html'>" + item.id + "</a></td>" + 
	                    "<td>" + item.movieName+ "</td>" +
	                    "<td>" + item.doubanId + "</td>" +
                    "</tr>");
        });
    });
	
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
*/