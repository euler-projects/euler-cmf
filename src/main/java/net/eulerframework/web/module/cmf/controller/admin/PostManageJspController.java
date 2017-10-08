/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013-2017 cFrost.sun(孙宾, SUN BIN) 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 * For more information, please visit the following website
 * 
 * https://eulerproject.io
 * https://github.com/euler-form/web-form
 * https://cfrost.net
 */
package net.eulerframework.web.module.cmf.controller.admin;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.eulerframework.web.core.annotation.JspController;
import net.eulerframework.web.core.base.controller.JspSupportWebController;
import net.eulerframework.web.module.cmf.entity.Post;
import net.eulerframework.web.module.cmf.exception.PostNotExistException;
import net.eulerframework.web.module.cmf.service.PostService;
import net.eulerframework.web.module.cmf.vo.PostExtraDataVO;

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
        
        this.getRequest().setAttribute("post", new PostExtraDataVO(post));
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
