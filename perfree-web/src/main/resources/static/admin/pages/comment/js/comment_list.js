let utils, toast, layer, tableIns,table;
layui.use(['toast','utils','layer','table'], function () {
    utils = layui.utils;
    toast = layui.toast;
    layer = layui.layer;
    table = layui.table;
    initPage();
});

/**
 * 页面初始化事件
 */
function initPage() {
    queryTable();

    // 查询
    $("#queryBtn").click(function () {
        queryTable();
    });

    // 批量删除
    $("#batchDeleteBtn").click(function () {
        const checkStatus = table.checkStatus('tableBox'), data = checkStatus.data;
        if (data.length <= 0) {
            toast.error({message: '至少选择一条数据',position: 'topCenter'});
        } else {
            let ids = "";
            data.forEach(res => {
                ids += res.id + ",";
            });
            ids = ids.substring(0, ids.length - 1);
            deleteData(ids)
        }
    });
}


/**
 * 查询表格数据
 */
function queryTable() {
    tableIns = utils.table({
        elem: '#tableBox',
        url: '/admin/comment/list',
        method: 'post',
        title: '评论列表',
        where: {
            form: {
                content: $("#content").val()
            }
        },
        cols: [[
            {type: 'checkbox'},
            {field: 'id', title: 'ID', width: 80, sort: true},
            {
                field: 'userName', minWidth: 100, title: '评论人', templet: "<div>{{d.userName}}</div>"
            },
            {
                field: 'email', minWidth: 180, title: '邮箱', templet: "<div>{{d.email}}</div>"
            },
            {field: 'content', title: '评论内容', minWidth: 240},
            {field: 'article', title: '所属文章', minWidth: 240, templet: "<div><a href='{{d.article.url}}' target='_blank'>{{d.article.title}}</a></div>"},
            {field: 'status', minWidth: 60, title: '状态', templet: "<div>{{d.status === 1? '待审核': '正常'}}</div>"},
            {
                field: 'createTime',
                width: 220,
                title: '评论时间',
                sort: true,
                minWidth: 160,
                templet: "<span>{{d.createTime == null ?'':layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}</span>"
            },
            {
                field: 'id', title: '操作', width: 120,
                templet: function (d) {
                    let html = "<div>";
                    if (d.status === 1) {
                        html += "<a class='layui-btn layui-btn-primary layui-btn-xs' onclick='changeStatus(\"" + d.id + "\",\"0\")'>审核</a>";
                    }
                    html += "<a class='layui-btn layui-btn-danger layui-btn-xs' onclick='deleteData(\"" + d.id + "\")'>删除</a>" +
                        "</div>";
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
    layer.confirm('确定要删除吗?', {icon: 3, title: '提示'}, function (index) {
        utils.ajax({
            type: "POST",
            url: "/admin/comment/del",
            data: ids,
            success: function (data) {
                if (data.code === 200) {
                    tableIns.reload();
                    toast.success({message: '删除成功',position: 'topCenter'});
                } else {
                    toast.error({message: data.msg,position: 'topCenter'});
                }
            }
        });
        layer.close(index);
    });
}

/**
 * 更改状态
 * @param id
 * @param status
 */
function changeStatus(id, status) {
    utils.ajax({
        type: "POST",
        url: "/admin/comment/changeStatus",
        data: JSON.stringify({id: id, status: status}),
        success: function (data) {
            if (data.code === 200) {
                tableIns.reload();
                toast.success({message: '操作成功',position: 'topCenter'});
            } else {
                toast.error({message: data.msg,position: 'topCenter'});
            }
        }
    });
}