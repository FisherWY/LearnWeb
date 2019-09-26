<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/login.css"/>
		<title>EasyMall欢迎您登陆</title>
	</head>
	<body>
		<h1>欢迎登陆EasyMall</h1>
		<form action="/LearnWeb/login" method="POST">
			<table>
				<tr>
					<td class="tdx">用户名：</td>
					<td><input type="text" name="username" value="${cookie.username.value}"/></td>
				</tr>
				<tr>
					<td class="tdx">密&nbsp;&nbsp; 码：</td>
					<td><input type="password" name="password"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="checkbox" name="remname" checked/>记住用户名
						<input type="checkbox" name="autologin"/>30天内自动登陆
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center">
						<input type="submit" value="登 陆"/>
					</td>
				</tr>
			</table>
		</form>		
	</body>
</html>
