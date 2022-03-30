let layer,form,element,toast, utils;
var ws = null;
layui.use(['layer','form','element', 'toast','utils'], function() {
    layer = layui.layer;
    form = layui.form;
    element = layui.element;
    toast = layui.toast;
    utils = layui.utils;
    layer.config({
        offset: '20%'
    });
    form.on('submit(optionForm2)', function(data){
        save(data);
        return false;
    });
    form.on('submit(optionForm)', function(data){
        save(data);
        return false;
    });

    element.on('tab(settingTab)', function(){
        if (this.getAttribute('lay-id') === "3") {
            let loadIndex = layer.load("正在检查更新...");
            utils.ajax({
                type: "GET",
                url: "/checkUpdate",
                success: function (data) {
                    layer.close(loadIndex);
                    if (data.code === 200) {
                        $("#updateTitle").text(data.data.name);
                        $("#updateVersion").text(data.data.tagName);
                        $("#updateContent").html(data.data.body.replaceAll("\r\n","<br>"));
                        $("#updateSize").text(data.data.sizeString);
                    } else if (data.code === 500) {
                        $(".update-content").html("检查更新出错,请重试");
                    } else {
                        $(".update-content").html("暂无更新");
                    }
                    $(".update-content").show();
                }
            });
        }
    });
});

function save(data) {
    utils.ajax({
        type: "POST",
        url: "/admin/setting/save",
        data: JSON.stringify(data.field),
        success:function(d){
            if (d.code === 200){
                toast.success({message: '保存成功',position: 'topCenter'});
            } else {
                toast.error({message: d.msg,position: 'topCenter'});
            }
        }
    });
}

function sendTestMail(){
    layer.prompt({title: '请输入收件人邮箱', formType: 3}, function(mail, index){
        layer.close(index);
        utils.ajax({
            type: "POST",
            url: "/admin/setting/testMail?mail="+mail,
            success:function(data){
                if (data.code === 200) {
                    toast.success({message: '发送成功',position: 'topCenter'});
                } else {
                    toast.error({message: data.msg,position: 'topCenter'});
                }
            }
        });
    });
    return false;
}