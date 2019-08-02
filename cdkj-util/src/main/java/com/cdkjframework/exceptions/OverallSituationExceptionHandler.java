package com.cdkjframework.exceptions;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.util.log.LogUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

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
    private static LogUtil logUtil = LogUtil.getlogUtil(OverallSituationExceptionHandler.class);

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
        ResponseBuilder.setCode(ResponseCode.INTERNAL_ERROR.getCode());
        ResponseBuilder.setMessage(ResponseCode.INTERNAL_ERROR.getDesc());
        Map<String, Object> params = new HashMap<>(10);
        params.put("error", e.getMessage());
        ResponseBuilder.setParams(params);
        logUtil.error("the service has error!!", e);
        LogSendUtil.sendLog(new LogMessageBuilder(LahuobaoLogLevel.ERROR,"the service has error!!")
                .setException(e).builder());
        return ResponseBuilder;
    }

    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public ResponseBuilder GlobalException(GlobalException e) {
        ResponseBuilder ResponseBuilder = new ResponseBuilder();
        ResponseBuilder.setCode(ResponseCode.VALIDATE_FAIL.getCode());
        ResponseBuilder.setRemark(e.getMessage());
        Map<String, Object> params = new HashMap<>(10);
        params.put("error", e.getMessage());
        ResponseBuilder.setParams(params);
        logUtil.error("the service has error!!", e);
        LogSendUtil.sendLog(new LogMessageBuilder(LahuobaoLogLevel.ERROR,"业务异常")
                .setException(e).builder());
        return ResponseBuilder;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseBuilder MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ResponseBuilder ResponseBuilder = new ResponseBuilder();
        ResponseBuilder.setCode(ResponseCode.VALIDATE_FAIL.getCode());

        BindingResult bindingResult = e.getBindingResult();
        List<String> errorList = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            if (!CollectionUtils.isEmpty(errors)) {
                // 只获取第一个异常信息
                ResponseBuilder.setRemark(errors.get(0).getDefaultMessage());
                for (FieldError error : errors) {
                    errorList.add(error.getDefaultMessage());
                }
            }
        }
        LogSendUtil.sendLog(new LogMessageBuilder(LahuobaoLogLevel.ERROR,"参数校验异常")
                .setException(e).setProjectName("拉货宝后台管理系统").builder());
        ResponseBuilder.setResult(errorList);
        return ResponseBuilder;
    }
    /**
     * hibernate 验证异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseBuilder constraintViolationExceptionHandler(ConstraintViolationException e) {
        ResponseBuilder ResponseBuilder=new ResponseBuilder();
        ResponseBuilder.setCode(ResponseCode.VALIDATE_FAIL.getCode());
        String message = e.getMessage();
        Integer begin = message.indexOf(":")+1;
        Integer end = message.indexOf(",");
        if(end>begin){
            message= message.substring(begin,end);
        }else{
            message= message.substring(begin);
        }
        ResponseBuilder.setRemark(message);
        logUtil.info("the business has verify info!!!!",e);
        return ResponseBuilder;
    }
    /**
     * 文件大小超过最大限制
     * @param e
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public ResponseBuilder sizeLimitExceededExceptionExceptionHandler(MaxUploadSizeExceededException e){
        ResponseBuilder ResponseBuilder=new ResponseBuilder();
        ResponseBuilder.setCode(ResponseCode.VALIDATE_FAIL.getCode());
        Long size = Long.valueOf(maxFileSize);
        Long fileSizeM = size/(1024*1024L);
        String info = String.format("文件请勿超过%sM",fileSizeM);
        ResponseBuilder.setRemark(info);
        logUtil.info(info);
        LogSendUtil.sendLog(new LogMessageBuilder(LahuobaoLogLevel.INFO,info)
                .setException(e).setProjectName("拉货宝后台管理系统").builder());
        return ResponseBuilder;
    }
}
