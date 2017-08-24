<%@ page import="com.zx.db.DBConn"%>
<%@ page import="com.zx.dao.SubincidentDao"%>
<%@ page import="com.zx.model.Subincident"%>
<%@ page import="java.util.List"%>
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

<title>车辆信息</title>

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

				SubincidentDao.delSubincident(deleteName);
			}
		%>
		
		<%
			//显示操作
			//int temp_sub_id = Integer.parseInt(request.getParameter("id").trim());
			//System.out.println(temp_sub_id);
			List<Subincident> resultInfor = SubincidentDao.getAll();
			int rowCount = resultInfor.size();//总行数
			int i = 1;
			int pageSize = 10;
			int pageNow = 1;

			//默认显示第一页  s
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
				//System.out.println("rowcount="+rowCount+"pageCount="+pageCount+"begin="+beginIndex);
				for (int j = beginIndex - 1; j < total - 1; j++) {

					Subincident tempInfo = resultInfor.get(j);

					int sub_id = tempInfo.getSub_id();
					int sub_event_id = tempInfo.getSub_event_id();
					int have_person_id = tempInfo.getHave_person_id();
					String date = tempInfo.getCar_date().toString();
					String car_id = tempInfo.getCar_id();
					String car_type = tempInfo.getCar_type();

					int tempj = j;
		%>


		<%
			}
			}
		%>

		<div class="containt-fluid" align="center">
			<form Action="" method="post">

				<input class="btn btn-primary add-btn pull-right" type="button"
					onclick="window.location.href='addsub_incident.jsp'" value="增加新事件" /> <input
					class="btn btn-primary add-btn pull-right" type="button"
					onclick="window.location.href='index.jsp'" value="返回首页" />
				<table class="containt-fluid table table-striped" width="100%"
					border="0" align="center" cellpadding="0" cellspacing="0">
					<tr align="center" height="30"
						style="border: 2px solid; text-align: center; vertical-align: middle; font-size: 18px; font-weight: bold;">
						<td width="58"><input class="btn btn-primary" type="submit"
							value="删除" onclick="return deleteConfirm()"></td>
						<td width="80">子编号</td>
						<td width="80" style="display: none;">索引编号</td>
						<td width="80" style="display: none;">司机编号</td>
						<td width="80">生产日期</td>
						<td width="80">车牌号</td>
						<td width="80">车型</td>
						<td width="80">人物信息</td>
						<td width="80">更新车辆信息</td>
						<%
							List<Subincident> list = SubincidentDao.getAll();

							if (!list.isEmpty()) {
								int n = list.size();
								for (int k = beginIndex - 1; k < total - 1; k++) {
									//System.out.println(k+"aaa"+n+"total="+total);
									Subincident t = null;
									t = list.get(k);

									int sub_id = t.getSub_id();
									int sub_event_id = t.getSub_event_id();
									int have_person_id = t.getHave_person_id();
									String date = t.getCar_date().toString();
									String car_id = t.getCar_id();
									String car_type = t.getCar_type();
						%>
					
					<tr align="center" height="30">
						<td width="58"><input type="checkbox" name="sub_ck"
							value=<%=sub_id%>></td>
						<td width="80"><%=sub_id%></td>
						<td width="80" style="display: none;"><%=sub_event_id%></td>
						<td width="80" style="display: none;"><%=have_person_id%></td>
						<td class="edit" width="80"><%=date%></td>
						<td class="edit" width="80"><%=car_id%></td>
						<td class="edit" width="80"><%=car_type%></td>
						<td><a class="link-a btn btn-primary"
								href="showCarToPerson.jsp?id=<%=sub_id%>">人物信息</a></td>
						<td width="58"><input class="btn btn-primary" type="button" id="toEdit"
							value="编辑"></td>


						<%
							}
							}
						%>
						
				</table>
				<%
					if (pageCount != 0) {
						if (pageNow != 1) {
							out.println(" <a href=showSub_incident.jsp?pageNow=1>" + "【首页】</a> ");
							out.println(
									" <a href=showSub_incident.jsp?pageNow=" + (pageNow - 1) + "> 上一页</a> ");
						}
						if (pageNow != pageCount) {
							out.println(
									" <a href=showSub_incident.jsp?pageNow=" + (pageNow + 1) + "> 下一页</a>");
							out.println(" <a href=showSub_incident.jsp?pageNow=" + pageCount +  "> 【尾页】</a>");
						}
						//显示当前页的前三页与后三页
						for (i = ((pageNow - 3) > 0 ? (pageNow - 3) : 1); i <= (pageCount < (pageNow + 3)
								? pageCount : (pageNow + 3)); i++) {
							out.println("<a href=showSub_incident.jsp?pageNow=" + i + ">[" + i + "]</a>");
						}
					}
					out.print("第");
				%>
				<input type="text" name="pages" id="page_id" size="3" value="<%=pageNow%>" />
				<%
					out.print("/");
				%>
				<font size="2"><%=pageCount%></font>

				<%
					out.print("页");
				%>
				
				<input class="btn btn-primary btn-xs" type="button" value="跳转" onclick="return jump('<%=pageCount%>')"/>
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
				var tempid1 = document.getElementById("temp_id1").value;
				pages_int = parseInt(pages)
				maxPage_int = parseInt(maxPage)
				if (pages_int > 0 && pages_int <= maxPage_int) {
					window.location.href = "showSub_incident.jsp?pageNow=" + pages;
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
								var sub_id = tr.children().eq(1).text();
								$.post("updatesub_incident", {
									"sub_id" : sub_id,
									"car_date" : data[0],
									"car_id" : data[1],
									"car_type" : data[2]
								}, function (result) {
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