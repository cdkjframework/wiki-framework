package com.cdkjframework.util.kryo;


/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkjframework.core.util.kryo
 * @ClassName: AbstractKryoCheckUtils
 * @Description: AbstractKryoCheckUtils
 * @Author: xiaLin
 * @Version: 1.0
 */
public abstract class AbstractKryoCheckUtil {

  /**
   * 检查类是否存在默认构造函数
   *
   * @param clazz 类
   * @return boolean
   */
  public static boolean checkExistDefaultConstructor(Class<?> clazz) {
    try {
      clazz.getDeclaredConstructor();
      return true;
    } catch (NoSuchMethodException e) {
      return false;
    }
  }
}

 