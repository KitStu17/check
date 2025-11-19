package com.cyantree.check.exception;

import java.net.SocketTimeoutException;
import java.net.URI;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler implements ProblemHandling {

    @Override
    public ProblemBuilder prepare(Throwable throwable, StatusType status, URI type) {
        if(throwable instanceof CustomException customException) {
            return Problem.builder()
                .withTitle(customException.getErrorTitle())
                .withStatus(status)
                .withDetail(customException.getMessage())
                .withType(type);
        }

        return Problem.builder()
            .withTitle(ErrorCode.INTERNAL_SERVER_ERROR.getMessage())
            .withStatus(status)
            .withDetail(throwable.getMessage())
            .withType(type);
    }

    @Override
    public ResponseEntity<Problem> handleThrowable(Throwable throwable, NativeWebRequest request) {
        log.error("Unhandled exception occurred", throwable);
        
        if(throwable instanceof CustomException customException) {
            return createProblemResponse(request, customException);
        }
        
        CustomException exception = new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, throwable.getMessage());
        return createProblemResponse(request, exception);
    }

    @Override
    public ResponseEntity<Problem> handleMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException exception, NativeWebRequest request) {
        log.warn("Unsupported media type: {}", exception.getMessage());
        CustomException customException = new CustomException(ErrorCode.UNSUPPORTED_MEDIA_TYPE, exception.getMessage());
        return createProblemResponse(request, customException);
    }

    @Override
    public ResponseEntity<Problem> handleTypeMismatch(TypeMismatchException exception, NativeWebRequest request) {
        log.warn("Type mismatch: {}", exception.getMessage());
        CustomException customException = new CustomException(ErrorCode.BAD_REQUEST, "알맞은 형식의 요청이 아닙니다.");
        return createProblemResponse(request, customException);
    }

    @Override
    public ResponseEntity<Problem> handleSocketTimeout(SocketTimeoutException exception, NativeWebRequest request) {
        log.warn("Socket timeout: {}", exception.getMessage());
        CustomException customException = new CustomException(ErrorCode.REQUEST_TIMEOUT, "타임 아웃이 발생했습니다.");
        return createProblemResponse(request, customException);
    }

    @Override
    public ResponseEntity<Problem> handleMissingServletRequestParameter(
            MissingServletRequestParameterException exception, NativeWebRequest request) {
        log.warn("Missing request parameter: {}", exception.getParameterName());
        CustomException customException = new CustomException(ErrorCode.BAD_REQUEST, "누락된 파라미터가 존재합니다.");
        return createProblemResponse(request, customException);
    }

    @Override
    public ResponseEntity<Problem> handleMissingServletRequestPart(
            MissingServletRequestPartException exception, NativeWebRequest request) {
        log.warn("Missing request part: {}", exception.getRequestPartName());
        CustomException customException = new CustomException(ErrorCode.BAD_REQUEST, "필수 요청이 누락되었습니다.");
        return createProblemResponse(request, customException);
    }

    // 공통 응답 생성 메서드
    private ResponseEntity<Problem> createProblemResponse(NativeWebRequest request, CustomException customException) {
        HttpServletRequest servletRequest = (HttpServletRequest) request.getNativeRequest();
        String uri = servletRequest.getRequestURI();
        
        Problem problem = Problem.builder()
                .withTitle(customException.getErrorTitle())
                .withDetail(customException.getMessage())
                .withType(URI.create(uri))
                .build();
                
        return ResponseEntity.status(customException.getStatusCode()).body(problem);
    }
}
