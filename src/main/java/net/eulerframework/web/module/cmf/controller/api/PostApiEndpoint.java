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
package net.eulerframework.web.module.cmf.controller.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.eulerframework.web.core.annotation.ApiEndpoint;
import net.eulerframework.web.core.base.controller.AbstractApiEndpoint;
import net.eulerframework.web.module.cmf.config.CmfConfig;
import net.eulerframework.web.module.cmf.entity.Post;
import net.eulerframework.web.module.cmf.service.PostService;

/**
 * @author cFrost
 *
 */
@ApiEndpoint
@RequestMapping("cms/post")
public class PostApiEndpoint extends AbstractApiEndpoint {
    
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
        return this.postService.findPostsYears(type,  this.getRequest().getLocale());
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
    
    private void eareaseContect(List<Post> posts) {
        if(!CollectionUtils.isEmpty(posts)) {
            for(Post each : posts) {
                each.setContent(null);
            }
        }
    }
}
