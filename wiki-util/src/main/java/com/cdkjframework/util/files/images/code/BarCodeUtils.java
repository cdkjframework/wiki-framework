//package com.cdkjframework.util.files.images.code;
//
//import com.cdkjframework.constant.IntegerConsts;
//import com.cdkjframework.util.log.LogUtils;
//import com.cdkjframework.util.tool.StringUtils;
////import org.krysalis.barcode4j.BarcodeGenerator;
////import org.krysalis.barcode4j.HumanReadablePlacement;
////import org.krysalis.barcode4j.impl.codabar.CodabarBean;
////import org.krysalis.barcode4j.impl.code128.Code128Bean;
////import org.krysalis.barcode4j.impl.code39.Code39Bean;
////import org.krysalis.barcode4j.impl.datamatrix.DataMatrixBean;
////import org.krysalis.barcode4j.impl.fourstate.RoyalMailCBCBean;
////import org.krysalis.barcode4j.impl.fourstate.USPSIntelligentMailBean;
////import org.krysalis.barcode4j.impl.int2of5.ITF14Bean;
////import org.krysalis.barcode4j.impl.int2of5.Interleaved2Of5Bean;
////import org.krysalis.barcode4j.impl.pdf417.PDF417Bean;
////import org.krysalis.barcode4j.impl.postnet.POSTNETBean;
////import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
////import org.krysalis.barcode4j.impl.upcean.EAN8Bean;
////import org.krysalis.barcode4j.impl.upcean.UPCABean;
////import org.krysalis.barcode4j.impl.upcean.UPCEBean;
////import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
////import org.krysalis.barcode4j.tools.UnitConv;
//
//import java.awt.image.BufferedImage;
//import java.io.*;
//
///**
// * @ProjectName: wiki-framework
// * @Package: com.cdkjframework.util.files.images.code
// * @ClassName: BarCode
// * @Description: java类作用描述
// * @Author: xiaLin
// * @Version: 1.0
// */
//
//public class BarCodeUtils {
//
//    /**
//     * 日志
//     */
//    private static LogUtils logUtils = LogUtils.getLogger(BarCodeUtils.class);
//    /**
//     * 精细度
//     */
//    private static final int dpi = 150;
//
//    /**
//     * module宽度
//     */
////    private static final double moduleWidth = UnitConv.in2mm(1.0f / dpi);
//
//    /**
//     * 生成文件
//     *
//     * @param content 内容
//     * @param path    文件路径
//     * @return 返回结果
//     */
//    public static File generateFile(String content, String path) {
//        File file = new File(path);
//        try {
//            generateCode39(content, new FileOutputStream(file));
//        } catch (FileNotFoundException e) {
//            logUtils.error(e);
//        }
//        return file;
//    }
//
//    /**
//     * 生成字节
//     *
//     * @param content 内容
//     * @return 返回结果
//     */
//    public static byte[] generate(String content) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        generateCode39(content, outputStream);
//        return outputStream.toByteArray();
//    }
//
//    /**
//     * 生成到流
//     *
//     * @param content      内容
//     * @param outputStream 二进制流
//     */
//    public static void generateCode39(String content, OutputStream outputStream) {
//        if (StringUtils.isNullAndSpaceOrEmpty(content) || outputStream == null) {
//            return;
//        }
//        Code39Bean bean = new Code39Bean();
//        // 配置对象
//        bean.setModuleWidth(moduleWidth);
//        bean.setWideFactor(IntegerConsts.THREE);
//        bean.doQuietZone(false);
//
//        // 构建流
//        buildStream(content, bean, dpi, outputStream);
//    }
//
//    /**
//     * 生成到流
//     *
//     * @param content      内容
//     * @param outputStream 二进制流
//     */
//    public static void generateCode128(String content, OutputStream outputStream) {
//        if (StringUtils.isNullAndSpaceOrEmpty(content) || outputStream == null) {
//            return;
//        }
//        Code128Bean bean = new Code128Bean();
//        // 配置对象
//        bean.setModuleWidth(moduleWidth);
//        bean.doQuietZone(false);
//
//        // 构建流
//        buildStream(content, bean, dpi, outputStream);
//    }
//
//    /**
//     * 生成到流
//     *
//     * @param content      内容
//     * @param outputStream 二进制流
//     */
//    public static void generateInterleaved2Of5(String content, OutputStream outputStream) {
//        if (StringUtils.isNullAndSpaceOrEmpty(content) || outputStream == null) {
//            return;
//        }
//        Interleaved2Of5Bean bean = new Interleaved2Of5Bean();
//        // 配置对象
//        bean.setModuleWidth(moduleWidth);
//        bean.doQuietZone(false);
//
//        // 构建流
//        buildStream(content, bean, dpi, outputStream);
//    }
//
//    /**
//     * 生成到流
//     *
//     * @param content      内容
//     * @param outputStream 二进制流
//     */
//    public static void generateItf14(String content, OutputStream outputStream) {
//        if (StringUtils.isNullAndSpaceOrEmpty(content) || outputStream == null) {
//            return;
//        }
//        ITF14Bean bean = new ITF14Bean();
//        // 配置对象
//        bean.setModuleWidth(moduleWidth);
//        bean.doQuietZone(true);
//
//        // 构建流
//        buildStream(content, bean, dpi, outputStream);
//    }
//
//    /**
//     * 生成到流
//     *
//     * @param content      内容
//     * @param outputStream 二进制流
//     */
//    public static void generateCodeBar(String content, OutputStream outputStream) {
//        if (StringUtils.isNullAndSpaceOrEmpty(content) || outputStream == null) {
//            return;
//        }
//        CodabarBean bean = new CodabarBean();
//        // 配置对象
//        bean.setModuleWidth(moduleWidth);
//        bean.doQuietZone(false);
//
//        // 构建流
//        buildStream(content, bean, dpi, outputStream);
//    }
//
//    /**
//     * 生成到流
//     *
//     * @param content      内容
//     * @param outputStream 二进制流
//     */
//    public static void generateUpca(String content, OutputStream outputStream) {
//        if (StringUtils.isNullAndSpaceOrEmpty(content) || outputStream == null) {
//            return;
//        }
//        UPCABean bean = new UPCABean();
//        // 配置对象
//        bean.setModuleWidth(moduleWidth);
//        bean.doQuietZone(false);
//
//        // 构建流
//        buildStream(content, bean, dpi, outputStream);
//    }
//
//    /**
//     * 生成到流
//     *
//     * @param content      内容
//     * @param outputStream 二进制流
//     */
//    public static void generateUpce(String content, OutputStream outputStream) {
//        if (StringUtils.isNullAndSpaceOrEmpty(content) || outputStream == null) {
//            return;
//        }
//        UPCEBean bean = new UPCEBean();
//        // 配置对象
//        bean.setModuleWidth(moduleWidth);
//        bean.doQuietZone(false);
//
//        // 构建流
//        buildStream(content, bean, dpi, outputStream);
//    }
//
//    /**
//     * 生成到流
//     *
//     * @param content      内容
//     * @param outputStream 二进制流
//     */
//    public static void generateEan13(String content, OutputStream outputStream) {
//        if (StringUtils.isNullAndSpaceOrEmpty(content) || outputStream == null) {
//            return;
//        }
//        EAN13Bean bean = new EAN13Bean();
//        // 配置对象
//        bean.setModuleWidth(moduleWidth);
//        bean.doQuietZone(false);
//
//        // 构建流
//        buildStream(content, bean, dpi, outputStream);
//    }
//
//    /**
//     * 生成到流
//     *
//     * @param content      内容
//     * @param outputStream 二进制流
//     */
//    public static void generateEan8(String content, OutputStream outputStream) {
//        if (StringUtils.isNullAndSpaceOrEmpty(content) || outputStream == null) {
//            return;
//        }
//
//        EAN8Bean bean = new EAN8Bean();
//        // 配置对象
//        bean.setModuleWidth(moduleWidth);
//        bean.doQuietZone(false);
//        bean.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);
//
//
//        // 构建流
//        buildStream(content, bean, dpi, outputStream);
//    }
//
//    /**
//     * 生成到流
//     *
//     * @param content      内容
//     * @param outputStream 二进制流
//     */
//    public static void generatePostnet(String content, OutputStream outputStream) {
//        if (StringUtils.isNullAndSpaceOrEmpty(content) || outputStream == null) {
//            return;
//        }
//        POSTNETBean bean = new POSTNETBean();
//        // 配置对象
//        bean.setModuleWidth(moduleWidth);
//        bean.doQuietZone(false);
//
//        // 构建流
//        buildStream(content, bean, dpi, outputStream);
//    }
//
//    /**
//     * 生成到流
//     *
//     * @param content      内容
//     * @param outputStream 二进制流
//     */
//    public static void generateRoyalMailCbc(String content, OutputStream outputStream) {
//        if (StringUtils.isNullAndSpaceOrEmpty(content) || outputStream == null) {
//            return;
//        }
//        RoyalMailCBCBean bean = new RoyalMailCBCBean();
//        // 配置对象
//        bean.setModuleWidth(moduleWidth);
//        bean.doQuietZone(false);
//
//        // 构建流
//        buildStream(content, bean, dpi, outputStream);
//    }
//
//    /**
//     * 生成到流
//     *
//     * @param content      内容
//     * @param outputStream 二进制流
//     */
//    public static void generateUspsIntelligentMail(String content, OutputStream outputStream) {
//        if (StringUtils.isNullAndSpaceOrEmpty(content) || outputStream == null) {
//            return;
//        }
//        USPSIntelligentMailBean bean = new USPSIntelligentMailBean();
//        // 配置对象
//        bean.setModuleWidth(moduleWidth);
//        bean.doQuietZone(false);
//
//        // 构建流
//        buildStream(content, bean, dpi, outputStream);
//    }
//
//    /**
//     * 生成到流
//     *
//     * @param content      内容
//     * @param outputStream 二进制流
//     */
//    public static void generatePdf417(String content, OutputStream outputStream) {
//        if (StringUtils.isNullAndSpaceOrEmpty(content) || outputStream == null) {
//            return;
//        }
//        PDF417Bean bean = new PDF417Bean();
//        // 配置对象
//        bean.setModuleWidth(moduleWidth);
//        bean.doQuietZone(false);
//
//        // 构建流
//        buildStream(content, bean, dpi, outputStream);
//    }
//
//    /**
//     * 生成到流
//     *
//     * @param content      内容
//     * @param outputStream 二进制流
//     */
//    public static void generateDataMatrix(String content, OutputStream outputStream) {
//        if (StringUtils.isNullAndSpaceOrEmpty(content) || outputStream == null) {
//            return;
//        }
//        DataMatrixBean bean = new DataMatrixBean();
//        // 配置对象
//        bean.setModuleWidth(moduleWidth);
//        bean.doQuietZone(false);
//
//        // 构建流
//        buildStream(content, bean, dpi, outputStream);
//    }
//
//    /**
//     * 构建流
//     *
//     * @param content      内容
//     * @param generator    接口
//     * @param dpi          dpi
//     * @param outputStream 输出流
//     */
//    private static void buildStream(String content, BarcodeGenerator generator, int dpi, OutputStream outputStream) {
//        String format = "image/png";
//        try {
//            // 输出到流
//            BitmapCanvasProvider canvas = new BitmapCanvasProvider(outputStream, format, dpi,
//                    BufferedImage.TYPE_BYTE_BINARY, false, IntegerConsts.ZERO);
//            // 生成条形码
//            generator.generateBarcode(canvas, content);
//            // 结束绘制
//            canvas.finish();
//        } catch (IOException e) {
//            logUtils.error(e);
//        }
//    }
//}
