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
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>数据查询</title>
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
		<%-- <%
			request.setCharacterEncoding("utf-8");
			//删除操作
			String deleteName[];
			if (request.getParameter("ck") != null) {
				deleteName = request.getParameterValues("ck");
				System.out.println(Arrays.asList(deleteName));
			}
		%> --%>
		<%
			request.setCharacterEncoding("utf-8");
			//response.setCharacterEncoding("utf-8");
			String tempvalue1 = new String();
			Integer tempvalue2 = null;
			Float tempvalue3 = null;
			Float tempvalue4 = null;
			Float tempvalue5 = null;

			String parking_state = request.getParameter("parking_state");
			String parking_year = request.getParameter("parking_year");
			String parking_density = request.getParameter("parking_density");
			String parking_coverage1 = request.getParameter("parking_coverage1");
			String parking_coverage2 = request.getParameter("parking_coverage2");
			String type = request.getParameter("state");
			//System.out.println(type + "+aaaaa+" + terr_coun);
			//String type = new String(request.getParameter("state").getBytes("ISO-8859-1"), "utf-8");
			List<Parkingincident> result = new LinkedList<Parkingincident>();

			if (type.equals("1")) {
				
				tempvalue1 = parking_state;
				//System.out.println("qwq"+tempvalue1);
				
				try {
					result = ParkingincidentDao.getAll(parking_state);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			if (type.equals("2")) {
				
				tempvalue2 = Integer.parseInt(parking_year);
				
				try {
					result = ParkingincidentDao.getYear(tempvalue2);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (type.equals("3")) {
				
				tempvalue3 = Float.parseFloat(parking_density);
				
				try {
					result = ParkingincidentDao.getDensity(tempvalue3);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (type.equals("4")) {
				
				tempvalue4 = Float.parseFloat(parking_coverage1);
				tempvalue5 = Float.parseFloat(parking_coverage2);
				
				try {
					result = ParkingincidentDao.getCoverage(tempvalue4, tempvalue5);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		%>



		<%
			//显示操作
			int rowCount = result.size();//总行数
			int i = 1;
			int pageSize = 10;
			int pageNow = 1;

			//默认显示第一页  
			int pageCount = 0;//总页数  0
			String nowpage = request.getParameter("pageNow");
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
			if (!result.isEmpty()) {

				if (pageNow < pageCount) {
					total = beginIndex + pageSize;
				} else
					total = rowCount - (pageCount - 1) * pageSize + beginIndex;
				for (int j = beginIndex - 1; j < total - 1; j++) {

					Parkingincident tempInfo = result.get(j);

					int id = tempInfo.getId();
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

		<div style="margin-top: 20px;" align="center">
			<form Action="" method="post"></form>
			<form id="formid" name="delete" action="" method="post">

				<input type="hidden" id="parking_state_id" value="<%=tempvalue1%>" />
				<input type="hidden" id="parking_year_id" value="<%=tempvalue2%>" />
				<input type="hidden" id="parking_density_id" value="<%=tempvalue3%>" /><input
					type="hidden" id="parking_coverage1_id" value="<%=tempvalue4%>" /><input
					type="hidden" id="parking_coverage2_id" value="<%=tempvalue5%>" />
				<input type="hidden" id="state_id" value="<%=type%>" /> <input
					class="btn btn-primary add-btn pull-right" type="button"
					onclick="window.location.href='showParkingInfo.jsp'" value="返回主页面" />
				<table class="table table-responsive table-striped" width="100%"
					border="0" align="center" cellpadding="0" cellspacing="0">
					<tr align="center" height="30"
						style="border: 2px solid; text-align: center; vertical-align: middle; font-size: 18px; font-weight: bold;">
						<td width="58"><input class="btn btn-primary" id="del_id"
							type="submit" value="删除"></td>
						<td width="80">编号</td>
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
						<td width="80">更新事件</td>

						<%
							List<Parkingincident> list = result;

							int n = list.size();
							int m = 0;
							
							for (m = beginIndex - 1; m < total - 1; m++) {
								
								Parkingincident t = list.get(m);
								
								int id = t.getId();
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
								
								DecimalFormat decimalFormat=new DecimalFormat(".00");
								String getCoverage = decimalFormat.format(coverage*100) + "%";
								String getDistance1 = decimalFormat.format(distance1) + "km";
								String getDistance2 = decimalFormat.format(distance2) + "km";
						%>
					
					<tr align="center" height="30">
						<td width="58"><input type="checkbox" name="ck" value="${id}"></td>
						<td width="80"><%=id%></td>
						<td class="edit" width="80"><%=year%></td>
						<td class="edit" width="80"><%=state%></td>
						<td class="edit" width="80"><%=nowAttack%></td>
						<td class="edit" width="80"><%=getCatch%></td>
						<td class="edit" width="80"><%=pastAttack%></td>
						<td class="edit" width="80"><%=getCoverage%></td>
						<td class="edit" width="80"><%=density%></td>
						<td class="edit" width="80"><%=timeToStr%></td>
						<td class="edit" width="80"><%=getDistance1%></td>
						<td class="edit" width="80"><%=getDistance2%></td>
						<td><a class="btn btn-primary"
							href="showParking_subInfo.jsp?id=<%=id%>">车辆信息</a></td>
						<%-- <td><a class="btn btn-primary"
							href="showcrimAll.jsp?id=<%=id%>">罪犯详情</a></td> --%>
						<td width="58"><input class="btn btn-primary" type="button"
							id="toEdit" value="编辑"></td>

						<%
							}
						%>
					
				</table>
				<%
					if (pageCount != 0) {
						if (pageNow != 1) {
							out.println(" <a href=selectParking.jsp?pageNow=1" + "&parking_state=" + tempvalue1 + "&parking_year="
									+ tempvalue2 + "&parking_density=" + tempvalue3 + "&parking_coverage1=" + tempvalue4 + "&parking_coverage2="
									+ tempvalue5 + "&state=" + type + ">【首页】</a> ");
							out.println(" <a href=selectParking.jsp?pageNow=" + (pageNow - 1) + "&parking_state=" + tempvalue1 + "&parking_year="
									+ tempvalue2 + "&parking_density=" + tempvalue3 + "&parking_coverage1=" + tempvalue4 + "&parking_coverage2="
									+ tempvalue5 + "&state=" + type
									+ ">上一页</a> ");
						}
						if (pageNow != pageCount) {
							out.println(" <a href=selectParking.jsp?pageNow=" + (pageNow + 1) + "&parking_state=" + tempvalue1 + "&parking_year="
									+ tempvalue2 + "&parking_density=" + tempvalue3 + "&parking_coverage1=" + tempvalue4 + "&parking_coverage2="
									+ tempvalue5 + "&state=" + type
									+ "> 下一页</a>");
							out.println(" <a href=selectParking.jsp?pageNow=" + pageCount + "&parking_state=" + tempvalue1 + "&parking_year="
									+ tempvalue2 + "&parking_density=" + tempvalue3 + "&parking_coverage1=" + tempvalue4 + "&parking_coverage2="
									+ tempvalue5 + "&state=" + type
									+ "> 【尾页】</a>");
						}
						//显示当前页的前三页与后三页
						for (i = ((pageNow - 3) > 0 ? (pageNow - 3) : 1); i <= (pageCount < (pageNow + 3)
								? pageCount
								: (pageNow + 3)); i++) {
							out.println("<a href=selectParking.jsp?pageNow=" + i + "&parking_state=" + tempvalue1 + "&parking_year="
									+ tempvalue2 + "&parking_density=" + tempvalue3 + "&parking_coverage1=" + tempvalue4 + "&parking_coverage2="
									+ tempvalue5 + "&state=" + type + ">[" + i + "]</a>");
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

		<script type="text/javascript" src="jquery-1.11.1.js"></script>
		<script type="text/javascript">
			$(function() {
				$("#del_id").click(function() {
					var checkNum = $("input[name='ck']:checked").length;
					if (checkNum == 0) {
						alert("请选择至少一条数据!");
						return;
					}
					
					var checkList = "";
					if (confirm("确定要删除所选项吗?")) {
						/*var checkList = new Array();
						$("input[name='ck']:checked").each(function() {
							checkList.push($(this).val());
						});*/
						var check_obj = $("input[name='ck']");
						for(var i = 0; i < check_obj.length; i++) {
							if(check_obj.get(i).checked == true) {
								checkList += check_obj.get(i).value + ",";
							}
						}
						
						checkList = checkList.substring(0,checkList.length - 1);
						//alert(checkList)
						var data1 = $("#parking_state_id").val();
						var data2 = $("#parking_year_id").val();
						var data3 = $("#parking_density_id").val();
						var data4 = $("#parking_coverage1_id").val();
						var data5 = $("#parking_coverage2_id").val();
						var data6 = $("#state_id").val();
					/*	$.post("selectParkingDelete", {
							"ck" : checkList.toString(),
							"parking_state" : data1,
							"parking_year" : data2,
							"parking_density" : data3,
							"parking_coverage1" : data4,
							"parking_coverage2" : data5,
							"state" : data6
						}, function(result) {
							callback = result.status;
							//alert(callback);
						}, "json"); */
						
						$ajax({
							type:"POST",
							url:"selectPakingDelete",
							data:{
								'ck' : checkList.toString(),
								'parking_state' : data1,
								'parking_year' : data2,
								'parking_density' : data3,
								'parking_coverage1' : data4,
								'parking_coverage2' : data5,
								'state' : data6
							},
							success:function(result) {
								$("[name='ck']:checkbox").attr("checked",false);
								window.location.reload();
							}
						})
					}
				});
			});

			function jump(maxPage) {
				var pages = document.getElementById("page_id").value;//得到id为page_id输入框的值
				var tempvalue1_1 = document.getElementById("parking_state_id").value;
				var tempvalue2_1 = document.getElementById("parking_year_id").value;
				var tempvalue3_1 = document.getElementById("parking_density_id").value;
				var tempvalue4_1 = document.getElementById("parking_coverage1_id").value;
				var tempvalue5_1 = document.getElementById("parking_coverage2_id").value;
				var type_1 = document.getElementById("state_id").value;
				pages_int = parseInt(pages)
				maxPage_int = parseInt(maxPage)
				if (pages_int > 0 && pages_int <= maxPage_int) {
					window.location.href = "selectParking.jsp?pageNow=" + pages
							+ "&parking_state=" + tempvalue1_1 + "&parking_year="
							+ tempvalue2_1 + "&parking_density=" + tempvalue3_1 
							+ "&parking_coverage1=" + tempvalue4_1 + "&parking_coverage2"
							+ tempvalue5_1 + "&state=" + type_1;
					return true;
				} else {
					alert("您输入的页码有误！");
					return false;
				}
			}

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


