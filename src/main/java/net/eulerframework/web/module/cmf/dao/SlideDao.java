package net.eulerframework.web.module.cmf.dao;

import java.util.List;
import java.util.Locale;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;

import net.eulerframework.web.core.base.dao.impl.hibernate5.BaseDao;
import net.eulerframework.web.module.cmf.entity.Slide;

/**
 * @author cFrost
 *
 */
public class SlideDao extends BaseDao<Slide> {

    /**
     * @param type 图片类型
     * @param locale 指定语言,空表示全部查出
     * @param desc <code>true</code> 倒序查出 <code>false</code> 正序查出
     * @return 符合条件的图片
     */
    public List<Slide> findSlideByOrder(String type, Locale locale, boolean desc) {
        Assert.hasText(type, "Type is null");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.entityClass);
        detachedCriteria.add(Restrictions.eq("type", type));
        
        if(locale != null) {
            detachedCriteria.add(Restrictions.eq("locale", locale));
        }
        
        if(desc) {
            detachedCriteria.addOrder(Order.desc("order"));            
        } else {
            detachedCriteria.addOrder(Order.asc("order"));  
        }
        return this.limitQuery(detachedCriteria, 100);
    }

}
