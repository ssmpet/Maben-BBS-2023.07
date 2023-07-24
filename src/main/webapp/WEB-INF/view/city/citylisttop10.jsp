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
    	
	    	<h2> 1. 국내 도시중 인구수가 많은 Top 10 도시 </h2>
	    	<hr>
	    	<div class="col-sm-9">
				<table class="table mt-2 text-center" style="margin-bottom: 50px;">
		            <tr class="table-secondary">
		                <th width="10%">ID</th>
		                <th width="10%">도시명</th>
		                <th width="10%">CountryCode</th>
		                <th width="20%">광역 시/도</th>
		                <th width="20%">인구</th>
		            </tr>
	            <c:forEach var="city" items="${cityList}">
	            	<tr>
	            		<td>${city.id}</td>
	            		<td>${city.name}</td>
	            		<td>${city.countryCode}</td>
	            		<td>${city.district}</td>
	            		<td><fmt:formatNumber type="number" value="${city.population}" /></td>	            		
	            	</tr>
	            </c:forEach>
		        </table>
			</div>	
    	</div>
    </div>
</body>
</html>