var filterTable;
var table;
globalDeleteSymbol = true;
layui.use(['jquery', 'table','layer','form'],function(){
    table = layui.table;
    var $ = layui.$
        ,layer = layui.layer;

    filterTable = table.render({
        elem: '#table',
        url:'/filter/find',
        request:{limitName: 'size',pageName: 'pageIndex'},
        response:{countName: 'totalCount'},
        method:'post',
        page:true,
        id:'table',
        limit:10,
        limits:[10,20,30],
        cols: [[
            { title: '序号',type:"numbers", align:'center',width:80},
    {field:'val', title: '过滤内容', align:'left'},
    {field:'types', title: '类型', align:'left',templet: function (d) {
            var str="";
            if(d.types=="1"){
                str ="符号";
            }
            if(d.types=="2"){
                str ="语气词";
            }
            return str;
    }},
            {field:'remarks', title: '说明', align:'left'},
    {title:'操作',align:'center',toolbar:'#barTable'}
]]
});
    table.on('tool(filter)', function(obj){
        var data = obj.data;
        if(obj.event === 'delete'){
            layer.confirm('确定删除该条记录？', function(index){
                $.ajax({
                    url:'/filter/delete',
                    type:'get',
                    dataType: 'json',
                    data:{
                        id: data.id
                    },
                    success : function(res) {
                        if (res.code == 0) {
                            layer.msg("删除成功", {icon: 1, time: 500}, function (){
                                filterTable.reload();
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
        area: ['680px', '640px'],
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
function search(){
    var params = {};
    if($('#val').val() != null && $('#val').val() != ""){
        params.val = $('#val').val();
    }
    if($('#types').val() != null && $('#types').val() != ""){
        params.types = $('#types').val();
    }
    filterTable.reload({
        where: params, //设定异步数据接口的额外参数，任意设
        page: {
            curr: 1 //重新从第 1 页开始
        }
    });
}
