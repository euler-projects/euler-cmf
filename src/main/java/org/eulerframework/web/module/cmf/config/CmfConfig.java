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
package org.eulerframework.web.module.cmf.config;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.eulerframework.cache.inMemoryCache.DefaultObjectCache;
import org.eulerframework.cache.inMemoryCache.ObjectCachePool;
import org.eulerframework.common.util.property.PropertyNotFoundException;
import org.eulerframework.common.util.property.PropertyReader;

@Configuration
public abstract class CmfConfig {
    protected static final Logger LOGGER = LoggerFactory.getLogger(CmfConfig.class);

    private static final DefaultObjectCache<String, Object> CONFIG_CAHCE = ObjectCachePool
            .generateDefaultObjectCache(Long.MAX_VALUE);

    private static final PropertyReader properties = new PropertyReader("/config-cmf.properties");

    private static class CmfConfigKey {
       private final static String SUPPORT_LANGUAGES = "supportLanguages";

       private final static String POST_QUERY_LIMIT_DEFAULT = "post.queryLimitDefault";
       private final static String POST_QUERY_LIMIT_MIN = "post.queryLimitMin";
       private final static String POST_QUERY_LIMIT_MAX = "post.queryLimitMax";
    }

    private static class CmfConfigDefault {
        private final static Locale[] SUPPORT_LANGUAGES = new Locale[] {Locale.CHINA, Locale.US};

        private final static int POST_QUERY_LIMIT_DEFAULT = 20;
        private final static int POST_QUERY_LIMIT_MIN = 1;
        private final static int POST_QUERY_LIMIT_MAX = 100;
    }

    public static boolean clearSecurityConfigCache() {
        properties.refresh();
        return CONFIG_CAHCE.clear();
    }

    public static Locale[] getSupportLanguages() {
        return (Locale[]) CONFIG_CAHCE.get(CmfConfigKey.SUPPORT_LANGUAGES, key -> {
            try {
                String supportLanguagesStr = properties.get(key);
                String[] supportLanguagesStrArray = supportLanguagesStr.split(",");
                Locale[] ret = new Locale[supportLanguagesStrArray.length];
                
                for(int i = 0; i < supportLanguagesStrArray.length; i++) {
                    String[] languageStr = supportLanguagesStrArray[i].split("_");
                    ret[i] = new Locale(languageStr[0], languageStr[1]);
                }
                return ret;
            } catch (PropertyNotFoundException e) {
                return CmfConfigDefault.SUPPORT_LANGUAGES;
            }
        });
    }
    
    public static int getPostQueryLimitDefault() {
        return (int) CONFIG_CAHCE.get(CmfConfigKey.POST_QUERY_LIMIT_DEFAULT, key ->
            properties.getIntValue(key, CmfConfigDefault.POST_QUERY_LIMIT_DEFAULT));
    }
    
    public static int getPostQueryLimitMin() {
        return (int) CONFIG_CAHCE.get(CmfConfigKey.POST_QUERY_LIMIT_MIN, key ->
            properties.getIntValue(key, CmfConfigDefault.POST_QUERY_LIMIT_MIN));
    }
    
    public static int getPostQueryLimitMax() {
        return (int) CONFIG_CAHCE.get(CmfConfigKey.POST_QUERY_LIMIT_MAX, key ->
            properties.getIntValue(key, CmfConfigDefault.POST_QUERY_LIMIT_MAX));
    }

}
