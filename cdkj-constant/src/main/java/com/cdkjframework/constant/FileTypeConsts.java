package com.cdkjframework.constant;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.constant
 * @ClassName: FileTypeConsts
 * @Description: 文件类型 魔术数字 常量
 * @Author: xiaLin
 * @Date: 2024/4/22 9:46
 * @Version: 1.0
 */
public class FileTypeConsts {

  /**
   * Bitmap format
   */
  public static final byte[] BMP = {(byte) 0x42, (byte) 0x4d};

  /**
   * FITS format
   */
  public static final byte[] FITS = {(byte) 0x53, (byte) 0x49, (byte) 0x4d, (byte) 0x50, (byte) 0x4c, (byte) 0x45};

  /**
   * GIF format
   */
  public static final byte[] GIF = {(byte) 0x47, (byte) 0x49, (byte) 0x46, (byte) 0x38};

  /**
   * Graphics Kernel System
   */
  public static final byte[] GKS = {(byte) 0x47, (byte) 0x4b, (byte) 0x53, (byte) 0x4d};

  /**
   * IRIS rgb format
   */
  public static final byte[] RGB = {(byte) 0x01, (byte) 0xda};

  /**
   * ITC (CMU WM) format
   */
  public static final byte[] ITC = {(byte) 0xf1, (byte) 0x00, (byte) 0x40, (byte) 0xbb};
  /**
   * JPEG File Interchange Format
   */
  public static final byte[] JPG = {(byte) 0xff, (byte) 0xd8, (byte) 0xff, (byte) 0xe0};
  /**
   * NIFF (Navy TIFF)
   */
  public static final byte[] NIF = {(byte) 0x49, (byte) 0x49, (byte) 0x4e, (byte) 0x31};
  /**
   * PM format
   */
  public static final byte[] PM = {(byte) 0x56, (byte) 0x49, (byte) 0x45, (byte) 0x57};
  /**
   * PNG format
   */
  public static final byte[] PNG = {(byte) 0x89, (byte) 0x50, (byte) 0x4e, (byte) 0x47};
  /**
   * Postscript format
   */
  public static final byte[] PS = {(byte) 0x25, (byte) 0x21};
  /**
   * Sun Rasterfile
   */
  public static final byte[] RAS = {(byte) 0x59, (byte) 0xa6, (byte) 0x6a, (byte) 0x95};

  /**
   * TIFF format (Motorola – big endian)
   */
  public static final byte[] TIF = {(byte) 0x4d, (byte) 0x4d, (byte) 0x00, (byte) 0x2a};
  /**
   * TIFF format (Intel – little endian)
   */
  public static final byte[] TIF_2 = {(byte) 0x49, (byte) 0x49, (byte) 0x2a, (byte) 0x00};
  /**
   * XCF Gimp file structure
   */
  public static final byte[] XCF = {(byte) 0x67, (byte) 0x69, (byte) 0x6d, (byte) 0x70, (byte) 0x20, (byte) 0x78, (byte) 0x63, (byte) 0x66, (byte) 0x20, (byte) 0x76};
  /**
   * Xfig format
   */
  public static final byte[] FIG = {(byte) 0x23, (byte) 0x46, (byte) 0x49, (byte) 0x47};
  /**
   * XPM format
   */
  public static final byte[] XPM = {(byte) 0x2f, (byte) 0x2a, (byte) 0x20, (byte) 0x58, (byte) 0x50, (byte) 0x4d, (byte) 0x20, (byte) 0x2a, (byte) 0x2f};

  /**
   * Bzip
   */
  public static final byte[] BZ = {(byte) 0x42, (byte) 0x5a};
  /**
   * Compress
   */
  public static final byte[] Z = {(byte) 0x1f, (byte) 0x9d};
  /**
   * gzip format
   */
  public static final byte[] GZ = {(byte) 0x1f, (byte) 0x8b};
  /**
   * pkzip format
   */
  public static final byte[] ZIP = {(byte) 0x50, (byte) 0x4b, (byte) 0x03, (byte) 0x04};
  /**
   * TAR (POSIX)
   */
  public static final byte[] TAR = {(byte) 0x75, (byte) 0x73, (byte) 0x74, (byte) 0x61, (byte) 0x72};


  /**
   * MS-DOS, OS/2 or MS Windows
   */
  public static final byte[] MZ = {(byte) 0x4d, (byte) 0x5a};
  /**
   * Unix elf
   */
  public static final byte[] ELF = {(byte) 0x50, (byte) 0x4b, (byte) 0x03, (byte) 0x04};

  /**
   * pgp public ring
   */
  public static final byte[] PGP = {(byte) 0x99, (byte) 0x00};


  /**
   * pgp security ring
   */
  public static final byte[] PGP_2 = {(byte) 0x95, (byte) 0x01};


  /**
   * pgp security ring
   */
  public static final byte[] PGP_3 = {(byte) 0x95, (byte) 0x00};


  /**
   * pgp encrypted data
   */
  public static final byte[] PGP_4 = {(byte) 0xa6, (byte) 0x00};
}
