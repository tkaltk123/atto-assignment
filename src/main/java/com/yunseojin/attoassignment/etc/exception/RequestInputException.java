package com.yunseojin.attoassignment.etc.exception;


import com.yunseojin.attoassignment.etc.enums.ErrorMessage;

public class RequestInputException extends BaseException {

    public RequestInputException(String className, ErrorMessage errorMessage) {

        super(className, errorMessage);
    }

    public RequestInputException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
