package com.utan.article.entity;

//import com.utan.article.constant.ArticleConsts;
//import com.utan.article.constant.BaseConsts;
//import com.utan.article.manage.MsgManage;
//import com.utan.article.util.JdbcUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User Info.
 *
 * @author Joker
 */
public class UserInfo implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfo.class);

    /**
     * Segment number.
     */
    private static final int SEGMENT_NUM = 50;
    /**
     * Segment inter.
     */
    private static final int SEGMENT_INTER = 40;
    /**
     * Favor recommend number.
     */
    private static final int FAVOR_REC_NUM = 3;
    /**
     * Editor recommend number.
     */
    private static final int EDIT_REC_NUM = 2;
    /**
     * Default recommend category array.
     */
    private static final String[] DEFAULT_RECOMMEND_CATEGORY = {"娱乐", "美食", "时尚", "旅游", "情感", "观点", "新闻", "微视频"};
    /**
     * Default recommend category size.
     */
    private static final int CATEGORY_NUM = DEFAULT_RECOMMEND_CATEGORY.length;

    /**
     * Unique mark.
     */
    private String key;
    /**
     * User id.
     */
    private long userId;
    /**
     * Mac address.
     */
    private String mac;
    /**
     * Love category array.
     */
    private String[] loveCategory = new String[CATEGORY_NUM];
    /**
     * Recommend number array.
     */
    private int[] recommendNum = new int[CATEGORY_NUM];
    /**
     * Recommend matrix.
     */
    private int[][] recommendMatrix = new int[CATEGORY_NUM][SEGMENT_NUM];
    /**
     * Whether to reset the recommendation.
     */
    private boolean recommendReset = true;
    /**
     * Whether to reload the recommend list.
     */
    private boolean recommendReload = true;
    /**
     * Whether to take the history cache.
     */
    private boolean historyCache = true;
    /**
     * Favor recommend list.
     */
    private List<Long> favorList;
    /**
     * Edit recommend cursor.
     */
    private int editRecCursor;
    /**
     * History recommend cursor.
     */
    private int[] historyRecCursor = new int[CATEGORY_NUM];

    public UserInfo() {
    }

    public UserInfo(Long userId, String mac, String key) {
        this.userId = userId;
        this.mac = mac;
        this.key = key;
    }

//    /**
//     * Init recommend data.
//     */
//    private void initRecommend() {
//        for (int i = 0; i < CATEGORY_NUM; i++) {
//            // init love category
//            loveCategory[i] = DEFAULT_RECOMMEND_CATEGORY[i];
//            // init recommend number
//            recommendNum[i] = ArticleConsts.CATEGORY_ENTERTAINMENT.equals(loveCategory[i]) ? 2 : 1;
//        }
//
//        // init love matrix
//        for (int i = 0; i < loveCategory.length; i++) {
//            for (int j = 0; j < SEGMENT_NUM; j++) {
//                recommendMatrix[i][j] = SEGMENT_INTER * j;
//            }
//        }
//
//        // init history recommend cursor
//        for (int i = 0; i < loveCategory.length; i++) {
//            historyRecCursor[i] = MsgManage.getYesterdayArticleListSize(loveCategory[i]);
//        }
//    }
//
//    /**
//     * Get the recommend article's list.
//     *
//     * @return the recommend article's list
//     */
//    public List<Long> recommend() {
//        if (recommendReset) {
//            LOGGER.info("userId={}-->今日首次浏览,初始化推荐数据", userId == 0 ? mac : userId);
//            initRecommend();
//            recommendReset = false;
//        }
//
//        if (recommendReload && userId != 0) {
//            LOGGER.info("userId={}-->推荐列表发生更新", userId);
//            favorList = listFavor(userId);
//            recommendReload = false;
//        }
//
//        List<Long> recommendList = new ArrayList<>();
//
//        // recommend according to favor
//        List<Long> favorRecList = listFavorRec();
//        if (favorRecList != null && favorRecList.size() > 0) {
//            recommendList.addAll(favorRecList);
//        }
//
//        // recommend according to editor
//        List<Long> editRecList = listEditRec();
//        if (editRecList != null && editRecList.size() > 0) {
//            recommendList.addAll(editRecList);
//        }
//
//        // recommend according to love category
//        for (int i = 0; i < loveCategory.length; i++) {
//            String categoryName = loveCategory[i];
//            int categoryRecNum = recommendNum[i];
//
//            List<Long> todayArticleList = MsgManage.getTodayArticleList(categoryName);
//            int todaySize = todayArticleList.size();
//            if (todayArticleList != null && todaySize > 0) {
//                // choose the segment
//                int segment = (todaySize - 1) / SEGMENT_INTER;
//                // if this segment more than SEGMENT_NUM, take data from the last segment
//                if (segment > SEGMENT_NUM - 1) {
//                    segment = SEGMENT_NUM - 1;
//                }
//                int endCursor = todaySize;
//
//                // take data from this segment, if done, move forward
//                outer:
//                for (int j = segment; j >= 0; j--) {
//                    int startCursor = recommendMatrix[i][j];
//
//                    // take data from start cursor to end cursor of this segment
//                    inner:
//                    for (int k = startCursor; k < endCursor; k++) {
//                        try {
//                            recommendList.add(todayArticleList.get(k));
//                            categoryRecNum--;
//                            recommendMatrix[i][j] = k + 1;
//                        } catch (IndexOutOfBoundsException e) {
//                            // 0 am today category article list's get and clear methods may exist concurrent
//                            // the next love category recommend, today category article list may have content
//                            break outer;
//                        }
//
//                        // the size between start cursor and end cursor meet the category recommend number
//                        if (categoryRecNum == 0) {
//                            break outer;
//                        }
//                    }
//
//                    endCursor = j * SEGMENT_INTER;
//                }
//            }
//
//            // if the category recommend number is not reach, recommend yesterday's article
//            if (categoryRecNum > 0) {
//                List<Long> yesterdayArticleList = MsgManage.getYesterdayArticleList(categoryName);
//                int yesterdaySize = yesterdayArticleList.size();
//                // if the yesterday category article list's size is 0 or is out
//                if (yesterdaySize == 0 || historyRecCursor[i] < 0) {
//                    continue;
//                }
//
//                for (int m = 0; m < categoryRecNum; m++) {
//                    try {
//                        recommendList.add(yesterdayArticleList.get(--historyRecCursor[i]));
//                    } catch (IndexOutOfBoundsException e) {
//                        // 0 am yesterday category article list may have different size
//                        continue;
//                    }
//                }
//            }
//        }
//
//        return recommendList;
//    }
//
//    /**
//     * Get the favor article list of the user.
//     *
//     * @param userId the id of the user
//     * @return the favor article list
//     */
//    private List<Long> listFavor(long userId) {
//        String favorStr = JdbcUtils.selectRecListByUserId(userId);
//        if (StringUtils.isBlank(favorStr)) {
//            return Collections.EMPTY_LIST;
//        }
//
//        String[] favorArr = favorStr.split(BaseConsts.COMMA_STR);
//        List<Long> favorList = new ArrayList<>(favorArr.length);
//        for (String favor : favorArr) {
//            favorList.add(Long.parseLong(favor.trim()));
//        }
//
//        return favorList;
//    }
//
//    /**
//     * Get the favor's recommend article list.
//     *
//     * @return the favor's recommend article list
//     */
//    private List<Long> listFavorRec() {
//        if (favorList == null || favorList.size() == 0) {
//            return Collections.EMPTY_LIST;
//        }
//
//        List<Long> recommendList = new ArrayList<>(FAVOR_REC_NUM);
//
//        for (int i = 0; i < FAVOR_REC_NUM; i++) {
//            try {
//                recommendList.add(favorList.remove(favorList.size() - 1));
//            } catch (IndexOutOfBoundsException e) {
//                break;
//            }
//        }
//
//        return recommendList;
//    }
//
//    /**
//     * Get the editor's recommend article list.
//     *
//     * @return the editor's recommend article list
//     */
//    private List<Long> listEditRec() {
//        int size = MsgManage.getEditRecListSize();
//        // if the editor's recommend list's size is 0 or is out
//        if (size == 0 || editRecCursor == size) {
//            return Collections.EMPTY_LIST;
//        }
//
//        List<Long> recommendList = new ArrayList<>();
//
//        for (int i = 0; i < EDIT_REC_NUM; i++) {
//            try {
//                recommendList.add(MsgManage.getFromEditRecList(editRecCursor++));
//            } catch (IndexOutOfBoundsException e) {
//                // 0 am EDIT_REC_LIST's get and clear methods may exist concurrent
//                editRecCursor--;
//                break;
//            }
//        }
//
//        return recommendList;
//    }
//
//    public void setHistoryCache(boolean historyCache) {
//        this.historyCache = historyCache;
//    }
//
//    public boolean isHistoryCache() {
//        return historyCache;
//    }

    public String getKey() {
        return key;
    }
}