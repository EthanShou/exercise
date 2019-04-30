package com.utan.article.dao.elasticsearch.impl;

import com.utan.article.constant.*;
import com.utan.article.dao.elasticsearch.ArticleEsDao;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;

@Repository
public class ArticleESDaoImpl implements ArticleEsDao {

    @Resource
    private Client elasticsearchClient;

    @Override
    public int index(String id, Map<String, String> source) {
        final IndexResponse indexResponse = elasticsearchClient.prepareIndex(EsConsts.ARTICLE_INDEX, EsConsts.ARTICLE_TYPE, id)
                .setSource(source)
                .get();
        return indexResponse.getResult() == DocWriteResponse.Result.CREATED ? 1 : 0;
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        final DeleteResponse deleteResponse = elasticsearchClient.prepareDelete(EsConsts.ARTICLE_INDEX, EsConsts.ARTICLE_TYPE, id)
                .get();
        return 0;
    }

    @Override
    public int updateByPrimaryKey(String id, Map<String, String> source) {
//        UpdateResponse updateResponse = elasticsearchClient.prepareUpdate(EsConsts.ARTICLE_INDEX, EsConsts.ARTICLE_TYPE, id).setDoc(source).get();
        final IndexResponse indexResponse = elasticsearchClient.prepareIndex(
                EsConsts.ARTICLE_INDEX, EsConsts.ARTICLE_TYPE, id)
                .setSource()
                .get();
        return indexResponse.getResult() == DocWriteResponse.Result.UPDATED ? 1:0;
    }

    @Override
    public SearchHits searchBySelective(String tag, long id, OperationEnum operation, int size) {
        RangeQueryBuilder rangeQueryBuilder;
        switch (operation) {
            case LT:
                rangeQueryBuilder = QueryBuilders.rangeQuery(EsConsts.UNIQUEKEY_FIELD).lt(id);
                break;
            case GT:
                rangeQueryBuilder = QueryBuilders.rangeQuery(EsConsts.UNIQUEKEY_FIELD).gt(id);
                break;
            default:
                return null;
        }

        QueryBuilder tagQuery = ArticleConsts.CATEGORY_VIDEO.equals(tag)
                ? EsConsts.TAG_VIDEO_QUERY
                : QueryBuilders.termQuery(EsConsts.TAG_FIELD, tag);

        final SearchResponse searchResponse = elasticsearchClient.prepareSearch()
                .setIndices(EsConsts.ARTICLE_INDEX)
                .setTypes(EsConsts.ARTICLE_TYPE)
                .setFetchSource(EsConsts.RETURN_FIELDS, null)
                .setQuery(QueryBuilders.boolQuery()
                            .must(tagQuery)
                            .must(EsConsts.STATE_QUERY)
                            .must(EsConsts.FLAG_QUERY)
                            .must(rangeQueryBuilder))
                .setSize(size)
                .addSort(EsConsts.UNIQUEKEY_FIELD, SortOrder.DESC)
                .get();
        final SearchHits hits = searchResponse.getHits();

        return hits;
    }

    @Override
    public SearchHits searchByIds(List<Long> ids, int size) {
        final SearchResponse searchResponse = elasticsearchClient.prepareSearch()
                .setIndices(EsConsts.ARTICLE_INDEX)
                .setTypes(EsConsts.ARTICLE_TYPE)
                .setFetchSource(EsConsts.RETURN_FIELDS, null)
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termsQuery(EsConsts.UNIQUEKEY_FIELD, ids))
                        .must(EsConsts.STATE_QUERY)
                        .must(EsConsts.FLAG_QUERY))
                .setSize(size)
                .addSort(EsConsts.UNIQUEKEY_FIELD, SortOrder.DESC)
                .get();
        final SearchHits hits = searchResponse.getHits();

        return hits;
    }

    @Override
    public List<Long> searchByDateAndTag(String date, String tag, DateEnum dateEnum) {
        QueryBuilder tagQuery = ArticleConsts.CATEGORY_VIDEO.equals(tag)
                ? EsConsts.TAG_VIDEO_QUERY
                : QueryBuilders.termQuery(EsConsts.TAG_FIELD, tag);

        SearchRequestBuilder searchRequestBuilder = elasticsearchClient.prepareSearch()
                .setIndices(EsConsts.ARTICLE_INDEX)
                .setTypes(EsConsts.ARTICLE_TYPE)
                .setFetchSource(EsConsts.UNIQUEKEY_FIELD, null)
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery(EsConsts.YEAR_FIELD, date.substring(0, 4)))
                        .must(QueryBuilders.termQuery(EsConsts.MONTH_FIELD, date.substring(4, 6)))
                        .must(QueryBuilders.termQuery(EsConsts.DAY_FIELD, date.substring(6, 8)))
                        .must(tagQuery)
                        .must(EsConsts.STATE_QUERY)
                        .must(EsConsts.FLAG_QUERY));
        switch (dateEnum){
            case TODAY:
                searchRequestBuilder.setSize(EsConsts.TODAY_RETURN_SIZE)
                        .addSort(EsConsts.UNIQUEKEY_FIELD, SortOrder.ASC);
                break;
            case YESTERDAY:
                searchRequestBuilder.setSize(EsConsts.YESTERDAY_RETURN_SIZE)
                        .addSort(EsConsts.UNIQUEKEY_FIELD, SortOrder.DESC);
                break;
            default:
                break;
        }
        final SearchResponse searchResponse = searchRequestBuilder.get();
        final SearchHits hits = searchResponse.getHits();
        int returnSize = hits.getHits().length;

        List<Long> list = new ArrayList<>(returnSize);

        if (hits == null || returnSize == 0) {
            return list;
        }

        for (SearchHit hit : hits) {
            list.add((Long) hit.getSourceAsMap().get(EsConsts.UNIQUEKEY_FIELD));
        }

        if (dateEnum == DateEnum.YESTERDAY) {
            Collections.reverse(list);
        }

        return list;
    }

    /**
     * Search the documents by tag and id desc.
     *
     * @param tag  the tag of the article document to search
     * @param size the limit size
     * @return the hits of the search request
     */
    @Override
    public SearchHits searchByTagAndIdDesc(String tag, int size) {
        QueryBuilder tagQuery = ArticleConsts.CATEGORY_VIDEO.equals(tag)
                ? EsConsts.TAG_VIDEO_QUERY
                : QueryBuilders.termQuery(EsConsts.TAG_FIELD, tag);

        final SearchResponse searchResponse = elasticsearchClient.prepareSearch()
                .setIndices(EsConsts.ARTICLE_INDEX)
                .setTypes(EsConsts.ARTICLE_TYPE)
                .setFetchSource(EsConsts.RETURN_FIELDS, null)
                .setQuery(QueryBuilders.boolQuery()
                        .must(tagQuery)
                        .must(EsConsts.STATE_QUERY)
                        .must(EsConsts.FLAG_QUERY))
                .setSize(size)
                .addSort(EsConsts.UNIQUEKEY_FIELD, SortOrder.DESC)
                .get();
        final SearchHits hits = searchResponse.getHits();

        return hits;
    }

    /**
     * Search the documents by the specified field in the period.
     *
     * @param fieldName  the field name
     * @param fieldValue the field value
     * @param articleId  except the articleId
     * @param size       the limit size
     * @return the article unique key list
     */
    @Override
    public List<Long> searchByFieldInPeriod(String fieldName, Object fieldValue, long articleId, int size) {
        QueryBuilder queryBuilder;
        if (EsConsts.TAG_FIELD.equals(fieldName)) {
            queryBuilder = ArticleConsts.CATEGORY_VIDEO.equals(fieldValue)
                    ? EsConsts.TAG_VIDEO_QUERY
                    : QueryBuilders.termQuery(EsConsts.TAG_FIELD, fieldValue);
        } else {
            queryBuilder = QueryBuilders.termQuery(EsConsts.AUTHOR_FIELD, fieldValue);
        }

        long toTime = System.currentTimeMillis();
        long fromTime = toTime - BaseConsts.ONE_MONTH_MILLISECONDS;

        final SearchResponse searchResponse = elasticsearchClient.prepareSearch()
                .setIndices(EsConsts.ARTICLE_INDEX)
                .setTypes(EsConsts.ARTICLE_TYPE)
                .setFetchSource(EsConsts.UNIQUEKEY_FIELD, null)
                .setQuery(QueryBuilders.boolQuery()
                        .must(queryBuilder)
                        .must(EsConsts.TAG_VIDEO_QUERY)
                        .mustNot(QueryBuilders.termQuery(EsConsts.UNIQUEKEY_FIELD, articleId))
                        .must(QueryBuilders.rangeQuery(EsConsts.TIME_FIELD).gte(fromTime).lte(toTime))
                        .must(EsConsts.STATE_QUERY)
                        .must(EsConsts.FLAG_QUERY))
                .get();
        final SearchHits hits = searchResponse.getHits();
        int returnSize = hits.getHits().length;

        List<Long> list = new ArrayList<>(returnSize);
        if (hits != null && returnSize > 0) {
            for (SearchHit hit : hits) {
                list.add((Long) hit.getSourceAsMap().get(EsConsts.UNIQUEKEY_FIELD));
            }

            if (returnSize > size) {
                Collections.shuffle(list);
                return list.subList(0, size);
            }
        }

        return list;
    }

    /**
     * Search the documents by authors' ids.
     *
     * @param id          the base id of the documents to search
     * @param authorIdSet the authors' ids of the documents to search
     * @param size        the size of the documents to return
     * @return the hits of the search request
     */
    @Override
    public SearchHits searchByAuthorIds(long id, Set<Integer> authorIdSet, int size) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termsQuery(EsConsts.AUTHOR_FIELD, authorIdSet))
                .must(EsConsts.STATE_QUERY)
                .must(EsConsts.FLAG_QUERY);

        if (id != 0) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery(EsConsts.UNIQUEKEY_FIELD).lt(id));
        }

        final SearchResponse searchResponse = elasticsearchClient.prepareSearch()
                .setIndices(EsConsts.ARTICLE_INDEX)
                .setTypes(EsConsts.ARTICLE_TYPE)
                .setFetchSource(EsConsts.RETURN_FIELDS, null)
                .setQuery(boolQueryBuilder)
                .setSize(size)
                .addSort(EsConsts.UNIQUEKEY_FIELD, SortOrder.DESC)
                .get();
        final SearchHits hits = searchResponse.getHits();

        return hits;
    }
}
