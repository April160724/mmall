package com.mmall.common;

import ch.qos.logback.classic.Logger;
import com.google.common.cache.CacheBuilder;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Author: July
 * @Description:
 * @Date Created in 2022-02-01 15:42
 * @Modified By:
 */
public class TokenCash {
    private static Logger logger= (Logger) LoggerFactory.getLogger(TokenCash.class);
    //将token的前缀置为常量
    public static final String TOKEN_PREFIX="token_";
    private static LoadingCache<String, String> localCache= CacheBuilder.
            newBuilder().initialCapacity(1000).maximumSize(10000)
            .expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String o) throws Exception {
                    return "null";
                }
            });

    public static  void setKey(String key,String  value){
        localCache.put(key,value);
    }

    public static String getkey(String key){
        String value=null;
        try {
            value=localCache.get(key);
            if ("null".equals(value)){
                return null;
            }
            return value;
        }catch (Exception e){
            logger.error("localcahe get error",e);
        }
        return null;
    }
}
