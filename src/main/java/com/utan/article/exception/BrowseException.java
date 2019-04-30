package com.utan.article.exception;

import com.utan.article.result.ResultInfoEnum;

public class BrowseException extends ErrorInfoException{
    /**
     * Constructs a BrowseArticlesException with no detail message.
     */
    public BrowseException() {
        super();
    }

    /**
     * Constructs an ErrorInfoException with result info enum.
     *
     * @param errorInfo
     */
    public BrowseException(ResultInfoEnum errorInfo) {
        super(errorInfo);
    }
}
