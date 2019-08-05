package com.cdkjframework.entity.file;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.*;

/**
 * @ProjectName: com.cdkjframework.webcode
 * @Package: com.cdkjframework.core.entity.file
 * @ClassName: ImageEntity
 * @Description: 生成图片实体
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class ImageEntity {

    /**
     * 宽度
     */
    private int width = 100;

    /**
     * 宽度
     */
    private int height = 100;

    /**
     * 文件路径
     */
    private String directoryPath;

    /**
     * 目录
     */
    private String catalog = "/picture/";

    /**
     * 内容
     */
    private String content = "无内容";

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 后缀
     */
    private String suffix = ".png";

    /**
     * 背景色
     */
    private Color backgroundColor;

    /**
     * 字体
     * Font
     */
    private String fontFamily = "宋体";

    /**
     * 字段样式
     */
    private int fontStyle;

    /**
     * 字段大小
     */
    private int fontSize = -1;

    /**
     * 字段颜色
     * Color 值
     */
    private Color fontColor = Color.black;

    /**
     * 线条颜色
     */
    private Color lineColor;

    /**
     * 干扰线（条数）
     */
    private int lineLength = 20;

    /**
     * 是否绘制干扰线
     */
    private boolean drawingInterferenceLines = false;

    /**
     * 是否添加噪点
     */
    private boolean hotPixel = false;

    /**
     * 是否图片扭曲
     */
    private boolean pictureDistortion = false;

    /**
     * 文字扭曲
     */
    private boolean characterDistortion = false;
}
