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
package net.eulerframework.web.module.cmf.controller.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.eulerframework.web.core.annotation.ApiEndpoint;
import net.eulerframework.web.core.base.controller.ApiSupportWebController;
import net.eulerframework.web.core.base.request.PageQueryRequest;
import net.eulerframework.web.core.base.response.PageResponse;
import net.eulerframework.web.module.cmf.config.CmfConfig;
import net.eulerframework.web.module.cmf.entity.Post;
import net.eulerframework.web.module.cmf.htservice.PostService;

/**
 * @author cFrost
 *
 */
@ApiEndpoint
@RequestMapping("cms/post")
public class PostApiEndpoint extends ApiSupportWebController {
    
    @Resource private PostService postService;

    @RequestMapping("{postId}")
    public Post findPost(@PathVariable("postId") String postId) {
        return this.postService.findPost(postId);
    }

    /**
     * 获取发表过某类型文章的所有年份
     * @param type 文章类型
     * @return 可用年份列表，正序排列
     */
    @RequestMapping("type/{type}:availableYears")
    public List<String> availableYears(@PathVariable("type") String type) {
        return this.postService.findPostsYears(type, this.getRequest().getLocale());
    }
    
    /**
     * 按文章类型读取文章，结果按正序排列
     * @param type 文章类型
     * @param year 文章发布年份
     * @param top <code>true</code> 只读取置顶文章 <code>false</code> 读取置顶和非置顶文章
     * @param max 最大结果数量限制
     * @return 符合条件的文章
     */
    @RequestMapping("type/{type}")
    public List<Post> findPosts(
            @PathVariable("type") String type, 
            @RequestParam(name = "year", required = false) String year,
            @RequestParam(name = "top", defaultValue = "false") boolean top,
            @RequestParam(name = "max", required = false) Integer max) {
        if(max == null) {
            max = CmfConfig.getPostQueryLimitDefault();
        } else {
            if(max > CmfConfig.getPostQueryLimitMax() || max < CmfConfig.getPostQueryLimitMin()) {
                throw new IllegalArgumentException("post query limit must between "
                        + CmfConfig.getPostQueryLimitMin() + " and " + CmfConfig.getPostQueryLimitMax());
            }
        }
        
        List<Post> ret = this.postService.findPostsInOrder(type, year, this.getRequest().getLocale(), top, max);

        this.eareaseContect(ret);
        
        return ret;
    }
    @RequestMapping("type/{type}:pageMode")
    public PageResponse<Post> findPostsByPage(@PathVariable("type") String type) {
        PageQueryRequest pageQueryRequest = new PageQueryRequest(this.getRequest());
        
        pageQueryRequest.getQueryMap().put("type", type);
        pageQueryRequest.getQueryMap().put("approved", "true");
        
        PageResponse<Post> ret = this.postService.findPostByPage(pageQueryRequest);
        
        this.eareaseContect(ret.getRows());
        
        return ret;
    }
    
    private void eareaseContect(List<Post> posts) {
        if(!CollectionUtils.isEmpty(posts)) {
            for(Post each : posts) {
                each.setContent(null);
            }
        }
    }
}
