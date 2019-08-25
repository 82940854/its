var layer;
$(function(){
    layui.use(['layer','form','laydate'], function () {
        var layer=layui.layer,
            form=layui.form,
            laydate = layui.laydate
            upload = layui.upload;

        //点击编辑回显数据
        var id = "";
        if(location.href.indexOf('?')!=-1){
            var params = location.href.split("?")[1].split("&");
            if(params.length>0){
                id = params[0].split("=")[1];
            }
            //如果是表单明细引入则隐藏按钮和input
            if(params.length>1){
                var type =params[1].split("=")[1];
                if(type=='1'){
                    $('.btns').hide();
                    $('.layui-input').addClass('clearInput').attr({'readonly':'readonly','placeholder':''});
                    $('.layui-textarea').addClass('clearInput').attr({'readonly':'readonly','placeholder':''});
                }
            }
        }
        if (id != "") {
            $.ajax({
                url: '/filter/get',
                type: 'get',
                dataType: 'json',
                data: {
                    id: id
                },
                success: function (res) {
                    if (res.code == 0) {
                        form.val("defaultForm",res.body);
                    } else {
                        layer.msg("网络出错，数据回显失败！", {icon: 2, time: 500});
                    }
                },
                error: function (e) {
                    layer.msg("网络出错，数据回显失败！", {icon: 2, time: 500});
                }
            });
        }
        form.on('submit(validate)', function(data){
            var  formParams =$('#defaultForm').serialize();
            if(id!=""){
                formParams.id=id;
            }
            $.ajax({
                url: '/filter/save',
                type: "post",
                dataType: "json",
                data:formParams,
                success : function(res) {
                    if (res.code == 0) {
                        layer.msg("保存成功", {icon: 1, time: 500}, function (){
                            // window.location.href='details.html?requestId='+res.body.filter.id;
                            window.parent.location.reload();
                        });
                    }else{
                        layer.msg("保存失败！", {icon: 2, time: 500});
                    }
                }
            });
        });
    });
});