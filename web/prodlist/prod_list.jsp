<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="model.Product" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<link href="css/prodList.css" rel="stylesheet" type="text/css">
</head>
<body>

	<%@ include file="../head/_head.jsp" %>
	<div id="content">
		<div id="search_div">
			<form method="post" action="#">
				<span class="input_span">商品名：<input type="text" name="name"/></span>
				<span class="input_span">商品种类：<input type="text" name="category"/></span>
				<span class="input_span">商品价格区间：<input type="text" name="minprice"/> - <input type="text" name="maxprice"/></span>
				<input type="submit" value="查 询">
			</form>
		</div>
		<div id="prod_content">
			<c:forEach items="${productList}" var="product">
				<div class="prod_div" onclick="window.location.href='../prodinfo/prod_info.jsp'">
					<img src="/LearnWeb/getProdImg?imgurl=${product.imgurl}">
					<div id="prod_name_div">
						${product.name}
					</div>
					<div id="prod_price_div">
						￥${product.price}
					</div>
					<div>
						<div id="gotocart_div">
							<a href="../cart/cart.jsp">加入购物车</a>
						</div>
						<div id="say_div">
							${product.pnum}
						</div>
					</div>
				</div>
			</c:forEach>
			<div class="prod_div" onclick="window.location.href='../prodinfo/prod_info.jsp'">
				<img src="img/prodlist/prod.jpg">
				<div id="prod_name_div">
					华为荣耀6plus
				</div>
				<div id="prod_price_div">
					￥2099元
				</div>
				<div>
					<div id="gotocart_div">
						<a href="../cart/cart.jsp">加入购物车</a>
					</div>
					<div id="say_div">
						133人评价
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../foot/_foot.jsp" %>
</body>
</html>
