package com.cdkjframework.minio;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.minio.enums.ContentTypeEnums;
import com.cdkjframework.util.tool.StringUtils;
import com.google.common.collect.Sets;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.minio
 * @ClassName: MinioUtils
 * @Description: minio工具类
 * @Author: xiaLin
 * @Date: 2024/9/2 13:32
 * @Version: 1.0
 */
public class MinioUtils {

	/**
	 * 默认的临时文件存储桶
	 */
	private static final String DEFAULT_TEMP_BUCKET_NAME = "temp-bucket";

	/**
	 * minio客户端
	 */
	private static MinioClient client = null;

	/**
	 * 构造函数
	 */
	public MinioUtils(MinioClient client) {
		MinioUtils.client = client;
	}

	/**
	 * 将URLDecoder编码转成UTF8
	 *
	 * @param str 待转码的字符串
	 * @return 转码后的字符串
	 */
	public static String getUtf8ByURLDecoder(String str) throws UnsupportedEncodingException {
		String url = str.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
		return URLDecoder.decode(url, StandardCharsets.UTF_8);
	}

	/**
	 * 把路径开头的"/"去掉，并在末尾添加"/"，这个是minio对象名的样子。
	 *
	 * @param projectPath 以"/"开头、以字母结尾的路径
	 * @return 去掉开头"/"
	 */
	private static String trimHead(String projectPath) {
		return projectPath.substring(IntegerConsts.ONE);
	}

	/**
	 * 把路径开头的"/"去掉，并在末尾添加"/"，这个是minio对象名的样子。
	 *
	 * @param projectPath 以"/"开头、以字母结尾的路径
	 * @return 添加结尾"/"
	 */
	private static String addTail(String projectPath) {
		return projectPath + StringUtils.BACKSLASH;
	}

	/**
	 * 获取数值的位数（用于构造临时文件名）
	 *
	 * @param number
	 * @return
	 */
	private static int countDigits(int number) {
		if (number == IntegerConsts.ZERO) {
			// 0 本身有一位
			return IntegerConsts.ONE;
		}
		int count = IntegerConsts.ZERO;
		while (number != IntegerConsts.ZERO) {
			number /= IntegerConsts.TEN;
			count++;
		}
		return count;
	}

	/**
	 * 检查是否空
	 *
	 * @param objects 待检查的对象
	 */
	private static void checkNull(Object... objects) {
		for (Object o : objects) {
			if (o == null) {
				throw new GlobalRuntimeException("Null param");
			}
			if (o instanceof String && StringUtils.isNullAndSpaceOrEmpty(o)) {
				throw new GlobalRuntimeException("Empty string");
			}
		}
	}

	/**
	 * 给定一个字符串，返回其"/"符号后面的字符串
	 *
	 * @param input 输入字符串
	 * @return 截取后的字符串
	 */
	private static String getContentAfterSlash(String input) {
		if (StringUtils.isNullAndSpaceOrEmpty(input)) {
			return StringUtils.Empty;
		}
		int slashIndex = input.indexOf(StringUtils.BACKSLASH);
		if (slashIndex != IntegerConsts.MINUS_ONE && slashIndex < input.length() - IntegerConsts.ONE) {
			return input.substring(slashIndex + IntegerConsts.ONE);
		}
		return StringUtils.Empty;
	}

	/**
	 * 判断Bucket是否存在
	 *
	 * @return true：存在，false：不存在
	 */
	private static boolean bucketExists(String bucketName) throws Exception {
		return client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
	}

	/**
	 * 如果一个桶不存在，则创建该桶
	 */
	public static void createBucket(String bucketName) throws Exception {
		if (!bucketExists(bucketName)) {
			client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
		}
	}

	/**
	 * 获取 Bucket 的相关信息
	 */
	public static Optional<Bucket> getBucketInfo(String bucketName) throws Exception {
		return client.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
	}

	/**
	 * 使用MultipartFile进行文件上传
	 *
	 * @param bucketName  存储桶
	 * @param file        文件
	 * @param fileName    对象名
	 * @param contentType 类型
	 * @return
	 * @throws Exception
	 */
	public static ObjectWriteResponse uploadFile(String bucketName, MultipartFile file,
																							 String fileName, ContentTypeEnums contentType) throws Exception {
		InputStream inputStream = file.getInputStream();
		return client.putObject(
				PutObjectArgs.builder()
						.bucket(bucketName)
						.object(fileName)
						.contentType(contentType.getValue())
						.stream(inputStream, inputStream.available(), IntegerConsts.MINUS_ONE)
						.build());
	}

	/**
	 * 将文件进行分片上传
	 * <p>有一个未处理的bug（虽然概率很低很低）：</p>
	 * 当两个线程同时上传md5相同的文件时，由于两者会定位到同一个桶的同一个临时目录，两个线程会相互产生影响！
	 *
	 * @param file        分片文件
	 * @param currIndex   当前文件的分片索引
	 * @param totalPieces 切片总数（对于同一个文件，请确保切片总数始终不变）
	 * @param md5         整体文件MD5
	 * @return 剩余未上传的文件索引集合
	 */
	public static ResponseBuilder uploadFileFragment(MultipartFile file,
																									 Integer currIndex, Integer totalPieces, String md5) throws Exception {
		checkNull(currIndex, totalPieces, md5);
		// 把当前分片上传至临时桶
		if (!bucketExists(DEFAULT_TEMP_BUCKET_NAME)) {
			createBucket(DEFAULT_TEMP_BUCKET_NAME);
		}
		uploadFileStream(DEFAULT_TEMP_BUCKET_NAME, getFileTempPath(md5, currIndex, totalPieces), file.getInputStream());
		// 得到已上传的文件索引
		Iterable<Result<Item>> results = getFilesByPrefix(DEFAULT_TEMP_BUCKET_NAME, md5.concat(StringUtils.BACKSLASH), Boolean.FALSE);
		Set<Integer> savedIndex = Sets.newHashSet();
		boolean fileExists = Boolean.FALSE;
		for (Result<Item> item : results) {
			Integer idx = Integer.valueOf(getContentAfterSlash(item.get().objectName()));
			if (currIndex.equals(idx)) {
				fileExists = Boolean.TRUE;
			}
			savedIndex.add(idx);
		}
		// 得到未上传的文件索引
		Set<Integer> remainIndex = Sets.newTreeSet();
		for (int i = IntegerConsts.ZERO; i < totalPieces; i++) {
			if (!savedIndex.contains(i)) {
				remainIndex.add(i);
			}
		}
		if (fileExists) {
			return ResponseBuilder.failBuilder("index [" + currIndex + "] exists");
		}
		// 还剩一个索引未上传，当前上传索引刚好是未上传索引，上传完当前索引后就完全结束了。
		if (remainIndex.size() == IntegerConsts.ONE && remainIndex.contains(currIndex)) {
			return ResponseBuilder.successBuilder("completed");
		}
		return ResponseBuilder.failBuilder("index [" + currIndex + "] has been uploaded");
	}

	/**
	 * 合并分片文件，并放到指定目录
	 * 前提是之前已把所有分片上传完毕。
	 *
	 * @param bucketName  目标文件桶名
	 * @param targetName  目标文件名（含完整路径）
	 * @param totalPieces 切片总数（对于同一个文件，请确保切片总数始终不变）
	 * @param md5         文件md5
	 * @return minio原生对象，记录了文件上传信息
	 */
	public static boolean composeFileFragment(String bucketName, String targetName,
																						Integer totalPieces, String md5) throws Exception {
		checkNull(bucketName, targetName, totalPieces, md5);
		// 检查文件索引是否都上传完毕
		Iterable<Result<Item>> results = getFilesByPrefix(DEFAULT_TEMP_BUCKET_NAME, md5.concat(StringUtils.BACKSLASH), false);
		Set<String> savedIndex = Sets.newTreeSet();
		for (Result<Item> item : results) {
			savedIndex.add(item.get().objectName());
		}
		if (savedIndex.size() == totalPieces) {
			// 文件路径 转 文件合并对象
			List<ComposeSource> sourceObjectList = savedIndex.stream()
					.map(filePath -> ComposeSource.builder()
							.bucket(DEFAULT_TEMP_BUCKET_NAME)
							.object(filePath)
							.build())
					.collect(Collectors.toList());
			ObjectWriteResponse objectWriteResponse = client.composeObject(
					ComposeObjectArgs.builder()
							.bucket(bucketName)
							.object(targetName)
							.sources(sourceObjectList)
							.build());
			// 上传成功，则删除所有的临时分片文件
			List<String> filePaths = Stream.iterate(IntegerConsts.ZERO, i -> ++i)
					.limit(totalPieces)
					.map(i -> getFileTempPath(md5, i, totalPieces))
					.collect(Collectors.toList());
			Iterable<Result<DeleteError>> deleteResults = removeFiles(DEFAULT_TEMP_BUCKET_NAME, filePaths);
			// 遍历错误集合（无元素则成功）
			for (Result<DeleteError> result : deleteResults) {
				DeleteError error = result.get();
				System.err.printf("[Bigfile] 分片'%s'删除失败! 错误信息: %s", error.objectName(), error.message());
			}
			return true;
		}
		throw new GlobalException("The fragment index is not complete. Please check parameters [totalPieces] or [md5]");
	}

	/**
	 * 上传本地文件
	 *
	 * @param bucketName 存储桶
	 * @param fileName   文件名称
	 * @param filePath   本地文件路径
	 */
	public ObjectWriteResponse uploadFile(String bucketName, String fileName,
																				String filePath) throws Exception {
		return client.uploadObject(
				UploadObjectArgs.builder()
						.bucket(bucketName)
						.object(fileName)
						.filename(filePath)
						.build());
	}

	/**
	 * 通过流上传文件
	 *
	 * @param bucketName  存储桶
	 * @param fileName    文件名
	 * @param inputStream 文件流
	 */
	public static ObjectWriteResponse uploadFileStream(String bucketName, String fileName, InputStream inputStream) throws Exception {
		return client.putObject(
				PutObjectArgs.builder()
						.bucket(bucketName)
						.object(fileName)
						.stream(inputStream, inputStream.available(), IntegerConsts.MINUS_ONE)
						.build());
	}

	/**
	 * 判断文件是否存在
	 *
	 * @param bucketName 存储桶
	 * @param fileName   文件名
	 * @return true: 存在
	 */
	public static boolean isFileExist(String bucketName, String fileName) {
		boolean exist = true;
		try {
			client.statObject(StatObjectArgs.builder().bucket(bucketName).object(fileName).build());
		} catch (Exception e) {
			exist = false;
		}
		return exist;
	}

	/**
	 * 判断文件夹是否存在
	 *
	 * @param bucketName 存储桶
	 * @param folderName 目录名称：本项目约定路径是以"/"开头，不以"/"结尾
	 * @return true: 存在
	 */
	public boolean isFolderExist(String bucketName, String folderName) {
		// 去掉头"/"，才能搜索到相关前缀
		folderName = trimHead(folderName);
		boolean exist = false;
		try {
			Iterable<Result<Item>> results = client.listObjects(
					ListObjectsArgs.builder().bucket(bucketName).prefix(folderName).recursive(false).build());
			for (Result<Item> result : results) {
				Item item = result.get();
				// 增加尾"/"，才能匹配到目录名字
				String objectName = addTail(folderName);
				if (item.isDir() && objectName.equals(item.objectName())) {
					exist = true;
				}
			}
		} catch (Exception e) {
			exist = false;
		}
		return exist;
	}

	/**
	 * 获取路径下文件列表
	 *
	 * @param bucketName 存储桶
	 * @param prefix     文件名称
	 * @param recursive  是否递归查找，false：模拟文件夹结构查找
	 * @return 二进制流
	 */
	public static Iterable<Result<Item>> getFilesByPrefix(String bucketName, String prefix,
																												boolean recursive) {
		return client.listObjects(
				ListObjectsArgs.builder()
						.bucket(bucketName)
						.prefix(prefix)
						.recursive(recursive)
						.build());

	}

	/**
	 * 获取文件信息, 如果抛出异常则说明文件不存在
	 *
	 * @param bucketName 存储桶
	 * @param fileName   文件名称
	 */
	public StatObjectResponse getFileStatusInfo(String bucketName, String fileName) throws Exception {
		return client.statObject(
				StatObjectArgs.builder()
						.bucket(bucketName)
						.object(fileName)
						.build());
	}

	/**
	 * 根据文件前缀查询文件
	 *
	 * @param bucketName 存储桶
	 * @param prefix     前缀
	 * @param recursive  是否使用递归查询
	 * @return MinioItem 列表
	 */
	public List<Item> getAllFilesByPrefix(String bucketName,
																				String prefix,
																				boolean recursive) throws Exception {
		List<Item> list = new ArrayList<>();
		Iterable<Result<Item>> objectsIterator = client.listObjects(
				ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(recursive).build());
		if (objectsIterator != null) {
			for (Result<Item> o : objectsIterator) {
				Item item = o.get();
				list.add(item);
			}
		}
		return list;
	}

	/**
	 * 批量删除文件
	 *
	 * @param bucketName        存储桶
	 * @param filePaths<String> 需要删除的文件列表
	 * @return Result
	 */
	public static Iterable<Result<DeleteError>> removeFiles(String bucketName, List<String> filePaths) {
		List<DeleteObject> objectPaths = filePaths.stream()
				.map(filePath -> new DeleteObject(filePath))
				.collect(Collectors.toList());
		return client.removeObjects(
				RemoveObjectsArgs.builder().bucket(bucketName).objects(objectPaths).build());
	}

	/**
	 * 获取文件的二进制流
	 *
	 * @param bucketName 存储桶
	 * @param fileName   文件名
	 * @return 二进制流
	 */
	public InputStream getFileStream(String bucketName, String fileName) throws Exception {
		return client.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
	}

	/**
	 * 断点下载
	 *
	 * @param bucketName 存储桶
	 * @param fileName   文件名称
	 * @param offset     起始字节的位置
	 * @param length     要读取的长度
	 * @return 二进制流
	 */
	public InputStream getFileStream(String bucketName, String fileName, long offset, long length) throws Exception {
		return client.getObject(
				GetObjectArgs.builder()
						.bucket(bucketName)
						.object(fileName)
						.offset(offset)
						.length(length)
						.build());
	}

	/**
	 * 拷贝文件
	 *
	 * @param bucketName    存储桶
	 * @param fileName      文件名
	 * @param srcBucketName 目标存储桶
	 * @param srcFileName   目标文件名
	 */
	public ObjectWriteResponse copyFile(String bucketName, String fileName,
																			String srcBucketName, String srcFileName) throws Exception {
		return client.copyObject(
				CopyObjectArgs.builder()
						.source(CopySource.builder().bucket(bucketName).object(fileName).build())
						.bucket(srcBucketName)
						.object(srcFileName)
						.build());
	}

	/**
	 * 删除文件夹(未完成)
	 *
	 * @param bucketName 存储桶
	 * @param fileName   路径
	 */
	@Deprecated
	public void removeFolder(String bucketName, String fileName) throws Exception {
		// 加尾
		fileName = addTail(fileName);
		client.removeObject(
				RemoveObjectArgs.builder()
						.bucket(bucketName)
						.object(fileName)
						.build());
	}

	/**
	 * 删除文件
	 *
	 * @param bucketName 存储桶
	 * @param fileName   文件名称
	 */
	public void removeFile(String bucketName, String fileName) throws Exception {
		// 掐头
		fileName = trimHead(fileName);
		client.removeObject(
				RemoveObjectArgs.builder()
						.bucket(bucketName)
						.object(fileName)
						.build());
	}

	/**
	 * 获得文件外链
	 *
	 * @param bucketName 存储桶
	 * @param fileName   文件名
	 * @return url 返回地址
	 * @throws Exception
	 */
	public static String getPresignedObjectUrl(String bucketName, String fileName) throws Exception {
		GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
				.bucket(bucketName)
				.object(fileName)
				.method(Method.GET).build();
		return client.getPresignedObjectUrl(args);
	}

	/**
	 * 获取文件外链
	 *
	 * @param bucketName 存储桶
	 * @param fileName   文件名
	 * @param expires    过期时间 <=7 秒 （外链有效时间（单位：秒））
	 * @return url
	 * @throws Exception
	 */
	public String getPresignedObjectUrl(String bucketName, String fileName, Integer expires) throws Exception {
		GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
				.method(Method.GET)
				.expiry(expires, TimeUnit.SECONDS)
				.bucket(bucketName)
				.object(fileName)
				.build();
		return client.getPresignedObjectUrl(args);
	}

	/**
	 * 通过文件的md5，以及分片文件的索引，构造分片文件的临时存储路径
	 *
	 * @param md5         文件md5
	 * @param currIndex   分片文件索引（从0开始）
	 * @param totalPieces 总分片
	 * @return 临时存储路径
	 */
	private static String getFileTempPath(String md5, Integer currIndex, Integer totalPieces) {

		int zeroCnt = countDigits(totalPieces) - countDigits(currIndex);
		StringBuilder name = new StringBuilder(md5);
		name.append(StringUtils.BACKSLASH);
		for (int i = IntegerConsts.ZERO; i < zeroCnt; i++) {
			name.append(IntegerConsts.ZERO);
		}
		name.append(currIndex);
		return name.toString();
	}

	/**
	 * 创建目录
	 *
	 * @param bucketName 存储桶
	 * @param folderName 目录路径：本项目约定路径是以"/"开头，不以"/"结尾
	 */
	public ObjectWriteResponse createFolder(String bucketName, String folderName) throws Exception {
		// 这是minio的bug，只有在路径的尾巴加上"/"，才能当成文件夹。
		folderName = addTail(folderName);
		return client.putObject(
				PutObjectArgs.builder()
						.bucket(bucketName)
						.object(folderName)
						.stream(new ByteArrayInputStream(new byte[]{}), IntegerConsts.ZERO, IntegerConsts.MINUS_ONE)
						.build());
	}

}
