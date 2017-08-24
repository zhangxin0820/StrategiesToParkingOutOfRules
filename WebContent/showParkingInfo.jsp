<%@ page import="com.zx.db.DBConn"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="com.zx.dao.ParkingincidentDao"%>
<%@ page import="com.zx.dao.GetEmDao"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.top1 {
	margin-bottom: 15px;
}

.top2 {
	margin-top: 15px;
	margin-right: 15px;
}

.add-btn {
	margin-top: 30px;
	margin-bottom: 15px;
	margin-right: 30px;
}

.body-main {
	background: url(image/bkground.png) repeat-x;
}

.test-select {
	padding-top: 5px;
	padding-bottom: 5px;
	font-size: 18px;
	background-color: #fcfcfc;
	color: #000000;
}

.year-select {
	padding-top: 5px;
	padding-bottom: 5px;
	font-size: 18px;
	background-color: #fcfcfc;
	color: #000000;
}

.situation-select {
	padding-top: 5px;
	padding-bottom: 5px;
	font-size: 18px;
	background-color: #fcfcfc;
	color: #000000;
}

.select-label {
	font-size: 18px;
	font-weight: bold;
	margin-right: 5px;
}

.table {
	text-overflow: elipsis;
	white-space: nowrap;
}

.liblogo {
	margin-top: 25px;
	margin-left: 25px;
}

.logo-container {
	height: 100px;
}
</style>
<title>私家车违章停车</title>
</head>
<body class="body-main">


	<div class="containt-fluid logo-container">
		<div class="row">
			<!-- logo -->
			<div class="col-md-4">
				<img src="image/a_logo.png" class="img-responsive liblogo">
			</div>
			<form action="importExcel" method="post" class="pull-right"
				enctype="multipart/form-data" onsubmit="return CheckExcel()">
				<input type="file" name="excel" id="excel"
					onchange="CheckExcel(this)" class="btn btn-warning"> <input
					type="submit" value="导入Excel数据" class="btn btn-warning">
			</form>
			<form action="getPersonDirectly" class="navbar-form navbar-right"
				style="margin-right: 45px" role="search">
				<div class="form-group">
					<input type="text" name="getPersonId" id="getPersonId"
						class="form-control" placeholder="请输入人物编号">
				</div>
				<input type="submit" class="btn btn-warning" value="查询">
			</form>
			<!--  校徽  -->
			<div class="col-md-3">
				<div class="bg">
					<img src="image/bg-header.png" class="img-responsive">
				</div>
			</div>
		</div>

		<div class="containt-fluid">

			<%
				//显示操作
				List<Parkingincident> resultInfor = ParkingincidentDao.getAll();
				int rowCount = resultInfor.size();//总行数
				int i = 1;
				int pageSize = 10;
				int pageNow = 1;

				//默认显示第一页  s
				int pageCount = 0;//总页数  0
				String nowpage = (String) request.getParameter("pageNow");
				//System.out.println(nowpage);
				if (nowpage != null) {
					pageNow = Integer.parseInt(nowpage);
				}
				if (rowCount != 0) {
					if (rowCount % pageSize == 0) {
						pageCount = rowCount / pageSize;
					} else {
						pageCount = rowCount / pageSize + 1;
					}
				} else {
					pageCount = 0;
				}

				int beginIndex = (pageNow - 1) * pageSize + 1;
				int total = 0;
				if (!resultInfor.isEmpty()) {

					if (pageNow < pageCount) {
						total = beginIndex + pageSize;
					} else
						total = rowCount - (pageCount - 1) * pageSize + beginIndex;
					for (int j = beginIndex - 1; j < total - 1; j++) {

						Parkingincident tempInfo = resultInfor.get(j);

						int id = tempInfo.getId();
						//int get_person_id = tempInfo.getGet_person_id();
						float year = tempInfo.getParking_year();
						String state = tempInfo.getParking_state();
						float attack_now = tempInfo.getParking_attack_now();
						float pCatch = tempInfo.getParking_catch();
						float attack_past = tempInfo.getParking_attack_past();
						float coverage = tempInfo.getParking_coverage();
						float density = tempInfo.getParking_density();
						float time = tempInfo.getParking_time();
						float distance1 = tempInfo.getParking_distance1();
						float distance2 = tempInfo.getParking_distance2();

						int tempj = j;
			%>


			<%
				}
				}
			%>

			<%-- <%
				int getPerId = Integer.parseInt(request.getParameter("getPersonId"));
			
				Connection conn = DBConn.getConnection();
				String sql_1 = "select have_car_id from parking_person where person_id = ?";//问号是getPerId
				String sql_2 = "";
			%> --%>

			<!-- <div class="row">
				<div class="col-md-9"></div>
				<div class="col-md-3">
					<form action="getPersonDirectly" class="top2" method="post">
						<input type="text" name="getPersonId" id="getPersonId"
							placeholder="请输入人物编号" class="" /> <input class="btn btn-primary"
							type="submit" value="查询人物">
					</form>
				</div>
			</div> -->



			<div style="margin-top: 20px; width: 100%;" align="center">
				<form Action="" method="post">
					<%
						//删除操作
						String deleteName[];
						if (request.getParameter("ck") != null) {
							deleteName = request.getParameterValues("ck");
							int[] deleteId = new int[deleteName.length];

							ParkingincidentDao.delParkingincident(deleteName);
						}
					%>
				</form>
				<!-- <div class="input-group col-md-2 top2 pull-right">
					<input type="text" class="form-control" placeholder="请输人物编号">
					<span class="input-group-btn">
						<button class="btn btn-info btn-search">查找</button>
					</span>
				</div> -->

				<div class="row">
					<div class="col-md-1"></div>
					<div class="col-md-4">
						<form action="getAllStrategy" class="top1" method="post"
							name="strategyInfo">
							<div class="col-md-3">
								<select class="situation-select" name="getSituation"
									id="getsituation_id">
									<option value="0">请选择策略模式</option>
									<option value="normal">正常模式</option>
									<option value="fixed">固定点模式</option>
								</select>
							</div>
							<select class="year-select" name="getYear" id="getyear_id">
								<option value="0">请选择年份</option>
								<option value="2016">2016年</option>
								<option value="2017">2017年</option>
							</select> <input class="btn btn-primary" type="submit" value="查询策略">
						</form>
					</div>
					<div class="col-md-6">
						<form action="selectParking.jsp" class="top1" method="post"
							name="selectInfo">
							<select class="test-select" name="state" id="state_id">
								<!-- <option value="0">请选择查询条件</option> -->
								<option value="1">按照地区查找结果</option>
								<option value="2">按照年份查找结果</option>
								<option value="3">按照人口密度查找结果</option>
								<option value="4">按照资源分布查找结果</option>
							</select> <span id="span1" class="select-label">地区</span><input
								type="text" id="pState_id" style="width: 90px"
								name="parking_state" /><span id="span2" style="display: none;"
								class="select-label">年份</span><input
								style="margin-left: 10px; display: none; width: 90px"
								type="text" id="year_id" name="parking_year" /><span id="span3"
								style="display: none;" class="select-label">人口密度</span><input
								style="margin-left: 10px; display: none; width: 90px"
								type="text" id="density_id" name="parking_density" /> <span
								id="span4" style="display: none;" class="select-label">资源分布</span><input
								type="text" id="coverage1_id"
								style="margin-right: 10px; display: none; width: 90px"
								name="parking_coverage1" /><span id="span5"
								style="display: none;" class="select-label">~</span><input
								style="margin-left: 10px; display: none; width: 90px"
								type="text" id="coverage2_id" name="parking_coverage2" /> <input
								class="btn btn-primary" type="submit" value="选择" />
						</form>
					</div>
				</div>
				<input class="btn btn-primary add-btn pull-right" type="button"
					onclick="window.location.href='addparking.jsp'" value="增加新事件" /><input
					class="btn btn-primary add-btn pull-right" type="button"
					onclick="window.location.href='index.jsp'" value="返回首页" />

				<!-- <input type="text" name="getPersonId" placeholder="人物编号" class="pull-left">
				<input class="btn btn-primary pull-left" type="submit" value="查询人物"> -->
				<form name="delete" action="" method="post">
					<table class="table table-responsive table-striped" width="100%"
						border="0" align="center" cellpadding="0" cellspacing="0">
						<tr align="center" height="30"
							style="border: 2px solid; text-align: center; vertical-align: middle; font-size: 18px; font-weight: bold;">

							<td width="58"><input class="btn btn-primary" type="submit"
								value="删除" onclick="return deleteConfirm()"></td>

							<td width="80">编号</td>
							<!-- <td width="80" style="display: none;">索引人物</td> -->
							<td width="80">年份</td>
							<td width="80">地区</td>
							<td width="80">车主行为</td>
							<td width="80">交警行为</td>
							<td width="80">车主过去行为</td>
							<td width="80">资源概率分布</td>
							<td width="80">人口密度</td>
							<td width="80">时间</td>
							<td width="80">停车场距离</td>
							<td width="80">距离</td>
							<td width="80">车辆信息</td>
							<td width="80">图谱展示</td>
							<td width="80">更新事件</td>

							<%
								List<Parkingincident> list = ParkingincidentDao.getAll();

								int n = list.size();
								for (int k = beginIndex - 1; k < total - 1; k++) {
									Parkingincident t = list.get(k);

									int id = t.getId();
									//int get_person_id = t.getGet_person_id();
									int year = t.getParking_year();
									String state = t.getParking_state();
									float attack_now = t.getParking_attack_now();
									float pCatch = t.getParking_catch();
									float attack_past = t.getParking_attack_past();
									float coverage = t.getParking_coverage();
									float density = t.getParking_density();
									float time = t.getParking_time();
									float distance1 = t.getParking_distance1();
									float distance2 = t.getParking_distance2();

									String getYear = String.valueOf(year) + "年";
									String getState = state + "号区域";

									String getTime = null;
									int hour = (int) time;
									int minute = (int) (60 * (time - hour));
									String timeToHour = String.valueOf(hour);
									String timeToMinute = String.valueOf(minute);
									String timeToStr = timeToHour + "时" + timeToMinute + "分";

									String nowAttack = null;
									String getCatch = null;
									String pastAttack = null;

									if (attack_now == 1.0) {
										nowAttack = "是";
									} else {
										nowAttack = "否";
									}

									if (pCatch == 1.0) {
										getCatch = "抓住";
									} else {
										getCatch = "未抓住";
									}

									if (attack_past == 1.0) {
										pastAttack = "是";
									} else {
										pastAttack = "否";
									}

									DecimalFormat decimalFormat = new DecimalFormat(".00");
									String getCoverage = decimalFormat.format(coverage * 100) + "%";
									String getDistance1 = decimalFormat.format(distance1) + "km";
									String getDistance2 = decimalFormat.format(distance2) + "km";
							%>
						
						<tr align="center" height="30">

							<td width="58"><input type="checkbox" name="ck"
								value=<%=id%>></td>

							<td width="80"><%=id%></td>
							<%-- <td width="80" style="display: none;"><%=get_person_id%></td> --%>
							<td class="edit" width="80"><%=getYear%></td>
							<td class="edit" width="80"><%=getState%></td>
							<td class="edit" width="80"><%=nowAttack%></td>
							<td class="edit" width="80"><%=getCatch%></td>
							<td class="edit" width="80"><%=pastAttack%></td>
							<td class="edit" width="80"><%=getCoverage%></td>
							<td class="edit" width="80"><%=density%></td>
							<td class="edit" width="80"><%=timeToStr%></td>
							<td class="edit" width="80"><%=getDistance1%></td>
							<td class="edit" width="80"><%=getDistance2%></td>
							<td><a class="link-a btn btn-primary"
								href="showParking_subInfo.jsp?id=<%=id%>">车辆信息</a></td>
							<td><a class="link-a btn btn-primary"
								href="showGplot.jsp?id=<%=id%>">图谱展示</a></td>
							<td width="58"><input class="btn btn-primary" id="toEdit"
								type="button" value="编辑"></td>
						</tr>
						<%
							}
						%>

					</table>
					<%
						if (pageCount != 0) {
							if (pageNow != 1) {
								out.println(" <a href=showParkingInfo.jsp?pageNow=1>【首页】</a> ");
								out.println(" <a href=showParkingInfo.jsp?pageNow=" + (pageNow - 1) + "> 上一页</a> ");
							}
							if (pageNow != pageCount) {
								out.println(" <a href=showParkingInfo.jsp?pageNow=" + (pageNow + 1) + "> 下一页</a>");
								out.println(" <a href=showParkingInfo.jsp?pageNow=" + pageCount + "> 【尾页】</a>");
							}
							//显示当前页的前三页与后三页
							for (i = ((pageNow - 3) > 0 ? (pageNow - 3) : 1); i <= (pageCount < (pageNow + 3)
									? pageCount
									: (pageNow + 3)); i++) {
								out.println("<a href=showParkingInfo.jsp?pageNow=" + i + ">[" + i + "]</a>");
							}
						}
						out.print("第");
					%>
					<input type="text" name="pages" id="page_id" size="3"
						value="<%=pageNow%>" />
					<%
						out.print("/");
					%>
					<font size="2"><%=pageCount%></font>

					<%
						out.print("页");
					%>

					<input class="btn btn-primary btn-xs" type="button" value="跳转"
						onclick="return jump('<%=pageCount%>')" />

				</form>
			</div>

		</div>



		<script type="text/javascript" src="jquery-1.11.1.js"></script>
		<script type="text/javascript">
			var select = [ [ "2016", "2017" ], [ "2016", "2017" ] ];
			function getSelect() {
				var strategy = document.strategyInfo.getSituation;
				var year = document.strategyInfo.getYear;
				var strategyYear = select[strategy.selectedIndex - 1];
				year.length = 1;

				for (var i = 0; i < strategyYear.length; i++) {
					year[i + 1] = new Option(strategyYear[i], strategyYear[i]);
				}
			}

			function deleteConfirm() {
				if (confirm("确认要删除吗?")) {
					return true;
				}
				return false;
			}

			function jump(maxPage) {
				var pages = document.getElementById("page_id").value;//得到id为page_id输入框的值
				pages_int = parseInt(pages)
				maxPage_int = parseInt(maxPage)
				if (pages_int > 0 && pages_int <= maxPage_int) {
					window.location.href = "showParkingInfo.jsp?pageNow="
							+ pages;
					return true;
				} else {
					alert("您输入的页码有误！");
					return false;
				}
			}

			function CheckExcel() {
				var mime = document.getElementById('excel').value;
				mime = mime.toLowerCase().substr(mime.lastIndexOf("."));

				if (!(mime == ".xls")) {
					alert("请导入正确的EXCEL文件，仅支持xls格式!");
					return false;
				}
			}

			$(function() {
				$("#state_id").bind("change", function() {
					if ($(this).val() == "1") {
						$("#pState_id").show();
						$("#span1").show();
						$("#year_id").hide();
						$("#span2").hide();
						$("#density_id").hide();
						$("#span3").hide();
						$("#coverage1_id").hide();
						$("#coverage2_id").hide();
						$("#span4").hide();
						$("#span5").hide();
					} else if ($(this).val() == "2") {
						$("#pState_id").hide();
						$("#span1").hide();
						$("#year_id").show();
						$("#span2").show();
						$("#density_id").hide();
						$("#span3").hide();
						$("#coverage1_id").hide();
						$("#coverage2_id").hide();
						$("#span4").hide();
						$("#span5").hide();
					} else if ($(this).val() == "3") {
						$("#pState_id").hide();
						$("#span1").hide();
						$("#year_id").hide();
						$("#span2").hide();
						$("#density_id").show();
						$("#span3").show();
						$("#coverage1_id").hide();
						$("#coverage2_id").hide();
						$("#span4").hide();
						$("#span5").hide();
					} else {
						$("#pState_id").hide();
						$("#span1").hide();
						$("#year_id").hide();
						$("#span2").hide();
						$("#density_id").hide();
						$("#span3").hide();
						$("#coverage1_id").show();
						$("#coverage2_id").show();
						$("#span4").show();
						$("#span5").show();
					}
				});
			});

			$(function() {
				$("#toSubmit").click(
						function() {
							var getPerId = document
									.getElementById("getPersonId").value;

							$.post("getPersonDirectly", {
								"getPerId" : getPerId
							}, function(result) {
								callback = result.status;
								alert(callback);
							}, "json");
						});
			});

			$(function() {
				$("#toEdit").click(
						function() {
							str = $(this).val();
							if (str == "编辑") {
								$(this).val("确定"); // 按钮被点击后，在“编辑”和“确定”之间切换
								tr = $(this).parents("tr")
								$('.edit', tr).each(
										function() { // 获取当前行的其他单元格
											obj_text = $(this).find(
													"input:text"); // 判断单元格下是否有文本框
											if (!obj_text.length) // 如果没有文本框，则添加文本框使之可以编辑
												$(this).html(
														"<input type='text' value='"
																+ $(this)
																		.text()
																+ "'>");
											else
												// 如果已经存在文本框，则将其显示为文本框修改的值
												$(this).html(obj_text.val());
										});
							} else {
								$(this).val("编辑");
								tr = $(this).parents("tr")
								var data = new Array();
								$('.edit', tr).each(function() {
									obj_text = $(this).find("input:text");
									$(this).html(obj_text.val());
									data.push(obj_text.val());
								});
								var id = tr.children().eq(1).text();
								//alert(id)
								$.post("updateparking", {
									"id" : id,
									"parking_year" : data[0],
									"parking_state" : data[1],
									"parking_attack_now" : data[2],
									"parking_catch" : data[3],
									"parking_attack_past" : data[4],
									"parking_coverage" : data[5],
									"parking_density" : data[6],
									"parking_time" : data[7],
									"parking_distance1" : data[8],
									"parking_distance2" : data[9]
								}, function(result) {
									callback = result.status;
									alert(callback);
								}, "json");
							}
							;
						});
			});
		</script>
	</div>
</body>
</html>