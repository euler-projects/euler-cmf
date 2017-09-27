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
package net.eulerframework.web.module.cmf.controller.admin.ajax;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.eulerframework.web.core.annotation.AjaxController;
import net.eulerframework.web.core.base.controller.AjaxSupportWebController;
import net.eulerframework.web.core.base.request.easyuisupport.EasyUiQueryReqeuset;
import net.eulerframework.web.core.base.response.easyuisupport.EasyUIPageResponse;
import net.eulerframework.web.module.authentication.context.UserContext;
import net.eulerframework.web.module.cmf.entity.Post;
import net.eulerframework.web.module.cmf.entity.PostType;
import net.eulerframework.web.module.cmf.service.PostService;

/**
 * @author cFrost
 *
 */
@AjaxController
@RequestMapping("cmf/post")
public class PostManageAjaxController extends AjaxSupportWebController {
    
    @Resource PostService postService;
    
    @RequestMapping(path = "savePost", method = RequestMethod.POST)
    public void savePostType(Post post) {
        post.setAuthorId(UserContext.getCurrentUser().getUserId().toString());
        this.postService.savePost(post);
    }

    @RequestMapping(path = "findPostByPage")
    public EasyUIPageResponse<Post> findPostByPage(){
        return this.postService.findPostByPage(new EasyUiQueryReqeuset(this.getRequest()));
    }
    
    @RequestMapping(path = "deletePosts", method = RequestMethod.POST)
    public void deletePosts(@RequestParam String[] postIds) {
        this.postService.deletePosts(postIds);
    }
    
    @RequestMapping(path = "sortPosts", method = RequestMethod.POST)
    public void sortPosts(@RequestParam String[] postIds, @RequestParam int[] postOrders) {
        this.postService.sortPostsRWT(postIds, postOrders);
    }
    
    @RequestMapping(path = "findAllPostTypes", method = RequestMethod.GET)
    public List<PostType> findAllPostTypes() {
        return this.postService.findAllPostTypes();
    }

    @RequestMapping(path = "findPostTypeByPage")
    public EasyUIPageResponse<PostType> findPostTypeByPage(){
        return this.postService.findPostTypeByPage(new EasyUiQueryReqeuset(this.getRequest()));
    }
    
    @RequestMapping(path = "savePostType", method = RequestMethod.POST)
    public void savePostType(PostType postType) {
        this.postService.savePostType(postType);
    }
    
    @RequestMapping(path = "deletePostTypes", method = RequestMethod.POST)
    public void deletePostTypes(@RequestParam String[] postTypes) {
        this.postService.deletePostTypes(postTypes);
    }
}
