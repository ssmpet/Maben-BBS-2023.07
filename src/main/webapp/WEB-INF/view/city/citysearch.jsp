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
    	
	    	<h2> 2. 도시 이름을 입력해 주세요. </h2>
	    	<hr>
	    	<div class="col-sm-6">
	    		<form action="/bbs/city/citysearch" method="post">
					<table class="table table-borderless mt-2 text-center" style="margin-bottom: 50px;">
		            	<tr>
	                        <td style="width:20%"><label class="col-form-label">도시 이름</label></td>
	                        <td style="width:80%"><input type="text" name="cname" class="form-control" autofocus></td>
	                    </tr>
	                    <tr>
	                        <td colspan="2" style="text-align: center;">
	                            <input class="btn btn-primary" type="submit" value="찾기">
	                            <input class="btn btn-secondary" type="reset" value="취소">
	                        </td>
	                    </tr>
			        </table>
		        </form>
			</div>	
    	</div>
    </div>
</body>
</html>