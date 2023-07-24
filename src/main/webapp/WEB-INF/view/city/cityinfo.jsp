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
    	
	    	<h2> ${cname} 도시의 정보 </h2>
	    	<hr>
	    	<div class="col-sm-6">
	    	<c:if test="${not empty city}">
				<table class="table table-borderless mt-2 text-center" style="margin-bottom: 50px;">
	            	<tr>
                        <td style="width:20%"><label class="col-form-label">도시 ID</label></td>
                        <td style="width:80%"><label class="col-form-label">${city.id}</label></td>
                    </tr>
                    <tr>
                        <td style="width:20%"><label class="col-form-label">CountryCode</label></td>
                        <td style="width:80%"><label class="col-form-label">${city.countryCode}</label></td>
                    </tr>
                    <tr>
                        <td style="width:20%"><label class="col-form-label">시/군구 단위</label></td>
                        <td style="width:80%"><label class="col-form-label">${city.district}</label></td>
                    </tr>
                    <tr>
                        <td style="width:20%"><label class="col-form-label">인구수</label></td>
                        <td style="width:80%"><label class="col-form-label"><fmt:formatNumber type="number" value="${city.population}" /></label></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center;">
                            <input class="btn btn-primary" type="button" value="수정" onclick="location.href='/bbs/city/cityupdate?cid=${city.id}'">
                            <input class="btn btn-secondary" type="button" value="삭제"  onclick="location.href='/bbs/city/citydelete?cid=${city.id}&cname=${cname}'">
                        </td>
                    </tr>
		        </table>
	       	</c:if>
	       	<c:if test="${ empty city}">
	       		<h5>도시의 정보가 없습니다.</h5>
	       	</c:if>
			</div>	
    	</div>
    </div>
</body>
</html>