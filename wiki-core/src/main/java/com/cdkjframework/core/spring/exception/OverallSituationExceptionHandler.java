package com.cdkjframework.core.spring.exception;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.constant.BusinessConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.enums.ResponseBuilderEnums;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.exceptions.UserRuntimeException;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.network.http.HttpServletUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.exceptions
 * @ClassName: OverallSituationExceptionHandler
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@SuppressWarnings("all")
@ControllerAdvice
@ResponseBody
public class OverallSituationExceptionHandler {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(OverallSituationExceptionHandler.class);

    /**
     * 公共异常
     *
     * @param e 公共异常数据
     * @return 返回公共异常结果
     */
    @ExceptionHandler(GlobalException.class)
    public ResponseBuilder GlobalException(GlobalException e) {
        String message = e.getMessage();
        Integer code = ResponseBuilderEnums.Error.getValue();
        if (StringUtils.isNotNullAndEmpty(message)) {
            String[] messageList = message.split(BusinessConsts.ERROR_KEY);
            if (messageList.length == IntegerConsts.TWO) {
                code = Integer.valueOf(messageList[IntegerConsts.ZERO]);
                message = messageList[IntegerConsts.ONE];
            }
        }
        ResponseBuilder builder = ResponseBuilder.failBuilder(message);
        builder.setCode(code);
        Map<String, Object> params = new HashMap<>(IntegerConsts.ONE);
        params.put("error", message);

        builder.setData(params);
        return builder;
    }

    /**
     * 用户权限公共异常
     *
     * @param e 公共异常数据
     * @return 返回公共异常结果
     */
    @ExceptionHandler(UserRuntimeException.class)
    public ResponseBuilder UserRuntimeException(UserRuntimeException e) {
        String message = e.getMessage();
        Integer code = ResponseBuilderEnums.Authority.getValue();
        if (StringUtils.isNotNullAndEmpty(message)) {
            String[] messageList = message.split(BusinessConsts.ERROR_KEY);
            if (messageList.length == IntegerConsts.TWO) {
                code = Integer.valueOf(messageList[IntegerConsts.ZERO]);
                message = messageList[IntegerConsts.ONE];
            }
        }
        response(code);
        ResponseBuilder builder = ResponseBuilder.failBuilder(message);
        builder.setCode(code);
        Map<String, Object> params = new HashMap<>(IntegerConsts.ONE);
        params.put("error", message);
        builder.setData(params);
        return builder;
    }


    /**
     * 声明要捕获的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(GlobalRuntimeException.class)
    public ResponseBuilder defultExcepitonHandler(GlobalRuntimeException e) {
        String message = e.getMessage();
        Integer code = ResponseBuilderEnums.Error.getValue();
        if (StringUtils.isNotNullAndEmpty(message)) {
            String[] messageList = message.split(BusinessConsts.ERROR_KEY);
            if (messageList.length == IntegerConsts.TWO) {
                code = Integer.valueOf(messageList[IntegerConsts.ZERO]);
                message = messageList[IntegerConsts.ONE];
            }
        }
        ResponseBuilder builder = ResponseBuilder.failBuilder(message);
        builder.setCode(code);
        Map<String, Object> params = new HashMap<>(IntegerConsts.ONE);
        params.put("error", message);

        logUtil.error(e, JsonUtils.objectToJsonString(params));

        builder.setData(params);
        return builder;
    }

    /**
     * 声明要捕获的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseBuilder defultExcepitonHandler(Exception e) {
        ResponseBuilder builder = ResponseBuilder.failBuilder();
        Map<String, Object> params = new HashMap<>(IntegerConsts.ONE);
        params.put("error", e.getMessage());
        builder.setData(params);

        return builder;
    }

    /**
     * 声明要捕获的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public ResponseBuilder defultExcepitonHandler(Throwable e) {
        ResponseBuilder builder = ResponseBuilder.failBuilder();
        Map<String, Object> params = new HashMap<>(IntegerConsts.ONE);
        params.put("error", e.getMessage());
        builder.setData(params);

        return builder;
    }

    /**
     * 数据验证异常
     *
     * @param e 验证异常信息
     * @return 返回数据验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseBuilder MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ResponseBuilder builder = ResponseBuilder.failBuilder();
        BindingResult bindingResult = e.getBindingResult();
        List<String> errorList = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            if (!CollectionUtils.isEmpty(errors)) {
                // 只获取第一个异常信息
                builder.setMessage(errors.get(IntegerConsts.ZERO).getDefaultMessage());
                for (FieldError error : errors) {
                    errorList.add(error.getDefaultMessage());
                }
            }
        }
        builder.setData(errorList);
        return builder;
    }

    /**
     * 数据访问异常
     *
     * @param e 数据访问异常信息
     * @return 返回数据访问异常
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseBuilder MyBatisException(DataAccessException e) {
        ResponseBuilder builder = ResponseBuilder.failBuilder();

        builder.setMessage(e.getMessage());
        Map<String, Object> params = new HashMap<>(IntegerConsts.ONE);
        params.put("error", e.getMessage());

        builder.setData(params);
        return builder;
    }

    /**
     * 数据访问异常
     *
     * @param e 数据访问异常信息
     * @return 返回数据访问异常
     */
    @ExceptionHandler(MyBatisSystemException.class)
    public ResponseBuilder MyBatisException(MyBatisSystemException e) {
        ResponseBuilder builder = ResponseBuilder.failBuilder();

        builder.setMessage(e.getMessage());
        Map<String, Object> params = new HashMap<>(IntegerConsts.ONE);
        params.put("error", e.getMessage());

        builder.setData(params);
        return builder;
    }

    /**
     * 数据访问异常
     *
     * @param e 数据访问异常信息
     * @return 返回数据访问异常
     */
    @ExceptionHandler(TooManyResultsException.class)
    public ResponseBuilder MyBatisException(TooManyResultsException e) {
        ResponseBuilder builder = ResponseBuilder.failBuilder();

        builder.setMessage(e.getMessage());
        Map<String, Object> params = new HashMap<>(IntegerConsts.ONE);
        params.put("error", e.getMessage());

        builder.setData(params);
        return builder;
    }

    /**
     * 数据访问异常
     *
     * @param e 数据访问异常信息
     * @return 返回数据访问异常
     */
    @ExceptionHandler(PersistenceException.class)
    public ResponseBuilder MyBatisException(PersistenceException e) {
        ResponseBuilder builder = ResponseBuilder.failBuilder();

        builder.setMessage(e.getMessage());
        Map<String, Object> params = new HashMap<>(IntegerConsts.ONE);
        params.put("error", e.getMessage());

        response(builder.getCode());
        builder.setData(params);
        return builder;
    }

    /**
     * SQL语法错误异常
     *
     * @param e SQL语法错误异常异常信息
     * @return 返回SQL语法错误异常
     */
    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResponseBuilder MyBatisException(SQLSyntaxErrorException e) {
        ResponseBuilder builder = ResponseBuilder.failBuilder();

        builder.setMessage(e.getMessage());
        Map<String, Object> params = new HashMap<>(IntegerConsts.ONE);
        params.put("error", e.getMessage());

        builder.setData(params);
        return builder;
    }

    /**
     * sql 异常捕获
     *
     * @param e SQL异常信息
     * @return 返回封装结果
     */
    @ExceptionHandler(SQLException.class)
    public ResponseBuilder MyBatisException(SQLException e) {
        ResponseBuilder builder = ResponseBuilder.failBuilder();

        builder.setMessage(e.getMessage());
        Map<String, Object> params = new HashMap<>(IntegerConsts.ONE);
        params.put("error", e.getMessage());

        builder.setData(params);
        return builder;
    }

    /**
     * hibernate 验证异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseBuilder constraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getMessage();
        Integer begin = message.indexOf(StringUtils.COLON) + 1;
        Integer end = message.indexOf(StringUtils.COMMA);
        if (end > begin) {
            message = message.substring(begin, end);
        } else {
            message = message.substring(begin);
        }
        ResponseBuilder builder = ResponseBuilder.failBuilder(message);
        return builder;
    }

    /**
     * 文件大小超过最大限制
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseBuilder sizeLimitExceededExceptionExceptionHandler(MaxUploadSizeExceededException e) {
        Long size = e.getMaxUploadSize();
        Long fileSizeM = size / (IntegerConsts.BYTE_LENGTH * IntegerConsts.BYTE_LENGTH);
        String info = String.format("文件请勿超过%sM", fileSizeM);
        logUtil.error(e, info);

        return ResponseBuilder.failBuilder(info);
    }

    /**
     * 响应值
     *
     * @param code 值
     */
    private void response(int code) {
        HttpServletResponse response = HttpServletUtils.getResponse();
        if (response == null) {
            return;
        }
        response.setStatus(code);
    }
}
