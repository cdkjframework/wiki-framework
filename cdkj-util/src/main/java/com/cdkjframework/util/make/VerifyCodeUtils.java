package com.cdkjframework.util.make;

import com.cdkjframework.entity.file.ImageEntity;
import com.cdkjframework.util.files.images.pictures.PicturesUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.util.make
 * @ClassName: VerifyCodeUtils
 * @Description: 验证码
 * @Author: xiaLin
 * @Version: 1.0
 */

public class VerifyCodeUtils {

    /**
     * 生成随机验证码文件,并返回验证码值
     *
     * @param w          宽度
     * @param h          高度
     * @param outputFile 输出文件
     * @param verifySize 验证码大小
     * @return 返回结果
     * @throws IOException IO 异常
     */
    public static String outputVerifyImage(int w, int h, File outputFile, int verifySize) throws IOException {
        String verifyCode = GeneratedValueUtils.getRandomCharacter(verifySize);
        outputImage(w, h, outputFile, verifyCode);
        return verifyCode;
    }

    /**
     * 输出随机验证码图片流,并返回验证码值
     *
     * @param w          宽度
     * @param h          高度
     * @param os         输出流
     * @param verifySize 验证码大小
     * @return 返回结果
     * @throws IOException IO 异常
     */
    public static String outputVerifyImage(int w, int h, OutputStream os, int verifySize) throws IOException {
        String verifyCode = GeneratedValueUtils.getRandomCharacter(verifySize);
        outputImage(w, h, os, verifyCode);
        return verifyCode;
    }

    /**
     * 生成指定验证码图像文件
     *
     * @param w          宽度
     * @param h          高度
     * @param outputFile 输出文件
     * @param code       验证码值
     * @throws IOException IO 异常
     */
    public static void outputImage(int w, int h, File outputFile, String code) throws IOException {
        if (outputFile == null) {
            return;
        }
        File dir = outputFile.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            outputFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(outputFile);
            outputImage(w, h, fos, code);
            fos.close();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 输出指定验证码图片流
     *
     * @param w    宽度
     * @param h    高度
     * @param os   输出流
     * @param code 验证码值
     * @throws IOException IO 异常
     */
    public static void outputImage(int w, int h, OutputStream os, String code) throws IOException {
        ImageEntity entity = new ImageEntity();
        entity.setWidth(w);
        entity.setHeight(h);
        entity.setContent(code);
        entity.setDrawingInterferenceLines(true);
        entity.setHotPixel(true);
        entity.setPictureDistortion(true);
        entity.setCharacterDistortion(true);

        PicturesUtil.outputImage(entity, os);
    }

    public static void main(String[] args) throws IOException {
        File dir = new File("D:\\upload\\qrcode");
        int w = 100, h = 30;

        String verifyCode = GeneratedValueUtils.getRandomCharacter(4);
        File file = new File(dir, verifyCode + ".jpg");
        outputImage(w, h, file, verifyCode);
    }
}
