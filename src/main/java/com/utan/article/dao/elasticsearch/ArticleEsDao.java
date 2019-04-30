package com.utan.article.dao.elasticsearch;

import com.utan.article.constant.DateEnum;
import com.utan.article.constant.OperationEnum;
import org.elasticsearch.search.SearchHits;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ArticleEsDao {

    /**
     * Index a document with a given id.
     *
     * @param id     the id of the document to index
     * @param source the map to index
     * @return the number of successfully indexed
     */
    int index(String id, Map<String, String> source);

    /**
     * Delete a document based on the id.
     *
     * @param id the id of the document to delete
     * @return the number of successfully deleted
     */
    int deleteByPrimaryKey(String id);

    /**
     * Update a document with a given id.
     *
     * @param id     the id of the document to update
     * @param source the map to update
     * @return the number of successfully updated
     */
    int updateByPrimaryKey(String id, Map<String, String> source);

    /**
     * Search the documents by selective.
     *
     * @param tag       the tag of the documents to search
     * @param id        the base id of the documents to search
     * @param operation the operation of the documents to search
     * @param size      the size of the documents to return
     * @return the hits of the search request
     */
    SearchHits searchBySelective(String tag, long id, OperationEnum operation, int size);

    /**
     * Search the documents by the ids
     *
     * @param ids  the ids of the documents
     * @param size the size of the documents to return
     * @return the hits of the search request
     */
    SearchHits searchByIds(List<Long> ids, int size);

    /**
     * Search the article unique keys by date and tag.
     * Latest 2000 size and order by unique key asc.
     *
     * @param date     the date of the article unique keys to search
     * @param tag      the tag of the article unique keys to search
     * @param dateEnum the date enum
     * @return the article unique key list
     */
    List<Long> searchByDateAndTag(String date, String tag, DateEnum dateEnum);

    /**
     * Search the documents by tag and id desc.
     *
     * @param tag  the tag of the article document to search
     * @param size the limit size
     * @return the hits of the search request
     */
    SearchHits searchByTagAndIdDesc(String tag, int size);

    /**
     * Search the documents by the specified field in the period.
     *
     * @param fieldName  the field name
     * @param fieldValue the field value
     * @param articleId  except the articleId
     * @param size       the limit size
     * @return the article unique key list
     */
    List<Long> searchByFieldInPeriod(String fieldName, Object fieldValue, long articleId, int size);

    /**
     * Search the documents by authors' ids.
     *
     * @param id          the base id of the documents to search
     * @param authorIdSet the authors' ids of the documents to search
     * @param size        the size of the documents to return
     * @return the hits of the search request
     */
    SearchHits searchByAuthorIds(long id, Set<Integer> authorIdSet, int size);



}
