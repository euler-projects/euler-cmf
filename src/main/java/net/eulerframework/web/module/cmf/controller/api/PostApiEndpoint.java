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

import net.eulerframework.web.core.annotation.ApiEndpoint;
import net.eulerframework.web.core.base.controller.AbstractApiEndpoint;
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

    /**
     * 获取发表过某类型文章的所有年份
     * @param type 文章类型
     * @return 可用年份列表，正序排列
     */
    @RequestMapping("availableYears/type/{type}")
    public List<String> availableYears(@PathVariable("type") String type) {
        return this.postService.findPostsYears(type,  this.getRequest().getLocale());
    }

    /**
     * 按文章类型读取文章，只读取最新一年的所有文章，结果按正序排列
     * @param type 文章类型
     * @return 符合条件的文章
     */
    @RequestMapping("type/{type}")
    public List<Post> findPosts(@PathVariable("type") String type) {
        List<String> years = this.availableYears(type);
        
        if(CollectionUtils.isEmpty(years)) {
            return null;
        }
        
        return this.postService.findPostsInOrder(type, years.get(years.size() - 1), this.getRequest().getLocale());
    }
    
    /**
     * 按文章发布年份和类型读取文章，结果按正序排列
     * @param year 文章发布年份
     * @param type 文章类型
     * @return 符合条件的文章
     */
    @RequestMapping("type/{type}/year/{year}")
    public List<Post> findPosts(@PathVariable("type") String type, @PathVariable("year") String year) {
        return this.postService.findPostsInOrder(type, year, this.getRequest().getLocale());
    }
}
