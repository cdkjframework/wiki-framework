package com.cdkjframework.util.encrypts;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.user.UserEntity;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.mapper.ReflectionUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.encrypts
 * @ClassName: DesensitizationUtils
 * @Description: 敏感数据脱敏工具类
 * @Author: xiaLin
 * @Date: 2022/10/5 11:04
 * @Version: 1.0
 */
@Component
public class DesensitizationUtils {

	/**
	 * 定义所有常量
	 */
	public static final int ONE = 1;
	public static final int TWO = 2;
	/**
	 * 日志
	 */
	private static LogUtils logUtils = LogUtils.getLogger(DesensitizationUtils.class);

	/**
	 * 脱敏加密
	 *
	 * @param keywords 关键词
	 * @return 返回结果
	 */
	public static String encode(String keywords) {
		StringBuffer buffer = new StringBuffer();
		char[] chars = keywords.toCharArray();
		CustomConfig config = new CustomConfig();
		AesUtils aes = new AesUtils(config);
		for (char c :
				chars) {
			try {
				buffer.append(AesUtils.base64Encode(String.valueOf(c)))
						.append(StringUtils.COMMA);
			} catch (Exception e) {
				logUtils.error(e);
			}
		}
		// 返回结果
		return buffer.toString();
	}

	/**
	 * 脱敏加密
	 *
	 * @param keywords 关键词
	 * @return 返回结果
	 */
	public static <T> void encode(T keywords) {
		encode(keywords, new String[IntegerConsts.ZERO]);
	}

	/**
	 * 脱敏加密
	 *
	 * @param keywords 关键词
	 * @param fields   字段
	 * @return 返回结果
	 */
	public static <T> void encode(T keywords, String... fields) {
		entityProcess(keywords, Boolean.TRUE, fields);
	}

	/**
	 * 脱敏解密
	 *
	 * @param keywords 关键词
	 * @return 返回密码结果
	 */
	public static String decrypt(String keywords) {
		String[] keys = keywords.split(StringUtils.COMMA);
		StringBuffer buffer = new StringBuffer();
		CustomConfig config = new CustomConfig();
		AesUtils aes = new AesUtils(config);
		for (String key :
				keys) {
			try {
				buffer.append(AesUtils.base64Decrypt(key));
			} catch (Exception e) {
				logUtils.error(e);
			}
		}
		// 返回结果
		return buffer.toString();
	}

	/**
	 * 脱敏解密
	 *
	 * @param keywords 关键词
	 * @return 返回密码结果
	 */
	public static <T> void decrypt(T keywords) {
		decrypt(keywords, new String[IntegerConsts.ZERO]);
	}

	/**
	 * 脱敏解密
	 *
	 * @param keywords 关键词
	 * @param fields   字段
	 * @return 返回密码结果
	 */
	public static <T> void decrypt(T keywords, String... fields) {
		entityProcess(keywords, false, fields);
	}

	/**
	 * 实体处理
	 *
	 * @param keywords 实体
	 * @param isEncode 是否加密
	 * @param fields   字段
	 * @param <T>      实体类型
	 */
	private static <T> void entityProcess(T keywords, boolean isEncode, String... fields) {
		List<Field> fieldList = ReflectionUtils.getDeclaredFields(keywords);
		if (fields == null) {
			fields = new String[IntegerConsts.ZERO];
		}
		for (Field field :
				fieldList) {
			Optional<String> optional = Arrays.stream(fields).filter(f -> f.equals(field.getName()))
					.findFirst();
			if (fields.length > IntegerConsts.ZERO && !optional.isPresent()) {
				continue;
			}
			Object value = ReflectionUtils.getFieldValue(field, keywords);
			if (StringUtils.isNullAndSpaceOrEmpty(value) || !value.getClass().equals(String.class)) {
				continue;
			}
			String keyValue;
			if (isEncode) {
				keyValue = encode(value.toString());
			} else {
				keyValue = decrypt(value.toString());
			}
			ReflectionUtils.setFieldValue(keywords, field, keyValue);
		}
	}

	/**
	 * 身份证号脱敏
	 *
	 * @param idCard 身份证号
	 * @return 脱敏后的身份证号
	 */
	public static String idCardDesensitization(String idCard) {
		if (StringUtils.isNotNullAndEmpty(idCard)) {
			// 身份证号脱敏规则一：保留前六后三
			if (idCard.length() == 15) {
				idCard = idCard.replaceAll("(\\w{6})\\w*(\\w{3})", "$1******$2");
			} else if (idCard.length() == 18) {
				idCard = idCard.replaceAll("(\\w{6})\\w*(\\w{3})", "$1*********$2");
			}
			// 身份证号脱敏规则二：保留前三后四
			// idCard = idCard.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
		}
		return idCard;
	}

	/**
	 * 手机号码脱敏
	 *
	 * @param mobilePhone 手机号码
	 * @return 脱敏后的手机号码
	 */
	public static String mobilePhoneDesensitization(String mobilePhone) {
		// 手机号码保留前三后四
		if (StringUtils.isNotNullAndEmpty(mobilePhone)) {
			mobilePhone = mobilePhone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
		}
		return mobilePhone;
	}

	/**
	 * 电子邮箱脱敏
	 *
	 * @param email 邮箱
	 * @return 脱敏后的邮箱
	 */
	public static String emailDesensitization(String email) {
		// 电子邮箱隐藏@前面的3个字符
		if (StringUtils.isNotNullAndEmpty(email)) {
			return email;
		}
		String encrypt = email.replaceAll("(\\w+)\\w{3}@(\\w+)", "$1***@$2");
		if (email.equalsIgnoreCase(encrypt)) {
			encrypt = email.replaceAll("(\\w*)\\w{1}@(\\w+)", "$1*@$2");
		}
		return encrypt;
	}

	/**
	 * 银行账号脱敏
	 *
	 * @param accountNo 银行账号
	 * @return 脱敏
	 */
	public static String accountNoDesensitization(String accountNo) {
		// 银行账号保留前六后四
		if (StringUtils.isNotNullAndEmpty(accountNo)) {
			String regex = "(\\w{6})(.*)(\\w{4})";
			Matcher m = Pattern.compile(regex).matcher(accountNo);
			if (m.find()) {
				String rep = m.group(2);
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < rep.length(); i++) {
					sb.append("*");
				}
				accountNo = accountNo.replaceAll(rep, sb.toString());
			}
		}
		return accountNo;
	}

	/**
	 * 客户名称脱敏
	 *
	 * @param customerName 客户名称
	 * @return 脱敏后的客户名称
	 */
	public static String customerNameDesensitization(String customerName) {
		// 规则说明：
		// 姓名：字符长度小于5位；企业名称：字符长度大于等于5位。
		// 姓名规则
		// 规则一：1个字则不脱敏，如"张"-->"张"
		// 规则二：2个字则脱敏第二个字，如"张三"-->"张*"
		// 规则三：3个字则脱敏第二个字，如"张三丰"-->"张*丰"
		// 规则四：4个字则脱敏中间两个字，如"易烊千玺"-->"易**玺"
		// 企业名称规则：
		// 从第4位开始隐藏，最多隐藏6位。

		if (StringUtils.isNotNullAndEmpty(customerName)) {
			char[] chars = customerName.toCharArray();
			// 表示姓名
			if (chars.length < 5) {
				if (chars.length > 1) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < chars.length - 2; i++) {
						sb.append("*");
					}
					customerName = customerName.replaceAll(customerName.substring(1, chars.length - 1), sb.toString());
				}
			} else {// 企业名称
				int start = 4;
				// 第一部分
				String str1 = customerName.substring(0, start);
				// 第二部分
				String str2 = "";
				if (chars.length == 5) {
					str2 = "*";
				} else if (chars.length == 6) {
					str2 = "**";
				} else if (chars.length == 7) {
					str2 = "***";
				} else if (chars.length == 8) {
					str2 = "****";
				} else if (chars.length == 9) {
					str2 = "*****";
				} else {
					str2 = "******";
				}
				// 通过计算得到第三部分需要从第几个字符截取
				int subIndex = start + str2.length();
				// 第三部分
				String str3 = customerName.substring(subIndex);
				StringBuffer sb = new StringBuffer();
				sb.append(str1);
				sb.append(str2);
				sb.append(str3);
				customerName = sb.toString();
			}
		}
		return customerName;
	}

	/**
	 * 家庭地址脱敏
	 *
	 * @param address 地址
	 * @return 返回脱敏后的地址
	 */
	public static String addressDesensitization(String address) {
		// 规则说明：从第4位开始隐藏，隐藏8位。
		if (StringUtils.isNotNullAndEmpty(address)) {
			char[] chars = address.toCharArray();
			// 由于需要从第4位开始，隐藏8位，因此数据长度必须大于11位
			if (chars.length > 11) {
				// 获取第一部分内容
				String str1 = address.substring(0, 4);
				// 获取第二部分
				String str2 = "********";
				// 获取第三部分
				String str3 = address.substring(12);
				StringBuffer sb = new StringBuffer();
				sb.append(str1);
				sb.append(str2);
				sb.append(str3);
				address = sb.toString();
			}
		}
		return address;
	}

	/**
	 * 姓名脱敏
	 *
	 * @param realName 姓名
	 * @return 返回脱敏后的姓名
	 */
	public static String desensitizedName(String realName) {
		if (realName == null) {
			return null;
		}
		if (realName.length() == ONE) {
			return realName;
		} else if (realName.length() == TWO) {
			return realName.substring(0, 1) + "*";
		} else {
			Integer length = realName.length();
			StringBuffer middle = new StringBuffer();
			for (int i = 0; i < realName.substring(1, length - 1).length(); i++) {
				middle.append("*");
			}
			return realName.substring(0, 1) + middle + realName.substring(length - 1, length);
		}
	}

	/**
	 * 详细地址脱敏
	 *
	 * @param address 地址信息
	 * @return 返回脱敏后的地址信息
	 */
	public static String desensitizedAddress(String address) {
		//江西省宜春市丰城市剑南街道人才教育小区41号丰城住总运营有限公司-->江西省宜春市丰城市剑南街道人才教育小区*************
		if (StringUtils.isNotNullAndEmpty(address)) {
			int length = address.length();
			int indes = address.indexOf("区");
			if (indes == -1) {
				indes = address.indexOf("市");
			}
			address = address.substring(0, indes + 1);
			StringBuffer middle = new StringBuffer();
			for (int i = 0; i < length - indes; i++) {
				middle.append("*");
			}
			return address + middle;
		}
		return address;
	}

	/**
	 * 对字符串进行脱敏操作
	 *
	 * @param origin          原始字符串
	 * @param prefixNoMaskLen 左侧需要保留几位明文字段
	 * @param suffixNoMaskLen 右侧需要保留几位明文字段
	 * @param maskStr         用于遮罩的字符串, 如'*'
	 * @return 脱敏后结果
	 */
	public static String desValue(String origin, int prefixNoMaskLen, int suffixNoMaskLen, String maskStr) {
		if (origin == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0, n = origin.length(); i < n; i++) {
			if (i < prefixNoMaskLen) {
				sb.append(origin.charAt(i));
				continue;
			}
			if (i > (n - suffixNoMaskLen - 1)) {
				sb.append(origin.charAt(i));
				continue;
			}
			sb.append(maskStr);
		}
		return sb.toString();
	}

	/**
	 * 中文姓名，只显示最后一个汉字，其他隐藏为星号，比如：**梦
	 *
	 * @param fullName
	 * @return
	 */
	public static String chineseName(String fullName) {
		if (fullName == null) {
			return null;
		}
		return desValue(fullName, 0, 1, "*");
	}

	public static void main(String[] args) {
		UserEntity user = new UserEntity();
		user.setCellphone("15928658740");
		System.out.println("user:" + user.getCellphone());
		encode(user);
		System.out.println("encodeUser:" + user.getCellphone());
		decrypt(user);
		System.out.println("decryptUser:" + user.getCellphone());
	}

}
