package com.cdkjframework.datasource.mongodb.number;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.datasource.mongodb.repository.IMongoRepository;
import com.cdkjframework.entity.number.NumberEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.datasource.mongodb.number
 * @ClassName: MongoNumberUtils
 * @Description: mongo生成单号
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class MongoNumberUtils {
  /**
   * 单号生成主键 KEY
   */
  private static final String ODD_NUMBER_KEY = "odd_Number_Key";

  /**
   * 日志
   */
  private static LogUtils logUtils = LogUtils.getLogger(MongoNumberUtils.class);

  /**
   * 获取参数
   */
  private static IMongoRepository repository;

  /**
   * mongo 注册
   */
  private final IMongoRepository mongoRepository;

  /**
   * 构造函数
   *
   * @param mongoRepository mongo存储库
   */
  public MongoNumberUtils(IMongoRepository mongoRepository) {
    this.mongoRepository = mongoRepository;
  }

  /**
   * 执行函数
   */
  public void start() {
    repository = mongoRepository;
  }

  /**
   * 生成单据号
   *
   * @param prefix 前缀
   * @param length 后缀长度
   * @return 返回单据结果
   */
  public static String generateDocumentNumber(String prefix, int length) throws GlobalException {
    //字母+日期+5位流水号
    //日期：年最后2位+月+天
    //客户代码：3位数字
    //采购订单：CG00117072500001
    //销售订单：XS00117072500001
    return generate(prefix, length, IntegerConsts.ZERO, IntegerConsts.ONE);
  }

  /**
   * 生成指定格式单据号
   *
   * @param prefix   前缀
   * @param length   后缀长度
   * @param dateType 时间格式
   *                 0、为无时间格式 例如：CG00001
   *                 1、为普通时间格式 例如：CG00117072500001
   *                 2、按每年周来实现 例如 CG03700001
   * @return 返回单据结果
   */
  public static String generateDocumentNumber(String prefix, int length, Integer dateType) throws GlobalException {
    //字母+日期+5位流水号
    //日期：年最后2位+月+天
    //客户代码：3位数字
    //采购订单：CG00117072500001
    //销售订单：XS00117072500001
    return generate(prefix, length, IntegerConsts.ZERO, dateType);
  }


  /**
   * 生成单据号（无日期）
   *
   * @param prefix 前缀
   * @param length 后缀长度
   * @return 返回单据结果
   * @throws GlobalException 异常信息
   */
  public static String generateNoDateNumber(String prefix, int length) throws GlobalException {
    return generate(prefix, length, IntegerConsts.ZERO, IntegerConsts.ZERO);
  }


  /**
   * 单号生成
   *
   * @param prefix   前缀
   * @param length   长度
   * @param init     初始值
   * @param dateType 时间格式
   *                 0、为无时间格式 例如：CG00001
   *                 1、为普通时间格式 例如：CG00117072500001
   *                 2、按每年周来实现 例如 CG03700001
   * @return 返回单号
   * @throws GlobalException 异常信息
   */
  private static String generate(String prefix, int length, int init, Integer dateType) throws GlobalException {
    final int error = IntegerConsts.FIVE;
    //生成单号
    String number = autoRetryGenerate(prefix, length, init, error, dateType);

    //验证单号
    if (StringUtils.isNullAndSpaceOrEmpty(number)) {
      throw new GlobalException(String.format("获取单号失败[%s]", prefix));
    }

    //返回单号结果
    return number;
  }

  /**
   * 自动生成并重试
   *
   * @param prefix   前缀
   * @param length   长度
   * @param init     初始值
   * @param maxError 最大生成次数
   * @param dateType 时间格式
   *                 0、为无时间格式 例如：CG00001
   *                 1、为普通时间格式 例如：CG00117072500001
   *                 2、按每年周来实现 例如 CG03700001
   * @return 返回结果
   */
  private static synchronized String autoRetryGenerate(String prefix, int length, int init, int maxError, Integer dateType) {
    int error = 0;
    while (error < maxError) {
      try {
        // 生成前缀信息
        StringBuffer buffer = new StringBuffer(prefix);
        if (dateType == null) {
          dateType = IntegerConsts.ONE;
        }
        String date;
        NumberEntity entity = new NumberEntity();
        entity.setDateType(dateType);
        Query query = new Query();
        Criteria criteria = Criteria.where("dateType").is(dateType);
        switch (dateType) {
          default:
            break;
          case 1:
            date = LocalDateUtils.dateTimeCurrentFormatter(LocalDateUtils.DATE_NOT_LINE_SHORT_YEAR);
            buffer.append(date);
            entity.setDateValue(date);
            criteria.and("dateValue").is(date);
            break;
          case 2:
            LocalDate localDate = LocalDate.now();
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("w");
            String weekValue = localDate.format(dateTimeFormatter);
            weekValue = StringUtils.format(IntegerConsts.TWO, Integer.valueOf(weekValue));
            date = weekValue + dayOfWeek.getValue();
            buffer.append(date);
            break;
        }
        String key = prefix + "-" + ODD_NUMBER_KEY + "-" + dateType;

        entity.setKey(key);
        criteria.and("key").is(key);
        query.addCriteria(criteria);
        NumberEntity no = repository.findEntity(query, NumberEntity.class);
        //获取单号记录
        Long target;
        if (no == null || StringUtils.isNullAndSpaceOrEmpty(no.getId())) {
          target = Long.valueOf(init + IntegerConsts.ONE);
          no = entity;
        } else {
          target = no.getValue() + IntegerConsts.ONE;
        }
        if (target > IntegerConsts.ZERO) {
          // 生成单号
          String number = StringUtils.format(length, target);
          no.setValue(target);
          repository.save(no);
          // 写放数据库
          return buffer.append(number).toString();
        } else {
          TimeUnit.MILLISECONDS.sleep(IntegerConsts.ONE_HUNDRED);
          throw new GlobalException("生成单号失败");
        }
      } catch (Exception ex) {
        error++;
        String errorMessage = String.format("第%s次获取单号失败[%s]，%s", error, prefix, ex.getMessage());
        logUtils.error(errorMessage);
      }
    }

    // 返回 null 说明生成单号失败
    return null;
  }
}
