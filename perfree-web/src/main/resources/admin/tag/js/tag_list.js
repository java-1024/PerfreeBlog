let table;
layui.use('table', function(){
    table = layui.table;
});
initPage();

/**
 * 页面初始化事件
 */
function initPage() {
    queryTable();

    // 查询
    $("#queryBtn").click(function () {
        queryTable();
    });

    // 添加
    $("#addBtn").click(function () {
        layer.open({
            title: "添加标签",
            type: 2,
            offset: '20%',
            area:  ['400px', '140px'],
            shadeClose: true,
            anim: 1,
            move: false,
            content: '/admin/tag/addPage'
        });
    });

    // 批量删除
    $("#batchDeleteBtn").click(function () {
        const checkStatus = table.checkStatus('tableBox'),data = checkStatus.data;
        if (data.length <= 0) {
            layer.msg("至少选择一条数据", {icon: 2});
        } else {
            let ids = "";
            data.forEach(res => {
                ids += res.id + ",";
            });
            ids = ids.substring(0, ids.length-1);
            deleteData(ids)
        }
    });
}


/**
 * 查询表格数据
 */
function queryTable() {
    table.render({
        elem: '#tableBox',
        url:'/admin/tag/list',
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        contentType: 'application/json',
        title: '标签列表',
        totalRow: false,
        where: {
            form: {
                name: $("#tagName").val()
            }
        },
        limit: 30,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field:'id', title:'ID', width:80, fixed: 'left',sort: true},
            {field:'name', title:'标签名'},
            {field:'count', title:'文章数量'},
            {field:'user', title:'用户名', templet: "<span>{{d.user.userName}}</span>"},
            {field:'createTime', title:'创建时间', sort: true, templet: "<span>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}</span>" },
            {field:'updateTime', title:'更新时间', sort: true, templet: "<span>{{layui.util.toDateString(d.updateTime, 'yyyy-MM-dd HH:mm:ss')}}</span>"},
            {field:'id', title:'操作', width:120, fixed: 'right',
                templet: "<div>" +
                            "<a class='layui-btn layui-btn-normal layui-btn-xs' onclick='editData(\"{{d.id}}\")'>编辑</a> " +
                            "<a class='layui-btn layui-btn-danger layui-btn-xs' onclick='deleteData(\"{{d.id}}\")'>删除</a>" +
                        "</div>"
            },
        ]],
        page: true,
        response: {statusCode: 200},
        parseData: function(res){
            return {
                "code": res.code,
                "msg": res.msg,
                "count": res.total,
                "data": res.data
            };
        },
        request: {
            pageName: 'pageIndex',
            limitName: 'pageSize'
        }
    });
}

/**
 * 编辑
 * @param id
 */
function editData(id) {
    layer.open({
        title: "编辑标签",
        type: 2,
        offset: '20%',
        area:  ['400px', '140px'],
        shadeClose: true,
        anim: 1,
        move: false,
        content: '/admin/tag/editPage/' + id
    });
}

/**
 *
 * @param ids
 */
function deleteData(ids) {
    layer.confirm('确定要删除吗?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "POST",
            url: "/admin/tag/del",
            contentType:"application/json",
            data: ids,
            success:function(data){
                if (data.code === 200){
                    queryTable();
                } else {
                    layer.msg(data.msg, {icon: 2});
                }
            },
            error: function (data) {
                layer.msg("删除失败", {icon: 2});
            }
        });
        layer.close(index);
    });
}