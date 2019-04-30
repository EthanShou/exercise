package com.utan.article.constant;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class EsConsts {
    /**
     * Article Index name.
     */
    public static final String ARTICLE_INDEX = "baby_crawler";

    /**
     * Article Type name.
     */
    public static final String ARTICLE_TYPE = "baby_crawler";

    /**
     * User Index name.
     */
    public static final String USER_INDEX = "toutiao_user_info";

    /**
     * User Type name.
     */
    public static final String USER_TYPE = "toutiao_user_info";

    /**
     * Unique key field.
     */
    public static final String UNIQUEKEY_FIELD = "uniquekey";

    /**
     * Tag field.
     */
    public static final String TAG_FIELD = "tag.keyword";

    /**
     * State field.
     */
    public static final String STATE_FIELD = "state";

    /**
     * Flag field.
     */
    public static final String FLAG_FIELD = "flag";

    /**
     * Year field.
     */
    public static final String YEAR_FIELD = "year";

    /**
     * Month field.
     */
    public static final String MONTH_FIELD = "month";

    /**
     * Day field.
     */
    public static final String DAY_FIELD = "day";

    /**
     * Video field.
     */
    public static final String VIDEO_FIELD = "isvideo";

    /**
     * Author field.
     */
    public static final String AUTHOR_FIELD = "authorid";

    /**
     * Time field.
     */
    public static final String TIME_FIELD = "time";

    /**
     * User id field.
     */
    public static final String USER_ID_FIELD = "user_id";

    /**
     * Avatar field.
     */
    public static final String AVATAR_FIELD = "avatar";

    /**
     * Must query builders.
     */
    public static final QueryBuilder STATE_QUERY = QueryBuilders.termQuery(STATE_FIELD, 0);
    public static final QueryBuilder FLAG_QUERY = QueryBuilders.termQuery(FLAG_FIELD, 0);

    /**
     * Video query builder.
     */
    public static final QueryBuilder TAG_VIDEO_QUERY = QueryBuilders.termQuery(VIDEO_FIELD, 1);

    /**
     * Return fields.
     */
    public static final String[] RETURN_FIELDS = new String[]{"uniquekey", "title", "picurl", "picurlthumbnail",
            "time", "utantag", "netorg", "flag", "videourl", "isvideo", "authorid", "publishtime", "state"};

    /**
     * Today Return size.
     */
    public static final int TODAY_RETURN_SIZE = 8000;

    /**
     * Yesterday Return size.
     */
    public static final int YESTERDAY_RETURN_SIZE = 2000;
}
