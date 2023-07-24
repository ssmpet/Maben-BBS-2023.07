<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
	
	<%@ include file="../city/cityhead.jspf" %>
	<style>
		td { text-align: center; }
	</style>
	
</head>
<body>
	<%@ include file="../city/citytop.jspf" %>

	<div class="container" style="margin-top: 80px;">
    	<div class="row">
    	
	    	<h2>도시 정보 삭제</h2>
	    	<hr>
	    	<div class="col-sm-6">
			<div class="col-6">
	            <div class="card border-warning mt-3">
	            	<div class="card-body">
	            		<strong class="card-title">삭제하시겠습니까?</strong>
	            		<p class="card-text text-center">
	            			<br>
	            			<button class="btn btn-primary" onclick="location.href='/bbs/city/deleteConfirm?cid=${cid}'">삭제</button>
	            			<button class="btn btn-secondary" onclick="location.href='/bbs/city/cityinfo?cname=${cname}'">취소</button>
	            		</p>
	            	</div>
	            </div>
		    </div>
	       
			</div>	
    	</div>
    </div>
</body>
</html>