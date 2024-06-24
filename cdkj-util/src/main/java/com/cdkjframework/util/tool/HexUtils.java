package com.cdkjframework.util.tool;

import com.cdkjframework.constant.IntegerConsts;

/**
 * @ProjectName: com.lesmarthome.iot
 * @Package: com.lesmarthome.iot.util
 * @ClassName: HexUtils
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public class HexUtils {

  /**
   * 用于建立十六进制字符的输出的小写字符数组
   */
  private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
      '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

  /**
   * 用于建立十六进制字符的输出的大写字符数组
   */
  private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5',
      '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

  /**
   * @param data byte[]
   * @return 十六进制char[]
   * @author Herman.Xiong
   * @date 2014年5月5日 17:06:52
   * 将字节数组转换为十六进制字符数组
   */
  public static char[] encodeHex(byte[] data) {
    return encodeHex(data, true);
  }

  /**
   * @param data        byte[]
   * @param toLowerCase true传换成小写格式 ，false传换成大写格式
   * @return 十六进制char[]
   * @author Herman.Xiong
   * @date 2014年5月5日 17:07:14
   * 将字节数组转换为十六进制字符数组
   */
  public static char[] encodeHex(byte[] data, boolean toLowerCase) {
    return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
  }

  /**
   * @param data     byte[]
   * @param toDigits 用于控制输出的char[]
   * @return 十六进制char[]
   * @author Herman.Xiong
   * @date 2014年5月5日 17:07:31
   * 将字节数组转换为十六进制字符数组
   */
  protected static char[] encodeHex(byte[] data, char[] toDigits) {
    int l = data.length;
    char[] out = new char[l << 1];
    for (int i = 0, j = 0; i < l; i++) {
      out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
      out[j++] = toDigits[0x0F & data[i]];
    }
    return out;
  }

  /**
   * @param data byte[]
   * @return 十六进制String
   * @date 2014年5月5日 17:07:43
   * @author Herman.Xiong
   * 将字节数组转换为十六进制字符串
   */
  public static String encodeHexStr(byte[] data) {
    return encodeHexStr(data, true);
  }

  /**
   * @param data        byte[]
   * @param toLowerCase true 传换成小写格式 ， false 传换成大写格式
   * @return 十六进制String
   * @author Herman.Xiong
   * @date 2014年5月5日 17:08:01
   * 将字节数组转换为十六进制字符串
   */
  public static String encodeHexStr(long data, boolean toLowerCase) {
    return encodeHexStr(String.valueOf(data).getBytes(), toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
  }

  /**
   * @param data        byte[]
   * @param toLowerCase true 传换成小写格式 ， false 传换成大写格式
   * @return 十六进制String
   * @author Herman.Xiong
   * @date 2014年5月5日 17:08:01
   * 将字节数组转换为十六进制字符串
   */
  public static String encodeHexStr(byte[] data, boolean toLowerCase) {
    return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
  }

  /**
   * @param data     byte[]
   * @param toDigits 用于控制输出的char[]
   * @return 十六进制String
   * @author Herman.Xiong
   * @date 2014年5月5日 17:08:15
   * 将字节数组转换为十六进制字符串
   */
  protected static String encodeHexStr(byte[] data, char[] toDigits) {
    return new String(encodeHex(data, toDigits));
  }

  /**
   * @param data 十六进制char[]
   * @return byte[]
   * @throws RuntimeException 如果源十六进制字符数组是一个奇怪的长度，将抛出运行时异常
   * @author Herman.Xiong
   * @date 2014年5月5日 17:08:28
   * 将十六进制字符数组转换为字节数组
   */
  public static byte[] decodeHex(char[] data) {
    int len = data.length;
    if ((len & 0x01) != 0) {
      throw new RuntimeException("未知的字符");
    }
    byte[] out = new byte[len >> 1];
    for (int i = 0, j = 0; j < len; i++) {
      int f = toDigit(data[j], j) << 4;
      j++;
      f = f | toDigit(data[j], j);
      j++;
      out[i] = (byte) (f & 0xFF);
    }
    return out;
  }


  /**
   * hex字符串转byte数组
   *
   * @param inHex 待转换的Hex字符串
   * @return 转换后的byte数组结果
   */
  public static byte[] hexToByteArray(String inHex) {
    int hexLen = inHex.length();
    byte[] result;
    if (hexLen % 2 == 1) {
      //奇数
      hexLen++;
      result = new byte[(hexLen / 2)];
      inHex = "0" + inHex;
    } else {
      //偶数
      result = new byte[(hexLen / 2)];
    }
    int j = 0;
    for (int i = 0; i < hexLen; i += 2) {
      result[j] = hexToByte(inHex.substring(i, i + 2));
      j++;
    }
    return result;
  }

  /**
   * char数组转hex字符串
   *
   * @param chars char数组
   * @return 转换后的hex字符串
   */
  public static String charArrayToHex(char[] chars) {
    return charArrayToHex(chars, Boolean.TRUE);
  }

  /**
   * char数组转hex字符串
   *
   * @param chars       char数组
   * @param toLowerCase 是否大写
   * @return 转换后的hex字符串
   */
  public static String charArrayToHex(char[] chars, boolean toLowerCase) {
    StringBuffer buffer = new StringBuffer();
    for (char c :
        chars) {
      buffer.append(Integer.toHexString(c));
    }
    return toLowerCase ? buffer.toString().toUpperCase() : buffer.toString();
  }

  /**
   * Hex字符串转byte
   *
   * @param inHex 待转换的Hex字符串
   * @return 转换后的byte
   */
  public static byte hexToByte(String inHex) {
    return (byte) Integer.parseInt(inHex, IntegerConsts.SIXTEEN);
  }

  /**
	 * 字节转换为整数
	 *
	 * @param ch    十六进制char
	 * @param index 十六进制字符在字符数组中的位置
	 * @return 一个整数
	 * @throws RuntimeException 当ch不是一个合法的十六进制字符时，抛出运行时异常
	 * @author Herman.Xiong
	 * @date 2014年5月5日 17:08:46
	 * 将十六进制字符转换成一个整数
	 */
	protected static int toDigit(char ch, int index) {
		int digit = Character.digit(ch, IntegerConsts.SIXTEEN);
		if (digit == -1) {
			throw new RuntimeException("非法16进制字符 " + ch
					+ " 在索引 " + index);
		}
		return digit;
	}

	/**
	 * 16进制转10进制
	 *
	 * @param hex 16进制字符
	 * @return 返回结果
	 */
	public static int toDigit(String hex) {
		return Integer.parseInt(hex, IntegerConsts.SIXTEEN);
	}

	/**
	 * 16进制转 long
	 *
	 * @param hex 16进制字符
	 * @return 返回结果
	 */
	public static Long toLong(String hex) {
		return Long.parseLong(hex, IntegerConsts.SIXTEEN);
	}

	/**
	 * 字节转十六进制
	 *
	 * @param data byte数组
	 * @return String 转换后的字符串
	 * 将byte[]数组转换为String字符串
	 */
	public static String byteToHexString(byte[] data) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			result.append(Integer.toHexString((data[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3));
		}
		return result.toString();
	}

	/**
	 * 字节转十六进制
	 *
	 * @param data byte数组
	 * @return String 转换后的字符串
	 * 将byte[]数组转换为String字符串
	 */
	public static String byteToHexString(byte data) {
		return Integer.toHexString((data & 0xFF) | 0x100).toUpperCase().substring(1, 3);
	}

  /**
   * 将字节转换为字符串
   *
   * @param src   字节
   * @param start 开始
   * @param end   结束
   * @return 返回字符
   */
  public static String bytesToHexString(byte[] src, int start, int end) {
    StringBuilder stringBuilder = new StringBuilder("");
    if (src == null || src.length <= 0) {
      return null;
    }
    for (int i = start; i < end; i++) {
      int v = src[i] & 0xFF;
      String hv = Integer.toHexString(v);
      if (hv.length() < 2) {
        stringBuilder.append(0);
      }
      stringBuilder.append(hv);
    }
    return stringBuilder.toString().toUpperCase();
  }


  /**
   * 16进制转换成为string类型字符串
   *
   * @param str
   * @return
   */
  public static String hexStringToString(String str) {
    if (StringUtils.isNullAndSpaceOrEmpty(str)) {
      return null;
    }
    str = str.replace(" ", StringUtils.Empty);
    byte[] baKeyword = new byte[str.length() / 2];
    for (int i = 0; i < baKeyword.length; i++) {
      try {
        baKeyword[i] = (byte) (0xff & Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16));
      } catch (Exception e) {
        e.printStackTrace();
			}
		}
		try {
			str = new String(baKeyword, "UTF-8");
			new String();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return str;
	}

	/**
	 * 字符串转化成为16进制字符串
	 *
	 * @param str 字符串
	 * @return 返回结果
	 */
	public static String strToHex(String str) {
		String strValue = StringUtils.Empty;
		for (int i = 0; i < str.length(); i++) {
			int ch = (int) str.charAt(i);
			String s4 = Integer.toHexString(ch);
			strValue = strValue + s4;
		}
		return strValue;
	}

	/**
	 * 10进制转换为16进制
	 *
	 * @param n           整数
	 * @param toUpperCase 是否大写
	 * @return: 返回16进制字符串
	 */
	public static String intToHex(int n, boolean toUpperCase) {
		StringBuffer buffer = new StringBuffer();
		String hex;
		char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		while (n != 0) {
			buffer = buffer.append(b[n % 16]);
			n = n / 16;
		}
		hex = buffer.reverse().toString();
		if ("".equals(hex)) {
			hex = "00";
		}
		if (hex.length() % IntegerConsts.TWO > IntegerConsts.ZERO) {
			hex = "0" + hex;
		}
		return toUpperCase ? hex.toUpperCase() : hex;
	}

	/**
	 * long转16进制
	 *
	 * @param n           数据
	 * @param toUpperCase 是否大写
	 * @return
	 */
	public static String longToHex(long n, boolean toUpperCase) {
		StringBuffer hex = new StringBuffer();
		hex.append(Long.toHexString(n));
		if (hex.length() % IntegerConsts.TWO > IntegerConsts.ZERO) {
			hex.insert(IntegerConsts.ZERO, StringUtils.ZERO);
		}

		// 返回结果
		return toUpperCase ? hex.toString().toLowerCase() : hex.toString();
	}
}
