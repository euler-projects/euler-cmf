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
 */
package net.eulerframework.web.module.cmf.controller.admin.ajax;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.eulerframework.web.core.annotation.AjaxController;
import net.eulerframework.web.core.base.controller.ApiSupportWebController;
import net.eulerframework.web.core.base.request.easyuisupport.EasyUiQueryReqeuset;
import net.eulerframework.web.core.base.response.PageResponse;
import net.eulerframework.web.module.cmf.entity.Slide;
import net.eulerframework.web.module.cmf.entity.SlideType;
import net.eulerframework.web.module.cmf.htservice.SlideService;

/**
 * @author cFrost
 *
 */
@AjaxController
@RequestMapping("cms/slide")
public class SlideManageAjaxController extends ApiSupportWebController {
    
    @Resource SlideService slideService;
    
    @RequestMapping(path = "saveSlide", method = RequestMethod.POST)
    public void saveSlideType(Slide slide) {
        this.slideService.saveSlide(slide);
    }

    @RequestMapping(path = "findSlideByPage")
    public PageResponse<Slide> findSlideByPage(){
        return this.slideService.findSlideByPage(new EasyUiQueryReqeuset(this.getRequest()));
    }
    
    @RequestMapping(path = "deleteSlides", method = RequestMethod.POST)
    public void deleteSlides(@RequestParam String[] slideIds) {
        this.slideService.deleteSlides(slideIds);
    }
    
    @RequestMapping(path = "sortSlides", method = RequestMethod.POST)
    public void sortSlides(@RequestParam String[] slideIds, @RequestParam int[] slideOrders) {
        this.slideService.sortSlidesRWT(slideIds, slideOrders);
    }
    
    @RequestMapping(path = "findAllSlideTypes", method = RequestMethod.GET)
    public List<SlideType> findAllSlideTypes() {
        return this.slideService.findAllSlideTypes();
    }

    @RequestMapping(path = "findSlideTypeByPage")
    public PageResponse<SlideType> findSlideTypeByPage(){
        return this.slideService.findSlideTypeByPage(new EasyUiQueryReqeuset(this.getRequest()));
    }
    
    @RequestMapping(path = "saveSlideType", method = RequestMethod.POST)
    public void saveSlideType(SlideType slideType) {
        this.slideService.saveSlideType(slideType);
    }
    
    @RequestMapping(path = "deleteSlideTypes", method = RequestMethod.POST)
    public void deleteSlideTypes(@RequestParam String[] slideTypes) {
        this.slideService.deleteSlideTypes(slideTypes);
    }
}
