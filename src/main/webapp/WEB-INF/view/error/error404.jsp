<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="../common/head.jspf" %>
</head>
<body>
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top">
	    <div class="container-fluid">
	        <ul class="navbar-nav">
	            <a class="navbar-brand ms-5 me-5" href="/bbs/board/list?p=1&f=&q">
	                <img src="/bbs/img/ckworld-logo.png" alt="Logo" style="height:36px;" class="rounded-3">
	            </a>            
	        </ul>
	    </div>
	</nav>
	<div class="container" style="margin-top: 300px;">
		<div class="row">
			<div class="col-2"></div>
			<div class="col-8">
				<h3><strong>에러 페이지</strong></h3>
				<hr>
				<h1>요청한 페이지는 존재하지 않습니다.</h1>
			</div>
			<div class="col-2"></div>
		</div>
	</div>
	<%@ include file="../common/bottom.jspf" %>
	
</body>
</html>