<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>欢迎注册EasyMall</title>
		<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/regist.css"/>
	</head>
	<body>
		<form action="/LearnWeb/register" method="POST">
			<%-- 防止重复提交 --%>
			<%
				Random random = new Random();
				int token = random.nextInt();
				session.setAttribute("token", token);
			%>
				<input type="hidden" name="token" value="<%=token%>">
			<h1>欢迎注册EasyMall</h1>
			<table>
				<tr>
					<td class="tds">用户名：</td>
					<td>
						<input type="text" name="username" value="zhangsan"/>
					</td>
				</tr>
				<tr>
					<td class="tds">密码：</td>
					<td>
						<input type="password" name="password" value="12345"/>
					</td>
				</tr>
				<tr>
					<td class="tds">确认密码：</td>
					<td>
						<input type="password" name="password2" value="12345"/>
					</td>
				</tr>
				<tr>
					<td class="tds">昵称：</td>
					<td>
						<input type="text" name="nickname" value="zhangsan"/>
					</td>
				</tr>
				<tr>
					<td class="tds">邮箱：</td>
					<td>
						<input type="text" name="email" value="a@163.com"/>
					</td>
				</tr>
				<tr>
					<td class="tds">验证码：</td>
					<td>
						<input type="text" name="valistr"/>
						<img src='/LearnWeb/getVerifyCode' width='120' height='30' onclick="this.src=this.src+'?'+Math.random()" />
					</td>
				</tr>
				<tr>
					<td class="sub_td" colspan="2" class="tds">
						<input type="submit" value="注册用户"/>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
