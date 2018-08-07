package net.eulerframework.web.module.cmf.controller.api;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.eulerframework.web.core.annotation.ApiEndpoint;
import net.eulerframework.web.core.base.controller.ApiSupportWebController;
import net.eulerframework.web.module.cmf.entity.Slide;
import net.eulerframework.web.module.cmf.htservice.SlideService;

/**
 * @author cFrost
 *
 */
@ApiEndpoint
@RequestMapping("cms/slide")
public class SlideApiEndpoint extends ApiSupportWebController {
    
    @Resource private SlideService slideService;
    
    @RequestMapping("type/{type}")
    public List<Slide> findSlidesByType(@PathVariable("type") String type, Locale locale) {
        return this.slideService.findSlidesByType(type, locale);
    }
}
