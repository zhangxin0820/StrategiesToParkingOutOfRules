<%@ page import="com.zx.db.DBConn"%>
<%@ page import="com.zx.dao.ParkingpersonDao"%>
<%@ page import="com.zx.model.Parkingperson"%>
<%@ page import="java.util.List"%>
<%@ page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<style>
.body-main {
	background: url(image/bkground.png) repeat-x;
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

<title>人物详细信息</title>

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
			//删除操作
			String deleteName[];
			if (request.getParameter("sub_ck") != null) {
				deleteName = request.getParameterValues("sub_ck");
				int[] deleteId = new int[deleteName.length];

				ParkingpersonDao.delParkingperson(deleteName);
			}
		%>

		<%
			//显示操作
			int temp_person_id = Integer.parseInt(request.getParameter("id").trim());
			int stateId = Integer.parseInt(request.getParameter("stateId").trim());
			int haveCarId = Integer.parseInt(request.getParameter("haveCarId").trim());
			List<Parkingperson> resultInfor = ParkingpersonDao.getDetailed(temp_person_id);
			int temp_have_car_id = 0;
			int rowCount = resultInfor.size();//总行数
			int i = 1;
			int pageSize = 10;
			int pageNow = 1;

			//默认显示第一页 
			int pageCount = 0;//总页数  0
			String nowpage = (String) request.getParameter("pageNow");
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

					Parkingperson tempInfo = resultInfor.get(j);

					int person_id = tempInfo.getPerson_id();
					int have_car_id = tempInfo.getHave_car_id();
					String date = tempInfo.getParking_date().toString();
					String name = tempInfo.getPerson_name();
					String gender = tempInfo.getPerson_gender();
					int age = tempInfo.getPerson_age();
					String nationality = tempInfo.getPerson_nationality();
					String birthday = tempInfo.getPerson_birthday().toString();
					String job = tempInfo.getPerson_job();
					String education = tempInfo.getPerson_education();
					String hkadr = tempInfo.getPerson_hkadr();
					int height = tempInfo.getPerson_height();
					int weight = tempInfo.getPerson_weight();
					String nativeplace = tempInfo.getPerson_nativeplace();
					String maritalstatus = tempInfo.getPerson_maritalstatus();
					String politicalstatus = tempInfo.getPerson_politicalstatus();

					int tempj = j;
					temp_have_car_id = have_car_id;
		%>


		<%
			}
			}
		%>

		<div class="containt-fluid" align="center">
			<form Action="" method="post">

				<input type="hidden" id="temp_id2" value="<%=haveCarId%>" /> <input
					type="hidden" id="temp_stateId" value="<%=stateId%>" /> <input
					class="btn btn-primary add-btn pull-right" type="button"
					onclick="window.location.href='showParking_personInfo.jsp?id=<%=haveCarId%>&stateId=<%=stateId%>'"
					value="返回人物信息" /> <input
					class="btn btn-primary add-btn pull-right" type="button"
					onclick="window.location.href='showParkingInfo.jsp'" value="返回主页面" />
				<table class="containt-fluid table table-striped" width="100%"
					border="0" align="center" cellpadding="0" cellspacing="0">
					<tr align="center" height="30"
						style="border: 2px solid; text-align: center; vertical-align: middle; font-size: 18px; font-weight: bold;">
						<td width="58"><input class="btn btn-primary" type="submit"
							value="删除" onclick="deleteConfirm()"></td>
						<td width="80">子编号</td>
						<td width="80" style="display: none;">索引编号</td>
						<td width="80" style="display: none;">车辆编号</td>
						<td width="80">违章时间</td>
						<td width="80">姓名</td>
						<td width="80">性别</td>
						<td width="80">年龄</td>
						<td width="80">民族</td>
						<td width="80">生日</td>
						<td width="80">工作</td>
						<td width="80">学历</td>
						<td width="80">户籍</td>
						<td width="80">身高</td>
						<td width="80">体重</td>
						<td width="80">籍贯</td>
						<td width="80">婚姻状况</td>
						<td width="80">政治面貌</td>
						<!-- <td width="80">车辆信息</td> -->
						<td width="80">更新人物信息</td>

						<%
							List<Parkingperson> list = ParkingpersonDao.getDetailed(temp_person_id);

							int n = list.size();
							if (!list.isEmpty()) {
								for (int k = beginIndex - 1; k < total - 1; k++) {
									Parkingperson t = list.get(k);

									int person_id = t.getPerson_id();
									int have_car_id = t.getHave_car_id();
									String date = t.getParking_date().toString();
									String name = t.getPerson_name();
									String gender = t.getPerson_gender();
									
									int age = t.getPerson_age();
									String getAge = String.valueOf(age) + "岁";
									
									String nationality = t.getPerson_nationality();
									String birthday = t.getPerson_birthday().toString();
									String job = t.getPerson_job();
									String education = t.getPerson_education();
									String hkadr = t.getPerson_hkadr();
									
									int height = t.getPerson_height();
									String getHeight = String.valueOf(height) + "厘米";
									int weight = t.getPerson_weight();
									String getWeight = String.valueOf(weight) + "公斤";
									
									String nativeplace = t.getPerson_nativeplace();
									String maritalstatus = t.getPerson_maritalstatus();
									String politicalstatus = t.getPerson_politicalstatus();
						%>
					
					<tr align="center" height="30">
						<td width="58"><input type="checkbox" name="person_ck"
							value=<%=person_id%>></td>
						<td width="80"><%=person_id%></td>
						<td width="80" style="display: none;"><%=have_car_id%></td>
						<td class="edit" width="80"><%=date%></td>
						<td class="edit" width="80"><%=name%></td>
						<td class="edit" width="80"><%=gender%></td>
						<td class="edit" width="80"><%=getAge%></td>
						<td class="edit" width="80"><%=nationality%></td>
						<td class="edit" width="80"><%=birthday%></td>
						<td class="edit" width="80"><%=job%></td>
						<td class="edit" width="80"><%=education%></td>
						<td class="edit" width="80"><%=hkadr%></td>
						<td class="edit" width="80"><%=getHeight%></td>
						<td class="edit" width="80"><%=getWeight%></td>
						<td class="edit" width="80"><%=nativeplace%></td>
						<td class="edit" width="80"><%=maritalstatus%></td>
						<td class="edit" width="80"><%=politicalstatus%></td>
						<%-- <td><a class="link-a btn btn-primary"
							href="showParking_carInfo.jsp?id=<%=person_id%>">车辆信息</a></td>
						<td><a class="link-a btn btn-primary"
							href="showParking_personDetailed.jsp?id=<%=person_id%>">人物详细信息</a></td> --%>
						<td width="58"><input class="btn btn-primary" type="button"
							id="toEdit" value="编辑"></td>


						<%
							}
							}
						%>
					
				</table>
				<%
					if (pageCount != 0) {
						if (pageNow != 1) {
							out.println(
									" <a   href=showParking_personDetailed.jsp?pageNow=1>" + "&id=" + temp_person_id + "【首页】</a> ");
							out.println(" <a   href=showParking_personDetailed.jsp?pageNow=" + (pageNow - 1) + "> 上一页</a> ");
						}
						if (pageNow != pageCount) {
							out.println(" <a   href=showParking_personDetailed.jsp?pageNow=" + (pageNow + 1) + "&id="
									+ temp_person_id + "> 下一页</a>");
							out.println(" <a   href=showParking_personDetailed.jsp?pageNow=" + pageCount + "&id=" + temp_person_id
									+ "> 【尾页】</a>");
						}
						//显示当前页的前三页与后三页
						for (i = ((pageNow - 3) > 0 ? (pageNow - 3) : 1); i <= (pageCount < (pageNow + 3)
								? pageCount
								: (pageNow + 3)); i++) {
							out.println(" <a   href=showParking_personDetailed.jsp?pageNow=" + i + "&id=" + temp_person_id + ">["
									+ i + "]</a>");
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
			function deleteConfirm() {
				if (confirm("确认要删除吗?")) {
					return true;
				}
				return false;
			}

			function jump(maxPage) {
				var pages = document.getElementById("page_id").value;//得到id为page_id输入框的值
				var tempid2 = document.getElementById("temp_id2").value;
				var tempStateId = document.getElementById("temp_stateId").value;
				pages_int = parseInt(pages)
				maxPage_int = parseInt(maxPage)
				if (pages_int > 0 && pages_int <= maxPage_int) {
					//alert("bbbb"+pages)
					window.location.href = "showParking_personDetailed.jsp?pageNow="
							+ pages + "&id=" + tempid2 + "&stateId=" + tempStateId;
					return true;
				} else {
					alert("您输入的页码有误！");
					return false;
				}
			}

			$(function() {
				$("#toEdit").click(
						function() {
							//$.post("updateterri.java",{{edit:$(".edit").val()}})
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
									//alert(obj_text.val());
									data.push(obj_text.val());
								});
								var person_id = tr.children().eq(1).text();
								$.post("updateparking_personDetailed", {
									"person_id" : person_id,
									"parking_date" : data[0],
									"person_name" : data[1],
									"person_gender" : data[2],
									"person_age" : data[3],
									"person_nationality" : data[4],
									"person_birthday" : data[5],
									"person_job" : data[6],
									"person_education" : data[7],
									"person_hkadr" : data[8],
									"person_height" : data[9],
									"person_weight" : data[10],
									"person_nativeplace" : data[11],
									"person_maritalstatus" : data[12],
									"person_politicalstatus" : data[13]
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