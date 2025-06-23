package com.cdkjframework.util.encrypts.crc;

import com.cdkjframework.constant.IntegerConsts;

/**
 * @ProjectName: com.lesmarthome.iot
 * @Package: com.lesmarthome.iot.util.crc
 * @ClassName: CheckUtils
 * @Description: 校验工具
 * @Author: xiaLin
 * @Version: 1.0
 */
public class CrcCalculatorUtils {

  /**
   * 参数
   */
  public AlgoParams parameters;

  /**
   * hash大小
   */
  public byte hashSize = 8;
  /**
   * 默认值
   */
  private long _mask = 0xFFFFFFFFFFFFFFFFL;
  /**
   * table数组
   */
  private long[] _table = new long[256];

  /**
   * 构造函数
   */
  public CrcCalculatorUtils(AlgoParams params) {
    parameters = params;
    hashSize = (byte) params.hashSize;
    if (hashSize < IntegerConsts.SIXTY_FOUR) {
      _mask = (1L << hashSize) - IntegerConsts.ONE;
    }

    createTable();
  }

  /**
   * 值
   *
   * @param data
   * @param offset
   * @param length
   * @return
   */
  public long calc(byte[] data, int offset, int length) {
    long init = parameters.refOut ? reverseBits(parameters.init, hashSize) : parameters.init;
    long hash = computeCrc(init, data, offset, length);
    return (hash ^ parameters.xorOut) & _mask;
  }

  /**
   * 计算Crc
   *
   * @param init   初始值
   * @param data   数据
   * @param offset 开始位置
   * @param length 长度
   * @return 返回计算结果
   */
  private long computeCrc(long init, byte[] data, int offset, int length) {
    long crc = init;
    if (parameters.refOut) {
      for (int i = offset; i < offset + length; i++) {
        crc = (_table[(int) ((crc ^ data[i]) & 0xFF)] ^ (crc >>> 8));
        crc &= _mask;
      }
    } else {
      int toRight = (hashSize - 8);
      toRight = toRight < 0 ? 0 : toRight;
      for (int i = offset; i < offset + length; i++) {
        crc = (_table[(int) (((crc >> toRight) ^ data[i]) & 0xFF)] ^ (crc << 8));
        crc &= _mask;
      }
    }

    return crc;
  }

  /**
   * 创建表格
   */
  private void createTable() {
    for (int i = 0; i < _table.length; i++) {
      _table[i] = createTableEntry(i);
    }
  }

  /**
   * 创建表条目
   *
   * @param index 索引
   * @return 返回结果
   */
  private long createTableEntry(int index) {
    long r = (long) index;

    if (parameters.refIn) {
      r = reverseBits(r, hashSize);
    } else if (hashSize > IntegerConsts.EIGHT) {
      r <<= (hashSize - 8);
    }

    long lastBit = (1L << (hashSize - 1));

    for (int i = 0; i < IntegerConsts.EIGHT; i++) {
      if ((r & lastBit) != 0) {
        r = ((r << 1) ^ parameters.poly);
      } else {
        r <<= 1;
      }
    }

    if (parameters.refOut) {
      r = reverseBits(r, hashSize);
    }

    return r & _mask;
  }

  /**
   * 反向位
   *
   * @param ul          值
   * @param valueLength 值长度
   * @return 返回结果
   */
  long reverseBits(long ul, int valueLength) {
    long newValue = 0;
    for (int i = valueLength - 1; i >= 0; i--) {
      newValue |= (ul & 1) << i;
      ul >>= 1;
    }

    return newValue;
  }
}
