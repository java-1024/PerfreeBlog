let form, element, layer, upload, utils, toast;
layui.use(['layer', 'form', 'element','upload', 'toast','utils'], function () {
    form = layui.form;
    element = layui.element;
    layer = layui.layer;
    upload = layui.upload;
    utils = layui.utils;
    toast = layui.toast;
    form.verify({});
    let loadIndex;
    upload.render({
        elem: '#file',
        url: '/admin/attach/upload',
        auto: false,
        bindAction: '#add',
        accept: 'file',
        choose: function (obj) {
            loadIndex = layer.load();
            let files = obj.pushFile();
            obj.preview(function (index, file, result) {
                $("#file").val(file.name);
                layer.close(loadIndex);
            });
        },
        error: function () {
            toast.error({message: "上传失败",position: 'topCenter'});
        },
        before: function (obj) {
            this.data.desc = $("#desc").val();
            this.data.flag = $("#flag").val();
            loadIndex = layer.load("正在上传");
        },
        done: function (res) {
            layer.close(loadIndex);
            if (res.code === 200) {
                parent.tableIns.reload();
                toast.success({message: "上传成功",position: 'topCenter'});
                const index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            } else {
                toast.error({message: res.msg,position: 'topCenter'});
            }

        }
    });
});

// 取消
$(".p-cancel-btn").click(function () {
    const index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});