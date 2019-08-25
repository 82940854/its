var reT;
var compT;
var layedit;
layui.use(['jquery','layer','form','layedit'],function(){
    layedit=layui.layedit;
    var $ = layui.$
        ,layer = layui.layer;
    reT=layedit.build("resuTex",{
        height:500
    });
    compT=layedit.build("compTex",{
        height:500
    });

});
function doupload(){
    var params = new FormData($('#uploadForm')[0]);
    $.ajax({
        url:"/filecomp/save",
        type:"POST",
        data:params,
        cache:false,
        contentType:false,
        processData:false,
        success:function(res){
            if (res.code == 0) {
                layedit.setContent(reT, res.body.original, false);
                layedit.setContent(compT, res.body.notes, false);
                $("#isPass").val(res.body.isPass);
                $("#repeat").val(res.body.repeat);
                $("#countWord").val(res.body.countWord);
                $("#ratio").val(res.body.ratio);
                $("#copOrig").html(res.body.copOrig);
                $("#copNote").html(res.body.copNote);
            }else{
                layer.msg(res.msg, {icon: 2, time: 1000});
            }
        }
    });

}
function toSearch() {
      layer.open({
        content: $('#multiForm'),
        title: "",
        type: 1,
        area: ['700px', '600px']
    });
}


