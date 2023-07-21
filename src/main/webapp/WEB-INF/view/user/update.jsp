<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
				<h3><strong>사용자 정보 수정</strong></h3>
				<hr>
				<div class="row mt-5">
				    <div class="col-2"></div>
				    <div class="col-7">
				       
				       <form action="/bbs/user/update" method="post" enctype="multipart/form-data">
		                    <table class="table table-borderless text-center">
		                    	<input type="hidden" name="uid" value="${user.uid}">
		                    	<input type="hidden" name="filename" value="${user.profile}">
			                    <tr>
			                        <td style="width:25%"><label class="col-form-label">사용자 ID</label></td>
			                        <td style="width:60%"><input type="text" name="uid" class="form-control" value="${user.uid}" disabled></td>
			                        <td><input class="btn btn-primary" type="button" value="패스워드 변경" onclick="location.href='/bbs/user/updatePwd?uid=${user.uid}'"></td>
			                    </tr>
			                    <tr>
			                        <td><label class="col-form-label">이름</label></td>
			                        <td><input type="text" name="uname" class="form-control" value="${user.uname}"></td>
			                    </tr>
			                    <tr>
			                        <td><label class="col-form-label">이메일</label></td>
			                        <td><input type="text" name="email" class="form-control" value="${user.email}"></td>
			                    </tr>
			                    <tr>
			                        <td><label class="col-form-label">프로필사진</label></td>
			                        <td><input type="file" accept=".png, .jpg, .jpeg, .gif, .bmp"  name="profile" class="form-control"></td>
			                    </tr>
			                    <tr>
			                        <td><label class="col-form-label">주소</label></td>
			                        <td><input type="text" name="addr" class="form-control" value="${user.addr}"></td>
			                    </tr>
			                    <tr>
			                        <td colspan="2" style="text-align: center;">
			                            <input class="btn btn-primary ms-5" type="submit" value="수정">
			                            <input class="btn btn-secondary ms-2" type="reset" value="취소">
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