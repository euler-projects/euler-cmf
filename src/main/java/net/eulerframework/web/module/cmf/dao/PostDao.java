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
 */
package net.eulerframework.web.module.cmf.dao;

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

import net.eulerframework.common.util.DateUtils;
import net.eulerframework.web.core.base.dao.impl.hibernate5.BaseDao;
import net.eulerframework.web.module.cmf.entity.Post;

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
