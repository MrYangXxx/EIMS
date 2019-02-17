//获取url的参数
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return decodeURIComponent(r[2]);
	}else{
		return null;
	}
}
/**
 * 日期转换--只针对layui
 * @param dateObj
 * @returns
 */
function dateToStringForLayui(dateObj) {
		var FullYear = dateObj.year;
		var Month = dateObj.month;
		var Day = dateObj.date;
		var Hours = dateObj.hours;
		var Minutes = dateObj.minutes;
		var Seconds = dateObj.seconds;
	return FullYear+"-"+Month+"-"+Day+" "+Hours+":"+Minutes+":"+Seconds

}
/**
 * 日期转换，用于后端时间值在前端显示的格式化
 * yyyy-MM-dd hh:mm
 */
function dateFormat(time) {
    var date=new Date(time);
    var year=date.getFullYear();
    /* 在日期格式中，月份是从0开始的，因此要加0
     * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
     * */
    var month= date.getMonth()+1<10 ? "0"+(date.getMonth()+1) : date.getMonth()+1;
    var day=date.getDate()<10 ? "0"+date.getDate() : date.getDate();
    var hours=date.getHours()<10 ? "0"+date.getHours() : date.getHours();
    var minutes=date.getMinutes()<10 ? "0"+date.getMinutes() : date.getMinutes();
    var seconds=date.getSeconds()<10 ? "0"+date.getSeconds() : date.getSeconds();
    // 拼接
    return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
}
//列表页表格第一列check选择框的选定
$(document).on("click",".layui-table-body table.layui-table tbody tr", function () {
    var index = $(this).attr('data-index');
    var tableBox = $(this).parents('.layui-table-box');
    //存在固定列
    if (tableBox.find(".layui-table-fixed.layui-table-fixed-l").length>0) {
        tableDiv = tableBox.find(".layui-table-fixed.layui-table-fixed-l");
    } else {
        tableDiv = tableBox.find(".layui-table-body.layui-table-main");
    }
    var checkCell = tableDiv.find("tr[data-index=" + index + "]").find("td div.laytable-cell-checkbox div.layui-form-checkbox I");
    if (checkCell.length>0) {
        checkCell.click();
    }
});
$(document).on("click", "td div.laytable-cell-checkbox div.layui-form-checkbox", function (e) {
    e.stopPropagation();
});
//内嵌(选项)列表页表格第一列radio选择框的选定
$(document).on("click",".layui-table-body table.layui-table tbody tr", function () {
	var index = $(this).attr('data-index');
	var tableBox = $(this).parents('.layui-table-box');
	//存在固定列
	if (tableBox.find(".layui-table-fixed.layui-table-fixed-l").length>0) {
		tableDiv = tableBox.find(".layui-table-fixed.layui-table-fixed-l");
	} else {
		tableDiv = tableBox.find(".layui-table-body.layui-table-main");
	}
	var checkCell = tableDiv.find("tr[data-index=" + index + "]").find("td div.laytable-cell-radio div.layui-form-radio I");
	if (checkCell.length>0) {
		checkCell.click();
	}
});
$(document).on("click", "td div.laytable-cell-radio div.layui-form-radio", function (e) {
	e.stopPropagation();
});
//打开iframe
function openTab(title,url,w,h){
    if (title == null || title == '') {
        title=false;
    };
    if (url == null || url == '') {
        url="404.html";
    };
    if (w == null || w == '') {
        w=($(window).width()*0.9);
    };
    if (h == null || h == '') {
        h=($(window).height()*0.9);
    };
    layer.open({
        type: 2,
        area: [w+'px', h +'px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: false,
        shade:0.4,
        title: title,
        content: url
    });
}

//关闭iframe
function closeIframe(){
	var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

/**
 * 饼图封装
 *element：所要填充div的id，
 *title：标题
 *legend： 图例数据，格式["",""]
 *dataName：图标数据统计数据的类型/总称
 *data：图表数据，格式[{name:"",value:数字},{}]
 */
function myPieChart(element,title,subtext,legend,dataName,data){
	// 基于准备好的dom，初始化echarts实例
	   var myChart = echarts.init(document.getElementById(element));

	   // 指定图表的配置项和数据
	   var option = {
	   	    title : {
	   	        text: title,
	   	     	subtext: subtext,
	   	        x:'center'
	   	    },
	   	    tooltip : {
	   	        trigger: 'item',
	   	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	   	    },
	   	    legend: {
	   	        orient: 'vertical',
	   	        left: 'left',
	   	        data: legend
	   	    },
	   	    series : [
	   	        {
	   	            name: dataName,
	   	            type: 'pie',
	   	            radius : '55%',
	   	         //   center: ['50%', '40%'],
	   	            data: data,
	   	            itemStyle: {
	   	                emphasis: {
	   	                    shadowBlur: 10,
	   	                    shadowOffsetX: 0,
	   	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	   	                }
	   	            }
	   	        }
	   	    ]
	   	};
	   // 使用刚指定的配置项和数据显示图表。
	   myChart.setOption(option);
}
/**
 * 折线图封装
 * element：所要填充div的id，
 * title：标题
 * legend： 图例数据，格式["",""]
 * data：图表数据，格式[数字,数字]
 * isSmooth：是否平滑
 */
function myLineChart(element,title,legend,data,isSmooth){
	if(isSmooth == null){
		isSmooth = true;
	}
	var myChart = echarts.init(document.getElementById(element));
	var w=($(window).width()*0.9);
	var option = {
			title: {
		        text: title,
		        x:'center'
		    },
		    toolbox: { //可视化的工具箱
                show: true,
                x: w,
                feature: {
                    dataView: { //数据视图
                        show: false
                    },
                    restore: { //重置
                        show: false
                    },
                    dataZoom: { //数据缩放视图
                        show: false
                    },
                    magicType: {//动态类型切换
                        type: ['bar', 'line']
                    },
                    saveAsImage: {//保存图片
                        show: true
                    },
                }
            },
		    tooltip: {
		        trigger: 'axis'
		    },
		    xAxis: {
		        type: 'category',
		        data: legend
		    },
		    yAxis: {
		        type: 'value'
		    },
		    series: [{
		        data: data,
		        type: 'line',
		        smooth: isSmooth
		    }]
		};
	 myChart.setOption(option);
}