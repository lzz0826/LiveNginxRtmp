package org.example.advice;

import org.example.common.BaseResp;
import org.example.common.StatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<BaseResp<?>> handleBindException(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            return ResponseEntity.badRequest().body(BaseResp.fail(fieldName + ": " + errorMessage, StatusCode.SystemError));
        } else {
            // 如果没有错误，返回其他适当的响应
            return ResponseEntity.badRequest().body(BaseResp.fail("绑定参数验证失败", StatusCode.ValidationError));
        }
    }
}
