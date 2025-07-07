package com.cdkjframework.util.encrypts.crc;


/**
 * @ProjectName: com.lesmarthome.iot
 * @Package: com.lesmarthome.iot.util.crc
 * @ClassName: CheckUtils
 * @Description: 校验工具
 * @Author: xiaLin
 * @Version: 1.0
 */
public class Crc16 {
  public static AlgoParams crc16CcittFalse = new AlgoParams("CRC-16/CCITT-FALSE", 16, 0x1021, 0xFFFF, false, false, 0x0, 0x29B1);
  public static AlgoParams crc16Arc = new AlgoParams("CRC-16/ARC", 16, 0x8005, 0x0, true, true, 0x0, 0xBB3D);
  public static AlgoParams crc16AugCcitt = new AlgoParams("CRC-16/AUG-CCITT", 16, 0x1021, 0x1D0F, false, false, 0x0, 0xE5CC);
  public static AlgoParams crc16Buypass = new AlgoParams("CRC-16/BUYPASS", 16, 0x8005, 0x0, false, false, 0x0, 0xFEE8);
  public static AlgoParams crc16Cdma2000 = new AlgoParams("CRC-16/CDMA2000", 16, 0xC867, 0xFFFF, false, false, 0x0, 0x4C06);
  public static AlgoParams crc16Dds110 = new AlgoParams("CRC-16/DDS-110", 16, 0x8005, 0x800D, false, false, 0x0, 0x9ECF);
  public static AlgoParams crc16DectR = new AlgoParams("CRC-16/DECT-R", 16, 0x589, 0x0, false, false, 0x1, 0x7E);
  public static AlgoParams crc16DectX = new AlgoParams("CRC-16/DECT-X", 16, 0x589, 0x0, false, false, 0x0, 0x7F);
  public static AlgoParams crc16Dnp = new AlgoParams("CRC-16/DNP", 16, 0x3D65, 0x0, true, true, 0xFFFF, 0xEA82);
  public static AlgoParams crc16En13757 = new AlgoParams("CRC-16/EN-13757", 16, 0x3D65, 0x0, false, false, 0xFFFF, 0xC2B7);
  public static AlgoParams crc16Genibus = new AlgoParams("CRC-16/GENIBUS", 16, 0x1021, 0xFFFF, false, false, 0xFFFF, 0xD64E);
  public static AlgoParams crc16Maxim = new AlgoParams("CRC-16/MAXIM", 16, 0x8005, 0x0, true, true, 0xFFFF, 0x44C2);
  public static AlgoParams crc16Mcrf4Xx = new AlgoParams("CRC-16/MCRF4XX", 16, 0x1021, 0xFFFF, true, true, 0x0, 0x6F91);
  public static AlgoParams crc16Riello = new AlgoParams("CRC-16/RIELLO", 16, 0x1021, 0xB2AA, true, true, 0x0, 0x63D0);
  public static AlgoParams crc16T10Dif = new AlgoParams("CRC-16/T10-DIF", 16, 0x8BB7, 0x0, false, false, 0x0, 0xD0DB);
  public static AlgoParams crc16Teledisk = new AlgoParams("CRC-16/TELEDISK", 16, 0xA097, 0x0, false, false, 0x0, 0xFB3);
  public static AlgoParams crc16Tms37157 = new AlgoParams("CRC-16/TMS37157", 16, 0x1021, 0x89EC, true, true, 0x0, 0x26B1);
  public static AlgoParams crc16Usb = new AlgoParams("CRC-16/USB", 16, 0x8005, 0xFFFF, true, true, 0xFFFF, 0xB4C8);
  public static AlgoParams crcA = new AlgoParams("CRC-A", 16, 0x1021, 0xc6c6, true, true, 0x0, 0xBF05);
  public static AlgoParams crc16Kermit = new AlgoParams("CRC-16/KERMIT", 16, 0x1021, 0x0, true, true, 0x0, 0x2189);
  public static AlgoParams crc16Modbus = new AlgoParams("CRC-16/MODBUS", 16, 0x8005, 0xFFFF, true, true, 0x0, 0x4B37);
  public static AlgoParams crc16X25 = new AlgoParams("CRC-16/X-25", 16, 0x1021, 0xFFFF, true, true, 0xFFFF, 0x906E);
  public static AlgoParams crc16Xmodem = new AlgoParams("CRC-16/XMODEM", 16, 0x1021, 0x0, false, false, 0x0, 0x31C3);

  public static final AlgoParams[] PARAMS = new AlgoParams[]
      {
          crc16CcittFalse,
          crc16Arc,
          crc16AugCcitt,
          crc16Buypass,
          crc16Cdma2000,
          crc16Dds110,
          crc16DectR,
          crc16DectX,
          crc16Dnp,
          crc16En13757,
          crc16Genibus,
          crc16Maxim,
          crc16Mcrf4Xx,
          crc16Riello,
          crc16T10Dif,
          crc16Teledisk,
          crc16Tms37157,
          crc16Usb,
          crcA,
          crc16Kermit,
          crc16Modbus,
          crc16X25,
          crc16Xmodem,
      };
}
