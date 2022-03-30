var table,toast,utils,layer, tableIns;
layui.use(['table','toast','utils', 'layer'], function () {
    table = layui.table;
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

    // 添加
    $("#addBtn").click(function () {
        layer.open({
            title: "添加友链",
            type: 2,
            area: common.layerArea($("html")[0].clientWidth, 500, 400),
            shadeClose: true,
            anim: 1,
            content: '/admin/link/addPage'
        });
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
        url: '/admin/link/list',
        method: 'post',
        title: '友链列表',
        where: {
            form: {
                name: $("#linkName").val()
            }
        },
        cols: [[
            {type: 'checkbox'},
            {field: 'id', title: 'ID', width: 80, sort: true},
            {field: 'name',   minWidth: 160,title: '网站名'},
            {field: 'address',   minWidth: 220,title: '网站地址'},
            {field: 'desc',   minWidth: 260,title: '网站描述'},
            {field: 'logo',   minWidth: 100,title: '网站logo', templet: function (d) {
                            return "<img src='/static/public/images/loading.gif' data-src='"+d.logo+"' class='lazyload'>";
                    }
            },
            {
                field: 'createTime',
                title: '创建时间',
                sort: true,
                minWidth: 160,
                templet: "<span>{{d.createTime ==null?'':layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}</span>"
            },
            {
                field: 'updateTime',
                title: '更新时间',
                sort: true,
                minWidth: 160,
                templet: "<span>{{d.updateTime == null?'':layui.util.toDateString(d.updateTime, 'yyyy-MM-dd HH:mm:ss')}}</span>"
            },
            {
                field: 'id', title: '操作', width: 120,
                templet: "<div>" +
                    "<a class='layui-btn layui-btn-normal layui-btn-xs' onclick='editData(\"{{d.id}}\")'>编辑</a> " +
                    "<a class='layui-btn layui-btn-danger layui-btn-xs' onclick='deleteData(\"{{d.id}}\")'>删除</a>" +
                    "</div>"
            },
        ]],
    });
}

/**
 * 编辑
 * @param id
 */
function editData(id) {
    layer.open({
        title: "编辑友链",
        type: 2,
        area: common.layerArea($("html")[0].clientWidth, 500, 400),
        shadeClose: true,
        anim: 1,
        content: '/admin/link/editPage/' + id
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
            url: "/admin/link/del",
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