<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<base href="<%=path%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
index.jsp
<hr />
<form action="/TestController/viewAll" method="POST" name="form">
账号:<input type="text" name="name" id="name"></input><br>
密码:<input type="text" name="pwd" id="pwd"></input><br>
<input type="submit" value="登录"/>
</form>
</body>
</html>