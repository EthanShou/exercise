package com.utan.article.dao.mysql;

import com.utan.article.domain.DO.CategoryDO;
import com.utan.article.domain.DO.LoveDO;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface HelpMapper {
    /**
     * Select the number of love and base weight by the article id.
     *
     * @param id the id of the article
     * @return the number of love and base weight
     */
    LoveDO selectLoveNumByArticleId(Long id);

    /**
     * Select the number of comment by the article id.
     *
     * @param id the id of the article
     * @return the number of comment
     */
    int selectCommentNumByArticleId(Long id);

    /**
     * Select the avatar by the id of the author.
     *
     * @param id the id of the author
     * @return the avatar of the author
     */
    String selectAvatarByAuthorId(Integer id);

    /**
     * Select the category list by the app name.
     *
     * @param appName the app name
     * @return the category list
     */
    List<CategoryDO> selectCategoryByAppName(String appName);

    /**
     * Select the recommend articles' unique keys by the article id.
     *
     * @param id the id of the article
     * @return the recommend articles' unique keys
     */
    String selectRecommendByArticleId(Long id);

    /**
     * Select the subscribe authors' ids by the user id.
     *
     * @param id the user id
     * @return the subscribe authors' ids
     */
    Set<Integer> selectSubscribeAuthorIdByUserId(Integer id);

    /**
     * Select all the hot article category.
     *
     * @return the hot article category
     */
    List<String> selectHotArticleCategory();

    /**
     * Select the hot article category by the user id.
     *
     * @param label the label of hot
     * @return the hot article category
     */
    String selectHotArticleCategoryByLabel(String label);

    /**
     * Select all the hot video category.
     *
     * @return the hot video category
     */
    List<String> selectHotVideoCategory();

    /**
     * Select the hot video category by the user id.
     *
     * @param label the label of hot
     * @return the hot video category
     */
    String selectHotVideoCategoryByLabel(String label);

    /**
     * Update the number of love.
     *
     * @param articleId the id of the article
     * @param loveNum   the number of love to updated
     * @return the number of successfully updated
     */
    int updateLoveNumByArticleId(@Param("articleId") long articleId, @Param("loveNum") int loveNum);

    /**
     * Update the name of the author by the author id.
     *
     * @param authorId   the id of the author
     * @param authorName the name of the author to updated
     * @return
     */
    int updateAuthorNameByAuthorId(@Param("authorId") Integer authorId, @Param("authorName") String authorName);
}
