<%@ page import="com.zx.db.DBConn"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="com.zx.dao.ParkingincidentDao"%>
<%@ page import="com.zx.model.Parkingincident"%>
<%@ page import="com.zx.dao.SubincidentDao"%>
<%@ page import="com.zx.model.Subincident"%>
<%@ page import="com.zx.dao.ParkingpersonDao"%>
<%@ page import="com.zx.model.Parkingperson"%>
<%@ page import="java.util.List"%>
<%@ page import="java.sql.Date"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">

.body-main {
	background: url(image/bkground.png) repeat-x;
}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>整体数据拓扑图</title>
</head>
<body>

	<canvas id="canvas" width="1680" height="960"></canvas>
<%
	int tempId = Integer.parseInt(request.getParameter("id"));
	//System.out.print(tempId);
	//parking_incident表
	List<Parkingincident> list1 = ParkingincidentDao.getOne(tempId);
	Parkingincident t1 = list1.get(0);
	
	int id = t1.getId();
	//int get_person_id = t.getGet_person_id();
	int year = t1.getParking_year();
	String state = t1.getParking_state();
	float attack_now = t1.getParking_attack_now();
	float pCatch = t1.getParking_catch();
	float attack_past = t1.getParking_attack_past();
	float coverage = t1.getParking_coverage();
	float density = t1.getParking_density();
	float tempTime = t1.getParking_time();
	float distance1 = t1.getParking_distance1();
	float distance2 = t1.getParking_distance2();

	String getYear = String.valueOf(year) + "年";
	String getState = state + "号区域";

	String getTime = null;
	int hour = (int) tempTime;
	int minute = (int) (60 * (tempTime - hour));
	String timeToHour = String.valueOf(hour);
	String timeToMinute = String.valueOf(minute);
	String time = timeToHour + "时" + timeToMinute + "分";

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
	
<input id="getYear" type="hidden" value="<%=getYear%>"/>
<input id="getState" type="hidden" value="<%=getState%>"/>
<input id="nowAttack" type="hidden" value="<%=nowAttack%>"/>
<input id="getCatch" type="hidden" value="<%=getCatch%>"/>
<input id="pastAttack" type="hidden" value="<%=pastAttack%>"/>
<input id="getCoverage" type="hidden" value="<%=getCoverage%>"/>
<input id="density" type="hidden" value="<%=density%>"/>
<input id="time" type="hidden" value="<%=time%>"/>
<input id="getDistance1" type="hidden" value="<%=getDistance1%>"/>
<input id="getDistance2" type="hidden" value="<%=getDistance2%>"/>
	
	<table  id="subTableId">
	<%
	//sub_incident表
	List<Subincident> list2 = SubincidentDao.getAll(tempId);
	int sub_num = list2.size();
	if(!list2.isEmpty()) {
		for (int k = 0;k < sub_num; k++) {
			Subincident t = list2.get(k);
			
			int sub_id = t.getSub_id();
			int sub_event_id = t.getSub_event_id();
			int have_person_id = t.getHave_person_id();
			String date = t.getCar_date().toString();
			String car_id = t.getCar_id();
			String car_type = t.getCar_type();
			
%>
<tr>
	<td width="80" style="display: none;"><%=sub_id%></td>
	<td width="80" style="display: none;"><%=sub_event_id%></td>
	<td width="80" style="display: none;"><%=have_person_id%></td>
	<td width="80" style="display: none;"><%=date%></td>
	<td width="80" style="display: none;"><%=car_id%></td>
	<td width="80" style="display: none;"><%=car_type%></td>
	
	<%
		}
			}
				%>
						
</table>

<table id="personTableId">
<%	
	//parking_person表
	List<Parkingperson> list3 = ParkingpersonDao.getPersonById(tempId);
	int person_sum = list3.size();
	if (!list3.isEmpty()) {
		for (int k = 0; k < person_sum; k++) {
			Parkingperson t = list3.get(k);

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

<tr>
	<td width="80" style="display: none;"><%=person_id%></td>
	<td width="80" style="display: none;"><%=have_car_id%></td>
	<td width="80" style="display: none;"><%=date%></td>
	<td width="80" style="display: none;"><%=name%></td>
	<td width="80" style="display: none;"><%=gender%></td>
	<td width="80" style="display: none;"><%=getAge%></td>
	<td width="80" style="display: none;"><%=nationality%></td>
	<td width="80" style="display: none;"><%=birthday%></td>
	<td width="80" style="display: none;"><%=job%></td>
	<td width="80" style="display: none;"><%=education%></td>
	<td width="80" style="display: none;"><%=hkadr%></td>
	<td width="80" style="display: none;"><%=getHeight%></td>
	<td width="80" style="display: none;"><%=getWeight%></td>
	<td width="80" style="display: none;"><%=nativeplace%></td>
	<td width="80" style="display: none;"><%=maritalstatus%></td>
	<td width="80" style="display: none;"><%=politicalstatus%></td>

<%
		}
	}
	
%>

</table>

<script type="text/javascript" src="jquery-1.11.1.js"></script>
<script type="text/javascript" src="jtopo-0.4.8-min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var canvas = document.getElementById('canvas');
        var stage = new JTopo.Stage(canvas);
        //显示工具栏
        /* showJTopoToobar(stage); */
        var scene = new JTopo.Scene();
        stage.add(scene);
        scene.background = 'image/map.png';
        
        var cloudNode = new JTopo.Node('数据关系图(双击返回主页面)');
        cloudNode.setSize(30, 26);
        cloudNode.setLocation(720, 480);            
        cloudNode.layout = {type: 'circle', radius: 160};
        scene.add(cloudNode);
        
        cloudNode.dbclick(function(event){
        	window.location.href = "index.jsp";
        });
        
        
        
        //违章信息
        var nodeParking = new JTopo.CircleNode('违章信息');
        nodeParking.fillStyle = '200,255,0';
        nodeParking.radius = 18;
        nodeParking.setLocation(scene.width * Math.random(), scene.height * Math.random());
        nodeParking.layout = {type: 'circle', radius: 60};
        scene.add(nodeParking);                                
        var linkParking = new JTopo.Link(cloudNode, nodeParking);
        scene.add(linkParking);
        var parkingNode1 = new JTopo.CircleNode('年份:' + document.getElementById("getYear").value);
        parkingNode1.radius = 10;
        parkingNode1.fillStyle = '255,255,0';
        parkingNode1.setLocation(scene.width * Math.random(), scene.height * Math.random());
        scene.add(parkingNode1);                                
        scene.add(new JTopo.Link(nodeParking, parkingNode1));
        var parkingNode2 = new JTopo.CircleNode('区域:' + document.getElementById("getState").value);
        parkingNode2.radius = 10;
        parkingNode2.fillStyle = '255,255,0';
        parkingNode2.setLocation(scene.width * Math.random(), scene.height * Math.random());
        scene.add(parkingNode2);                                
        scene.add(new JTopo.Link(nodeParking, parkingNode2));
        var parkingNode3 = new JTopo.CircleNode('车主行为:' + document.getElementById("nowAttack").value);
        parkingNode3.radius = 10;
        parkingNode3.fillStyle = '255,255,0';
        parkingNode3.setLocation(scene.width * Math.random(), scene.height * Math.random());
        scene.add(parkingNode3);                                
        scene.add(new JTopo.Link(nodeParking, parkingNode3));
        var parkingNode4 = new JTopo.CircleNode('交警行为:' + document.getElementById("getCatch").value);
        parkingNode4.radius = 10;
        parkingNode4.fillStyle = '255,255,0';
        parkingNode4.setLocation(scene.width * Math.random(), scene.height * Math.random());
        scene.add(parkingNode4);                                
        scene.add(new JTopo.Link(nodeParking, parkingNode4));
        var parkingNode5 = new JTopo.CircleNode('车主过去行为:' + document.getElementById("pastAttack").value);
        parkingNode5.radius = 10;
        parkingNode5.fillStyle = '255,255,0';
        parkingNode5.setLocation(scene.width * Math.random(), scene.height * Math.random());
        scene.add(parkingNode5);                                
        scene.add(new JTopo.Link(nodeParking, parkingNode5));
        var parkingNode6 = new JTopo.CircleNode('资源概率分布:' + document.getElementById("getCoverage").value);
        parkingNode6.radius = 10;
        parkingNode6.fillStyle = '255,255,0';
        parkingNode6.setLocation(scene.width * Math.random(), scene.height * Math.random());
        scene.add(parkingNode6);                                
        scene.add(new JTopo.Link(nodeParking, parkingNode6));
        var parkingNode7 = new JTopo.CircleNode('人口密度:' + document.getElementById("density").value);
        parkingNode7.radius = 10;
        parkingNode7.fillStyle = '255,255,0';
        parkingNode7.setLocation(scene.width * Math.random(), scene.height * Math.random());
        scene.add(parkingNode7);                                
        scene.add(new JTopo.Link(nodeParking, parkingNode7));
        var parkingNode8 = new JTopo.CircleNode('时间:' + document.getElementById("time").value);
        parkingNode8.radius = 10;
        parkingNode8.fillStyle = '255,255,0';
        parkingNode8.setLocation(scene.width * Math.random(), scene.height * Math.random());
        scene.add(parkingNode8);                                
        scene.add(new JTopo.Link(nodeParking, parkingNode8));
        var parkingNode9 = new JTopo.CircleNode('停车场距离:' + document.getElementById("getDistance1").value);
        parkingNode9.radius = 10;
        parkingNode9.fillStyle = '255,255,0';
        parkingNode9.setLocation(scene.width * Math.random(), scene.height * Math.random());
        scene.add(parkingNode9);                                
        scene.add(new JTopo.Link(nodeParking, parkingNode9));
        var parkingNode10 = new JTopo.CircleNode('距离:' + document.getElementById("getDistance2").value);
        parkingNode10.radius = 10;
        parkingNode10.fillStyle = '255,255,0';
        parkingNode10.setLocation(scene.width * Math.random(), scene.height * Math.random());
        scene.add(parkingNode10);                                
        scene.add(new JTopo.Link(nodeParking, parkingNode10));
        
        
        
        
        
        //车辆信息
        var nodeSub = new JTopo.CircleNode('车辆信息');
        nodeSub.fillStyle = '200,255,0';
        nodeSub.radius = 18;
        nodeSub.setLocation(scene.width * Math.random(), scene.height * Math.random());
        nodeSub.layout = {type: 'tree', direction: 'left', width: 50, height: 90};
        scene.add(nodeSub);
        var linkSub = new JTopo.Link(cloudNode, nodeSub);
        scene.add(linkSub);

        var subTable = document.getElementById("subTableId");
        
        for (var i = 0; i < subTable.rows.length; i++) {
        	var str = "车辆" + (i+1).toString();
        	var nodeCar = new JTopo.CircleNode(str);
        	nodeCar.fillStyle = '200,255,0';
            nodeCar.radius = 15;
            nodeCar.setLocation(scene.width * Math.random(), scene.height * Math.random());
            nodeCar.layout = {type: 'circle', radius: 60};
            scene.add(nodeCar);                                
            scene.add(new JTopo.Link(nodeSub, nodeCar));
            
            var id = subTable.rows[i].cells[0].innerText
            var stateId = subTable.rows[i].cells[1].innerText;
            
            var node1 = new JTopo.CircleNode('对应人物:' + subTable.rows[i].cells[2].innerText);
        	node1.radius = 10;
        	node1.fillStyle = '255,255,0';
        	node1.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node1);                                
            scene.add(new JTopo.Link(nodeCar, node1));
            node1.dbclick(function(event){
            	window.location.href = "showParking_personInfo.jsp?id=" + id + "&stateId=" + stateId;
            });
            
            var node2 = new JTopo.CircleNode('车辆生产日期:' + subTable.rows[i].cells[3].innerText);
        	node2.radius = 10;
        	node2.fillStyle = '255,255,0';
        	node2.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node2);                                
            scene.add(new JTopo.Link(nodeCar, node2));
            
            var node3 = new JTopo.CircleNode('车牌号:' + subTable.rows[i].cells[4].innerText);
        	node3.radius = 10;
        	node3.fillStyle = '255,255,0';
        	node3.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node3);                                
            scene.add(new JTopo.Link(nodeCar, node3));
            
            var node4 = new JTopo.CircleNode('车辆类型:' + subTable.rows[i].cells[5].innerText);
        	node4.radius = 10;
        	node4.fillStyle = '255,255,0';
        	node4.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node4);                                
            scene.add(new JTopo.Link(nodeCar, node4));
        }
        
        
        
        
        //人物信息
        var nodePerson = new JTopo.CircleNode('人物信息');
        nodePerson.fillStyle = '200,255,0';
        nodePerson.radius = 18;
        nodePerson.setLocation(scene.width * Math.random(), scene.height * Math.random());
        nodePerson.layout = {type: 'tree', direction: 'top', width: 50, height: 90};
        scene.add(nodePerson);
        var linkPerson = new JTopo.Link(cloudNode, nodePerson);
        scene.add(linkPerson);
        
        var personTable = document.getElementById("personTableId");
        for (var i = 0; i < personTable.rows.length; i++) {
        	var str = "人物" + (i+1).toString();
        	var nodePer = new JTopo.CircleNode(str);
        	nodePer.fillStyle = '200,255,0';
        	nodePer.radius = 15;
        	nodePer.setLocation(scene.width * Math.random(), scene.height * Math.random());
        	nodePer.layout = {type: 'circle', radius: 60};
            scene.add(nodePer);                                
            scene.add(new JTopo.Link(nodePerson, nodePer));
            
            var id = personTable.rows[i].cells[1].innerText;
            
            var node1 = new JTopo.CircleNode('对应车辆:' + personTable.rows[i].cells[1].innerText);
        	node1.radius = 10;
        	node1.fillStyle = '255,255,0';
        	node1.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node1);                                
            scene.add(new JTopo.Link(nodePer, node1));
            node1.dbclick(function(event){
            	window.location.href = "showParking_carInfo.jsp?id=" + id;
            });
            
            var node2 = new JTopo.CircleNode('违章时间:' + personTable.rows[i].cells[2].innerText);
        	node2.radius = 10;
        	node2.fillStyle = '255,255,0';
        	node2.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node2);                                
            scene.add(new JTopo.Link(nodePer, node2));
            
            var node3 = new JTopo.CircleNode('姓名:' + personTable.rows[i].cells[3].innerText);
        	node3.radius = 10;
        	node3.fillStyle = '255,255,0';
        	node3.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node3);                                
            scene.add(new JTopo.Link(nodePer, node3));
            
            var node4 = new JTopo.CircleNode('性别:' + personTable.rows[i].cells[4].innerText);
        	node4.radius = 10;
        	node4.fillStyle = '255,255,0';
        	node4.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node4);                                
            scene.add(new JTopo.Link(nodePer, node4));
            
            var node5 = new JTopo.CircleNode('年龄:' + personTable.rows[i].cells[5].innerText);
        	node5.radius = 10;
        	node5.fillStyle = '255,255,0';
        	node5.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node5);                                
            scene.add(new JTopo.Link(nodePer, node5));
            
            var node6 = new JTopo.CircleNode('民族:' + personTable.rows[i].cells[6].innerText);
        	node6.radius = 10;
        	node6.fillStyle = '255,255,0';
        	node6.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node6);                                
            scene.add(new JTopo.Link(nodePer, node6));
            
            var node7 = new JTopo.CircleNode('生日:' + personTable.rows[i].cells[7].innerText);
        	node7.radius = 10;
        	node7.fillStyle = '255,255,0';
        	node7.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node7);                                
            scene.add(new JTopo.Link(nodePer, node7));
            
            var node8 = new JTopo.CircleNode('职业:' + personTable.rows[i].cells[8].innerText);
        	node8.radius = 10;
        	node8.fillStyle = '255,255,0';
        	node8.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node8);                                
            scene.add(new JTopo.Link(nodePer, node8));
            
            var node9 = new JTopo.CircleNode('学历:' + personTable.rows[i].cells[9].innerText);
        	node9.radius = 10;
        	node9.fillStyle = '255,255,0';
        	node9.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node9);                                
            scene.add(new JTopo.Link(nodePer, node9));
            
            var node10 = new JTopo.CircleNode('户籍:' + personTable.rows[i].cells[10].innerText);
        	node10.radius = 10;
        	node10.fillStyle = '255,255,0';
        	node10.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node10);                                
            scene.add(new JTopo.Link(nodePer, node10));
            
            var node11 = new JTopo.CircleNode('身高:' + personTable.rows[i].cells[11].innerText);
        	node11.radius = 10;
        	node11.fillStyle = '255,255,0';
        	node11.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node11);                                
            scene.add(new JTopo.Link(nodePer, node11));
            
            var node12 = new JTopo.CircleNode('体重:' + personTable.rows[i].cells[12].innerText);
        	node12.radius = 10;
        	node12.fillStyle = '255,255,0';
        	node12.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node12);                                
            scene.add(new JTopo.Link(nodePer, node12));
            
            var node13 = new JTopo.CircleNode('籍贯:' + personTable.rows[i].cells[13].innerText);
        	node13.radius = 10;
        	node13.fillStyle = '255,255,0';
        	node13.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node13);                                
            scene.add(new JTopo.Link(nodePer, node13));
            
            var node14 = new JTopo.CircleNode('婚姻状况:' + personTable.rows[i].cells[14].innerText);
        	node14.radius = 10;
        	node14.fillStyle = '255,255,0';
        	node14.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node14);                                
            scene.add(new JTopo.Link(nodePer, node14));
            
            var node15 = new JTopo.CircleNode('政治面貌:' + personTable.rows[i].cells[15].innerText);
        	node15.radius = 10;
        	node15.fillStyle = '255,255,0';
        	node15.setLocation(scene.width * Math.random(), scene.height * Math.random());
            scene.add(node15);                                
            scene.add(new JTopo.Link(nodePer, node15));
        }
        
        
        
        
        
        JTopo.layout.layoutNode(scene, cloudNode, true);
        
        scene.addEventListener('mouseup', function(e){
        	if(e.target && e.target.layout){
                JTopo.layout.layoutNode(scene, e.target, true);    
            }
        });
	});
</script>
</body>
</html>