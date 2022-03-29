layui.define([ 'jquery' ],  function (exports){
    "use strict";
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
    }
    exports('utils', utils);
});