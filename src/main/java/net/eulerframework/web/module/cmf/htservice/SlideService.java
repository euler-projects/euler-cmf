package net.eulerframework.web.module.cmf.htservice;

import java.util.ArrayList;
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

import net.eulerframework.web.core.base.request.PageQueryRequest;
import net.eulerframework.web.core.base.response.PageResponse;
import net.eulerframework.web.core.base.service.impl.BaseService;
import net.eulerframework.web.module.cmf.dao.SlideDao;
import net.eulerframework.web.module.cmf.dao.SlideTypeDao;
import net.eulerframework.web.module.cmf.entity.Slide;
import net.eulerframework.web.module.cmf.entity.SlideType;

/**
 * @author cFrost
 *
 */
@Service
public class SlideService extends BaseService {
    
    @Resource SlideTypeDao slideTypeDao;

    /**
     * 新建图片类型
     * @param slideType 图片类型实体类
     */
    public void saveSlideType(SlideType slideType) {
        this.slideTypeDao.saveOrUpdate(slideType);
    }

    /**
     * 分页查询图片类型
     * @param pageQueryRequeset 分页请求
     * @return 分页响应
     */
    public PageResponse<SlideType> findSlideTypeByPage(PageQueryRequest pageQueryRequeset) {
        return this.slideTypeDao.pageQuery(pageQueryRequeset);
    }

    /**
     * 删除图片类型
     * @param slideTypes 图片类型标识
     */
    public void deleteSlideTypes(String... slideTypes) {
        this.slideTypeDao.deleteByIds(slideTypes);
    }

    /**
     * 查询所有已生效的图片类型
     * @return 图片类型
     */
    public List<SlideType> findAllSlideTypes() {
        SlideType tmp = new SlideType();
        tmp.setEnabled(true);
        return this.slideTypeDao.queryByEntity(tmp);
    }
    
    @Resource SlideDao slideDao;

    /**
     * @param slide
     */
    public void saveSlide(Slide slide) {
        if(!StringUtils.hasText(slide.getId())) {
            List<Slide> slideInDescByOrder = this.slideDao.findSlideByOrder(slide.getType(), slide.getLocale(), true);
            if(CollectionUtils.isEmpty(slideInDescByOrder)) {
                slide.setOrder(0);
            } else {
                slide.setOrder(slideInDescByOrder.get(0).getOrder() + 1);
            }
        }
        this.slideDao.saveOrUpdate(slide);
    }

    /**
     * @param pageQueryRequeset
     * @return
     */
    public PageResponse<Slide> findSlideByPage(PageQueryRequest pageQueryRequeset) {
        List<Criterion> criterions = new ArrayList<>();
        
        String type = pageQueryRequeset.getFilterValue("type");
        if(StringUtils.hasText(type)) {
            criterions.add(Restrictions.eq("type", type));
        }
        
        String locale = pageQueryRequeset.getFilterValue("locale");
        if(StringUtils.hasText(locale)) {
            criterions.add(Restrictions.eq("locale", new Locale(locale)));
        }
        
        List<Order> orders = new ArrayList<>();
        orders.add(Order.asc("order"));
        
        return this.slideDao.pageQuery(pageQueryRequeset, criterions, orders);
    }

    /**
     * @param ids
     */
    public void deleteSlides(String... id) {
        this.slideDao.deleteByIds(id);
        
    }

    /**
     * @param slideIds
     * @param slideOrders
     */
    public void sortSlidesRWT(String[] slideIds, int[] slideOrders) {
        Assert.notEmpty(slideIds, "slideIds is empty");
        Assert.notNull(slideOrders, "slideIds is empty");
        Assert.isTrue(slideIds.length == slideOrders.length, "slideIds and slideOrders must have save elements");
        
        for(int i = 0; i < slideIds.length; i++) {
            Slide slide = this.slideDao.load(slideIds[i]);
            slide.setOrder(slideOrders[i]);
            this.slideDao.update(slide);
        }
    }

    /**
     * @param type
     * @param locale 
     * @return
     */
    public List<Slide> findSlidesByType(String type, Locale locale) {
        Assert.hasText(type, "Slide type can not be empty");
        Assert.notNull(locale, "locale can not be null");
        return this.slideDao.findSlideByOrder(type, locale, false);
    }

}
