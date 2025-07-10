package com.cdkjframework.license.service;

import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.license.service
 * @ClassName: LinuxServerInfos
 * @Description: 用于获取客户Windows服务器的基本信息
 * @Author: xiaLin
 * @Version: 1.0
 */
public class WindowsServerInfos extends AServerInfos {

    /**
     * 日志
     */
    private final LogUtils logUtils = LogUtils.getLogger(WindowsServerInfos.class);

    /**
     * CPU信息
     *
     * @return 返回结果
     * @throws Exception 异常信息
     */
    @Override
    protected String getCPUSerial() throws Exception {
        StringBuilder result = new StringBuilder(StringUtils.Empty);
        try {
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n" + "   (\"Select * from Win32_Processor\") \n"
                    + "For Each objItem in colItems \n" + "    Wscript.Echo objItem.ProcessorId \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result.append(line);
            }
            input.close();
            file.delete();
        } catch (Exception e) {
            logUtils.error("获取cpu信息错误", e);
        }
        return result.toString().trim();
    }

    /**
     * 获取主板序列号
     *
     * @return 返回结果
     * @throws Exception 异常信息
     */
    @Override
    protected String getMainBoardSerial() throws Exception {

        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);

            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n" + "   (\"Select * from Win32_BaseBoard\") \n"
                    + "For Each objItem in colItems \n" + "    Wscript.Echo objItem.SerialNumber \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            logUtils.error("获取主板信息错误", e);
        }
        return result.trim();
    }
}
