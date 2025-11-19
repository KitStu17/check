package com.cyantree.check.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CustomException extends RuntimeException {
    private static final String ERROR_TITLE = "%d : %s";

    private final ErrorCode code;
    private final String message;

    public CustomException(ErrorCode errorCode) {
        this.code = errorCode;
        this.message = errorCode.getMessage();
    }

    public CustomException(ErrorCode errorCode, String message) {
        this.code = errorCode;
        this.message = message;
    }

    public String getErrorTitle() {
        return String.format(ERROR_TITLE, this.code.getCode(), this.message);
    }

    public HttpStatus getStatusCode() {
        int codeValue = this.code.getCode();
        
        // HTTP 표준 상태 코드 범위 (400-599)
        if (codeValue >= 400 && codeValue < 600) {
            try {
                return HttpStatus.valueOf(codeValue);
            } catch (IllegalArgumentException e) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }

        // 비즈니스 에러 코드 (4xxx 대)
        if (codeValue >= 4000 && codeValue < 5000) {
            return HttpStatus.BAD_REQUEST;
        }
        
        // 비즈니스 에러 코드 (5xxx 대)
        if (codeValue >= 5000 && codeValue < 6000) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        
        // 기본값
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
