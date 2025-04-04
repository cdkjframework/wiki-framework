package com.cdkjframework.util.files.images.code;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.Map;

/**
 * @ProjectName: com.cdkjframework.webcode
 * @Package: com.cdkjframework.core.util.images
 * @ClassName: QrCode
 * @Description: 二维码 util
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
@Getter
@Setter
@ToString
public class QrCodeUtils {

    /**
     * 宽度
     */
    private int width = 400;

    /**
     * 高度
     */
    private int height = 400;

    /**
     * 编码
     */
    private String encoding = "UTF-8";

    /**
     * 图片格式
     */
    private String formatName = "png";
    private float logoScale = 0.2f;
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    public QrCodeUtils() {

    }

    /**
     * 设置二维码宽度和高度
     *
     * @param width
     * @param height
     */
    public QrCodeUtils(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * 创建二维码
     *
     * @param content 内容
     * @param out     内容输出流
     * @throws Exception 异常信息
     */
    public void createQrCode(String content, OutputStream out) throws Exception {
        BitMatrix bitMatrix = getBitMatrix(content);

        BufferedImage image = convertBitMatrixToImage(bitMatrix);

        ImageIO.write(image, formatName, out);
    }

    /**
     * 创建二维码
     *
     * @param content      内容
     * @param logoImgInput 输出图片流
     * @param out          内容输出流
     * @throws Exception 异常信息
     */
    public void createQrCode(String content, InputStream logoImgInput, OutputStream out)
            throws Exception {
        BitMatrix bitMatrix = getBitMatrix(content);

        BufferedImage image = convertBitMatrixToImage(bitMatrix);

        addLogoToImage(image, logoImgInput);

        ImageIO.write(image, formatName, out);
    }

    /**
     * 解析二维码
     *
     * @param file
     * @return
     * @throws Exception
     */
    public String decode(File file) throws Exception {
        BufferedImage image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(
                image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Map<DecodeHintType, Object> tmpHintsMap = new EnumMap<DecodeHintType, Object>(
                DecodeHintType.class);
        tmpHintsMap.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
        tmpHintsMap.put(DecodeHintType.CHARACTER_SET, encoding);
        Result result = new MultiFormatReader().decode(bitmap, tmpHintsMap);
        String resultStr = result.getText();
        return resultStr;
    }

    /**
     * 获取2D位图的二维码图片
     *
     * @param content
     * @return
     * @throws Exception
     */
    private BitMatrix getBitMatrix(String content) throws Exception {
        Map<EncodeHintType, Object> tmpHintsMap = new EnumMap<EncodeHintType, Object>(
                EncodeHintType.class);
        tmpHintsMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        tmpHintsMap.put(EncodeHintType.CHARACTER_SET, encoding);
        tmpHintsMap.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, tmpHintsMap);
        return bitMatrix;
    }

    /**
     * 添加 logo 到二维码上
     *
     * @param image     图片
     * @param logoInput 输出
     * @throws Exception 异常信息
     */
    private void addLogoToImage(BufferedImage image, InputStream logoInput) throws Exception {
        BufferedImage logoImage = ImageIO.read(logoInput);
        int width = image.getWidth();
        int height = image.getHeight();
        int logoWidth = (int) (width * logoScale);
        int logoHeight = (int) (height * logoScale);
        Image logo = logoImage.getScaledInstance(logoWidth, logoHeight,
                Image.SCALE_SMOOTH);
        // 插入LOGO
        Graphics2D graph = image.createGraphics();
        int x = (width - logoWidth) / 2;
        int y = (height - logoHeight) / 2;
        graph.drawImage(logo, x, y, logoWidth, logoHeight, null);
        Shape shape = new RoundRectangle2D.Float(x, y, logoWidth, logoHeight, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 将位矩阵转换成图像
     *
     * @param bitMatrix
     * @return
     */
    private BufferedImage convertBitMatrixToImage(BitMatrix bitMatrix) {
        int qrWidth = bitMatrix.getWidth();
        int qrHeight = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(qrWidth, qrHeight, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
            }
        }

        return image;
    }

    /**
     * 制作颜色
     *
     * @param background 背景色
     * @return 返回结果
     */
    @Deprecated
    private int makeColor(boolean background) {
        int[] backgrounds = {Color.WHITE.getRGB(), Color.BLUE.getRGB(), Color.GREEN.getRGB()};
        int[] foregrounds = {Color.ORANGE.getRGB(), Color.CYAN.getRGB(), Color.RED.getRGB(), Color.YELLOW.getRGB()};
        if (background) {
            return backgrounds[(int) (System.currentTimeMillis()) % 3];
        } else {
            return foregrounds[(int) (System.currentTimeMillis()) % 4];
        }
    }
}
