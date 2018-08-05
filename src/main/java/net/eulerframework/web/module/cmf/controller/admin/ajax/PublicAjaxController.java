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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import net.eulerframework.web.core.annotation.AjaxController;
import net.eulerframework.web.core.base.controller.ApiSupportWebController;
import net.eulerframework.web.module.cmf.config.CmfConfig;

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
