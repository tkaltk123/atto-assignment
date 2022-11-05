package com.yunseojin.attoassignment.etc.enums;

import org.springframework.http.HttpStatus;

public enum ErrorMessage {
    UNDEFINED_EXCEPTION(0, "정의되지 않은 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR)
    ,NULL_POINTER_EXCEPTION(1, "NULL 여부를 확인해주세요.", HttpStatus.BAD_REQUEST)
    ,VALIDATION_FAIL_EXCEPTION(2, "입력값의 조건이 올바르지 않습니다.", HttpStatus.BAD_REQUEST)
    ,DUPLICATED_HOST_NAME_EXCEPTION(3, "이미 존재하는 호스트 이름입니다.", HttpStatus.CONFLICT)
    ,DUPLICATED_HOST_IP_EXCEPTION(4, "이미 존재하는 호스트 IP 입니다.", HttpStatus.CONFLICT)
    ,LIMIT_OVER_HOST_EXCEPTION(5, "더 이상 호스트를 추가할 수 없습니다.", HttpStatus.BAD_REQUEST)
    ,NOT_EXISTS_HOST_EXCEPTION(6, "존재하지 않는 호스트 입니다.", HttpStatus.FORBIDDEN)
    ;

    Integer code;
    String errorMessage;
    HttpStatus httpStatus;

    ErrorMessage(int code, String errorMessage, HttpStatus httpStatus) {
        this.code = code;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }


    public Integer getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
