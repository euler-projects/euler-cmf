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
package org.eulerframework.web.module.cmf.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;

import org.eulerframework.web.core.base.dao.impl.hibernate5.BaseDao;
import org.eulerframework.web.module.cmf.entity.PostAttachment;

/**
 * @author cFrost
 *
 */
public class PostAttachmentDao extends BaseDao<PostAttachment> {

    /**
     * 根据内容ID查询附件列表，按顺序返回
     * @param postId 内容ID
     * @return 附件列表
     */
    public List<PostAttachment> findPostAttachmentByPostId(String postId) {
        Assert.hasText(postId, "postId is null");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.entityClass);
        detachedCriteria.add(Restrictions.eq("postId", postId));
        detachedCriteria.addOrder(Order.asc("order"));
        return this.query(detachedCriteria);
    }

    /**
     * 删除某内容的所有附件
     * @param postId 内容ID
     */
    public void deleteByPostId(String postId) {
        Assert.hasText(postId, "postId is null");
        String hql = "delete from PostAttachment a where a.postId = :postId";
        Query query = this.getCurrentSession().createQuery(hql);
        query.setString("postId", postId);
        query.executeUpdate();
    }

}
