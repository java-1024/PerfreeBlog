layui.define([ 'jquery','toast', 'table'],  function (exports){
    "use strict";
    var $ = layui.jquery;
    var toast = layui.toast;
    var table = layui.table;
    var utils = new function() {
        /**
         * ajax请求
         * @param opt
         */
        this.ajax = function (opt) {
            $.ajax({
                url:opt.url,
                type:opt.type||'POST',
                async:opt.async||true,
                contentType: opt.contentType || "application/json",
                data:opt.data||{},
                success:opt.success,
                error:opt.error || function () {
                    toast.error({message: '操作失败,网络错误',position: 'topCenter'});
                }
            })
        }

        /**
         * table
         * @param opt
         * @returns {*}
         */
        this.table = function (opt) {
            return table.render({
                elem: opt.elem,
                url: opt.url,
                method: opt.method || 'post',
                headers: opt.headers || {'Content-Type': 'application/json'},
                contentType: opt.contentType || 'application/json',
                title: opt.title || '列表',
                totalRow: opt.totalRow || false,
                where: opt.where || {},
                limit: opt.limit || 20,
                cols: opt.cols || [],
                page: {
                    curr: opt.curr || 1
                },
                response: {statusCode: 200},
                parseData: function (res) {
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
    }
    exports('utils', utils);
});