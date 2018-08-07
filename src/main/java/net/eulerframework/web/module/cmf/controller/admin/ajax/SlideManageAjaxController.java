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
