<%@ page import="com.zx.dao.GetEmDao"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="com.zx.dao.ParkingincidentDao"%>
<%@ page import="com.zx.model.Parkingincident"%>
<%@ page import="java.util.List"%>
<%@ page import="java.sql.Date"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<style>
.body-main {
	background: url(image/bkground.png) repeat-x;
}

.table {
	text-overflow: elipsis;
	white-space: nowrap;
}

.add-btn {
	margin-top: 30px;
	margin-bottom: 15px;
	margin-right: 30px;
}

.liblogo {
	margin-top: 25px;
	margin-left: 25px;
}

.logo-container {
	height: 100px;
}

.div-inline {
	display: inline;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>策略查询</title>
</head>
<body class="body-main">
	<div class="containt-fluid logo-container">
		<div class="row">
			<!-- logo -->
			<div class="col-md-8">
				<img src="image/a_logo.png" class="img-responsive liblogo">
			</div>
			<!--  校徽  -->
			<div class="col-md-4">
				<div class="bg">
					<img src="image/bg-header.png" class="img-responsive">
				</div>
			</div>
		</div>
		<%
			request.setCharacterEncoding("utf-8");
		
			float[] result = new float[100];
			result = (float[])request.getAttribute("result");
			
			String str = "";
			for (int i = 0; i < 99; i++) {
				str += result[i];
				str += ",";
			}
			
			str = str + result[99];
			
			//System.out.println(result[0]);
			/* String typeStrategy = request.getParameter("getSituation");
			String typeYear = request.getParameter("getYear");

			float[] result = new float[100];

			if (typeStrategy.equals("normal")) {

				result = GetEmDao.getEM(Integer.parseInt(typeYear));

			}

			if (typeStrategy.equals("fixed")) {

			} */
		%>
		
		<input class="btn btn-primary add-btn pull-right" type="button"
			onclick="window.location.href='showParkingInfo.jsp'" value="返回主页面" />
		<input class="btn btn-primary add-btn pull-left" type="button"
			onclick="window.location.href='gisForStrategy.jsp?result=<%=str%>'" value="具体策略展示" />
		<form>
			<table class="table table-responsive table-striped" width="100%"
				border="0" align="center" cellpadding="0" cellspacing="0">
				<tr align="center" height="30"
					style="border: 2px solid; text-align: center; vertical-align: middle; font-size: 18px; font-weight: bold;">
					<th width="80">区域编号</th>
					<th width="80">生成策略</th>
					<th width="80">区域编号</th>
					<th width="80">生成策略</th>
					<th width="80">区域编号</th>
					<th width="80">生成策略</th>
					<th width="80">区域编号</th>
					<th width="80">生成策略</th>
					<th width="80">区域编号</th>
					<th width="80">生成策略</th>
				</tr>
					
					<%  
						int step = 20;
						for (int i = 0; i < 20; i++) {
							
					%>
					
					<tr align="center" height="30">
						<td width="80"><%=i%></td>
						<td width="80"><%=result[i]%></td>
						<td width="80"><%=i+step%></td>
						<td width="80"><%=result[i+step]%></td>
						<td width="80"><%=i+step*2%></td>
						<td width="80"><%=result[i+step*2]%></td>
						<td width="80"><%=i+step*3%></td>
						<td width="80"><%=result[i+step*3]%></td>
						<td width="80"><%=i+step*4%></td>
						<td width="80"><%=result[i+step*4]%></td>
					</tr>
					
					<%
						}
					%>
			</table>
		</form>
		
	</div>
</body>
</html>


