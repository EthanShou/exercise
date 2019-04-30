package com.utan.article.exception;

import com.utan.article.result.ResultInfoEnum;

import javax.xml.transform.Result;

public class ErrorInfoException extends RuntimeException{
    private ResultInfoEnum errorInfo;

    /**
     * Constructs an ErrorInfoException with no detail message.
     */
    public ErrorInfoException() {
        super();
    }

    /**
     * Constructs an ErrorInfoException with result info enum.
     *
     * @param errorInfo
     */
    public ErrorInfoException(ResultInfoEnum errorInfo) {
        super(errorInfo.getMsg());
        this.errorInfo = errorInfo;
    }

    public ResultInfoEnum getErrorInfo() {
        return errorInfo;
    }

}
