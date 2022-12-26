package com.cdkjframework.util.encrypts.crc;

/**
 * @ProjectName: com.lesmarthome.iot
 * @Package: com.lesmarthome.iot.util.crc
 * @ClassName: CheckUtils
 * @Description: 校验工具
 * @Author: xiaLin
 * @Version: 1.0
 */
public class Crc64 {
  public static AlgoParams crc64 = new AlgoParams("CRC-64", 64, 0x42F0E1EBA9EA3693L, 0x00000000L, false, false, 0x00000000L, 0x6C40DF5F0B497347L);
  public static AlgoParams crc64We = new AlgoParams("CRC-64/WE", 64, 0x42F0E1EBA9EA3693L, 0xFFFFFFFFFFFFFFFFL, false, false, 0xFFFFFFFFFFFFFFFFL, 0x62EC59E3F1A4F00AL);
  public static AlgoParams crc64Xz = new AlgoParams("CRC-64/XZ", 64, 0x42F0E1EBA9EA3693L, 0xFFFFFFFFFFFFFFFFL, true, true, 0xFFFFFFFFFFFFFFFFL, 0x995DC9BBDF1939FAL);

  /**
   * 常量
   */
  public static final AlgoParams[] PARAMS = new AlgoParams[]{
      crc64, crc64We, crc64Xz
  };
}
