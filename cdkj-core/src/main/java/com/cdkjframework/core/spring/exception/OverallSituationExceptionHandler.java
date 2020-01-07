package com.cdkjframework.core.spring.exception;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.exceptions
 * @ClassName: OverallSituationExceptionHandler
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@SuppressWarnings("all")
@ControllerAdvice
public class OverallSituationExceptionHandler {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(OverallSituationExceptionHandler.class);

    /**
     * 声明要捕获的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseBuilder defultExcepitonHandler(Exception e) {
        ResponseBuilder builder = ResponseBuilder.failBuilder();
        Map<String, Object> params = new HashMap<>(10);
        params.put("error", e.getMessage());
        builder.setData(params);

        logUtil.error(e.getCause(), JsonUtils.objectToJsonString(params));

        return builder;
    }

    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public ResponseBuilder GlobalException(GlobalException e) {
        ResponseBuilder builder = ResponseBuilder.failBuilder();
        builder.setMessage(e.getMessage());
        Map<String, Object> params = new HashMap<>(10);
        params.put("error", e.getMessage());

        logUtil.error(e.getCause(), JsonUtils.objectToJsonString(params));

        builder.setData(params);
        return builder;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseBuilder MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ResponseBuilder builder = ResponseBuilder.failBuilder();

        BindingResult bindingResult = e.getBindingResult();
        List<String> errorList = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            if (!CollectionUtils.isEmpty(errors)) {
                // 只获取第一个异常信息
                builder.setMessage(errors.get(0).getDefaultMessage());
                for (FieldError error : errors) {
                    errorList.add(error.getDefaultMessage());
                }
            }
        }

        logUtil.error(e.getCause(), String.join(";", errorList));

        builder.setData(errorList);
        return builder;
    }

    /**
     * hibernate 验证异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseBuilder constraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getMessage();
        Integer begin = message.indexOf(":") + 1;
        Integer end = message.indexOf(",");
        if (end > begin) {
            message = message.substring(begin, end);
        } else {
            message = message.substring(begin);
        }
        logUtil.error(e.getCause(), message);
        return ResponseBuilder.failBuilder(message);
    }

    /**
     * 文件大小超过最大限制
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public ResponseBuilder sizeLimitExceededExceptionExceptionHandler(MaxUploadSizeExceededException e) {
        Long size = Long.valueOf(1024);
        Long fileSizeM = size / (1024 * 1024L);
        String info = String.format("文件请勿超过%sM", fileSizeM);
        logUtil.error(e.getCause(), info);

        return ResponseBuilder.failBuilder(info);
    }
}
