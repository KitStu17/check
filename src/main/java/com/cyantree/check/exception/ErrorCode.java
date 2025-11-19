package com.cyantree.check.exception;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 4xx Client Errors
    BAD_REQUEST(400, "잘못된 요청입니다."),
    UNAUTHORIZED(401, "인증이 필요합니다."),
    FORBIDDEN(403, "접근이 금지되었습니다."),
    NOT_FOUND(404, "리소스를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(405, "허용되지 않은 메서드입니다."),
    REQUEST_TIMEOUT(408, "요청 시간이 초과되었습니다."),
    CONFLICT(409, "리소스 충돌이 발생했습니다."),
    UNSUPPORTED_MEDIA_TYPE(415, "지원하지 않는 형식의 요청입니다."),
    UNPROCESSABLE_ENTITY(422, "처리할 수 없는 요청입니다."),

    // 5xx Server Errors
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다."),
    SERVICE_UNAVAILABLE(503, "서비스를 사용할 수 없습니다."),

    // Business Errors (4xxx)
    EXTENSION_ALREADY_EXISTS(4001, "이미 존재하는 확장자입니다."),
    EXTENSION_LIMIT_EXCEEDED(4002, "커스텀 확장자는 최대 200개까지 추가 가능합니다."),
    EXTENSION_NAME_TOO_LONG(4003, "확장자명은 최대 20자까지 입력 가능합니다."),
    EXTENSION_NOT_FOUND(4004, "해당 확장자를 찾을 수 없습니다."),
    INVALID_EXTENSION_NAME(4005, "유효하지 않은 확장자명입니다."),

    // General Business Logic Errors
    UNEXPECTED_ERROR(9999, "예상치 못한 오류가 발생했습니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 에러 코드로 메시지 조회
     */
    public static String getMessage(int code) {
        return Arrays.stream(ErrorCode.values())
                .filter(errorCode -> errorCode.getCode() == code)
                .findFirst()
                .map(ErrorCode::getMessage)
                .orElse("Unknown error code: " + code);
    }

    /**
     * 에러 코드로 ErrorCode 조회
     */
    public static ErrorCode fromCode(int code) {
        return Arrays.stream(ErrorCode.values())
                .filter(errorCode -> errorCode.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown error code: " + code));
    }
}
