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
package org.eulerframework.web.module.cmf.controller.admin.ajax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.eulerframework.web.core.annotation.AjaxController;
import org.eulerframework.web.core.base.controller.ApiSupportWebController;
import org.eulerframework.web.core.base.request.PageQueryRequest;
import org.eulerframework.web.core.base.response.PageResponse;
import org.eulerframework.web.module.authentication.context.UserContext;
import org.eulerframework.web.module.cmf.entity.Post;
import org.eulerframework.web.module.cmf.entity.PostAttachment;
import org.eulerframework.web.module.cmf.entity.PostType;
import org.eulerframework.web.module.cmf.htservice.PostService;

/**
 * @author cFrost
 *
 */
@AjaxController
@RequestMapping("cms/post")
public class PostManageAjaxController extends ApiSupportWebController {
    
    @Resource PostService postService;
    @Resource ObjectMapper objectMapper;
    
    private final static String EXTRA_DATA_PREFIX = "post.extra.";
    
    @RequestMapping(path = "findPostAttachments", method = RequestMethod.GET)
    public List<PostAttachment> findPostAttachments(@RequestParam String postId) {
        return this.postService.findPostAttachment(postId);
    }
    
    @RequestMapping(path = "savePost", method = RequestMethod.POST)
    public void savePostType(Post post) throws JsonProcessingException {
        Map<String, String[]> params = this.getRequest().getParameterMap();
        Map<String, Object> extraData = new HashMap<>();
        
        Set<Entry<String, String[]>> entry = params.entrySet();
        for(Entry<String, String[]> each : entry) {
            if(each.getKey().startsWith(EXTRA_DATA_PREFIX)) {
                String[] values = each.getValue();
                extraData.put(each.getKey().substring(EXTRA_DATA_PREFIX.length()), 
                        (values == null || values.length != 1) ? values : values[0]);
            }
        }
        
        if(!CollectionUtils.isEmpty(extraData)) {
            post.setExtraData(this.objectMapper.writeValueAsString(extraData));
        }
        
        post.setAuthorId(UserContext.getCurrentUser().getUserId().toString());
        this.postService.saveOrUpdatePost(post);
    }

    @RequestMapping(path = "findPostByPage")
    public PageResponse<Post> findPostByPage(){
        return this.postService.findPostByPage(
                new PageQueryRequest(this.getRequest()));
    }
    
    @RequestMapping(path = "deletePosts", method = RequestMethod.POST)
    public void deletePosts(@RequestParam String[] postIds) {
        this.postService.deletePosts(postIds);
    }
    
    @RequestMapping(path = "sortPosts", method = RequestMethod.POST)
    public void sortPosts(@RequestParam String[] postIds, @RequestParam int[] postOrders) {
        this.postService.sortPostsRWT(postIds, postOrders);
    }
    
    @RequestMapping(path = "topPosts", method = RequestMethod.POST)
    public void topPosts(@RequestParam String[] postIds) {
        this.postService.topPostsRWT(postIds);
    }
    @RequestMapping(path = "untopPosts", method = RequestMethod.POST)
    public void untopPosts(@RequestParam String[] postIds) {
        this.postService.untopPostsRWT(postIds);
    }
    @RequestMapping(path = "approvePosts", method = RequestMethod.POST)
    public void approvePosts(@RequestParam String[] postIds) {
        this.postService.approvePostsRWT(postIds);
    }
    @RequestMapping(path = "unapprovePosts", method = RequestMethod.POST)
    public void unapprovePosts(@RequestParam String[] postIds) {
        this.postService.unapprovePostsRWT(postIds);
    }
    
    @RequestMapping(path = "findAllPostTypes", method = RequestMethod.GET)
    public List<PostType> findAllPostTypes() {
        return this.postService.findAllPostTypes();
    }

    @RequestMapping(path = "findPostTypeByPage")
    public PageResponse<PostType> findPostTypeByPage(){
        return this.postService.findPostTypeByPage(
                new PageQueryRequest(this.getRequest()));
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
