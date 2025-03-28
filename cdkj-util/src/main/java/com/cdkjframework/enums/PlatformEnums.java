package com.cdkjframework.enums;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.enums
 * @ClassName: PlatformEnums
 * @Description: 操作系统类型枚举
 * @Author: xiaLin
 * @Version: 1.0
 */
public enum PlatformEnums {
    Any("any"),

    Linux("Linux"),

    Mac_OS("Mac OS"),

    Mac_OS_X("Mac OS X"),

    Windows("Windows"),

    OS2("OS/2"),

    Solaris("Solaris"),

    SunOS("SunOS"),

    MPEiX("MPE/iX"),

    HP_UX("HP-UX"),

    AIX("AIX"),

    OS390("OS/390"),

    FreeBSD("FreeBSD"),

    Irix("Irix"),

    Digital_Unix("Digital Unix"),

    NetWare_411("NetWare"),

    OSF1("OSF1"),

    OpenVMS("OpenVMS"),

    Others("Others");

    /**
     * 构造函数
     *
     * @param description 描述
     */
    PlatformEnums(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    /**
     * 描述
     */
    private final String description;
}
