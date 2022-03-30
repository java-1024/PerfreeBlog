let form, xmSelect,categorySelect,tagSelect,loadIndex, utils, toast, layer;
layui.use(['layer', 'form', 'xmSelect', 'utils', 'toast'], function () {
    layer = layui.layer;
    form = layui.form;
    xmSelect = layui.xmSelect;
    utils = layui.utils;
    toast = layui.toast;
    layer.config({
        offset: '20%'
    });
    initEditor($("#editorMode").val(), $("#articleContent").val());
    initEvent();
    initTag();
    initCategory();
});

/**
 * 初始化页面事件
 */
function initEvent() {
    // 表单验证
    form.verify({});
    // 表单提交
    form.on('submit(draftForm)', function (data) {
        data.field.status = 1;
        submitArticle(data.field);
        return false;
    });
    form.on('submit(publishForm)', function (data) {
        data.field.status = 0;
        submitArticle(data.field);
        return false;
    });

    window.addEventListener("keydown", function(e) {
        if((e.key==='s'||e.key==='S')&&(navigator.platform.match("Mac") ? e.metaKey : e.ctrlKey)){
            e.preventDefault();
            $("#publishBtn").click();
        }
    }, false);
}

/**
 * 提交文章
 * @param data
 */
function submitArticle(data) {
    if ($("#editorMode").val() === "markdown") {
        data.content = markdownEditor.getMarkdown();
    } else {
        data.content = richEditor.txt.html();
    }
    data.contentModel = $("#editorMode").val();
    if (categorySelect.getValue().length > 0) {
        data.categoryId = categorySelect.getValue()[0].value;
    }
    let tagArr = [];
    tagSelect.getValue().forEach(res => {
        const tag = {tagId: res.id, name: res.name};
        tagArr.push(tag)
    });
    data.articleTags = tagArr;
    data.isComment = data.isComment === 'on' ? 1 : 0
    data.isTop = data.isTop === 'on' ? 1 : 0
    utils.ajax({
        type: "POST",
        url: "/admin/article/update",
        data: JSON.stringify(data),
        success: function (d) {
            if (d.code === 200) {
                location.reload();
                toast.success({message: '文章修改成功',position: 'topCenter'});
                parent.toPage('/admin/article');
                parent.element.tabDelete('tabNav', '-1');
            } else {
                toast.error({message: d.msg,position: 'topCenter'});
            }
        }
    });
}


/**
 * 初始化标签选择框
 */
function initTag() {
    let tagsValue = $("#tagsValue").val();
    tagsValue = tagsValue.substr(0, tagsValue.length - 1);
    let initValue = tagsValue.split(",");
    utils.ajax({
        type: "GET",
        url: "/admin/tag/allList",
        success: function (res) {
            if (res.code === 200) {
                let tagArr = [];
                res.data.forEach(r => {
                    const param = {name: r.name, value: r.id, id: r.id};
                    tagArr.push(param);
                })
                tagSelect = xmSelect.render({
                    el: '#tag',
                    tips: '请选择标签',
                    theme: {
                        color: '#1E9FFF',
                    },
                    initValue: initValue,
                    searchTips: '搜索标签或输入标签名新增',
                    filterable: true,
                    create: function (val, arr) {
                        return {
                            name: val,
                            value: val
                        }
                    },
                    data: tagArr,
                    on: function (data) {
                        if (data.isAdd && data.change[0].id === null || data.isAdd && data.change[0].id === undefined) {
                            utils.ajax({
                                type: "POST",
                                url: "/admin/tag/add",
                                data: JSON.stringify({name: data.change[0].value}),
                                success: function (d) {
                                    if (d.code === 200) {
                                        const currentProfileIndex = (data.arr || []).findIndex((profile) => profile.value === d.data.name);
                                        data.arr[currentProfileIndex].id = d.data.id;
                                    } else {
                                        toast.error({message: "新建标签失败",position: 'topCenter'});
                                    }
                                },
                            });
                        }
                    }
                });
            } else {
                toast.error({message: res.msg,position: 'topCenter'});
            }
        }
    });
}

/**
 * 初始化分类选择框
 */
function initCategory() {
    utils.ajax({
        type: "GET",
        url: "/admin/category/allList",
        success: function (res) {
            if (res.code === 200) {
                categorySelect = xmSelect.render({
                    el: '#category',
                    theme: {
                        color: '#1E9FFF',
                    },
                    model: {label: {type: 'text'}},
                    radio: true,
                    tips: '请选择分类',
                    filterable: true,
                    searchTips: '输入分类名搜索',
                    clickClose: true,
                    initValue: [$("#categoryIdValue").val()],
                    tree: {
                        show: true,
                        strict: false,
                        expandedKeys: [-1],
                    },
                    height: 'auto',
                    data() {
                        return res.data
                    }
                });
            } else {
                toast.error({message: res.msg,position: 'topCenter'});
            }
        }
    });
}

/**
 * 动态修改PageUrl
 */
function updatePageUrl(){
    let slug = $("#slug").val();
    if (slug !== null && slug !== undefined &&　slug　!== '') {
        $("#pageUrl").html(slug);
    } else {
        $("#pageUrl").html('[ID]');
    }
}
