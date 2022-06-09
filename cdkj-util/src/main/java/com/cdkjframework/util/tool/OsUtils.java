package com.cdkjframework.util.tool;

import com.cdkjframework.enums.PlatformEnums;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.tool
 * @ClassName: OsUtils
 * @Description: 系统 util
 * @Author: xiaLin
 * @Version: 1.0
 */
public class OsUtils {

    /**
     * 获取系统信息
     */
    private static String OS = System.getProperty("os.name").toLowerCase();

    /**
     * 初始货
     */
    private static OsUtils _instance = new OsUtils();

    /**
     * 操作系统枚举
     */
    private PlatformEnums platform;

    public static boolean isLinux() {
        return OS.indexOf("linux") >= 0;
    }

    public static boolean isMacOS() {
        return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") < 0;
    }

    public static boolean isMacOSX() {
        return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") > 0;
    }

    public static boolean isWindows() {
        return OS.indexOf("windows") >= 0;
    }

    public static boolean isOS2() {
        return OS.indexOf("os/2") >= 0;
    }

    public static boolean isSolaris() {
        return OS.indexOf("solaris") >= 0;
    }

    public static boolean isSunOS() {
        return OS.indexOf("sunos") >= 0;
    }

    public static boolean isMPEiX() {
        return OS.indexOf("mpe/ix") >= 0;
    }

    public static boolean isHPUX() {
        return OS.indexOf("hp-ux") >= 0;
    }

    public static boolean isAix() {
        return OS.indexOf("aix") >= 0;
    }

    public static boolean isOS390() {
        return OS.indexOf("os/390") >= 0;
    }

    public static boolean isFreeBSD() {
        return OS.indexOf("freebsd") >= 0;
    }

    public static boolean isIrix() {
        return OS.indexOf("irix") >= 0;
    }

    public static boolean isDigitalUnix() {
        return OS.indexOf("digital") >= 0 && OS.indexOf("unix") > 0;
    }

    public static boolean isNetWare() {
        return OS.indexOf("netware") >= 0;
    }

    public static boolean isOSF1() {
        return OS.indexOf("osf1") >= 0;
    }

    public static boolean isOpenVMS() {
        return OS.indexOf("openvms") >= 0;
    }

    /**
     * 获取操作系统名字
     *
     * @return 操作系统名
     */
    public static PlatformEnums getOsName() {
        if (isAix()) {
            _instance.platform = PlatformEnums.AIX;
        } else if (isDigitalUnix()) {
            _instance.platform = PlatformEnums.Digital_Unix;
        } else if (isFreeBSD()) {
            _instance.platform = PlatformEnums.FreeBSD;
        } else if (isHPUX()) {
            _instance.platform = PlatformEnums.HP_UX;
        } else if (isIrix()) {
            _instance.platform = PlatformEnums.Irix;
        } else if (isLinux()) {
            _instance.platform = PlatformEnums.Linux;
        } else if (isMacOS()) {
            _instance.platform = PlatformEnums.Mac_OS;
        } else if (isMacOSX()) {
            _instance.platform = PlatformEnums.Mac_OS_X;
        } else if (isMPEiX()) {
            _instance.platform = PlatformEnums.MPEiX;
        } else if (isNetWare()) {
            _instance.platform = PlatformEnums.NetWare_411;
        } else if (isOpenVMS()) {
            _instance.platform = PlatformEnums.OpenVMS;
        } else if (isOS2()) {
            _instance.platform = PlatformEnums.OS2;
        } else if (isOS390()) {
            _instance.platform = PlatformEnums.OS390;
        } else if (isOSF1()) {
            _instance.platform = PlatformEnums.OSF1;
        } else if (isSolaris()) {
            _instance.platform = PlatformEnums.Solaris;
        } else if (isSunOS()) {
            _instance.platform = PlatformEnums.SunOS;
        } else if (isWindows()) {
            _instance.platform = PlatformEnums.Windows;
        } else {
            _instance.platform = PlatformEnums.Others;
        }
        return _instance.platform;
    }
}
