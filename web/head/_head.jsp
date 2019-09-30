<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="model.User" %>
<!DOCTYPE HTML>
<link rel="stylesheet" href="../head/css/head.css"/>
<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />

<div id="common_head">
	<div id="line1">
		<div id="content">
		<%
			String loginStatus = "登录";
			String loginHref = "../login/login.jsp";
			String registStatus = "注册";
			String registHref = "../regist/regist.jsp";
			if (session.getAttribute("user") != null) {
				User user = (User) session.getAttribute("user");
				loginStatus = "欢迎" + user.getNickname() + "回来";
				loginHref = "#";
				registStatus = "退出";
				registHref = "../login/login.jsp";
			}
		%>
			<a href=<%=loginHref%>><%=loginStatus%></a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=<%=registHref%>><%=registStatus%></a>
		</div>
	</div>
	<div id="line2">
		<img id="logo" src="../head/img/head/logo.jpg"/>
		<input type="text" name=""/>
		<input type="button" value="搜 索"/>
		<span id="goto">
			<a id="goto_order" href="../orderlist/order_list.jsp">我的订单</a>
			<a id="goto_cart" href="../cart/cart.jsp">我的购物车</a>
		</span>
		<img id="erwm" src="../head/img/head/qr.jpg"/>
	</div>
	<div id="line3">
		<div id="content">
			<ul>
				<li><a href="../index/index.jsp">首页</a></li>
				<li><a href="/LearnWeb/getProdlist">全部商品</a></li>
				<li><a href="#">手机数码</a></li>
				<li><a href="#">电脑平板</a></li>
				<li><a href="#">家用电器</a></li>
				<li><a href="#">汽车用品</a></li>
				<li><a href="#">食品饮料</a></li>
				<li><a href="#">图书杂志</a></li>
				<li><a href="#">服装服饰</a></li>
				<li><a href="#">理财产品</a></li>
			</ul>
		</div>
	</div>
</div>