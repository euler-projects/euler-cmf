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
     * @param easyUiQueryReqeuset 分页请求
     * @return 分页响应
     */
    public EasyUIPageResponse<SlideType> findSlideTypeByPage(EasyUiQueryReqeuset easyUiQueryReqeuset) {
        return this.slideTypeDao.pageQuery(easyUiQueryReqeuset);
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
            List<Slide> slideInDescByOrder = this.slideDao.findSlideByOrder(slide.getType(), true);
            if(CollectionUtils.isEmpty(slideInDescByOrder)) {
                slide.setOrder(0);
            } else {
                slide.setOrder(slideInDescByOrder.get(0).getOrder() + 1);
            }
        }
        this.slideDao.saveOrUpdate(slide);
    }

    /**
     * @param easyUiQueryReqeuset
     * @return
     */
    public EasyUIPageResponse<Slide> findSlideByPage(EasyUiQueryReqeuset easyUiQueryReqeuset) {
        List<Criterion> criterions = new ArrayList<>();
        
        String type = easyUiQueryReqeuset.getFilterValue("type");
        if(StringUtils.hasText(type)) {
            criterions.add(Restrictions.eq("type", type));
        }
        
        String locale = easyUiQueryReqeuset.getFilterValue("locale");
        if(StringUtils.hasText(locale)) {
            criterions.add(Restrictions.eq("locale", new Locale(locale)));
        }
        
        return this.slideDao.pageQuery(easyUiQueryReqeuset, criterions);
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
     * @return
     */
    public List<Slide> findSlidesByType(String type) {
        Assert.hasText(type, "Slide type can not be empty");
        return this.slideDao.findSlideByOrder(type, false);
    }

}
