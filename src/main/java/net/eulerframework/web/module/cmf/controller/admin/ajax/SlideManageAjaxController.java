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
package net.eulerframework.web.module.cmf.controller.admin.ajax;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.eulerframework.web.core.annotation.AjaxController;
import net.eulerframework.web.core.base.controller.ApiSupportWebController;
import net.eulerframework.web.core.base.request.PageQueryRequest;
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
        return this.slideService.findSlideByPage(
                new PageQueryRequest(
                        this.getRequest(), 
                        PageQueryRequest.EASYUI_PAGE_INDEX_NAME, 
                        PageQueryRequest.EASYUI_PAGE_SIZE_NAME));
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
        return this.slideService.findSlideTypeByPage(
                new PageQueryRequest(
                    this.getRequest(), 
                    PageQueryRequest.EASYUI_PAGE_INDEX_NAME, 
                    PageQueryRequest.EASYUI_PAGE_SIZE_NAME));
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
