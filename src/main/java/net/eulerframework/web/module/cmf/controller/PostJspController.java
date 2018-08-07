package net.eulerframework.web.module.cmf.controller;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.eulerframework.web.core.annotation.JspController;
import net.eulerframework.web.core.base.controller.JspSupportWebController;
import net.eulerframework.web.module.cmf.entity.Post;
import net.eulerframework.web.module.cmf.htservice.PostService;

/**
 * @author cFrost
 *
 */
@JspController
@RequestMapping("cms/post")
public class PostJspController extends JspSupportWebController {

    @Resource
    private PostService postService;

    public PostJspController() {
        this.setWebControllerName("cms/post");
    }

    @RequestMapping("{postId}")
    public String findPost(@PathVariable("postId") String postId) {
        Post post = this.postService.findPost(postId);

        if (post == null) {
            this.notfound();
        }

        String viewPageSuffix = this.postService.findViewPageSuffix(post.getType());

        this.getRequest().setAttribute("post", post);
        return this.display("post-" + (StringUtils.hasText(viewPageSuffix) ? viewPageSuffix : "default"));
    }
}
