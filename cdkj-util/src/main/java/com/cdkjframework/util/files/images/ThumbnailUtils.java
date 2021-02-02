package com.cdkjframework.util.files.images;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.files.images
 * @ClassName: ThumbnailUtils
 * @Description: 缩略图
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class ThumbnailUtils {

    /**
     * 读取配置
     */
    private static CustomConfig customConfig;

    /**
     * 日志
     */
    private static LogUtils logUtils = LogUtils.getLogger(ThumbnailUtils.class);

    /**
     * 构造函数
     *
     * @param config 配置
     */
    @Autowired
    public ThumbnailUtils(CustomConfig config) {
        customConfig = config;
    }

    /**
     * 执行
     *
     * @param targetFile 目标文件
     */
    public static void execute(File targetFile) {
        try {
            String filePath = targetFile.getPath();
            // 获取文件的后缀名
            String suffix = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
            // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
            String types = Arrays.toString(ImageIO.getReaderFormatNames());
            if (types.toLowerCase().indexOf(suffix.toLowerCase()) < IntegerConsts.ONE || CollectionUtils.isEmpty(customConfig.getThumbnail())) {
                return;
            }
            Image image = ImageIO.read(targetFile);
            for (String thumbnail :
                    customConfig.getThumbnail()) {
                String[] arrayList = thumbnail.split("x");
                if (arrayList.length < IntegerConsts.ONE) {
                    continue;
                }
                int bufferWidth = Integer.valueOf(arrayList[0]);
                int bufferHeight = Integer.valueOf(arrayList[1]);
                BufferedImage bi = new BufferedImage(bufferWidth, bufferHeight, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(image, IntegerConsts.ZERO, IntegerConsts.ZERO, bufferWidth, bufferHeight, Color.LIGHT_GRAY, null);
                g.dispose();
                String filePathName = filePath + "_" + thumbnail.toLowerCase() + "." + suffix;
                // 将图片保存在原目录并加上前缀
                ImageIO.write(bi, suffix, new File(filePathName));
            }
        } catch (IOException e) {
            logUtils.error(e, "生成缩略图失败。");
        }
    }
}
