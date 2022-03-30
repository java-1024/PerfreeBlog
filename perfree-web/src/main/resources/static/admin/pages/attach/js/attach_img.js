let layer, layPage, flow, upload, laytpl, utils, toast;
let pageIndex = 1, pageSize = 8;
layui.use([ 'layer', 'laypage', 'flow', 'upload', 'laytpl', 'toast','utils'], function () {
    layer = layui.layer;
    layPage = layui.laypage;
    flow = layui.flow;
    upload = layui.upload;
    laytpl = layui.laytpl;
    utils = layui.utils;
    toast = layui.toast;
    queryTable();
    initUpload();

    // 查询
    $("#queryBtn").click(function () {
        queryTable();
    });

    $("#tableBox").on("click", ".img-box", function () {
        parent.selectImg($(this).children("img").attr("src"),$(this).children("img").attr("alt"));
        parent.layer.close(parent.layer.getFrameIndex(window.name));
    });
});

/**
 * 初始化页面数据
 */
function queryTable() {
    utils.ajax({
        type: "POST",
        url: "/admin/attach/list",
        data: JSON.stringify({
            pageSize,
            pageIndex,
            form: {
                name: $("#name").val(),
                type: "img"
            }
        }),
        success: function (data) {
            if (data.code === 200) {
                laytpl($("#tableTpl").html()).render(data.data, function (html) {
                    $("#tableBox").html(html);
                });
                layPage.render({
                    elem: 'tabBoxPage',
                    limit: pageSize,
                    count: data.total,
                    curr: data.pageIndex,
                    layout: ['count', 'prev', 'page', 'next'],
                    jump: function (obj, first) {
                        pageIndex = obj.curr;
                        pageSize = obj.limit;
                        if (!first) {
                            queryTable();
                        }
                    }
                });

                flow.lazyimg();
            } else {
                toast.error({message: data.msg,position: 'topCenter'});
            }
        }
    })
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
        , acceptMime: 'image/*,image/ico'
        , exts: 'ico|jpg|png|gif|bmp|jpeg|tif|svg'
        ,choose: function (obj) {
            loadIndex = layer.load();
        }
        , done: function (res) {
            layer.close(loadIndex);
            if (res.code === 200) {
                parent.layer.close(parent.layer.getFrameIndex(window.name));
                parent.selectImg(res.data.url);
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