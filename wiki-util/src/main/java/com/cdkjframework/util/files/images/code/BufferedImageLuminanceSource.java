package com.cdkjframework.util.files.images.code;

import com.google.zxing.LuminanceSource;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * @ProjectName: com.cdkjframework.webcode
 * @Package: com.cdkjframework.core.util.images
 * @ClassName: BufferedImageLuminanceSource
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class BufferedImageLuminanceSource extends LuminanceSource {

  /**
   * 源图片
   */
  private final BufferedImage image;

  /**
   * 图片的左边界
   */
  private final int left;
  /**
   * 图片的上边界
   */
  private final int top;

  /**
   * 构造函数
   *
   * @param image 源图片
   */
  public BufferedImageLuminanceSource(BufferedImage image) {
    this(image, 0, 0, image.getWidth(), image.getHeight());
  }

  /**
   * 构造函数
   *
   * @param image  源图片
   * @param left   图片的左边界
   * @param top    图片的上边界
   * @param width  图片的宽度
   * @param height 图片的高度
   */
  public BufferedImageLuminanceSource(BufferedImage image, int left, int top, int width, int height) {
    super(width, height);

    int sourceWidth = image.getWidth();
    int sourceHeight = image.getHeight();
    if (left + width > sourceWidth || top + height > sourceHeight) {
      throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
    }

    for (int y = top; y < top + height; y++) {
      for (int x = left; x < left + width; x++) {
        if ((image.getRGB(x, y) & 0xFF000000) == 0) {
          image.setRGB(x, y, 0xFFFFFFFF);
        }
      }
    }

    this.image = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_BYTE_GRAY);
    this.image.getGraphics().drawImage(image, 0, 0, null);
    this.left = left;
    this.top = top;
  }

  /**
   * 获取图片行
   *
   * @param y   该行得到
   * @param row 用于存储行的可选数组。如果为null，将分配一个新数组
   */
  @Override
  public byte[] getRow(int y, byte[] row) {
    if (y < 0 || y >= getHeight()) {
      throw new IllegalArgumentException("Requested row is outside the image: " + y);
    }
    int width = getWidth();
    if (row == null || row.length < width) {
      row = new byte[width];
    }
    image.getRaster().getDataElements(left, top + y, width, 1, row);
    return row;
  }

  /**
   * 从底层平台的位图中获取一行亮度数据。数值范围从
   *
   * @return 亮度数据
   */
  @Override
  public byte[] getMatrix() {
    int width = getWidth();
    int height = getHeight();
    int area = width * height;
    byte[] matrix = new byte[area];
    image.getRaster().getDataElements(left, top, width, height, matrix);
    return matrix;
  }

  /**
   * 是否支持作物
   *
   * @return 返回布尔值
   */
  @Override
  public boolean isCropSupported() {
    return true;
  }

  /**
   * 剪裁图片
   *
   * @param left   左边剪裁点
   * @param top    顶部剪裁点
   * @param width  宽度
   * @param height 高度
   * @return 返回剪裁后的图片
   */
  @Override
  public LuminanceSource crop(int left, int top, int width, int height) {
    return new BufferedImageLuminanceSource(image, this.left + left, this.top + top, width, height);
  }

  /**
   * 是否支持旋转
   *
   * @return 返回布尔值
   */
  @Override
  public boolean isRotateSupported() {
    return true;
  }

  /**
   * 旋转图片
   *
   * @return 旋转后的图片
   */
  @Override
  public LuminanceSource rotateCounterClockwise() {

    int sourceWidth = image.getWidth();
    int sourceHeight = image.getHeight();

    AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);

    BufferedImage rotatedImage = new BufferedImage(sourceHeight, sourceWidth, BufferedImage.TYPE_BYTE_GRAY);

    Graphics2D g = rotatedImage.createGraphics();
    g.drawImage(image, transform, null);
    g.dispose();

    int width = getWidth();
    return new BufferedImageLuminanceSource(rotatedImage, top, sourceWidth - (left + width), getHeight(), width);
  }
}
