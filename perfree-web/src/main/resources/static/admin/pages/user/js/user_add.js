let form, element, layer, upload,utils,toast;
initPage();

function initPage() {
    layui.use(['layer', 'form', 'element','utils', 'toast'], function () {
        form = layui.form;
        element = layui.element;
        layer = layui.layer;
        upload = layui.upload;
        toast = layui.toast;
        utils = layui.utils;
        formEvent();
        initEvent();
        loadRoleList();
        initUpload();
    });

}

/**
 * 初始化表单操作
 */
function formEvent() {
    form.render();
    // 表单验证
    form.verify({});
    // 表单提交
    form.on('submit(addForm)', function (data) {
        utils.ajax({
            type: "POST",
            url: "/admin/user/add",
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
            },
            error: function (data) {
                toast.error({message: '添加失败',position: 'topCenter'});
            }
        });
        return false;
    });
}

/**
 * 初始化页面事件
 */
function initEvent() {
    // 取消
    $(".p-cancel-btn").click(function () {
        const index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    });

    // 上传图片hover
    $("#uploadSuccessPanel").hover(function () {
        $(".delete-img-box").addClass("delete-img-box-show");
    }, function () {
        $(".delete-img-box").removeClass("delete-img-box-show");
    });

    // 删除图片
    $("#deleteImg").click(function () {
        $("#upload").show();
        $("#uploadSuccessPanel").hide();
        $("#uploadSuccessPanel > img").attr("src", "");
        $("#avatar").val("");
    });
}

/**
 * 加载角色列表
 */
function loadRoleList() {
    utils.ajax({
        type: "GET",
        url: "/admin/role/getRoleList",
        success: function (data) {
            let html = '<option value="">请选择</option>';
            data.data.forEach(res => {
                html += ' <option value="' + res.id + '">' + res.name + '</option>';
            });
            $("#roleId").html(html);
            form.render('select');
        }
    });
}


/**
 * 初始化上传
 */
function initUpload() {
    upload.render({
        elem: '#upload',
        url: '/admin/user/uploadImg',
        accept: 'images',
        done: function (res, index, upload) {
            if (res.code === 200) {
                $("#upload").hide();
                $("#uploadSuccessPanel").show();
                $("#uploadSuccessPanel > img").attr("src", res.data);
                $("#avatar").val(res.data);
            }
        }
    });
}