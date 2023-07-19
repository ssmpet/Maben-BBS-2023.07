<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>File Upload</title>
</head>
<body>
	<form action="/bbs/file/upload" method="post" enctype="multipart/form-data">
	    <input type="file" accept=".png, .jpg, .jpeg, .gif, .bmp"  name="profile" class="form-control">
    	<input class="btn btn-primary" type="submit" value="가입">
		<input class="btn btn-secondary" type="reset" value="취소">
	</form>
</body>
</html>