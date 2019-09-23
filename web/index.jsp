<%--
  Created by IntelliJ IDEA.
  User: fisher
  Date: 2019-09-19
  Time: 08:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Welcome</title>
  </head>
  <body>
    <h1>This is the root dir from LearnWeb</h1>
    <button onclick="window.location='/LearnWeb/regist/regist.jsp'">跳转到regist.jsp</button>
    <button onclick="window.location='/LearnWeb/login/login.jsp'">跳转到login.jsp</button>
    <button onclick="window.location='/LearnWeb/index/index.jsp'">跳转到index.jsp</button>
    <h1><%=request.getContextPath()%></h1>
  </body>
</html>
