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
    	
	    	<h2>도시 정보 수정</h2>
	    	<hr>
	    	<div class="col-sm-6">
			<form action="/bbs/city/cityupdate" method="post">
				<table class="table table-borderless mt-2 text-center" style="margin-bottom: 50px;">
					<input type="hidden" name="cid" class="form-control" value="${city.id}" >
	            	<tr>
                        <td style="width:20%"><label class="col-form-label">도시 이름</label></td>
                        <td style="width:80%"><input type="text" name="cname" class="form-control" value="${city.name}"></label></td>
                    </tr>
                    <tr>
                        <td style="width:20%"><label class="col-form-label">CountryCode</label></td>
                        <td style="width:80%"><input type="text" name="countryCode" class="form-control" value="${city.countryCode}"></label></td>
                    </tr>
                    <tr>
                        <td style="width:20%"><label class="col-form-label">시/군구 단위</label></td>
                        <td style="width:80%"><input type="text" name="district" class="form-control" value="${city.district}"></label></td>
                    </tr>
                    <tr>
                        <td style="width:20%"><label class="col-form-label">인구수</label></td>
                        <td style="width:80%"><input type="text" name="population" class="form-control" value="${city.population}"></label></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center;">
                            <input class="btn btn-primary" type="submit" value="수정">
                            <input class="btn btn-secondary" type="reset" value="취소 ">
                        </td>
                    </tr>
		        </table>
	       	</form>
	       
			</div>	
    	</div>
    </div>
</body>
</html>