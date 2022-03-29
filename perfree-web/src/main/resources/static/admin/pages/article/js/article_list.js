var tableIns, form, $,toast,roleCode, utils,table;
layui.use([ 'layer', 'form', 'jquery', 'toast','utils', 'table'], function () {
    form = layui.form;
    $ = layui.jquery;
    toast = layui.toast;
    utils = layui.utils;
    table = layui.table;
    roleCode = $("#roleCode").val();
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

    // 添加
    $("#addBtn").click(function () {
        parent.toPage('/admin/article/addPage');
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
        url: '/admin/article/list',
        title: '文章列表',
        where: {
            form: {
                title: $("#title").val(),
                type: "article",
                categoryId: $("#category").val()
            }
        },
        cols: [[
            {type: 'checkbox'},
            {field: 'id', title: 'ID', width: 60},
            {
                field: 'title',
                title: '文章标题',
                minWidth: 250,
                templet: '<div><a class="articleHref" href="{{d.url}}" target="_blank">{{d.title}}</a></div>'
            },
            {field: 'category', title: '分类',  minWidth: 160,templet: "<span>{{d.category === null ? '' : d.category.name}}</span>"},
            {
                field: 'status', minWidth: 100, title: '状态', templet: function (d) {
                    let html = '<span>';
                    if (d.status === 0) { html += "已发布"; }
                    if (d.status === 1) { html += "草稿"; }
                    if (d.status === 2) { html += "待审核"; }
                    html += '</div>';
                    return html;
                }
            },
            {
                field: 'isTop', minWidth: 100, title: '是否置顶', templet: function (d) {
                    let html;
                    if (roleCode === "contribute") {
                        if (d.isTop === 1) {
                            html = "置顶";
                        } else {
                            html = "不置顶";
                        }
                        return html;
                    }
                    if (d.isTop === 1) {
                        html = "<input type='checkbox' name='isTop' lay-filter='isTop' lay-skin='switch' value='" + d.id + "' lay-text='置顶|不置顶' checked>";
                    } else {
                        html = "<input type='checkbox' name='isTop' lay-filter='isTop' value='" + d.id + "' lay-skin='switch' lay-text='置顶|不置顶'>";
                    }
                    return html;
                }
            },
            {
                field: 'isComment', minWidth: 100, title: '允许评论', templet: function (d) {
                    let html;
                    if (roleCode === "contribute") {
                        if (d.isComment === 1) {
                            html = "允许";
                        } else {
                            html = "不允许";
                        }
                        return html;
                    }
                    if (d.isComment === 1) {
                        html = "<input type='checkbox' name='isComment' lay-filter='isComment' lay-skin='switch' value='" + d.id + "' lay-text='允许|不允许' checked>";
                    } else {
                        html = "<input type='checkbox' name='isComment' lay-filter='isComment' value='" + d.id + "' lay-skin='switch' lay-text='允许|不允许'>";
                    }
                    return html;
                }
            },
            {field: 'user', minWidth: 80, title: '创建人', templet: "<span>{{d.user.userName}}</span>"},
            {
                field: 'createTime',
                title: '创建时间',
                sort: true,
                minWidth: 160,
                templet: "<span>{{d.createTime==null?'':layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}</span>"
            },
            {
                field: 'updateTime',
                title: '更新时间',
                minWidth: 160,
                sort: true,
                templet: "<span>{{d.updateTime==null?'':layui.util.toDateString(d.updateTime, 'yyyy-MM-dd HH:mm:ss')}}</span>"
            },
            {
                field: 'id', title: '操作', width: 150,
                templet: function (d) {
                    let html = "<div>";
                    if (roleCode !== "contribute") {
                        if (d.status === 1 || d.status === 2) {
                            html += "<a class='layui-btn layui-btn-normal layui-btn-xs' onclick='changeStatus(\"" + d.id + "\",\"0\")'>发布</a>";
                        }
                        if (d.status === 0) {
                            html += "<a class='layui-btn layui-btn-normal layui-btn-xs' onclick='changeStatus(\"" + d.id + "\",\"1\")'>草稿</a>";
                        }
                    }
                    html += "<a class='layui-btn layui-btn-normal layui-btn-xs' onclick='editData(\"" + d.id + "\")'>编辑</a> " +
                        "<a class='layui-btn layui-btn-danger layui-btn-xs' onclick='deleteData(\"" + d.id + "\")'>删除</a>" +
                        "</div>";
                    return html;
                }
            },
        ]],
    });

    form.on('switch(isTop)', function (data) {
        const id = this.value;
        const status = this.checked ? 1 : 0;
        changeTopStatus(id, status);
    });

    form.on('switch(isComment)', function (data) {
        const id = this.value;
        const status = this.checked ? 1 : 0;
        changeCommentStatus(id, status);
    });
}

/**
 * 编辑
 * @param id
 */
function editData(id) {
    parent.openTab('', '编辑文章', '/admin/article/updatePage/' + id, "-1");
}

/**
 *
 * @param ids
 */
function deleteData(ids) {
    layer.confirm('确定要删除吗?', {icon: 3, title: '提示'}, function (index) {
        utils.ajax({
            type: "POST",
            url: "/admin/article/del",
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
 * 更改置顶状态
 */
function changeTopStatus(id, status) {
    utils.ajax({
        type: "POST",
        url: "/admin/article/changeTopStatus",
        data: JSON.stringify({id: id, isTop: status}),
        success: function (data) {
            if (data.code === 200) {
                tableIns.reload();
                toast.success({message: '修改成功',position: 'topCenter'});
            } else {
                toast.error({message: data.msg,position: 'topCenter'});
            }
        }
    });
}

/**
 * 更改是否可以评论
 */
function changeCommentStatus(id, status) {
    utils.ajax({
        type: "POST",
        url: "/admin/article/changeCommentStatus",
        data: JSON.stringify({id: id, isComment: status}),
        success: function (data) {
            if (data.code === 200) {
                tableIns.reload();
                toast.success({message: '修改成功',position: 'topCenter'});
            } else {
                toast.error({message: data.msg,position: 'topCenter'});
            }
        }
    })
}

/**
 * 更改状态
 * @param id
 * @param status
 */
function changeStatus(id, status) {
    utils.ajax({
        type: "POST",
        url: "/admin/article/changeStatus",
        data: JSON.stringify({id: id, status: status}),
        success: function (data) {
            if (data.code === 200) {
                tableIns.reload();
                toast.success({message: '修改成功',position: 'topCenter'});
            } else {
                toast.error({message: data.msg,position: 'topCenter'});
            }
        }
    })
}