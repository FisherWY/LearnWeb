<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
		<link href="css/cart.css" rel="stylesheet" type="text/css">
	</head>
	<body>
	
	<%@ include file="../head/_head.jsp" %>
		<div id="wrap">
			<!-- 标题信息 -->
			<ul id="title">
				<li>
					<input class="allC" name="allC" type="checkbox"/>
					<span id="title_checkall_text">全选</span>
				</li>
				<li class="li_prod">商品</li>
				<li>单价（元）</li>
				<li>数量</li>
				<li>小计（元）</li>
				<li>操作</li>
			</ul>

			<!-- 购物信息 -->
			<ul class="prods">
				<li>
					<input type="checkbox" class="prodC"/> 
				</li>
				<li class="li_prod">
					<img src="img/cart/prod.jpg" width="90" height="90" class="prodimg" />
					<span class="prodname">荣耀6plus</span>
				</li>
				<li class="li_price">1999</li>
				<li>
					<a href="javascript:void(0)" class="delNum" >-</a>
					<input class="buyNumInp" type="text" readonly="readonly" value="1" />
					<a href="javascript:void(0)" class="addNum" >+</a>
				</li>
				<li class="sum_price">1999</li>
				<li><a id="" class="delProd" href="javascript:void(0)">删除</a></li>
			</ul>
			<!-- 总计条 -->
			<div id="total">
				<div id="total_1">
					<input type="checkbox" class="allC" name="allC"/> 
					<span>全选</span>
					<a id="del_a" href="javascript:void(0)">删除选中的商品</a>
					<div id="div_sum">
						<span id="span_1">总价：</span>
						<span>￥</span>
						<span id="span_2" class="total_sum_price">199</span>&nbsp;&nbsp;&nbsp;
					</div>
				</div>
				<div id="total_2">	
					<a id="goto_order" href="javascript:void(0)">去结算</a>
				</div>
			</div>
		</div>
	<%@ include file="../foot/_foot.jsp" %>
	</body>
</html>