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
<title>录入车主信息</title>
</head>
<body align="center" >
	<div class="container form-group panel panel-primary">
		<dir class="panel-heading" style="background-color:#84c1ff;">
			<h1>增加车主信息</h1>
		</dir>
		<% 
			int temp_person_id = Integer.parseInt(request.getParameter("id").trim());
			int stateId = Integer.parseInt(request.getParameter("stateId").trim());
		%>
		<div class="panel-body">
			<form action="addparking_person" method="post" name="insert">
				<!-- <input class ="form-control" type="text" name="terr_id" placeholder="犯罪分子编号"> --> 
				<input class ="form-control" type="text" name="have_car_id" placeholder="索引编号">
				<input class ="form-control" type="text" name="parking_date" placeholder="违章时间">
				<input class ="form-control" type="text" name="person_name" placeholder="名字">
				<input class ="form-control" type="text" name="person_gender" placeholder="性别">
				<input class ="form-control" type="text" name="person_age" placeholder="年龄">
				<input class ="form-control" type="text" name="person_nationality" placeholder="民族">
				<input class ="form-control" type="text" name="person_birthday" placeholder="生日"> 
				<input class ="form-control" type="text" name="person_job" placeholder="工作">
				<input class ="form-control" type="text" name="person_education" placeholder="学历">
				<input class ="form-control" type="text" name="person_hkadr" placeholder="户籍">
				<input class ="form-control" type="text" name="person_height" placeholder="身高">
				<input class ="form-control" type="text" name="person_weight" placeholder="体重">
				<input class ="form-control" type="text" name="person_nativeplace" placeholder="籍贯">
				<input class ="form-control" type="text" name="person_maritalstatus" placeholder="婚姻状况">
				<input class ="form-control" type="text" name="person_politicalstatus" placeholder="政治面貌">
				<input class="btn btn-primary btn-lg" type="button" value="取消" 
					onclick="window.location.href='showParking_personInfo.jsp?id=<%=temp_person_id%>&stateId=<%=stateId%>'" > 
				<input class="btn btn-primary btn-lg" type="submit" style="margin-left:20px;">
			</form>
		</div>
	</div>
</body>
</html>