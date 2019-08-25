var keywordsTable;
var table;
layui.use(['jquery', 'table','layer','upload','form','laydate'],function(){
    table = layui.table;
    var $ = layui.$
        ,layer = layui.layer,
        laydate = layui.laydate;

    keywordsTable = table.render({
        elem: '#table',
        url:'/keywords/find',
        request:{limitName: 'size',pageName: 'pageIndex'},
        response:{countName: 'totalCount'},
        method:'post',
        page:true,
        id:'table',
        limit:10,
        limits:[10,20,30],
        cols: [[
            { title: '序号',type:"numbers", align:'center',width:80},
            {field:'minnum', title: '大于等于字数', align:'left'},
            {field:'maxnum', title: '小于字数', align:'left'},
            {field:'fcolor', title: '标记色', align:'left',templet: function (d) {
                var str="";
                    if(d.fcolor=="#FF0000"){
                        str ="红色";
                    }
                    if(d.fcolor=="#FF7F00"){
                        str ="橙色";
                    }
                    if(d.fcolor=="#FFFF00"){
                        str ="黄色";
                    }
                    if(d.fcolor=="#00FF00"){
                        str ="绿色";
                    }
                    if(d.fcolor=="#00FFFF"){
                        str ="青色";
                    }
                    if(d.fcolor=="#0000FF"){
                        str ="蓝色";
                    }
                    if(d.fcolor=="#8B00FF"){
                        str ="紫色";
                    }
                    if(d.fcolor=="#F29CB1"){
                        str ="粉色";
                    }
                    return str;
    }},
    {title:'操作',align:'center',toolbar:'#barTable'}
]]
});
    table.on('tool(keywords)', function(obj){
        var data = obj.data;
        if(obj.event === 'delete'){
            globalDeleteSymbol = true;
            layer.confirm('确定删除该条记录？', function(index){
                $.ajax({
                    url:'/keywords/delete',
                    type:'get',
                    dataType: 'json',
                    data:{
                        id: data.id
                    },
                    success : function(res) {
                        if (res.code == 0) {
                            layer.msg("删除成功", {icon: 1, time: 500}, function (){
                                keywordsTable.reload();
                            });
                        }else{
                            layer.msg("删除失败，请重试！！", {icon: 2, time: 500});
                        }
                    },
                    error : function(e) {
                        layer.msg("删除失败，请重试！！", {icon: 2, time: 500});
                    }
                });
                layer.close(index);
            });
        }
        if(obj.event === 'edit'){
            var data
            var index = layer.open({
                content: 'add.html?id='+data.id,
                title : "编辑",
                type : 2,
                area: ['680px', '640px'],
                success : function(layero, index){
                    setTimeout(function(){
                        layui.layer.tips('点击此处关闭表单', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    },500)
                }
            });
        }
    });
});
function add(){
    var index = layer.open({
        content: 'add.html',
        title : "新增",
        type : 2,
        area: ['800px', '400px'],
        success : function(layero, index){
            setTimeout(function(){
                layui.layer.tips('点击此处关闭表单', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            },500)
        },
        cancel: function(){
            window.location.reload();
        }
    });
}
function search(idName){
    var params = {};
    if($('#keyval').val() != null && $('#keyval').val() != ""){
        params.keyval = $('#keyval').val();
    }
    keywordsTable.reload({
        where: params //设定异步数据接口的额外参数，任意设
    });
}
