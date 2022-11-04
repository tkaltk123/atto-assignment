package com.yunseojin.attoassignment.etc.config;

import com.yunseojin.attoassignment.etc.enums.ErrorMessage;
import com.yunseojin.attoassignment.etc.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<BaseException> defaultException(Throwable e, HandlerMethod handlerMethod) throws IOException {

        BaseException baseException;

        //시스템 정의 예외인 경우
        if (e instanceof BaseException) {

            ((BaseException) e).setErrorTrace(e.getStackTrace()[0].toString());
            baseException = (BaseException) e;
        }
        //Validation 예외인 경우
        else if (e instanceof BindException) {
            baseException = convertBindToBase((BindException) e);
        } else if (e instanceof ValidationException) {

            baseException = new BaseException(e.getClass().getSimpleName(), ErrorMessage.VALIDATION_FAIL_EXCEPTION);
            baseException.setErrorMessage(e.getMessage());
            baseException.setErrorTrace(e.getStackTrace()[0].toString());
        }
        //정의되지 않은 예외인 경우
        else {

            baseException = new BaseException(e.getClass().getSimpleName(), ErrorMessage.UNDEFINED_EXCEPTION);
            baseException.setErrorMessage(e.getMessage());
            baseException.setErrorTrace(e.getStackTrace()[0].toString());
        }

        log.error(baseException.getErrorMessage(), e);

        return new ResponseEntity<>(baseException, baseException.getHttpStatus());
    }

    private BaseException convertBindToBase(BindException e) {

        BaseException baseException = new BaseException(e.getClass().getSimpleName(), ErrorMessage.VALIDATION_FAIL_EXCEPTION);
        List<ObjectError> messageList = e.getAllErrors();
        StringBuilder message = new StringBuilder();
        for (ObjectError objectError : messageList) {

            String validationMessage = objectError.getDefaultMessage();
            message.append("[").append(validationMessage).append("]");
        }
        baseException.setErrorMessage(message.toString());
        baseException.setErrorTrace(e.getStackTrace()[0].toString());

        return baseException;
    }
}
