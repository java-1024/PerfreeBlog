let table,  layer, upload, utils, toast;
let pageIndex = 1, pageSize = 8;
layui.use(['toast','utils', 'layer', 'upload'], function () {
    layer = layui.layer;
    upload = layui.upload;
    utils = layui.utils;
    toast = layui.toast;
    queryTable();
    initUpload();

    // 查询
    $("#queryBtn").click(function () {
        queryTable();
    });
});

/**
 * 初始化页面数据
 */
function queryTable() {
    utils.table({
        elem: '#tabBoxPage',
        url: '/admin/attach/list',
        title: '附件列表',
        where: {
            form: {
                name: $("#name").val(),
                type: "other"
            }
        },
        limit: 30,
        cols: [[
            {field: 'name', title: '文件名'},
            {field: 'desc', title: '描述'},
            {field: 'flag', title: '标识',width: 100},
            {
                field: 'id', title: '操作', width: 80, fixed: 'right',
                templet: "<div>" +
                    "<a class='layui-btn layui-btn-normal layui-btn-xs' onclick='selectFile(\"{{d.url}}\",\"{{d.name}}\")'>选择</a> " +
                    "</div>"
            },
        ]]
    });
}

/**
 * 初始化上传
 */
function initUpload() {
    let loadIndex;
    upload.render({
        elem: '#uploadBtn'
        , url: '/admin/attach/upload'
        , accept: 'file'
        ,choose: function (obj) {
            loadIndex = layer.load();
        }
        , done: function (res) {
            layer.close(loadIndex);
            if (res.code === 200) {
                parent.layer.close(parent.layer.getFrameIndex(window.name));
                parent.selectAttach(res.data.name,res.data.url);
            } else {
                toast.error({message: res.msg,position: 'topCenter'});
            }
        }
        , error: function () {
            layer.close(loadIndex);
            toast.error({message: "上传失败",position: 'topCenter'});
        }
    });
}

function selectFile(url, name){
    parent.selectAttach(name, url);
    parent.layer.close(parent.layer.getFrameIndex(window.name));
}