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
