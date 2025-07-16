package com.cdkjframework.util.encrypts.crc;

/**
 * @ProjectName: com.lesmarthome.iot
 * @Package: com.lesmarthome.iot.util.crc
 * @ClassName: CheckUtils
 * @Description: 校验工具
 * @Author: xiaLin
 * @Version: 1.0
 */
public class AlgoParams {

  /**
   * 构造函数
   *
   * @param name     名称
   * @param hashSize 输出字节数
   * @param poly     多项式
   * @param init     初始值
   * @param refIn    反转输入
   * @param refOut   反转输出
   * @param xorOut   结果异或
   * @param check    校验值
   */
  public AlgoParams(String name, int hashSize, long poly, long init, boolean refIn, boolean refOut, long xorOut, long check) {
    this.name = name;
    this.check = check;
    this.init = init;
    this.poly = poly;
    this.refIn = refIn;
    this.refOut = refOut;
    this.xorOut = xorOut;
    this.hashSize = hashSize;
  }

  /**
   * This is the official checksum value.
   */
  public long check;

  /**
   * This is hash size.
   */
  public int hashSize;

  /**
   * This parameter specifies the initial value of the register
   * when the algorithm starts.This is the value that is to be assigned
   * to the register in the direct table algorithm. In the table
   * algorithm, we may think of the register always commencing with the
   * value zero, and this value being XORed into the register after the
   * N'th bit iteration. This parameter should be specified as a
   * hexadecimal number.
   */
  public long init;

  /**
   * This is a name given to the algorithm. A string value.
   */
  public String name;

  /**
   * This parameter is the poly. This is a binary value that
   * should be specified as a hexadecimal number.The top bit of the
   * poly should be omitted.For example, if the poly is 10110, you
   * should specify 06. An important aspect of this parameter is that it
   * represents the unreflected poly; the bottom bit of this parameter
   * is always the LSB of the divisor during the division regardless of
   * whether the algorithm being modelled is reflected.
   */
  public long poly;

  /**
   * This is a boolean parameter. If it is FALSE, input bytes are
   * processed with bit 7 being treated as the most significant bit
   * (MSB) and bit 0 being treated as the least significant bit.If this
   * parameter is FALSE, each byte is reflected before being processed.
   */
  public boolean refIn;

  /**
   * This is a boolean parameter. If it is set to FALSE, the
   * final value in the register is fed into the XOROUT stage directly,
   * otherwise, if this parameter is TRUE, the final register value is
   * reflected first.
   */
  public boolean refOut;

  /**
   * This is an W-bit value that should be specified as a
   * hexadecimal number.It is XORed to the final register value (after
   * the REFOUT) stage before the value is returned as the official
   * checksum.
   */
  public long xorOut;
}
