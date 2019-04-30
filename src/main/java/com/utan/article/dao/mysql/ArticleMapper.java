package com.utan.article.dao.mysql;

import com.utan.article.domain.DO.ArticleDO;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

@Mapper
public interface ArticleMapper {
    /**
     * Insert.
     *
     * @param articleDO the article do
     * @return the number to insert successfully
     */
    int insert(ArticleDO articleDO);

    /**
     * Delete.
     *
     * @param id the unique key of the article do
     * @return the number to delete successfully
     */
    int deleteByPrimaryKey(Long id);

    /**
     * Update.
     *
     * @param articleDO the article do
     * @return the number to update successfully
     */
    int updateByPrimaryKeySelective(ArticleDO articleDO);

    /**
     * Update label.
     *
     * @param id    the unique key of the article do
     * @param label the label of the article do
     * @return
     */
    int updateLabelByPrimaryKey(@Param("id") Long id, @Param("label") String label);

    /**
     * Select the id of the author.
     *
     * @param id the unique key of the article do
     * @return the id of the author
     */
    int selectAuthorIdByPrimaryKey(Long id);
}
