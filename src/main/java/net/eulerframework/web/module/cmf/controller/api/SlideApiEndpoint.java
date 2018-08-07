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
