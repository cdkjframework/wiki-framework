package com.cdkjframework.exceptions;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.enums.ResponseBuilderEnum;
import com.cdkjframework.util.log.LogUtils;
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
        ResponseBuilder ResponseBuilder = new ResponseBuilder();
        ResponseBuilder.setCode(ResponseBuilderEnum.Error.getValue());
        ResponseBuilder.setMessage(ResponseBuilderEnum.Error.getName());
        Map<String, Object> params = new HashMap<>(10);
        params.put("error", e.getMessage());
        ResponseBuilder.setData(params);
        return ResponseBuilder;
    }

    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public ResponseBuilder GlobalException(GlobalException e) {
        ResponseBuilder ResponseBuilder = new ResponseBuilder();
        ResponseBuilder.setCode(ResponseBuilderEnum.Error.getValue());
        ResponseBuilder.setMessage(e.getMessage());
        Map<String, Object> params = new HashMap<>(10);
        params.put("error", e.getMessage());
        ResponseBuilder.setData(params);
        return ResponseBuilder;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseBuilder MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ResponseBuilder builder = new ResponseBuilder();
        builder.setCode(ResponseBuilderEnum.Error.getValue());

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
        ResponseBuilder builder = new ResponseBuilder();
        builder.setCode(ResponseBuilderEnum.Error.getValue());
        String message = e.getMessage();
        Integer begin = message.indexOf(":") + 1;
        Integer end = message.indexOf(",");
        if (end > begin) {
            message = message.substring(begin, end);
        } else {
            message = message.substring(begin);
        }
        builder.setMessage(message);
        logUtil.info(e, "the business has verify info!!!!");
        return builder;
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
        ResponseBuilder builder = new ResponseBuilder();
        builder.setCode(ResponseBuilderEnum.Error.getValue());
        Long size = Long.valueOf(1024);
        Long fileSizeM = size / (1024 * 1024L);
        String info = String.format("文件请勿超过%sM", fileSizeM);
        builder.setMessage(info);
        logUtil.info(info);
        return builder;
    }
}
