package net.eulerframework.web.module.cmf.config;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import net.eulerframework.cache.inMemoryCache.DefaultObjectCache;
import net.eulerframework.cache.inMemoryCache.ObjectCachePool;
import net.eulerframework.common.util.property.PropertyNotFoundException;
import net.eulerframework.common.util.property.PropertyReader;

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
                    ret[i] = new Locale(supportLanguagesStrArray[i]);
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
