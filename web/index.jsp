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
    <button onclick="window.location='/LearnWeb/regist/regist.jsp'">注册页面</button>
    <button onclick="window.location='/LearnWeb/login/login.jsp'">登录页面</button>
    <button onclick="window.location='/LearnWeb/index/index.jsp'">商城主页</button>
    <button onclick="window.location='/LearnWeb/getProdlist'">所有商品页</button>
    <button onclick="window.location='/LearnWeb/prodinfo/prod_info.jsp'">商品详情页</button>
    <button onclick="window.location='/LearnWeb/cart/cart.jsp'">购物车页</button>
    <button onclick="window.location='/LearnWeb/orderadd/order_add.jsp'">下单页</button>
    <button onclick="window.location='/LearnWeb/orderlist/order_list.jsp'">所有订单页</button>
    <h1><%=request.getContextPath()%></h1>
  </body>
</html>
