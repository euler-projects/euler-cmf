package net.eulerframework.web.module.cmf.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;

import net.eulerframework.web.core.annotation.JspController;
import net.eulerframework.web.core.base.controller.JspSupportWebController;

/**
 * @author cFrost
 *
 */
@JspController
@RequestMapping("cms/slide")
public class SlideManageJspController extends JspSupportWebController {

    public SlideManageJspController() {
        this.setWebControllerName("cms/slide");
    }
    
    @RequestMapping("slideManage")
    public String slideManage() {
        return this.display("slideManage");
    }
    
    @RequestMapping("slideTypeManage")
    public String slideTypeManage() {
        return this.display("slideTypeManage");
    }
}
