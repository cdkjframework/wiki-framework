package com.cdkjframework.util.tool.validate;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.enums.ResponseBuilderEnums;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.util.validate
 * @ClassName: ValidateUtil
 * @Description: 验证 操作工具
 * @Author: xiaLin
 * @Version: 1.0
 */
public class ValidateUtils {

    /**
     * 默认提示信息
     */
    private static final String DATA_NULL = "传入数据不能为空";

    /**
     * 判断是否有验证错误信息
     *
     * @param result 参数信息
     * @return 返回结果
     */
    public static String judgeValidate(BindingResult result) {
        StringBuilder builder = new StringBuilder();
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            List<String> errorList = list
                    .stream()
                    .map(m -> m.getDefaultMessage())
                    .collect(Collectors.toList());
            builder.append(String.join("\n", errorList));
        }
        return builder.toString();
    }

    /**
     * 获取错误信息
     *
     * @param result 参数信息
     * @return 返回结果
     */
    public static ResponseBuilder getIllegalMessage(BindingResult result) {
        String errorMessage = judgeValidate(result);
        return new ResponseBuilder(ResponseBuilderEnums.Error.getValue(), errorMessage);
    }

}
