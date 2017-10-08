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
package net.eulerframework.web.module.cmf.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import net.eulerframework.web.core.base.request.easyuisupport.EasyUiQueryReqeuset;
import net.eulerframework.web.core.base.response.easyuisupport.EasyUIPageResponse;
import net.eulerframework.web.core.base.service.impl.BaseService;
import net.eulerframework.web.module.authentication.util.SecurityTag;
import net.eulerframework.web.module.cmf.dao.PostDao;
import net.eulerframework.web.module.cmf.dao.PostTypeDao;
import net.eulerframework.web.module.cmf.entity.Post;
import net.eulerframework.web.module.cmf.entity.PostType;
import net.eulerframework.web.module.cmf.exception.PostNotExistException;

/**
 * @author cFrost
 *
 */
@Service
public class PostService extends BaseService {
    
    @Resource PostTypeDao postTypeDao;
    
    @Resource PostDao postDao;
    
    /*
     * ===========================ADMIN=============================
     */
    
    /**
     * 获取文章管理页面后缀，管理页面后缀用于定制不同类型文章的管理页面，默认为default
     * @param type 文章类型
     * @return 文章管理页面后缀
     */
    public String findAdminPageSuffix(String type) {
        PostType postType = this.postTypeDao.load(type);
        if(postType == null) {
            return null;
        }
        
        return postType.getAdminPageSuffix();
    }

    /**
     * 新建文章类型
     * @param postType 文章类型实体类
     */
    public void savePostType(PostType postType) {
        this.postTypeDao.saveOrUpdate(postType);
    }

    /**
     * 分页查询文章类型
     * @param easyUiQueryReqeuset 分页请求
     * @return 分页响应
     */
    public EasyUIPageResponse<PostType> findPostTypeByPage(EasyUiQueryReqeuset easyUiQueryReqeuset) {
        return this.postTypeDao.pageQuery(easyUiQueryReqeuset);
    }

    /**
     * 删除文章类型
     * @param postTypes 文章类型标识
     */
    public void deletePostTypes(String... postTypes) {
        this.postTypeDao.deleteByIds(postTypes);
    }

    /**
     * 查询所有已生效的文章类型
     * @return 文章类型
     */
    public List<PostType> findAllPostTypes() {
        PostType tmp = new PostType();
        tmp.setEnabled(true);
        return this.postTypeDao.queryByEntity(tmp);
    }

    /**
     * 保存或更新文章
     * @param post 待保存或跟新的文章实体
     */
    public void saveOrUpdatePost(Post post) {
        if(StringUtils.hasText(post.getId())) {
            Post old = this.postDao.load(post.getId());
            
            if(old == null) {
                throw new PostNotExistException();
            }
            
            post.setCreateDate(old.getCreateDate());
        } else {
            List<Post> postInDescByOrder = this.postDao.findPostsInOrder(post.getType(), null, post.getLocale(), true, false, 1);
            if(CollectionUtils.isEmpty(postInDescByOrder)) {
                post.setOrder(0);
            } else {
                post.setOrder(postInDescByOrder.get(0).getOrder() + 1);
            }
            post.setCreateDate(new Date());            
        }
        this.postDao.saveOrUpdate(post);
    }

    /**
     * 分页查询文章
     * @param easyUiQueryReqeuset 分页请求
     * @return 分页响应
     */
    public EasyUIPageResponse<Post> findPostByPage(EasyUiQueryReqeuset easyUiQueryReqeuset) {
        List<Criterion> criterions = new ArrayList<>();
        
        String type = easyUiQueryReqeuset.getFilterValue("type");
        if(StringUtils.hasText(type)) {
            criterions.add(Restrictions.eq("type", type));
        }
        
        String locale = easyUiQueryReqeuset.getFilterValue("locale");
        if(StringUtils.hasText(locale)) {
            criterions.add(Restrictions.eq("locale", new Locale(locale)));
        }
        
        String top = easyUiQueryReqeuset.getFilterValue("top");
        if(StringUtils.hasText(top)) {
            criterions.add(Restrictions.eq("top", Boolean.parseBoolean(top)));
        }
        
        String approved = easyUiQueryReqeuset.getFilterValue("approved");
        if(StringUtils.hasText(approved)) {
            criterions.add(Restrictions.eq("approved", Boolean.parseBoolean(approved)));
        }
        
        List<Order> orders = new ArrayList<>();
        orders.add(Order.asc("order"));
        
        EasyUIPageResponse<Post> ret = this.postDao.pageQuery(easyUiQueryReqeuset, criterions, orders);
        
        if(!CollectionUtils.isEmpty(ret.getRows())) {
            ret.getRows().stream().forEach(row -> row.setAuthorUsername(SecurityTag.userIdtoUserame(row.getAuthorId())));
        }
        
        return ret;
    }

    /**
     * 批量删除文章
     * @param postId 待删除的文章id
     */
    public void deletePosts(String... postId) {
        this.postDao.deleteByIds(postId);
    }

    /**
     * 为文章排序
     * @param postIds 文章ID
     * @param postOrders 文章序号，需于文章ID一一对应
     */
    public void sortPostsRWT(String[] postIds, int[] postOrders) {
        Assert.notEmpty(postIds, "postIds is empty");
        Assert.notNull(postOrders, "postIds is empty");
        Assert.isTrue(postIds.length == postOrders.length, "postIds and postOrders must have save elements");
        
        for(int i = 0; i < postIds.length; i++) {
            Post post = this.postDao.load(postIds[i]);
            post.setOrder(postOrders[i]);
            this.postDao.update(post);
        }
    }

    /**
     * 置顶文章
     * @param postId 待置顶的文章ID
     */
    public void topPostsRWT(String... postId) {
        List<Post> posts = this.postDao.load(postId);
        
        if(CollectionUtils.isEmpty(posts)) {
            return;
        }
        
        for(Post post : posts) {
            post.setTop(true);
        }
        
        this.postDao.saveOrUpdateBatch(posts);
    }

    /**
     * 取消置顶文章
     * @param postId 待取消置顶的文章ID
     */
    public void untopPostsRWT(String... postId) {
        List<Post> posts = this.postDao.load(postId);
        
        if(CollectionUtils.isEmpty(posts)) {
            return;
        }
        
        for(Post post : posts) {
            post.setTop(false);
        }
        
        this.postDao.saveOrUpdateBatch(posts);
    }

    /**
     * 审批文章
     * @param postId 待审批的文章ID
     */
    public void approvePostsRWT(String... postId) {
        List<Post> posts = this.postDao.load(postId);
        
        if(CollectionUtils.isEmpty(posts)) {
            return;
        }
        
        for(Post post : posts) {
            post.setApproved(true);
        }
        
        this.postDao.saveOrUpdateBatch(posts);
    }

    /**
     * 取消审批文章
     * @param postId 待取消审批的文章ID
     */
    public void unapprovePostsRWT(String... postId) {
        List<Post> posts = this.postDao.load(postId);
        
        if(CollectionUtils.isEmpty(posts)) {
            return;
        }
        
        for(Post post : posts) {
            post.setApproved(false);
        }
        
        this.postDao.saveOrUpdateBatch(posts);
    }
    
    /*
     * ===========================VIEW=============================
     */

    /**
     * 获取文章展示页面后缀，管理页面后缀用于定制不同类型文章的展示页面，默认为default
     * @param type 文章类型
     * @return 文章展示页面后缀
     */
    public String findViewPageSuffix(String type) {
        PostType postType = this.postTypeDao.load(type);
        if(postType == null) {
            return null;
        }
        
        return postType.getViewPageSuffix();
    }

    /**
     * 根据ID查找文章
     * @param id 文章ID
     * @return 文章
     */
    public Post findPost(String id) {
        return this.postDao.load(id);
    }

    /**
     * 按年份、语言和类型正序读取文章
     * @param type 文章类型
     * @param year 发布年份
     * @param locale 语言
     * @param max 
     * @return 文章列表
     */
    public List<Post> findPostsInOrder(String type, String year, Locale locale, boolean onlyTop, int max) {
        return this.postDao.findPostsInOrder(type, year, locale, false, onlyTop, max);
    }

    /**
     * 按语言获取某类型文章的所有可用年份
     * @param type 文章类型
     * @param locale 语言
     * @return 可用年份列表，正序排列
     */
    public List<String> findPostsYears(String type, Locale locale) {
        return this.postDao.findPostsYears(type, locale);
    }

}
