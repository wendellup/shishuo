$(document).ready(function () {
	var test = "test";
	$("#d_page1").append(
			"<a onclick＝'alert(1);' style='cursor:pointer'>"+test+"&nbsp;"+"</a>"
	);
	
	window.location.hash = "123";
});
