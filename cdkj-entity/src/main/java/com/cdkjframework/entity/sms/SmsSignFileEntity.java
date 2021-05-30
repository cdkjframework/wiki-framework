package com.cdkjframework.entity.sms;

import lombok.Data;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.sms
 * @ClassName: SmsSignFileEntity
 * @Description: 受权文件
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class SmsSignFileEntity {

    /**
     * 签名的资质证明文件经base64编码后的字符串。图片不超过2 MB。
     */
    private String fileContents;

    /**
     * 签名的证明文件格式，支持上传多张图片。当前支持JPG、PNG、GIF或JPEG格式的图片。
     */
    private String fileSuffix;
}
