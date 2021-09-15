package com.perfree.controller.admin;

import com.perfree.common.Pager;
import com.perfree.common.ResponseBean;
import com.perfree.controller.BaseController;
import com.perfree.model.Comment;
import com.perfree.model.Menu;
import com.perfree.model.User;
import com.perfree.service.CommentService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
@RequiresRoles(value={"admin","superAdmin"}, logical= Logical.OR)
public class CommentController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(TagController.class);
    @Autowired
    private CommentService commentService;

    /**
     * 评论管理列表页
     * @return String
     */
    @RequestMapping("/comment")
    @RequiresRoles(value={"admin","editor", "contribute"}, logical= Logical.OR)
    public String index() {
        return view("static/admin/pages/comment/comment_list.html");
    }

    /**
     * 评论管理列表数据
     * @return String
     */
    @PostMapping("/comment/list")
    @RequiresRoles(value={"admin","editor", "contribute"}, logical= Logical.OR)
    @ResponseBody
    public Pager<Comment> list(@RequestBody Pager<Comment> pager) {
        User user = getUser();
        if (user.getRole().getCode().equals("contribute")) {
            return commentService.list(pager, user.getId().toString());
        }
        return commentService.list(pager, null);
    }

    /**
     * 删除评论
     * @return String
     */
    @PostMapping("/comment/del")
    @RequiresRoles(value={"admin","editor", "contribute"}, logical= Logical.OR)
    @ResponseBody
    public ResponseBean del(@RequestBody String ids) {
        String[] idArr = ids.split(",");
        if (commentService.del(idArr) > 0) {
            return ResponseBean.success("删除成功", null);
        }
        logger.error("评论删除失败: {}", ids);
        return ResponseBean.fail("删除失败", null);
    }

    /**
     * 更改状态
     * @return String
     */
    @PostMapping("/comment/changeStatus")
    @RequiresRoles(value={"admin","editor", "contribute"}, logical= Logical.OR)
    @ResponseBody
    public ResponseBean changeStatus(@RequestBody Comment comment) {
        if (commentService.changeStatus(comment) > 0) {
            return ResponseBean.success("操作成功", null);
        }
        logger.error("评论操作失败: {}", comment.toString());
        return ResponseBean.fail("操作失败", null);
    }

}
