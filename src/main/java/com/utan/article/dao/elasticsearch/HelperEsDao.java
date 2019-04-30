package com.utan.article.dao.elasticsearch;

public interface HelperEsDao {
    /**
     * Search the avatar by the id of the author.
     *
     * @param authorId the id of the author
     * @return the avatar
     */
    String searchAvatarByAuthorId(int authorId);
}
