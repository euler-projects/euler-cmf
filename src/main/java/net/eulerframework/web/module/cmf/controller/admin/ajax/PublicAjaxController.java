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
