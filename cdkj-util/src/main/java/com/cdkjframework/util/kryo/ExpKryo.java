package com.cdkjframework.util.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.serializers.JavaSerializer;


/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkjframework.core.util.kryo
 * @ClassName: ExpKryo
 * @Description: ExpKryo
 * @Author: xiaLin
 * @Version: 1.0
 */
public class ExpKryo extends Kryo {
    @Override
    public Serializer getDefaultSerializer(Class type) {
        if (type == null) {
            throw new IllegalArgumentException("TYPE cannot be null.");
        }
        if (!type.isArray() && !AbstractKryoCheckUtil.checkExistDefaultConstructor(type)) {
            return new JavaSerializer();
        }
        return super.getDefaultSerializer(type);
    }
}
