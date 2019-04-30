package com.utan.article.dao.redis;

import com.utan.article.entity.UserInfo;

import java.util.List;
import java.util.Map;

public interface RedisCacheDao {
    /**
     * Get the user.
     *
     * @param userId userId
     * @param mac    mac
     * @param key    userId or mac
     * @return
     */
    UserInfo getUser(long userId, String mac, String key);

    /**
     * Update the user.
     *
     * @param key  userId or mac
     * @param user user info
     * @return
     */
    UserInfo updateUser(String key, UserInfo user);

    /**
     * Clear the users.
     */
    void clearUsers();

    /**
     * Get historical articles by time.
     *
     * @param hour the hour of the day
     * @param tag  the tag of the articles
     * @return the init articles
     */
    List<Map<String, Object>> getInitArticlesByTime(int hour, String tag);

    /**
     * Get historical articles by last id.
     *
     * @param tag    the tag of the articles
     * @param lastId the last id of the previous articles
     * @param size   the limit size
     * @return the history articles
     */
    List<Map<String, Object>> getHistoryArticlesByLastId(String tag, long lastId, int size);

    /**
     * Add the specified article's unique key to the end of the today's specified category article list.
     *
     * @param category the specified category
     * @param id       the specified article's unique key
     */
    void pushTodayArticleList(String category, Long id);

    /**
     * Push the specified articles' unique keys to the end of the today's specified category article list.
     *
     * @param category the specified category
     * @param list     the specified articles' unique keys
     */
    void pushTodayArticleList(String category, List<Long> list);

    /**
     * Remove all of the articles' unique keys from the today's specified category article list.
     *
     * @param category the specified category
     */
    void clearTodayArticleList(String category);

    /**
     * Get the today's specified category article list.
     *
     * @param category the specified category
     * @return the today's specified category article list
     */
    List<Long> getTodayArticleList(String category);

    /**
     * Push the specified article's unique key to the end of the editor's recommended list.
     *
     * @param id the article's unique key to be pushed to this list
     */
    void pushEditRecList(Long id);

    /**
     * Remove all of the articles' unique keys from the editor's recommended list.
     */
    void clearEditRecList();

    /**
     * Get the editor's recommend article list.
     *
     * @return the editor's recommend article list
     */
    List<Long> getEditRecList();

    /**
     * Put the author to be updated name.
     *
     * @param authorId   the id of the author
     * @param authorName the name to be updated
     */
    void putUpdateAuthor(String authorId, String authorName);

    /**
     * Get the authors to be updated name.
     *
     * @return the authors to be updated name
     */
    Map<String, String> getUpdateAuthors();
}
