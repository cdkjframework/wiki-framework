package com.cdkjframework.util.make;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.tool.StringUtils;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Random;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.make
 * @ClassName: IdCardUtils
 * @Description: 身份证号生成示例
 * @Author: xiaLin
 * @Date: 2023/6/9 15:16
 * @Version: 1.0
 */
public class IdCardUtils {

  private static final
  int[] weights = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

  /**
   * 生成身份证号
   *
   * @return 返回结果
   */
  public static String generateIdCard(String areaId, String gender, String birthday) {
    // 生成身份证前17位
    StringBuilder idBuilder = new StringBuilder(areaId);
    idBuilder.append(birthday);
    Random random = new Random();
    for (int i = IntegerConsts.ZERO; i < IntegerConsts.TWO; i++) {
      int num = random.nextInt(IntegerConsts.TEN);
      idBuilder.append(num);
    }

    // 生成性别识别信息
    int sex = random.nextInt(IntegerConsts.FIVE) * IntegerConsts.TWO;
    if (gender == null) {
      sex = random.nextInt(IntegerConsts.TEN);
      gender = StringUtils.Empty;
    }
    switch (gender) {
      case "1":
        idBuilder.append(sex + IntegerConsts.ONE);
        break;
      case "2":
        idBuilder.append(random.nextInt(IntegerConsts.FIVE));
        break;
      default:
        idBuilder.append(sex);
        break;
    }

    // 计算身份证最后一位校验码
    String id17 = idBuilder.toString();
    char[] chars = id17.toCharArray();
    int sum = IntegerConsts.ZERO;
    for (int i = IntegerConsts.ZERO; i < IntegerConsts.SEVENTEEN; i++) {
      sum += (chars[i] - '0') * weights[i];
    }
    int checkCode = sum % IntegerConsts.ELEVEN;
    char[] checkCodes = new char[]{'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    char lastChar = checkCodes[checkCode];

    // 构建完整的18位身份证号码
    idBuilder.append(lastChar);

    // 返回结果
    return idBuilder.toString();
  }

  /**
   * 根据身份编号获取性别
   *
   * @param idCard 身份证号
   * @return 返回结果
   */
  public static Integer getGender(String idCard) {
    String cardNum = idCard.substring(IntegerConsts.SIXTEEN, IntegerConsts.SEVENTEEN);
    if (Integer.parseInt(cardNum) % IntegerConsts.TWO != IntegerConsts.ZERO) {
      return IntegerConsts.ONE;
    } else {
      return IntegerConsts.TWO;
    }
  }

  /**
   * 根据身份编号获取生日
   *
   * @param idCard 身份证号
   * @return 返回结果
   */
  public static String getBirthday(String idCard) {
    char[] number = idCard.toCharArray();
    StringBuffer buffer = new StringBuffer();
    switch (number.length) {
      case 15:
        buffer.append(IntegerConsts.NINETEEN);
        buffer.append(idCard.substring(IntegerConsts.SIX, IntegerConsts.EIGHT));
        buffer.append(StringUtils.HORIZONTAL);
        buffer.append(idCard.substring(IntegerConsts.EIGHT, IntegerConsts.TEN));
        buffer.append(StringUtils.HORIZONTAL);
        buffer.append(idCard.substring(IntegerConsts.TEN, IntegerConsts.TWELVE));
        break;
      default:
        buffer.append(idCard.substring(IntegerConsts.SIX, IntegerConsts.TEN));
        buffer.append(StringUtils.HORIZONTAL);
        buffer.append(idCard.substring(IntegerConsts.TEN, IntegerConsts.TWELVE));
        buffer.append(StringUtils.HORIZONTAL);
        buffer.append(idCard.substring(IntegerConsts.TWELVE, IntegerConsts.FOURTEEN));
        break;
    }
    // 返回结果
    return buffer.toString();
  }

  /**
   * 根据身份编号获取年龄
   *
   * @param idCard 身份证编码
   * @return 返回结果
   */
  public static Integer getAge(String idCard) {
    Integer age = null;
    int year = LocalDate.now().getYear();
    char[] number = idCard.toCharArray();
    boolean flag = true;
    if (number.length == IntegerConsts.FIFTEEN) {
      for (int x = IntegerConsts.ZERO; x < number.length; x++) {
        if (!flag)
          return null;
        flag = Character.isDigit(number[x]);
      }
    } else if (number.length == IntegerConsts.EIGHTEEN) {
      for (int x = IntegerConsts.ZERO; x < number.length - IntegerConsts.ONE; x++) {
        if (!flag)
          return null;
        flag = Character.isDigit(number[x]);
      }
    }
    if (flag && idCard.length() == IntegerConsts.FIFTEEN) {
      age = (year - Integer.parseInt(IntegerConsts.NINETEEN + idCard.substring(IntegerConsts.SIX, IntegerConsts.EIGHT)));
    } else if (flag && idCard.length() == IntegerConsts.EIGHTEEN) {
      age = (year - Integer.parseInt(idCard.substring(IntegerConsts.SIX, IntegerConsts.TEN)));
    }
    return age;
  }
}
