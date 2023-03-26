package com.cdkjframework.license.util;

import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.license.entity.LicenseCreatorEntity;
import com.cdkjframework.license.entity.LicenseResultEntity;
import com.cdkjframework.license.entity.ResponseResultEntity;
import com.cdkjframework.license.manger.LicenseCreatorManager;
import com.cdkjframework.license.util.DateUtils;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.tool.StringUtils;

import java.io.File;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.license.creator.service
 * @ClassName: LicenseCreatorService
 * @Description: 证书生成接口实现
 * @Author: xiaLin
 * @Date: 2023/3/15 15:14
 * @Version: 1.0
 */
public class LicenseCreatorUtils {

    /**
     * 路径
     */
    private final static String PATH = "license";

    /**
     * 文件名称
     */
    private final static String FILE_NAME = "license.lic";


    /**
     * <p>生成证书</p>
     *
     * @param param 生成证书需要的参数，如：
     */
    public static ResponseResultEntity generate(LicenseCreatorEntity param) throws Exception {
        // 如果没有人为的指定lic要生成的位置，则程序自动处理
        if (StringUtils.isNullAndSpaceOrEmpty(param.getLicensePath())) {
            throw new GlobalException("证书生成路径不能为空！");
        }

        // 根据时间戳，命名lic文件
        String licDir = param.getLicensePath() + PATH +
                LocalDateUtils.dateTimeCurrentFormatter(LocalDateUtils.DATE_HHMMSS);
        File file = new File(licDir);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new GlobalException("创建目录" + licDir + ",失败，请检查是是否有创建目录的权限或者手动进行创建！");
            }
        }
        param.setLicensePath(licDir + FILE_NAME);

        // 返回结果
        return generateLicense(param);
    }


    /**
     * <p>生成证书</p>
     *
     * @param param 证书创建需要的参数对象
     * @return 返回生成结果
     */
    public static ResponseResultEntity generateLicense(LicenseCreatorEntity param) {
        if (param.getYear() == null && param.getMonth() == null) {
            throw new GlobalRuntimeException("年或月不能为空！");
        }
        if (param.getIssuedTime() == null) {
            param.setIssuedTime(new Date());
        }
        if (param.getExpiryTime() == null) {
            Date issuedTime = param.getIssuedTime();

            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(issuedTime);
            if (param.getYear() != null) {
                rightNow.add(Calendar.YEAR, param.getYear());
            }
            if (param.getMonth() != null) {
                rightNow.add(Calendar.MONTH, param.getMonth());
            }
            param.setExpiryTime(rightNow.getTime());
        }
        LicenseCreatorManager licenseCreator = new LicenseCreatorManager(param);
        LicenseResultEntity licenseResult = licenseCreator.generateLicense();
        if (licenseResult.getResult()) {
            String message = MessageFormat.format("证书生成成功，证书有效期：{0} - {1}",
                    DateUtils.date2Str(param.getIssuedTime()), DateUtils.date2Str(param.getExpiryTime()));
            return ResponseResultEntity.ok(message, param);
        } else {
            return ResponseResultEntity.fail("证书文件生成失败！");
        }
    }
}
