/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
