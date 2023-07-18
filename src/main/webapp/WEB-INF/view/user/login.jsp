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
			<%--@ include file="../common/aside.jspf" --%>
			<!-- ======================== main ==============================  -->
			<div class="col-sm-9">
				<div class="row">
					<div class="col-2">
						<h3><strong>로그인</strong></h3>
					</div>
					<div class="col-7 mt-3">
						<span>
		                	<a href="/bbs/user/register" class="ms-5"><i class="fas fa-user-plus"></i> 회원가입</a>
		                </span>
		            </div>
		         </div>
	                
				<hr>
				<div class="row mt-5">
				    <div class="col-2"></div>
				    <div class="col-6">
				       
				        <form action="/bbs/user/login" method="post" id="form_login">
				            <table class="table table-borderless">
				                <tr>
				                    <td><label for="uid" class="col-form-label">아이디</label></td>
				                    <td><input type="text" name="uid" id="uid" class="form-control" placeholder="아이디" autofocus></td>
				                </tr>
				                <tr>
				                    <td><label for="pwd" class="col-form-label">패스워드</label></td>
				                    <td><input type="password" name="pwd" id="pwd" class="form-control" placeholder="패스워드"></td>
				                </tr>
				                <tr>
				                    <td colspan="2" style="text-align: center;">
				                        <input class="btn btn-primary" type="submit" value="로그인"">
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