package com.cdkjframework.minio.enums;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.minio.enums
 * @ClassName: ContentTypeEnums
 * @Description: 内容类型枚举
 * @Author: xiaLin
 * @Date: 2024/9/2 13:30
 * @Version: 1.0
 */
public enum ContentTypeEnums {
	/**
	 * 默认类型
	 */
	DEFAULT("default", "application/octet-stream"),
	JPG("jpg", "image/jpeg"),
	TIFF("tiff", "image/tiff"),
	GIF("gif", "image/gif"),
	JFIF("jfif", "image/jpeg"),
	PNG("png", "image/png"),
	TIF("tif", "image/tiff"),
	ICO("ico", "image/x-icon"),
	JPEG("jpeg", "image/jpeg"),
	WBMP("wbmp", "image/vnd.wap.wbmp"),
	FAX("fax", "image/fax"),
	NET("net", "image/pnetvue"),
	JPE("jpe", "image/jpeg"),
	RP("rp", "image/vnd.rn-realpix"),
	MP4("mp4", "video/mp4");

	/**
	 * 文件名后缀
	 */
	private final String suffix;

	/**
	 * 返回前端请求头中，Content-Type具体的值
	 */
	private final String value;

	ContentTypeEnums(String suffix, String value) {
		this.suffix = suffix;
		this.value = value;
	}

	/**
	 * 根据文件后缀，获取Content-Type
	 *
	 * @param suffix 文件后缀
	 * @return 返回结果
	 */
	public static String formContentType(String suffix) {
		if (suffix == null || "".equals(suffix.trim())) {
			return DEFAULT.getValue();
		}
		suffix = suffix.substring(suffix.lastIndexOf(".") + 1);
		for (ContentTypeEnums value : ContentTypeEnums.values()) {
			if (suffix.equalsIgnoreCase(value.getSuffix())) {
				return value.getValue();
			}
		}
		return DEFAULT.getValue();
	}

	public String getSuffix() {
		return suffix;
	}

	public String getValue() {
		return value;
	}
}
