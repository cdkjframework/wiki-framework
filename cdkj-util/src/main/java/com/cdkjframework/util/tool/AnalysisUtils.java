package com.cdkjframework.util.tool;

import com.cdkjframework.constant.HttpHeaderConsts;
import com.cdkjframework.constant.RegexConsts;
import com.cdkjframework.entity.log.LogRecordDto;
import com.cdkjframework.util.network.http.HttpServletUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.network
 * @ClassName: AnalysisUtils
 * @Description: 解析
 * @Author: xiaLin
 * @Version: 1.0
 */
public class AnalysisUtils {

    /**
     * 浏览器正则
     */
    private static List<String> browserRegexList;

    static {
        browserRegexList = new ArrayList<>();
        browserRegexList.add(RegexConsts.IE);
        browserRegexList.add(RegexConsts.IE_EDGE);
        browserRegexList.add(RegexConsts.MICROSOFT_EDGE);
        browserRegexList.add(RegexConsts.FIREFOX);
        browserRegexList.add(RegexConsts.OPERA);
        browserRegexList.add(RegexConsts.CHROME);
        browserRegexList.add(RegexConsts.SAFARI);
    }

    /**
     * 处理请求日志
     *
     * @param logRecordDto 日志信息
     */
    public static void requestHandle(LogRecordDto logRecordDto) {
        requestHandle(logRecordDto, null);
    }

    /**
     * 处理请求日志
     *
     * @param logRecordDto 日志信息
     * @param userAgent       代理用户
     */
    public static void requestHandle(LogRecordDto logRecordDto, String userAgent) {
        if (StringUtils.isNullAndSpaceOrEmpty(userAgent)) {
            userAgent = HttpServletUtils.getRequest().getHeader(HttpHeaderConsts.USER_AGENT);
        }
        userAgent = userAgent.toLowerCase();
        logRecordDto.setOs(findOsInfo(userAgent));
        findBrowser(logRecordDto, userAgent);
    }

    /**
     * 获取浏览器信息
     *
     * @param userAgent 代理用户
     * @return
     */
    public static void findBrowser(LogRecordDto logRecordDto, String userAgent) {
        for (String key :
                browserRegexList) {
            Pattern pattern = Pattern.compile(key);
            Matcher matcher = pattern.matcher(userAgent);
            if (matcher.find()) {
                switch (key) {
                    case RegexConsts.IE:
                        logRecordDto.setBrowser("IE");
                        break;
                    case RegexConsts.IE_EDGE:
                        logRecordDto.setBrowser("Edge");
                        break;
                    case RegexConsts.MICROSOFT_EDGE:
                        logRecordDto.setBrowser("Microsoft Edge");
                        break;
                    case RegexConsts.FIREFOX:
                        logRecordDto.setBrowser("Firefox");
                        break;
                    case RegexConsts.CHROME:
                        logRecordDto.setBrowser("Chrome");
                        break;
                    case RegexConsts.OPERA:
                        logRecordDto.setBrowser("Opera");
                        break;
                    case RegexConsts.SAFARI:
                        logRecordDto.setBrowser("Safari");
                        break;
                }
                logRecordDto.setBrowserVersion(matcher.group());
                break;
            }
        }
    }

    /**
     * 获取到操作系统信息
     *
     * @param userAgent 代理用户
     * @return 返回操作系统名称
     */
    public static String findOsInfo(String userAgent) {
        String name;
        if (userAgent.indexOf("win") > -1) {
            if (userAgent.indexOf("windows nt 6.1") > -1 || userAgent.indexOf("windows 7") > -1) {
                name = "Windows 7";
            } else if (userAgent.indexOf("windows nt 6.2") > -1 || userAgent.indexOf("windows 8") > -1) {
                name = "Windows 8";
            } else if (userAgent.indexOf("windows nt 6.3") > -1) {
                name = "Windows 8.1";
            } else if (userAgent.indexOf("windows nt 10.0") > -1 || userAgent.indexOf("windows nt 10.0") > -1) {
                name = "Windows 10";
            } else {
                name = "未知系统";
            }
        } else if (userAgent.indexOf("iphone") > -1) {
            name = "Iphone";
        } else if (userAgent.indexOf("mac") > -1) {
            name = "Mac";
        } else if (userAgent.indexOf("x11") > -1 || userAgent.indexOf("unix") > -1 || userAgent.indexOf("sunname") > -1 || userAgent.indexOf("bsd") > -1) {
            name = "Unix";
        } else if (userAgent.indexOf("linux") > -1) {
            if (userAgent.indexOf("android") > -1) {
                name = "Android";
            } else {
                name = "Linux";
            }
        } else {
            name = "未知系统";
        }
        return name;
    }
}
