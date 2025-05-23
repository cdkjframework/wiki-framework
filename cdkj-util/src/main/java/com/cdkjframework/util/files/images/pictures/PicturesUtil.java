package com.cdkjframework.util.files.images.pictures;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.file.ImageEntity;
import com.cdkjframework.util.files.FileUtils;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

/**
 * @ProjectName: com.cdkjframework.webcode
 * @Package: com.cdkjframework.core.util.images.pictures
 * @ClassName: PicturesUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class PicturesUtil {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(PicturesUtil.class);

    /**
     * 生成图片返回路径
     *
     * @param imageEntity 图片参数实体
     * @return 返回路径
     */
    public static String outputPath(ImageEntity imageEntity) {
        String picturesPath = "";

        //生成二进制图片信息
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            //生成图片信息
            outputImage(imageEntity, outputStream);
            //输出为 input 流
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            //目录
            String catalog = imageEntity.getCatalog();
            //文件名
            String fileName = imageEntity.getFileName() + imageEntity.getSuffix();
            //保存为文件
            FileUtils.saveFile(inputStream, imageEntity.getDirectoryPath(), catalog, fileName);

            //图片路径
            picturesPath = catalog + fileName;
        } catch (Exception e) {
            logUtil.error(e.getMessage());
        }

        //返回结果
        return picturesPath;
    }

    /**
     * 生成图片返回文件流
     *
     * @param imageEntity 图片参数实体
     * @param os          文件流
     */
    public static void outputImage(ImageEntity imageEntity, OutputStream os) throws IOException {
        BufferedImage image = new BufferedImage(imageEntity.getWidth(), imageEntity.getHeight(), BufferedImage.TYPE_INT_RGB);

        /**
         * 构造图片
         */
        buildImage(image, imageEntity);

        //输出图片信息
        ImageIO.write(image, imageEntity.getSuffix().replace(".", ""), os);
    }

    /**
     * 构建图像
     *
     * @param image       图片信息
     * @param imageEntity 图片参数
     */
    private static void buildImage(BufferedImage image, ImageEntity imageEntity) {
        int w = imageEntity.getWidth();
        int h = imageEntity.getHeight();
        int verifySize = imageEntity.getContent().length();
        Random rand = new Random();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[]{Color.WHITE, Color.CYAN,
                Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
                Color.PINK, Color.YELLOW};
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
            fractions[i] = rand.nextFloat();
        }
        Arrays.sort(fractions);
        // 设置边框色
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, w, h);

        Color c = getRandColor(200, 250);
        // 设置背景色
        g2.setColor(c);
        g2.fillRect(0, 2, w, h - 4);

        //绘制干扰线
        Random random = new Random();
        // 设置线条的颜色
        g2.setColor(getRandColor(160, 200));
        for (int i = 0; i < IntegerConsts.TWENTY; i++) {
            int x = random.nextInt(w - 1);
            int y = random.nextInt(h - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // 添加噪点
        float yawpRate = 0.05f;
        // 噪声率
        int area = (int) (yawpRate * w * h);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }
        // 使图片扭曲
        shear(g2, w, h, c);
        g2.setColor(getRandColor(100, 160));
        int fontSize = h - 4;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = imageEntity.getContent().toCharArray();
        for (int i = 0; i < verifySize; i++) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (w / verifySize) * i + fontSize / 2, h / 2);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((w - 10) / verifySize) * i + 5, h / 2 + fontSize / 2 - 5);
        }

        g2.dispose();
    }

    /**
     * 设置背景
     *
     * @param fc
     * @param bc
     * @return
     */
    public static Color getRandColor(int fc, int bc) {
        final int finalInt = 255;
        if (fc > finalInt) {
            fc = finalInt;
        }
        if (bc > finalInt) {
            bc = finalInt;
        }
        Random random = new Random();
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 随机int颜色
     *
     * @return
     */
    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    /**
     * 随机生成RGB
     *
     * @return 返回int
     */
    private static int[] getRandomRgb() {
        int[] rgb = new int[3];
        int length = 3;
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    /**
     * 剪切
     *
     * @param g     Graphics
     * @param w1    宽度
     * @param h1    高度
     * @param color 颜色
     */
    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearXaxis(g, w1, h1, color);
        shearYaxis(g, w1, h1, color);
    }

    /**
     * X剪切
     *
     * @param g     Graphics
     * @param w1    宽度
     * @param h1    高度
     * @param color 颜色
     */
    private static void shearXaxis(Graphics g, int w1, int h1, Color color) {
        Random random = new Random();
        int period = random.nextInt(IntegerConsts.TWO);
        boolean borderGap = true;
        int frames = IntegerConsts.ONE;
        int phase = random.nextInt(IntegerConsts.TWO);
        for (int i = IntegerConsts.ZERO; i < h1; i++) {
            double d = (double) (period >> IntegerConsts.ONE)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            //拷贝区域
            g.copyArea(IntegerConsts.ZERO, i, w1, IntegerConsts.ONE, (int) d, IntegerConsts.ZERO);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, IntegerConsts.ZERO, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }
    }

    /**
     * Y轴剪切
     *
     * @param g     Graphics
     * @param w1    宽度
     * @param h1    高度
     * @param color 颜色
     */
    private static void shearYaxis(Graphics g, int w1, int h1, Color color) {
        Random random = new Random();
        int period = random.nextInt(40) + 10;
        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            //拷贝区域
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }
    }
}
