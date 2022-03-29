window.rootPath = (function(src) {
    src = document.scripts[document.scripts.length - 1].src;
    return src.substring(0, src.lastIndexOf("/") + 1);
})();

layui.config({
    base: rootPath + "module/",
    version: "1.0.0"
}).extend({
    treeTable: "treeTable",
    xmSelect: "xm-select",
    toast: "toast/toast",
    loading: "loading/loading",
    count: "count",
    step: "step",
    ajax: 'ajax'
});