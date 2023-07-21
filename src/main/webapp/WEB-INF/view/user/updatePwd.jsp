<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="../common/head.jspf" %>
</head>
<body>

	<%@ include file="../common/top.jspf" %>
	<div class="container" style="margin-top: 80px;">
    	<div class="row">
			<%@ include file="../common/aside.jspf" %>
			<!-- ======================== main ==============================  -->
			<div class="col-sm-9">
				<h3><strong>회원 패스워드 변경</strong></h3>
				<hr>
				<div class="row mt-5">
				    <div class="col-2"></div>
				    <div class="col-6">
			           <form action="/bbs/user/updatePwd" method="post">
		                    <table class="table table-borderless text-center">
		                    	<input type="hidden" name="uid" value="${uid}">
			                    <tr>
			                        <td style="width:25%"><label class="col-form-label">사용자 ID</label></td>
			                        <td style="width:60%"><input type="text" name="uid" class="form-control" value="${uid}" disabled></td>
			                    </tr>
			                    <tr>
			                        <td><label class="col-form-label">패스워드</label></td>
			                        <td><input type="password" name="pwd" class="form-control"></td>
			                    </tr>
			                    <tr>
			                        <td><label class="col-form-label">패스워드 확인</label></td>
			                        <td><input type="password" name="pwd2" class="form-control"></td>
			                    </tr>
			                    <tr>
			                        <td colspan="2" style="text-align: center;">
			                            <input class="btn btn-primary ms-5" type="submit" value="수정">
			                            <input class="btn btn-secondary ms-2" type="button" value="돌아가기" onclick="location.href='/bbs/user/update?uid=${uid}'">
			                        </td>
			                    </tr>
			                </table>
		                </form>
				    </div>
				    <div class="col-2"></div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../common/bottom.jspf" %>
	
</body>
</html>