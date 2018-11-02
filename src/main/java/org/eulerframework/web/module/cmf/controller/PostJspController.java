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
package org.eulerframework.web.module.cmf.controller;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.eulerframework.web.core.annotation.JspController;
import org.eulerframework.web.core.base.controller.JspSupportWebController;
import org.eulerframework.web.module.cmf.entity.Post;
import org.eulerframework.web.module.cmf.htservice.PostService;

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
