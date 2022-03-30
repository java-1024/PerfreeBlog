let form, element, layer,utils, toast;
layui.use(['layer', 'form', 'element', 'toast','utils'], function () {
    form = layui.form;
    element = layui.element;
    layer = layui.layer;
    utils = layui.utils;
    toast = layui.toast;
    // 表单验证
    form.verify({});
    // 表单提交
    form.on('submit(addForm)', function (data) {
        utils.ajax({
            type: "POST",
            url: "/admin/category/add",
            data: JSON.stringify(data.field),
            success: function (data) {
                if (data.code === 200) {
                    parent.tableIns.reload();
                    toast.success({message: '添加成功',position: 'topCenter'});
                    const index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                } else {
                    toast.error({message: data.msg,position: 'topCenter'});
                }
            }
        });
        return false;
    });
});

// 取消
$(".p-cancel-btn").click(function () {
    const index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});