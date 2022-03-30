let table,upload, toast,utils,tableIns,layer;
layui.use(['table', 'toast','utils','layer'], function () {
    table = layui.table;
    upload = layui.upload;
    toast = layui.toast;
    utils = layui.utils;
    layer = layui.layer;
    initPage();
});

/**
 * 页面初始化事件
 */
function initPage() {
    queryTable();

    layer.config({
        offset: '20%'
    });

    // 查询
    $("#queryBtn").click(function () {
        queryTable();
    });

    let loadIndex;
    upload.render({
        elem: '#addBtn',
        url: '/admin/plugin/addPlugin',
        accept: "file",
        exts: "jar",
        before: function (obj) {
            loadIndex = layer.load("正在上传");
        },
        done: function (res) {
            layer.close(loadIndex);
            if (res.code === 200) {
                tableIns.reload();
                toast.success({message: '插件安装成功',position: 'topCenter'});
                setTimeout(function (){
                    localStorage.setItem("plugin", "success");
                    parent.location.reload();
                }, 500)
            } else {
                toast.error({message: res.msg,position: 'topCenter'});
            }
        },
        error: function () {
            layer.close(loadIndex);
            toast.error({message: "插件安装失败",position: 'topCenter'});
        }
    });
}


/**
 * 查询表格数据
 */
function queryTable() {
    tableIns = utils.table({
        elem: '#tableBox',
        url: '/admin/plugin/list',
        method: 'post',
        title: '插件列表',
        where: {
            form: {
                name: $("#pluginName").val()
            }
        },
        cols: [[
            {field: 'id', title: 'ID', width: 80, sort: true},
            {field: 'name', title: '插件名',  minWidth: 180},
            {field: 'version', title: '版本',  minWidth: 100},
            {field: 'desc', title: '描述',minWidth: 260},
            {field: 'author', title: '作者',  minWidth: 100},
            {field: 'path', title: '路径',  minWidth: 220},
            {
                field: 'createTime',
                title: '创建时间',
                sort: true,
                templet: "<span>{{d.createTime ==null?'':layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}</span>",
                minWidth: 160
            },
            {
                field: 'updateTime',
                title: '更新时间',
                sort: true,
                templet: "<span>{{d.updateTime == null?'':layui.util.toDateString(d.updateTime, 'yyyy-MM-dd HH:mm:ss')}}</span>",
                minWidth: 160
            },
            {
                field: 'id', title: '操作', width: 120,
                templet: function (d) {
                    let html = "<div>";
                    if (d.status === 0) {
                        html += "<a class='layui-btn layui-btn-xs' onclick='startPlugin(\""+d.id+"\")'>启用</a>";
                    } else {
                        html += "<a class='layui-btn layui-btn-xs' onclick='stopPlugin(\""+d.id+"\")'>禁用</a>";
                    }
                    html +=  "<a class='layui-btn layui-btn-danger layui-btn-xs' onclick='deleteData(\""+d.id+"\")'>卸载</a></div>";
                    return html;
                }
            },
        ]]
    });
}


/**
 *
 * @param ids
 */
function deleteData(ids) {
    layer.confirm('确定要卸载吗?', {icon: 3, title: '提示'}, function (index) {
        utils.ajax({
            type: "POST",
            url: "/admin/plugin/del",
            data: ids,
            success: function (data) {
                if (data.code === 200) {
                    tableIns.reload();
                    toast.success({message: "插件卸载成功",position: 'topCenter'});
                    setTimeout(function (){
                        localStorage.setItem("plugin", "success");
                        parent.location.reload();
                    }, 500)
                } else {
                    toast.error({message: data.msg,position: 'topCenter'});
                }
            }
        });
        layer.close(index);
    });
}

function startPlugin(id){
    utils.ajax({
        type: "POST",
        url: "/admin/plugin/startPlugin",
        data: id,
        success: function (data) {
            if (data.code === 200) {
                tableIns.reload();
                toast.success({message: "插件启用成功",position: 'topCenter'});
                setTimeout(function (){
                    localStorage.setItem("plugin", "success");
                    parent.location.reload();
                }, 500)
            } else {
                toast.error({message: data.msg,position: 'topCenter'});
            }
        }
    });
}

function stopPlugin(id){
    utils.ajax({
        type: "POST",
        url: "/admin/plugin/stopPlugin",
        data: id,
        success: function (data) {
            if (data.code === 200) {
                tableIns.reload();
                toast.success({message: "插件禁用成功",position: 'topCenter'});
                setTimeout(function (){
                    localStorage.setItem("plugin", "success");
                    parent.location.reload();
                }, 500)
            } else {
                toast.error({message: data.msg,position: 'topCenter'});
            }
        }
    });
}