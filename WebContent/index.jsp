<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>私家车违章停车系统首页</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link href="http://fonts.googleapis.com/css?family=Montserrat"
	rel="stylesheet" type="text/css">
<link href="http://fonts.googleapis.com/css?family=Lato"
	rel="stylesheet" type="text/css">
<script src="bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>

<style type="text/css">
.navbar {
	margin-bottom: 0;
	background-color: #2d2d30;
	border: 0;
	font-size: 11px !important;
	letter-spacing: 4px;
	height: 70px;
}

/* Add a gray color to all navbar links */
.navbar li a, .navbar .navbar-brand {
	color: #d5d5d5 !important;
}

/* On hover, the links will turn white */
.navbar-nav li a:hover {
	color: #fff !important;
}

/* The active link */
.navbar-nav li.active a {
	color: #fff !important;
	background-color: #29292c !important;
}

/* Remove border color from the collapsible button */
.navbar-default .navbar-toggle {
	border-color: transparent;
}

.carousel-inner img {
	width: 100%;
	height: 80px;
}

.open .dropdown-toggle {
	color: #fff;
	background-color: #555 !important;
}

/* Dropdown links */
.dropdown-menu li a {
	color: #000 !important;
}

/* On hover, the dropdown links will turn red */
.dropdown-menu li a:hover {
	background-color: red !important;
}

.elian-header-logo-img {
	width: 67px;
	height: 67px;
	margin-left: 10px;
    background-image: url(image/a_logo.png);
}
</style>
<title></title>
</head>
<body>
	<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#myNavbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand elian-header-logo-img" href="index.jsp"></a>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="index.jsp">首页</a></li>
				<li><a href="showParkingInfo.jsp">违章信息页</a></li>
				<li><a href="showSub_incident.jsp">车辆信息页</a></li>
				<li><a href="showPerson.jsp">人物信息页</a></li>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">MORE <span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="introduceAlgorithm.jsp">算法介绍</a></li>
						<li><a href="#">其他</a></li>
						<li><a href="#">其他</a></li>
					</ul></li>
				<li><a href="#"><span class="glyphicon glyphicon-search"></span></a></li>
			</ul>
		</div>
	</div>
	</nav>

	<div id="carousel-example-generic" class="carousel slide"
		data-ride="carousel">
		<!-- Indicators -->
		<ol class="carousel-indicators">
			<li data-target="#carousel-example-generic" data-slide-to="0"
				class="active"></li>
			<li data-target="#carousel-example-generic" data-slide-to="1"></li>
			<li data-target="#carousel-example-generic" data-slide-to="2"></li>
			<li data-target="#carousel-example-generic" data-slide-to="3"></li>
		</ol>

		<!-- Wrapper for slides -->
		<div class="carousel-inner" role="listbox">
			<div class="item active">
				<img src="image/1.jpeg" class="img-responsive" alt="1">
				<div class="carousel-caption">
					<!-- <h1>first</h1> -->
				</div>
			</div>

			<div class="item">
				<img src="image/2.jpeg" class="img-responsive" alt="2">
				<div class="carousel-caption">
					<!-- <h1>first</h1> -->
				</div>
			</div>


			<div class="item">
				<img src="image/3.jpeg" class="img-responsive" alt="3">
				<div class="carousel-caption">
					<!-- <h1>first</h1> -->
				</div>
			</div>


			<div class="item">
				<img src="image/4.jpeg" class="img-responsive" alt="4">
				<div class="carousel-caption">
					<!-- <h1>first</h1> -->
				</div>
			</div>
		</div>

		<!-- Controls -->
		<a class="left carousel-control" href="#carousel-example-generic"
			role="button" data-slide="prev"> <span
			class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> <span
			class="sr-only">Previous</span>
		</a> <a class="right carousel-control" href="#carousel-example-generic"
			role="button" data-slide="next"> <span
			class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
			<span class="sr-only">Next</span>
		</a>
	</div>
	<!-- carousel -->
</body>
</html>