package com.utan.article.service.impl;

import com.utan.article.constant.ArticleConsts;
import com.utan.article.constant.BrowseEnum;
import com.utan.article.dao.elasticsearch.ArticleEsDao;
import com.utan.article.dao.mysql.HelpMapper;
import com.utan.article.dao.redis.RedisCacheDao;
import com.utan.article.domain.DTO.BrowseDTO;
import com.utan.article.domain.DTO.SubscribeDTO;
import com.utan.article.entity.UserInfo;
import com.utan.article.exception.BrowseException;
import com.utan.article.result.ResultInfoEnum;
import com.utan.article.service.BrowseService;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

        BrowseEnum browseEnum;
        if(ArticleConsts.CATEGORY_RECOMMEND.equals(browseDTO.getTag())){
            browseEnum = BrowseEnum.RECOMMEND;
        }else {
            boolean getNew = (browseDTO.getFirstId() == 0 && browseDTO.getLastId() == 0) ||
                    (browseDTO.getFirstId() != 0 && browseDTO.getLastId() == 0);
            browseEnum = getNew? BrowseEnum.NEW:BrowseEnum.HISTORY;
        }

        List<Map<String, Object>> articleList = new ArrayList<>(browseDTO.getSize());
        switch (browseEnum){
            case RECOMMEND:
                articleList = getRecommend(user, browseDTO);
                break;
            case NEW:
                articleList = getNew(user, browseDTO);
                break;
            case HISTORY:
                articleList = getHistory(user, browseDTO);
                break;
            default:
                break;
        }

        List<Map<String, Object>> resultList = new ArrayList<>(articleList.size());
        Set<String> titleSet = new HashSet<>();

        for(Map<String, Object> article:articleList){
            String title = (String) article.get("title");
            if(titleSet.contains(title)){continue;}//重复title文章跳过

            titleSet.add(title);
            resultList.add(article);

        }
        return resultList;

    }

    @Override
    public List<Map<String, Object>> subscribe(SubscribeDTO subscribeDTO) {
        Set<Integer> authorIdSet = helpMapper.selectSubscribeAuthorIdByUserId(subscribeDTO.getUserId());

        SearchHits hits = articleEsDao.searchByAuthorIds(subscribeDTO.getLastId(), authorIdSet, subscribeDTO.getSize());

        int returnSize;
        if(hits == null || (returnSize = hits,getHits()) == 0){
            throw new BrowseException(ResultInfoEnum.NO_SUBSCRIBE_ARTICLES);
        }

        return buildArticleShow(hits, returnSize, BrowseEnum.SUBSCRIBE);
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
