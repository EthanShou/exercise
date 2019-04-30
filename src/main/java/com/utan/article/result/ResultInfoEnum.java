package com.utan.article.result;

public enum ResultInfoEnum {
    /** Success */
    SUCCESS(0, "success"),

    // Client Error
    /** Parameter error */
    PARAMS_ERROR(600, "parameter error"),

    // Server Error
    /** System exception */
    SYSTEM_EXCEPTION(700, "system exception"),

    /** No lastest articles */
    NO_LATEST_ARTICLES(1001, "no latest articles"),

    /** No oldest articles */
    NO_OLDEST_ARTICLES(1002, "no oldest articles"),

    /** No recommend articles */
    NO_RECOMMEND_ARTICLES(1003, "no recommend articles"),

    /** No similar articles */
    NO_SIMILAR_ARTICLES(1004, "no similar articles"),

    /** No subscribe articles */
    NO_SUBSCRIBE_ARTICLES(1005, "no subscribe articles"),

    /** No hot articles */
    NO_HOT_ARTICLES(1006, "no hot articles");

    /** Response code */
    private final Integer code;

    /** Response message */
    private final String msg;

    ResultInfoEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
