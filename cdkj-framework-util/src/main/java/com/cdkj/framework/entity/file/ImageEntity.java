package com.cdkj.framework.entity.file;



import com.cdkj.framework.util.images.pictures.PicturesUtil;
import com.cdkj.framework.util.make.GeneratedValueUtil;

import java.awt.*;

/**
 * @ProjectName: com.cdkj.framework.webcode
 * @Package: com.cdkj.framework.core.entity.file
 * @ClassName: ImageEntity
 * @Description: 生成图片实体
 * @Author: xiaLin
 * @Version: 1.0
 */

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
    private String fileName = GeneratedValueUtil.getUuidNotTransverseLine();

    /**
     * 后缀
     */
    private String suffix = ".png";

    /**
     * 背景色
     */
    private Color backgroundColor = PicturesUtil.getRandColor(200, 250);

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
    private Color lineColor = PicturesUtil.getRandColor(160, 200);

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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
    }

    public int getFontSize() {
        if (fontSize < 0) {
            fontSize = getHeight() - 4;
        }
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public int getLineLength() {
        return lineLength;
    }

    public void setLineLength(int lineLength) {
        this.lineLength = lineLength;
    }

    public boolean isDrawingInterferenceLines() {
        return drawingInterferenceLines;
    }

    public void setDrawingInterferenceLines(boolean drawingInterferenceLines) {
        this.drawingInterferenceLines = drawingInterferenceLines;
    }

    public boolean isHotPixel() {
        return hotPixel;
    }

    public void setHotPixel(boolean hotPixel) {
        this.hotPixel = hotPixel;
    }

    public boolean isPictureDistortion() {
        return pictureDistortion;
    }

    public void setPictureDistortion(boolean pictureDistortion) {
        this.pictureDistortion = pictureDistortion;
    }

    public boolean isCharacterDistortion() {
        return characterDistortion;
    }

    public void setCharacterDistortion(boolean characterDistortion) {
        this.characterDistortion = characterDistortion;
    }
}
