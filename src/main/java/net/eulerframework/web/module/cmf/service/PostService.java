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
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import net.eulerframework.web.core.base.request.easyuisupport.EasyUiQueryReqeuset;
import net.eulerframework.web.core.base.response.easyuisupport.EasyUIPageResponse;
import net.eulerframework.web.core.base.service.impl.BaseService;
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
    
    @Resource PostDao postDao;

    /**
     * @param post
     */
    public void savePost(Post post) {
        if(StringUtils.hasText(post.getId())) {
            Post old = this.postDao.load(post.getId());
            
            if(old == null) {
                throw new PostNotExistException();
            }
            
            post.setCreateDate(old.getCreateDate());
        } else {
            List<Post> postInDescByOrder = this.postDao.findPostByOrder(post.getType(), post.getLocale(), true);
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
     * @param easyUiQueryReqeuset
     * @return
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
        
        return this.postDao.pageQuery(easyUiQueryReqeuset, criterions);
    }

    /**
     * @param ids
     */
    public void deletePosts(String... id) {
        this.postDao.deleteByIds(id);
        
    }

    /**
     * @param postIds
     * @param postOrders
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
     * @param type
     * @param locale 
     * @return
     */
    public List<Post> findPostsByType(String type, Locale locale) {
        Assert.hasText(type, "Post type can not be empty");
        Assert.notNull(locale, "locale can not be null");
        return this.postDao.findPostByOrder(type, locale, false);
    }

    /**
     * 根据ID查找文章
     * @param id 文章ID
     * @return 文章
     */
    public Post findPost(String id) {
        return this.postDao.load(id);
    }

}
