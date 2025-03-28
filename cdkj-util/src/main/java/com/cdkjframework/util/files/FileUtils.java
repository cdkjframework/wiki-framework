package com.cdkjframework.util.files;

import com.cdkjframework.constant.FileTypeConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.enums.FileTypeEnums;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.HostUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Iterator;
import java.util.List;

/**
 * @ProjectName: com.cdkjframework.webcode
 * @Package: com.cdkjframework.core.util.files
 * @ClassName: FileUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class FileUtils {
    /**
     * 单位 KB
     */
    private static final int KB_SIZE = IntegerConsts.BYTE_LENGTH;

    /**
     * 单位 MB
     */
    private static final int MB_SIZE = IntegerConsts.BYTE_LENGTH * KB_SIZE;

    /**
     * 单位 GB
     */
    private static final int GB_SIZE = IntegerConsts.BYTE_LENGTH * MB_SIZE;
    /**
     * 加密解密秘钥
     */
    private static final int encAndDecKey = 0x99;
    /**
     * 文件字节内容
     */
    private static int dataOfFile = 0;

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(FileUtils.class);

    /**
     * 保存文件
     *
     * @param inputStream 文件流
     * @param fileName    文件名称
     * @return 返回结果
     * @throws GlobalException 异常信息
     */
    public static boolean saveFile(InputStream inputStream, String fileName) throws GlobalException {
        return saveFile(inputStream, StringUtils.Empty, fileName);
    }

    /**
		 * 保存文件
		 *
		 * @param inputStream 文件流
		 * @param fileName    文件路径
		 * @param catalog     自定义文件路径
		 * @param fileName    文件名称
		 * @return 返回结果
		 * @throws GlobalException 异常信息
		 */
		public static boolean saveFile(InputStream inputStream, String catalog, String fileName) throws GlobalException {
			return saveFile(inputStream, StringUtils.Empty, catalog, fileName);
		}

	/**
	 * 保存文件
	 *
	 * @param character     文件内容
	 * @param directoryPath 文件路径
	 * @param catalog       自定义文件路径
	 * @param fileName      文件名称
	 * @return 返回结果
	 * @throws GlobalException 异常信息
	 */
	public static boolean saveFile(String character, String directoryPath, String catalog, String fileName) throws GlobalException {
		InputStream inputStream = new ByteArrayInputStream(character.getBytes());
		return saveFile(inputStream, directoryPath, catalog, fileName);
	}

	/**
	 * 保存文件
	 *
	 * @param character     文件内容
	 * @param directoryPath 文件路径
	 * @param catalog       自定义文件路径
	 * @param fileName      文件名称
	 * @param append        是否追加
	 * @return 返回结果
	 * @throws GlobalException 异常信息
	 */
	public static boolean saveFile(String character, String directoryPath, String catalog, String fileName, boolean append) throws GlobalException {
		InputStream inputStream = new ByteArrayInputStream(character.getBytes());
		return saveFile(inputStream, directoryPath, catalog, fileName, append);
	}

	/**
	 * 保存文件
	 *
	 * @param inputStream   文件流
	 * @param directoryPath 文件路径
	 * @param catalog       自定义文件路径
	 * @param fileName      文件名称
	 * @return 返回结果
	 * @throws GlobalException 异常信息
	 */
	public static boolean saveFile(InputStream inputStream, String directoryPath, String catalog, String fileName) throws GlobalException {
		return saveFile(inputStream, directoryPath, catalog, fileName, Boolean.TRUE);
	}

	/**
	 * 保存文件
	 *
	 * @param inputStream   文件流
	 * @param directoryPath 文件路径
	 * @param catalog       自定义文件路径
	 * @param fileName      文件名称
	 * @param append        是否追加
	 * @return 返回结果
	 * @throws GlobalException 异常信息
	 */
	public static boolean saveFile(InputStream inputStream, String directoryPath, String catalog, String fileName, boolean append) throws GlobalException {
		OutputStream outputStream = null;
		try {
			directoryPath = splicingPath(directoryPath, catalog);

			//读取文件路径
			File file = new File(directoryPath);
			if (!file.exists()) {
				file.mkdirs();
				fileUnModified(file);
			}

			// 1K的数据缓冲
			byte[] bytes = new byte[IntegerConsts.BYTE_LENGTH];
			// 读取到的数据长度
			int len;

			String name = file.getPath() + File.separator + fileName;
			outputStream = new FileOutputStream(name, append);
			// 开始读取
			while ((len = inputStream.read(bytes)) != IntegerConsts.MINUS_ONE) {
				outputStream.write(bytes, IntegerConsts.ZERO, len);
			}
			return true;
		} catch (Exception ex) {
			logUtil.error(ex);
			throw new GlobalException(ex.getMessage());
		} finally {
			// 完毕，关闭所有链接
			try {
				outputStream.close();
				inputStream.close();
			} catch (IOException e) {
				logUtil.error(e);
				throw new GlobalException(e.getMessage());
			}
		}
	}

    /**
     * 读取文件
     *
     * @param directoryPath 目录
     * @param catalog       自定义路径
     * @param fileName      文件史
     * @return 返回结果
     * @throws GlobalException 仅供异常信息
     * @throws IOException     异常信息
     */
    public static byte[] readFile(String directoryPath, String catalog, String fileName) throws GlobalException, IOException {

        InputStream inputStream = null;
        byte[] byteList = null;
        try {
            //拼接路径
            directoryPath = splicingPath(directoryPath, catalog) + fileName;

            // 读取文件到流
            inputStream = new FileInputStream(directoryPath);
            //创建文件字段长度
            byteList = new byte[inputStream.available()];
            //将文件流读取为字节
            inputStream.read(byteList);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GlobalException(ex.getMessage());
        } finally {
            //关闭文件流
            inputStream.close();
        }

        //返回结果
        return byteList;
    }

    /**
     * 删除指定目录、时间之前的文件
     *
     * @param date    时间
     * @param catalog 目录
     * @return
     */
    public static void beforeDeleteSpecifiedTimeFile(LocalDateTime date, String catalog) throws GlobalException {
        beforeDeleteSpecifiedTimeFile(date, catalog, StringUtils.Empty, null);
    }

    /**
     * 删除指定目录、时间之前的文件
     *
     * @param date          时间
     * @param catalog       目录
     * @param directoryPath 路径（可为空）
     * @param excludeFiles  不删除文件集合（可为空）
     * @return
     */
    public static void beforeDeleteSpecifiedTimeFile(LocalDateTime date, String catalog, String directoryPath, List<String> excludeFiles) throws GlobalException {
        try {
            //获取完整路径
            String tempFolder = splicingPath(directoryPath, catalog);
            //读取文件路径
            File folder = new File(tempFolder);
            //获取全部文件
            File[] files = folder.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                //验证是否为不删除文件
                if (excludeFiles != null && excludeFiles.size() > 0 && excludeFiles.contains(file.getName())) {
                    continue;
                }
                //判断文件是否在指定日间之前生成
                LocalDateTime dateTime = LocalDateUtils.timestampToLocalDateTime(file.lastModified());
                if (LocalDateUtils.greater(dateTime, date)) {
                    file.delete();
                }
            }
        } catch (Exception ex) {
            logUtil.error(ex);
        }
    }

    /**
     * 获取文件后缀
     *
     * @param fileName 文件名
     * @return 返回结果
     */
    public static String getFileSuffix(String fileName) {
        String suffix = StringUtils.Empty;
        if (StringUtils.isNullAndSpaceOrEmpty(fileName)) {
            return suffix;
        }
        //获取后缀
        suffix = fileName.substring(fileName.lastIndexOf(StringUtils.POINT));
        //返回结果
        return suffix;
    }

    /**
     * 获取文件名称
     *
     * @param filePath 文件路径
     * @return 返回结果
     */
    public static String getFileName(String filePath) {
        String fileName = StringUtils.Empty;
        if (StringUtils.isNullAndSpaceOrEmpty(filePath)) {
            return fileName;
        }
        //网络地址
        final String splitValue = "/";
        if (filePath.contains(splitValue)) {
            fileName = filePath.substring(filePath.lastIndexOf(splitValue) + IntegerConsts.ONE);
        } else {
            //目录地址
            fileName = filePath.substring(filePath.lastIndexOf("\\") + IntegerConsts.ONE);
        }
        //返回结果
        return fileName;
    }

    /**
     * 拼接路径
     *
     * @param directoryPath 路径
     * @param catalog       目录
     * @return 返回结果
     * @throws FileNotFoundException 异常信息
     */
    private static String splicingPath(String directoryPath, String catalog) throws FileNotFoundException {
        //验证路径是否存在
        if (StringUtils.isNullAndSpaceOrEmpty(directoryPath)) {
            directoryPath = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
        }
        //验证自定义路径是否有
        if (!StringUtils.isNullAndSpaceOrEmpty(catalog)) {
            directoryPath += catalog;
        }
        //返回结果
        return directoryPath;
    }

    /**
     * 获取到目录
     *
     * @return 返回结果
     */
    public static String getPath(String path) {
        final String os = HostUtils.getOs();
        String division = "/";
        final String osKey = "win";
        if (os.startsWith(osKey)) {
            division = "\\";
        }
        String classPath;
        try {
            classPath = ResourceUtils
                    .getFile("classpath:")
                    .getPath()
                    .replace("target" + division + "classes", "src" + division + "main" + division + "java" + division);
            path = path
                    .replace("[", "")
                    .replace("]", "")
                    .replace(".", division);
            classPath += path;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            classPath = "";
        }

        //返回结果
        return classPath;
    }

    /**
     * 文件不能修改，执行、只读取
     *
     * @param file 文件
     */
    public static void fileUnModified(File file) {
        if (file != null) {
            file.setWritable(false);
            file.setExecutable(false);
            file.setReadOnly();
        }
    }


    /**
     * 获取文件大小
     *
     * @param file 文件
     * @return 返回大小
     */
    public static long getFileSize(File file) {
        long size = IntegerConsts.ZERO;
        if (file.exists()) {
            if (!file.isDirectory()) {
                size = file.length();
            } else {
                size += getDirSize(file);
            }
        }
        return size;
    }

    /**
     * 多聚目录大小
     *
     * @param file 文件
     * @return 返回大小
     */
    public static long getDirSize(File file) {
        long dirSize = IntegerConsts.ZERO;
        if (file != null) {
            if (file.exists()) {
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    for (File fl : files) {
                        if (fl.isDirectory()) {
                            dirSize += getDirSize(fl);
                        } else {
                            dirSize += fl.length();
                        }
                    }
                }
            }
        }
        return dirSize;
    }

    /**
     * byte字节转换为字符串
     *
     * @param fileBytes 文件 byte
     * @return 返回字符编码
     */
    public static String byteToString(long fileBytes) {
        StringBuffer byteStr = new StringBuffer();
        BigDecimal fullSize = new BigDecimal(fileBytes);
        //mb
      BigDecimal mbSize = new BigDecimal(MB_SIZE);
      float gbSize = fullSize.divide(new BigDecimal(GB_SIZE), IntegerConsts.TWO, RoundingMode.HALF_UP).floatValue();
        if (gbSize > IntegerConsts.ONE) {
            byteStr.append(gbSize).append("GB");
        } else {
          float dvSize = fullSize.divide(mbSize, IntegerConsts.TWO, RoundingMode.HALF_UP).floatValue();
            if (dvSize > IntegerConsts.ONE) {
                byteStr.append(dvSize).append("MB");
            } else {
                //kb显示
                BigDecimal kbSize = new BigDecimal(KB_SIZE);
              byteStr.append(fullSize.divide(kbSize, IntegerConsts.TWO, RoundingMode.HALF_UP).floatValue()).append("KB");
            }
        }
        return byteStr.toString();
    }

    /**
     * 加密文件
     *
     * @param sourceFile  源文件
     * @param encryptFile 加密后文件
     * @throws Exception 异常
     */
    public static void encrypt(File sourceFile, File encryptFile) throws Exception {
        if (!sourceFile.exists()) {
            return;
        }

        InputStream inputStream = new FileInputStream(sourceFile);
        encrypt(inputStream, encryptFile);
    }

    /**
     * 文件加密
     *
     * @param inputStream 文件流
     * @param encryptFile 加密后文件
     * @throws Exception 异常信息
     */
    public static void encrypt(InputStream inputStream, File encryptFile) throws Exception {
        if (!encryptFile.exists()) {
            encryptFile.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(encryptFile);
        while ((dataOfFile = inputStream.read()) > IntegerConsts.MINUS_ONE) {
            outputStream.write(dataOfFile ^ encAndDecKey);
        }
        inputStream.close();
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 解密文件
     *
     * @param sourceFile  源文件
     * @param decryptFile 解密文件
     * @throws Exception 异常信息
     */
    public static void decrypt(File sourceFile, File decryptFile) throws Exception {
        if (!sourceFile.exists()) {
            return;
        }
        if (!decryptFile.exists()) {
            decryptFile.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(decryptFile);
        decrypt(sourceFile, outputStream);
    }

    /**
     * 解密文件
     *
     * @param sourceFile   源文件
     * @param outputStream 解密文件流
     * @throws Exception 异常信息
     */
    private static void decrypt(File sourceFile, OutputStream outputStream) throws Exception {
        InputStream inputStream = new FileInputStream(sourceFile);

        while ((dataOfFile = inputStream.read()) > IntegerConsts.MINUS_ONE) {
            outputStream.write(dataOfFile ^ encAndDecKey);
        }
        inputStream.close();
        outputStream.flush();
        outputStream.close();
    }

  /**
   * 获取传输的 multipartFile，将输入流+文件名转成multipartFile文件，去调用feignClient
   *
   * @param inputStream 文件流
   * @param fileName    文件名
   * @return 返回多部分文件
   */
  public static MultipartFile buildMultipartFile(InputStream inputStream, String fileName) throws IOException {
    //CommonsMultipartFile是feign对multipartFile的封装，但是要FileItem类对象
    return new MockMultipartFile(fileName, inputStream);
  }

    /**
     * 获取传输的 multipartFile，将输入流+文件名转成multipartFile文件，去调用feignClient
     *
     * @param outputStream 文件流
     * @param fileName     文件名
     * @return 返回多部分文件
     */
    public static MultipartFile buildMultipartFile(OutputStream outputStream, String fileName) {
        ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) outputStream;
      return new MockMultipartFile(fileName, byteArrayOutputStream.toByteArray());
    }

    /**
     * 删除日志
     *
     * @param catalog 日志路径
     */
    public static void deleteCatalogFile(String catalog) {
        deleteCatalogFile(catalog, true);
    }

    /**
     * 删除日志
     *
     * @param catalog     日志路径
     * @param isDirectory 是否删除根目录(默认为true)
     */
    public static void deleteCatalogFile(String catalog, boolean isDirectory) {
        deleteCatalogFile(catalog, isDirectory, IntegerConsts.ZERO);
    }

    /**
     * 删除日志
     *
     * @param catalog     日志路径
     * @param isDirectory 是否删除根目录(默认为true)
     * @param day         删除几天前的
     */
    public static void deleteCatalogFile(String catalog, boolean isDirectory, int day) {
        Long deleteTime;
        if (day == IntegerConsts.ZERO) {
            deleteTime = System.currentTimeMillis();
        } else {
            day = Math.abs(day) * IntegerConsts.MINUS_ONE;
            LocalDate localDate = LocalDate.now().plusDays(day);
            deleteTime = localDate.atStartOfDay(ZoneOffset
                    .ofHours(IntegerConsts.EIGHT)).toInstant().toEpochMilli();
        }
        try {
            File file = new File(catalog);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    // 若是文件夹且在设定日期之前
                    if (f.isDirectory()) {
                        deleteCatalogFile(f.getPath(), true, day);
                        f.delete();
                    } else {
                        deleteFile(f, deleteTime);
                    }
                }
                if (isDirectory) {
                    file.delete();
                }
            } else {
                deleteFile(file, deleteTime);
            }
        } catch (IOException e) {
        }
    }

    /**
     * 获取文件创建时间
     *
     * @param filePath 文件路径
     * @return 返回创建文件时间戳
     */
    public static long fileCreationTime(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        BasicFileAttributeView attributeView = Files
                .getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        BasicFileAttributes attr = attributeView.readAttributes();
        return attr.creationTime().toMillis();
    }

    /**
     * 删除文件
     *
     * @param file       文件
     * @param deleteTime 删除时间
     */
    private static void deleteFile(File file, long deleteTime) throws IOException {
        long toMillis = fileCreationTime(file.getPath());
        if (toMillis > deleteTime) {
            return;
        }
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 改变图片的大小到宽为size，然后高随着宽等比例变化
     *
     * @param inputStream 上传的图片的输入流
     * @param percent     图片倍率
     * @param fileName    图片文件名称
     * @throws IOException 异常信息
     */
    public static OutputStream resizeImage(InputStream inputStream, int percent, String fileName) throws IOException {
        return resizeImage(inputStream, percent, fileName, null);
    }

    /**
     * 改变图片的大小到宽为size，然后高随着宽等比例变化
     *
     * @param inputStream 上传的图片的输入流
     * @param percent     图片倍率
     * @param fileName    图片文件名称
     * @param bgColor     背景色
     * @throws IOException 异常信息
     */
    public static OutputStream resizeImage(InputStream inputStream, int percent, String fileName, Color bgColor) throws IOException {
        // 改变了图片的大小后，把图片的流输出到目标 OutputStream
        OutputStream os = new ByteArrayOutputStream();
        BufferedImage prevImage = ImageIO.read(inputStream);
        double width = prevImage.getWidth();
        double height = prevImage.getHeight();
        int newWidth = (int) (width * percent);
        int newHeight = (int) (height * percent);
        BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = image.createGraphics();
        if (bgColor != null) {
            graphics.drawImage(prevImage, IntegerConsts.ZERO, IntegerConsts.ZERO, newWidth, newHeight, bgColor, null);
        } else {
            graphics.drawImage(prevImage, IntegerConsts.ZERO, IntegerConsts.ZERO, newWidth, newHeight, null);
        }
        String format = getFileSuffix(fileName).replace(StringUtils.POINT, StringUtils.Empty);
        ImageIO.write(image, format, os);
        os.flush();
        inputStream.close();
        os.close();
        return os;
    }

    /**
     * 压缩图片（通过降低图片质量）
     *
     * @param quality 图片质量（0-1）
     * @return byte[] 返回数据结果
     */
    public static byte[] compressPictures(String filePath, float quality) {
        File file = new File(filePath);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logUtil.error(e);
        }
        if (inputStream != null) {
            return compressPictures(inputStream, quality);
        } else {
            return null;
        }
    }

    /**
     * 压缩图片（通过降低图片质量）
     *
     * @param quality 图片质量（0-1）
     * @return byte[] 返回数据结果
     */
    public static byte[] compressPictures(byte[] bytes, float quality) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        return compressPictures(inputStream, quality);
    }

    /**
     * 压缩图片（通过降低图片质量）
     *
     * @param quality 图片质量（0-1）
     * @return byte[] 返回数据结果
     */
    public static byte[] compressPictures(InputStream byteInput, float quality) {
        byte[] imgBytes = null;
        try {
          BufferedImage image = ImageIO.read(byteInput);

          // 如果图片空，返回空
          if (image == null) {
            return null;
          }
          // 得到指定Format图片的writer（迭代器）
          Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
          // 得到writer
          ImageWriter writer = (ImageWriter) iter.next();
          // 得到指定writer的输出参数设置(ImageWriteParam )
          ImageWriteParam iwp = writer.getDefaultWriteParam();
          // 设置可否压缩
          iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
          // 设置压缩质量参数
          iwp.setCompressionQuality(quality);

          iwp.setProgressiveMode(ImageWriteParam.MODE_DISABLED);

          ColorModel colorModel = ColorModel.getRGBdefault();
          // 指定压缩时使用的色彩模式
          iwp.setDestinationType(
                  new javax.imageio.ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)));

          // 开始打包图片，写入byte[]
          // 取得内存输出流
          ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
          IIOImage iioImage = new IIOImage(image, null, null);

          // 此处因为ImageWriter中用来接收write信息的output要求必须是ImageOutput
          // 通过ImageIo中的静态方法，得到byteArrayOutputStream的ImageOutput
          writer.setOutput(ImageIO.createImageOutputStream(byteArrayOutputStream));
          writer.write(null, iioImage, iwp);
          imgBytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
          logUtil.error(e);
        } finally {
          return imgBytes;
        }
    }

  /**
   * 验证文件的魔术数字
   *
   * @param inputStream 文件流
   * @param typeEnums   文件类型
   * @return 返回结果
   */
  public static boolean validFileTypeByMagicNumber(InputStream inputStream, List<FileTypeEnums> typeEnums) {
		for (FileTypeEnums fileType :
				typeEnums) {
			byte[] magicNumbers = null;
			switch (fileType) {
				case PNG:
					magicNumbers = FileTypeConsts.PNG;
					break;
				case GIF:
					magicNumbers = FileTypeConsts.GIF;
					break;
				case JPG:
					magicNumbers = FileTypeConsts.JPG;
					break;
				case BMP:
					magicNumbers = FileTypeConsts.BMP;
					break;
				case TAR:
					magicNumbers = FileTypeConsts.TAR;
					break;
				case ZIP:
					magicNumbers = FileTypeConsts.ZIP;
					break;
			}
			if (magicNumbers == null) {
				continue;
			}
			byte[] header = new byte[magicNumbers.length];
			try {
				inputStream.read(header);
				for (int i = 0; i < magicNumbers.length; i++) {
					if (magicNumbers[i] != header[i]) {
						return false;
					}
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return true;
	}

	/**
	 * 计算文件的 MD5 哈希值
	 *
	 * @param fileBytes 文件字节数组
	 * @return MD5 哈希值
	 * @throws NoSuchAlgorithmException 如果找不到指定的加密算法，则抛出此异常
	 */
	public static String calculateHash(byte[] fileBytes) throws NoSuchAlgorithmException {
		String algorithm = "MD5";
		MessageDigest md = MessageDigest.getInstance(algorithm);
		byte[] digest = md.digest(fileBytes);
		StringBuilder sb = new StringBuilder();
		for (byte b : digest) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
}
