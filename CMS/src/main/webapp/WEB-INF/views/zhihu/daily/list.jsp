<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cloud.valueobject.vo.NewsInfo"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>

<!-- 最新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/css/bootstrap.min.css">

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet"
	href="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/css/bootstrap-theme.min.css">

<link href="http://static.bootcss.com/www/assets/css/site.min.css"
	rel="stylesheet">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script
	src="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/js/bootstrap.min.js"></script>

<!-- <script src="http://cdn.bootcss.com/unveil/1.3.0/jquery.unveil.min.js"></script>
<script src="http://static.bootcss.com/www/assets/js/jquery.scrollUp.min.js"></script>	-->
<script src="http://cdn.bootcss.com/holder/2.0/holder.min.js"></script>

<link
	href="${request.pageContext.contextPath}/static/css/my.site.min.css"
	rel="stylesheet">


<script type="text/javascript">
	function changeIframe(src, e, obj) {
		$("#menu").find("li.selected").removeClass("selected");
		$(obj).addClass("selected");
		$("#backIframe").attr("src", src);
	}

	function getUrlParam(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}

	$(function() {
		var channelId = getUrlParam("channel_id");
		//alert(channelId);
		$("#nav_main li").each(function() {
			if ($(this).attr("value") == channelId) {
				$(this).addClass("active");
			}
			//alert($(this).attr("value"));
		});
	});

	function showImg(url, href) {
		var imgid = Math.random(), frameid = 'frameimg' + imgid;
		window['img' + imgid] = '<a target="_blank" href="'+href+'"> <img id="img" style="height: 300px; display: block;" src=\''
				+ url
				+ '?kilobug\' /> </a> <script>window.onload = function() { parent.document.getElementById(\''
				+ frameid
				+ '\').height = document.getElementById(\'img\').height+\'px\'; }<'+'/script>';
		document
				.write('<iframe id="'
						+ frameid
						+ '" src="javascript:parent[\'img'
						+ imgid
						+ '\'];" frameBorder="0" scrolling="no" width="100%"></iframe>');
	}
</script>
</head>
<body style="padding-top: 70px;">
	<nav class="navbar navbar-default navbar-fixed-top navbar-inverse"
		role="navigation" id="nav_main">

		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">TBS</a>
		</div>

		<%-- <div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<c:forEach items="${listChannel}" var="channel">
					<li value="${channel.id}"><a
						href="${pageContext.request.contextPath}${channel.remark}">${channel.name}</a>
					</li>
				</c:forEach>
			</ul>
		</div> --%>
	</nav>

	<div class="container projects">

		<div class="projects-header page-header">
			<h2>阅读知乎日报,提升逼格</h2>
			<p>${currentDateStrShow}</p>
		</div>



		<div class="row">
			<div>
				<c:if test="${preDateStr!=null}">
					<a style="text-align: left; font-size: 28px"
						href="?date=${preDateStr}">前一天</a>
				</c:if>
				&nbsp;&nbsp;&nbsp;
				<c:if test="${nextDateStr!=null}">
					<a style="text-align: right; font-size: 28px"
						href="?date=${nextDateStr}">后一天</a>
				</c:if>
			</div>
			<c:forEach items="${newsInfoList}" var="newsInfo">
				<div class="col-sm-6 col-md-4 ">
					<div class="thumbnail">
						<%-- <a href="${newsInfo.share_url}"
							title="${newsInfo.title}" target="_blank" onclick="${newsInfo.share_url}"> --%>
						<script type="text/javascript">
							showImg('${newsInfo.image}',
									'${newsInfo.share_url}');
						</script>
						<%-- <img
							src="${newsInfo.image}"
							style="height: 200px; display: block;" src=""> --%>
						<!-- </a> -->
						<div class="caption">
							<h4>
								<a href="${newsInfo.share_url}" title="" target="_blank"
									onclick="">${newsInfo.title}<br>
								</a>
							</h4>
						</div>
					</div>
				</div>

			</c:forEach>
			<%-- <li value="${channel.id}">
					<a href="${pageContext.request.contextPath}${channel.remark}">${channel.name}</a>
				</li> --%>

		</div>
		<div>
			<c:if test="${preDateStr!=null}">
				<a style="text-align: left; font-size: 28px"
					href="?date=${preDateStr}">前一天</a>
			</c:if>
			&nbsp;&nbsp;&nbsp;
			<c:if test="${nextDateStr!=null}">
				<a style="text-align: right; font-size: 28px"
					href="?date=${nextDateStr}">后一天</a>
			</c:if>
		</div>
	</div>

	
	<script type="text/javascript">
		var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
		document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Ff58c5217515b490a0be5bb523c285c5c' type='text/javascript'%3E%3C/script%3E"));
	</script>



	<!-- 多说评论框 start -->
	<div class="ds-thread" data-thread-key="${currentDateStrShow}"
		data-title="" data-url=""></div>
	<!-- 多说评论框 end -->
	<!-- 多说公共JS代码 start (一个网页只需插入一次) -->
	<script type="text/javascript">
		var duoshuoQuery = {
			short_name : "wendellup"
		};
		(function() {
			var ds = document.createElement('script');
			ds.type = 'text/javascript';
			ds.async = true;
			ds.src = (document.location.protocol == 'https:' ? 'https:'
					: 'http:')
					+ '//static.duoshuo.com/embed.js';
			ds.charset = 'UTF-8';
			(document.getElementsByTagName('head')[0] || document
					.getElementsByTagName('body')[0]).appendChild(ds);
		})();
	</script>
	<!-- 多说公共JS代码 end -->

</body>
</html>