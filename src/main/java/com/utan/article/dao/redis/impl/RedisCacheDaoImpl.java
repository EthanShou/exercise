package com.utan.article.dao.redis.impl;

import com.utan.article.constant.BaseConsts;
import com.utan.article.dao.redis.RedisCacheDao;
import com.utan.article.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import sun.plugin.services.BrowserService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Repository
public class RedisCacheDaoImpl implements RedisCacheDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheDaoImpl.class);

    @Autowired
    @Lazy
    private BrowseService browseService

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RedisTemplate redisTemplate;

    private final String CACHE_NAME_USER = "getUser";
    private final String CACHE_NAME_INIT = "getInitArticlesByTime";
    private final String CACHE_NAME_HISTORY = "getHistoryArticlesByLastId";
    private final String KEY_EDIT_REC = "edit_rec";
    private final String KEY_UPDATE_AUTHOR = "update_author";

    private final Lock lock= new ReentrantLock();

    @Override
    @Cacheable(value = CACHE_NAME_USER,key="#key")
    public UserInfo getUser(long userId, String mac, String key) {
        return new UserInfo(userId,mac,key);
    }

    @Override
    @CachePut(value = CACHE_NAME_USER, key = "#key")
    public UserInfo updateUser(String key, UserInfo user) {
        return user;
    }

    @Override
    @CacheEvict(value = CACHE_NAME_USER, allEntries = true)
    public void clearUsers() {

    }

    @Override
    public List<Map<String, Object>> getInitArticlesByTime(int hour, String tag) {
        Cache.ValueWrapper wrapper = cacheManager.getCache(CACHE_NAME_INIT).get(hour + BaseConsts.COLON_STR + tag);
        if(wrapper != null){
            return (List<Map<String,Object>>) wrapper.get();
        }

        lock.lock();
        try {
            wrapper = cacheManager.getCache(CACHE_NAME_INIT).get(hour + BaseConsts.COLON_STR + tag);
            if (wrapper != null) {
                return (List<Map<String, Object>>) wrapper.get();
            }

            LOGGER.info("hour={} category={}-->类目初始化", hour, tag);

            browseService.buildInitCache(tag, BaseConsts.DEFAULT_LIMIT_SIZE);


    }

    @Override
    public List<Map<String, Object>> getHistoryArticlesByLastId(String tag, long lastId, int size) {
        return null;
    }

    @Override
    public void pushTodayArticleList(String category, Long id) {

    }

    @Override
    public void pushTodayArticleList(String category, List<Long> list) {

    }

    @Override
    public void clearTodayArticleList(String category) {

    }

    @Override
    public List<Long> getTodayArticleList(String category) {
        return null;
    }

    @Override
    public void pushEditRecList(Long id) {

    }

    @Override
    public void clearEditRecList() {

    }

    @Override
    public List<Long> getEditRecList() {
        return null;
    }

    @Override
    public void putUpdateAuthor(String authorId, String authorName) {

    }

    @Override
    public Map<String, String> getUpdateAuthors() {
        return null;
    }
}
