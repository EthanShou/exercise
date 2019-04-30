package com.utan.article.dao.elasticsearch.impl;

import com.utan.article.constant.EsConsts;
import com.utan.article.dao.elasticsearch.HelperEsDao;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;

import javax.annotation.Resource;

public class HelperEsDaoImpl implements HelperEsDao {

    @Resource
    private Client elasticsearchClient;
    @Override
    public String searchAvatarByAuthorId(int authorId) {
        final SearchResponse searchResponse = elasticsearchClient.prepareSearch()
                .setIndices(EsConsts.USER_INDEX)
                .setTypes(EsConsts.USER_TYPE)
                .setFetchSource(EsConsts.AVATAR_FIELD, null)
                .setQuery(QueryBuilders.termQuery(EsConsts.USER_ID_FIELD, authorId))
                .get();
        final SearchHits hits = searchResponse.getHits();

        String avatar = "";
        if (hits != null && hits.getHits().length > 0) {
            avatar = (String) hits.getAt(0).getSourceAsMap().get(EsConsts.AVATAR_FIELD);
        }

        return avatar;
    }
}
