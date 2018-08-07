package net.eulerframework.web.module.cmf.controller.admin;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.eulerframework.web.core.annotation.JspController;
import net.eulerframework.web.core.base.controller.JspSupportWebController;
import net.eulerframework.web.module.cmf.entity.Post;
import net.eulerframework.web.module.cmf.exception.PostNotExistException;
import net.eulerframework.web.module.cmf.htservice.PostService;

/**
 * @author cFrost
 *
 */
@JspController
@RequestMapping("cms/post")
public class PostManageJspController extends JspSupportWebController {
    
    @Resource PostService postService;

    public PostManageJspController() {
        this.setWebControllerName("cms/post");
    }
    
    @RequestMapping("postManage")
    public String postManage() {
        return this.display("postManage");
    }
    
    @RequestMapping("addPost")
    public String addPost(@RequestParam(required = false) String type) {
        if(StringUtils.hasText(type)) {
            String adminPageSuffix = this.postService.findAdminPageSuffix(type);
            this.getRequest().setAttribute("type", type);
            return this.display("addPost-" + (StringUtils.hasText(adminPageSuffix) ? adminPageSuffix : "default"));
        } else {
            return this.display("addPost-default");
        }
    }
    
    @RequestMapping("editPost")
    public String editPost(
            @RequestParam String id,
            @RequestParam(required = false) String type) {
        Post post = this.postService.findPost(id);
        
        if(post == null) {
            throw new PostNotExistException();
        }
        
        this.getRequest().setAttribute("post", post);
        if(StringUtils.hasText(type)) {
            String adminPageSuffix = this.postService.findAdminPageSuffix(type);
            return this.display("editPost-" + (StringUtils.hasText(adminPageSuffix) ? adminPageSuffix : "default"));
        } else {
            return this.display("editPost-default");
        }
    }
    
    @RequestMapping("postTypeManage")
    public String postTypeManage() {
        return this.display("postTypeManage");
    }
}
