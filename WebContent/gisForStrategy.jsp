<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
body, html {
	width: 100%;
	height: 100%;
	margin: 0;
	font-family: "微软雅黑";
}

#allmap {
	width: 100%;
	height: 1000px;
}

p {
	margin-left: 5px;
	font-size: 14px;
}
</style>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=KYxU1FkI5IZ61VHo25nwKsfGwBGyF5aU"></script>
<script type="text/javascript" src="jquery-1.11.1.js"></script>
<title>策略展示GIS</title>
</head>
<body>
	<div id="allmap"></div>
	<div id="r-result">
		<input type="button" onclick="add_control();" value="添加" /> <input
			type="button" onclick="delete_control();" value="删除" />
	</div>
	
	<%
		request.setCharacterEncoding("utf-8");
		String[] result = request.getParameter("result").split(",");
	%>
	
	<input id="id_54" type="hidden" value="<%=result[54]%>"/>
	<input id="id_55" type="hidden" value="<%=result[55]%>"/>
	<input id="id_56" type="hidden" value="<%=result[56]%>"/>
	<input id="id_64" type="hidden" value="<%=result[64]%>"/>
	<input id="id_65" type="hidden" value="<%=result[65]%>"/>
	<input id="id_66" type="hidden" value="<%=result[66]%>"/>
	<input id="id_83" type="hidden" value="<%=result[83]%>"/>
	<input id="id_84" type="hidden" value="<%=result[84]%>"/>
	<input id="id_85" type="hidden" value="<%=result[85]%>"/>
	<input id="id_93" type="hidden" value="<%=result[93]%>"/>
	<input id="id_94" type="hidden" value="<%=result[94]%>"/>
	<input id="id_95" type="hidden" value="<%=result[95]%>"/>

	<script type="text/javascript">  
    		// 百度地图API功能  
    		var map = new BMap.Map("allmap");//创建百度地图实例，这里的allmap是地图容器的id  
    		var point = new BMap.Point(121.785974,39.055469);//创建一个点对象，这里的参数是地图上的经纬度  
    		map.centerAndZoom(point, 20);//这里是将地图的中心移动到我们刚才创建的点；这里的12是地图的缩放界别；数值越大，地图看的越细  
    		var tileLayer = new BMap.TileLayer({isTransparentPng: true});
    		tileLayer.getTilesUrl = function(tileCoord, zoom) {
    			var x = tileCoord.x;
    			var y = tileCoord.y;
    			return "http://developer.baidu.com/map/jsdemo/img/border.png";
    		}      
    		function add_control(){
    			map.addTileLayer(tileLayer);
    		}
    		function delete_control(){
    			map.removeTileLayer(tileLayer);
    		}
    		add_control();
    		
    		var data_info = [[121.783778,39.056045,"区域54：" + document.getElementById("id_54").value],
    						 [121.784936,39.055968,"区域55：" + document.getElementById("id_55").value],
    						 [121.786046,39.056168,"区域56：" + document.getElementById("id_56").value],
    						 [121.783881,39.05532,"区域64：" + document.getElementById("id_64").value],
    						 [121.784846,39.055289,"区域65：" + document.getElementById("id_65").value],
    						 [121.785996,39.055303,"区域66：" + document.getElementById("id_66").value],
    						 [121.782632,39.053331,"区域83：" + document.getElementById("id_83").value],
    						 [121.783769,39.053352,"区域84：" + document.getElementById("id_84").value],
    						 [121.784873,39.053356,"区域85：" + document.getElementById("id_85").value],
    						 [121.782641,39.052508,"区域93：" + document.getElementById("id_93").value],
    						 [121.783602,39.052393,"区域94：" + document.getElementById("id_94").value],
    						 [121.784968,39.052547,"区域95：" + document.getElementById("id_95").value]
    						];
    		var opts = {
    				width : 250,     // 信息窗口宽度
    				height: 80,     // 信息窗口高度
    				title : "巡逻策略" , // 信息窗口标题
    				enableMessage:true//设置允许信息窗发送短息
    			   };
    		for(var i=0;i<data_info.length;i++){
    			var marker = new BMap.Marker(new BMap.Point(data_info[i][0],data_info[i][1]));  // 创建标注
    			var content = data_info[i][2];
    			map.addOverlay(marker);               // 将标注添加到地图中
    			addClickHandler(content,marker);
    		}
    		function addClickHandler(content,marker){
    			marker.addEventListener("click",function(e){
    				openInfo(content,e)}
    			);
    		}
    		function openInfo(content,e){
    			var p = e.target;
    			var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
    			var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
    			map.openInfoWindow(infoWindow,point); //开启信息窗口
    		}
	</script>

</body>
</html>