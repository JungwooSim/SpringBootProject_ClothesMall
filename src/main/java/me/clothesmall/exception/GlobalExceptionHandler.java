package me.clothesmall.exception;

import me.clothesmall.dto.common.ApiResponseTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseTemplate> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity.ok().body(
                ApiResponseTemplate.builder().code(HttpStatus.BAD_REQUEST.value()).message(exception.getBindingResult().getFieldError().getDefaultMessage()).build()
        );
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseTemplate> businessException(BusinessException exception) {
        return ResponseEntity.ok().body(
                ApiResponseTemplate.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(exception.getMessage())
                        .build()
        );
    }
}
