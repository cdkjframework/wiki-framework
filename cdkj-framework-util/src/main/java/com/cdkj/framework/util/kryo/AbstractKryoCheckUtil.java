package com.cdkj.framework.util.kryo;


/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkj.framework.core.util.kryo
 * @ClassName: AbstractKryoCheckUtils
 * @Description: AbstractKryoCheckUtils
 * @Author: xiaLin
 * @Version: 1.0
 */
public abstract class AbstractKryoCheckUtil {

    public static boolean checkExistDefaultConstructor(Class<?> clazz) {
        try {
            clazz.getDeclaredConstructor();
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}

 