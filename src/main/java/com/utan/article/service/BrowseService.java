package com.utan.article.service;

import com.utan.article.domain.DTO.BrowseDTO;
import com.utan.article.domain.DTO.SubscribeDTO;

import java.util.List;
import java.util.Map;

public interface BrowseService {
    /**
     * Browse the articles.
     *
     * @param browseDTO the browse dto
     * @return the articles to browse
     */
    List<Map<String, Object>> browse(BrowseDTO browseDTO);

    /**
     * Subscribe the articles.
     *
     * @param subscribeDTO the subscribe dto
     * @return the articles to subscribe
     */
    List<Map<String, Object>> subscribe(SubscribeDTO subscribeDTO);

    /**
     * Get the hot articles.
     *
     * @param label the label of the hot
     * @return the articles to label hot
     */
    List<Map<String, Object>> hot(String label);

    /**
     * Build init articles cache.
     *
     * @param tag  the tag of the articles
     * @param size the limit size
     * @return the init category article's list
     */
    List<Map<String, Object>> buildInitCache(String tag, int size);

    /**
     * Build history articles cache.
     *
     * @param tag    the tag of the articles.
     * @param lastId the last id of the previous articles
     * @param size   the limit size
     * @return the history category article's list
     */
    List<Map<String, Object>> buildHistoryCache(String tag, long lastId, int size);
}
