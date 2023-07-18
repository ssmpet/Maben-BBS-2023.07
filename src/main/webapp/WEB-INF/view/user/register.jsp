<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
				<div class="row">
					<div class="col-2">
						<h3><strong>회원가입</strong></h3>
					</div>
					<div class="col-7 mt-3">
						
		            </div>
		         </div>
	                
				<hr>
				<div class="row mt-5">
				    <div class="col-2"></div>
				    <div class="col-6">
				       
				       <form action="/bbs/user/register" method="post">
		                    <table class="table table-borderless text-center">
			                    <tr>
			                        <td style="width:35%"><label class="col-form-label">사용자 ID</label></td>
			                        <td style="width:65%"><input type="text" name="uid" class="form-control" autofocus></td>
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
			                        <td><label class="col-form-label">이름</label></td>
			                        <td><input type="text" name="uname" class="form-control"></td>
			                    </tr>
			                    <tr>
			                        <td><label class="col-form-label">이메일</label></td>
			                        <td><input type="text" name="email" class="form-control"></td>
			                    </tr>
			                    <tr>
			                        <td><label class="col-form-label">주소</label></td>
			                        <td><input type="text" name="addr" class="form-control"></td>
			                    </tr>
			                    <tr>
			                        <td><label class="col-form-label">프로필사진</label></td>
			                        <td><input type="file" accept=".png, .jpg, .jpeg, .gif, .bmp"  name="profile" class="form-control"></td>
			                    </tr>
			                    <tr>
			                        <td colspan="2" style="text-align: center;">
			                            <input class="btn btn-primary" type="submit" value="가입">
			                            <input class="btn btn-secondary" type="reset" value="취소">
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