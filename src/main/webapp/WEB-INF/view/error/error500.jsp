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
			<div class="col-3"></div>
			<div class="col-6">
				<h3><strong>에러 페이지</strong></h3>
				<hr>
				<h1>죄송합니다. <br>
				서비스 실행중 오류가 발생하였습니다.<br>
				잠시후 다시 시도해 보세요.</h1>
			</div>
			<div class="col-5"></div>
		</div>
	</div>
	<%@ include file="../common/bottom.jspf" %>
	
</body>
</html>