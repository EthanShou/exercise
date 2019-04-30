package com.utan.article.service.impl;

import com.utan.article.dao.elasticsearch.ArticleEsDao;
import com.utan.article.dao.mysql.HelpMapper;
import com.utan.article.dao.redis.RedisCacheDao;
import com.utan.article.domain.DTO.BrowseDTO;
import com.utan.article.domain.DTO.SubscribeDTO;
import com.utan.article.entity.UserInfo;
import com.utan.article.service.BrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.PrimitiveIterator;

@Service
public class BrowseServiceImpl implements BrowseService {

    @Autowired
    private ArticleEsDao articleEsDao;
    @Autowired
    private RedisCacheDao redisCacheDao;
    @Autowired
    private HelpMapper helpMapper;

    @Override
    public List<Map<String, Object>> browse(BrowseDTO browseDTO) {
        Long userId = browseDTO.getUserId();
        String mac = browseDTO.getMac();
        String key = userId.longValue() == 0 ? mac : userId.toString();
        UserInfo user = redisCacheDao.getUser(userId, mac, key);
        return null;

    }

    @Override
    public List<Map<String, Object>> subscribe(SubscribeDTO subscribeDTO) {
        return null;
    }

    @Override
    public List<Map<String, Object>> hot(String label) {
        return null;
    }

    @Override
    public List<Map<String, Object>> buildInitCache(String tag, int size) {
        return null;
    }

    @Override
    public List<Map<String, Object>> buildHistoryCache(String tag, long lastId, int size) {
        return null;
    }
}
