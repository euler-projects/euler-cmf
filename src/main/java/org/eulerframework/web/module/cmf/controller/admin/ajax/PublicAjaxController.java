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
package org.eulerframework.web.module.cmf.controller.admin.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.eulerframework.web.core.annotation.AjaxController;
import org.eulerframework.web.core.base.controller.ApiSupportWebController;
import org.eulerframework.web.module.cmf.config.CmfConfig;

/**
 * @author cFrost
 *
 */
@AjaxController
@RequestMapping("cms/public")
public class PublicAjaxController extends ApiSupportWebController {
    
    @RequestMapping(path = "findAllSupportLanguages", method = RequestMethod.GET)
    public Locale[] findAllSupportLanguages() {
        return CmfConfig.getSupportLanguages();
    }
    
    @RequestMapping(path = "findAllSupportLanguagesKV", method = RequestMethod.GET)
    public List<Map<String, String>> findAllSupportLanguagesKV(boolean addAll) {
        List<Map<String, String>> ret = new ArrayList<>();
        Locale[] locales = CmfConfig.getSupportLanguages();
        
        if(addAll) {
            Map<String, String> map = new HashMap<>();
            map.put("key", "");
            map.put("value", "All");
            ret.add(map);
        }
        
        for(Locale locale : locales) {
            Map<String, String> map = new HashMap<>();
            map.put("key", locale.toString());
            map.put("value", locale.toString());
            ret.add(map);
        }
        
        return ret;
    }
}
