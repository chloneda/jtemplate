package com.chloneda.exception;

import com.chloneda.beans.ResultBean;
import com.chloneda.beans.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author chloneda
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResultBean handlerBusinessException(BusinessException e) {
        log.error("自定义异常：{}", e);
        return ResultBean.failed(e.getStatusCode(), e.getMessage());
    }

    /**
     * 参数校验异常
     *
     * @param e 参数异常父类
     * @return
     */
    @ExceptionHandler(value = ValidationException.class)
    @ResponseBody
    public ResultBean validException(ValidationException e) {
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException exception = (ConstraintViolationException) e;
            List<String> list = exception.getConstraintViolations().stream().map(constraintViolation -> {
                return constraintViolation.getMessage();
            }).collect(Collectors.toList());
            return ResultBean.failed(ResultCode.FAILED, list.toString());
        }
        log.error("参数校验异常没有捕获对应子类: {}", e.getClass().getName(), e);

        return ResultBean.failed(ResultCode.FAILED, e.getMessage());
    }

    /**
     * 一般的参数绑定时候抛出的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultBean handleBindException(MethodArgumentNotValidException e) {
        log.error("参数校验异常：{}", e);
        List<String> list = e.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return ResultBean.failed(ResultCode.FAILED, list.toString());
    }

    /**
     * 兜底的全局异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public ResultBean allException(Exception e) {
        log.error("服务异常：{}", e);
        return ResultBean.failed("服务异常");
    }

}

