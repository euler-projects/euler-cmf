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

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import org.eulerframework.common.util.DateUtils;
import org.eulerframework.web.core.base.dao.impl.hibernate5.BaseDao;
import org.eulerframework.web.module.cmf.entity.Post;

/**
 * @author cFrost
 *
 */
public class PostDao extends BaseDao<Post> {

    /**
     * @param year 文章发布年份,可选条件
     * @param type 文章类型
     * @param locale 指定语言,空表示全部查出
     * @param desc <code>true</code> 倒序查出 <code>false</code> 正序查出
     * @param onlyTop <code>true</code> 只查置顶项目 <code>false</code> 查出置顶和非置顶项目
     * @param onlyApproved <code>true</code> 只查已审核项目 <code>false</code> 查出已审核和未审核项目
     * @param max 查询结果数量限制,最大值1000
     * @return 符合条件的文章
     */
    public List<Post> findPostsInOrder(String type, String year, Locale locale, boolean desc, boolean onlyTop, boolean onlyApproved, int max) {
        Assert.hasText(type, "Type is null");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.entityClass);
        detachedCriteria.add(Restrictions.eq("type", type));
        
        if(StringUtils.hasText(year)) {
            Date begin;
            try {
                begin = DateUtils.parseDate(year, "yyyy");
            } catch (ParseException e) {
                throw new IllegalArgumentException();
            }
            Calendar endC = DateUtils.toCalendar(begin);
            endC.add(Calendar.YEAR, 1);
            Date end = endC.getTime();
            
            detachedCriteria.add(Restrictions.ge("updateDate", begin));
            detachedCriteria.add(Restrictions.lt("updateDate", end));
        }
        
        if(locale != null) {
            detachedCriteria.add(Restrictions.eq("locale", locale));
        }
        
        if(desc) {
            detachedCriteria.addOrder(Order.desc("order"));            
        } else {
            detachedCriteria.addOrder(Order.asc("order"));  
        }
        
        if(onlyTop) {
            detachedCriteria.add(Restrictions.eq("top", onlyTop));
        }
        
        if(onlyApproved) {
            detachedCriteria.add(Restrictions.eq("approved", onlyApproved));
        }
        
        return this.limitQuery(detachedCriteria, max > 1000 ? 1000 : max);
    }
    
    public List<String> findPostsYears(String type, Locale locale) {
        String sql = "select distinct DATE_FORMAT(UPDATE_DATE,'%Y') years from CMF_POST where TYPE = :type and LOCALE = :locale and APPROVED = true order by years;";
        SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
        
        query.setString("type", type);
        query.setString("locale", locale.toString());
        
        @SuppressWarnings("unchecked")
        List<String> ret = query.list();
        
        return ret;
    }

}
