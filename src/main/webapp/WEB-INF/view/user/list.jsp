<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="../common/head.jspf" %>
	<style>
		td { text-align: center;}
		.disabled-link { pointer-events: none;}
	</style>
</head>
<body>

	<%@ include file="../common/top.jspf" %>
 	<div class="container" style="margin-top: 80px;">
    	<div class="row">
			<%@ include file="../common/aside.jspf" %>
			<!-- ======================== main ==============================  -->
			<div class="col-sm-9">
				<h3><strong>사용자 목록</strong></h3>
				<hr>
				 <table class="table table-condensed table-hover text-center" style="margin-bottom: 50px;">
		            <tr class="table-secondary">
		                <th width="8%">번호</th>
		                <th width="10%">UID</th>
		                <th width="10%">이름</th>
		                <th width="20%">이메일</th>
		                <th width="10%">가입일</th>
		                <th width="20%">주소</th>
		                <th width="10%">액션</th>
		            </tr>
	            <c:forEach var="user" items="${userList}" varStatus="loop">
	            	<tr>
	            		<td>${loop.count}</td>
	            		<td>${user.uid}</td>
	            		<td>${user.uname}</td>
	            		<td>${user.email}</td>
	            		<td>${user.regDate}</td>
	            		<td>${user.addr}</td>
	            		<td>
	            			<%-- 본인만이 수정권한이 있음 --%>
	            			<c:if test="${uid eq user.uid}">
	            				<a href="/bbs/user/update?uid=${user.uid}"><i class="fas fa-user-edit"></i></a>
	            			</c:if>
	            			<c:if test="${uid ne user.uid}">
	            				<a href="#" class="disabled-link"><i class="fas fa-user-edit"></i></a>
	            			</c:if>
	            			<%-- 관리자만이 삭제권한이 있음 --%>
	            			<c:if test="${uid eq 'admin' }">
	            				<a href="/bbs/user/delete?uid=${user.uid}"><i class="fas fa-user-minus ms-1"></i></a>
	            			</c:if>
	            			<c:if test="${uid ne 'admin' }">
	            				<a href="#" class="disabled-link"><i class="fas fa-user-minus ms-1"></i></a>
	            			</c:if>
	            		</td>
	            	</tr>
	            </c:forEach>
		        </table>
		         <%-- 페이지 네이션 --%>
		        <ul class="pagination justify-content-center">
		        	<li class="page-item"><a class="page-link" href="">&laquo; 이전</a></li>
		        <c:forEach var="page" items="${pageList}">
		        	<li class="page-item ${(currentUserPage eq page) ? 'active' : ''}">
		        		<a class="page-link" href="/bbs/user/list?page=${page}">${page}</a>
		        	</li>
				</c:forEach>		        	
		        	<li class="page-item"><a class="page-link" href=""> 다음 &raquo;</a></li>
		        </ul>
		        
			</div>			
		</div>
    </div>
	<%@ include file="../common/bottom.jspf" %>
</body>
</html>