<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel = "stylesheet" href = "bootstrap/css/bootstrap.min.css">

<style>
	.form-control{
		margin-top:10px;
		margin-bottom:10px;
	}
	.panel{
		margin-top:30px;
	}
	
	.panel-heading{
		font-weight:bold;
		font-style:italic;
		text-align:left;
	}
</style>



<title>录入新数据</title>
</head>
<body align="center" >
	<div class="container form-group panel panel-primary">
	
		<dir class="panel-heading" style="background-color:#84c1ff;">
			<h1>增加新事件</h1>
		</dir>
		
		<div class="panel-body">
			<form action="addparking" method="post" name="insert">
				<!-- <input class ="form-control" type="text" name="id" placeholder="编号"> -->
				<input class ="form-control" type="text" name="parking_year" placeholder="年份">
				<input class ="form-control" type="text" name="parking_state" placeholder="地区">
				<input class ="form-control" type="text" name="parking_attack_now" placeholder="车主行为">
				<input class ="form-control" type="text" name="parking_catch" placeholder="交警行为">
				<input class ="form-control" type="text" name="parking_attack_past" placeholder="车主过去行为">
				<input class ="form-control" type="text" name="parking_coverage" placeholder="巡逻策略">
				<input class ="form-control" type="text" name="parking_density" placeholder="人口密度">
				<input class ="form-control" type="text" name="parking_time" placeholder="时间">
				<input class ="form-control" type="text" name="parking_distance1" placeholder="停车场距离">
				<input class ="form-control" type="text" name="parking_distance2" placeholder="距离">
				<input class="btn btn-primary btn-lg" type="button" value="取消" onclick="window.location.href='showParkingInfo.jsp'"> 
				<input class="btn btn-primary btn-lg" type="submit" style="margin-left:20px;"/>
			</form>
		</div>
	</div>
</body>
</html>
