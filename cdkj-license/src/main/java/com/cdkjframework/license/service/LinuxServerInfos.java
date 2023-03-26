package com.cdkjframework.license.service;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <p>用于获取客户Linux服务器的基本信息</p>
 *
 * @author appleyk
 * @version V.0.2.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on  10:42 下午 2020/8/21
 */
public class LinuxServerInfos extends AServerInfos {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(LinuxServerInfos.class);

    private final String[] CPU_SHELL = {"/bin/bash", "-c", "dmidecode -t processor | grep 'ID' | awk -F ':' '{print $2}' | head -n 1"};
    private final String[] MAIN_BOARD_SHELL = {"/bin/bash", "-c", "dmidecode | grep 'Serial Number' | awk -F ':' '{print $2}' | head -n 1"};

    @Override
    protected String getCPUSerial() throws Exception {
        String result = StringUtils.Empty;
        String CPU_ID_CMD = "dmidecode";
        BufferedReader bufferedReader = null;
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(new String[]{"sh", "-c", CPU_ID_CMD});// 管道
            bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                // 寻找标示字符串[hwaddr]
                index = line.toLowerCase().indexOf("uuid");
                if (index >= IntegerConsts.ZERO) {
                    // 找到了
                    // 取出mac地址并去除2边空格
                    result = line.substring(index + "uuid".length() + IntegerConsts.ONE).trim();
                    break;
                }
            }
        } catch (IOException e) {
            logUtils.error("获取cpu信息错误", e);
        }
        return result.trim();
    }

    /**
     * 获取主板序列号
     *
     * @return 返回结果
     * @throws Exception 异常信息
     */
    @Override
    protected String getMainBoardSerial() throws Exception {
        String result = StringUtils.Empty;
        String maniBord_cmd = "dmidecode | grep 'Serial Number' | awk '{print $3}' | tail -1";
        Process p;
        try {
            p = Runtime.getRuntime().exec(new String[]{"sh", "-c", maniBord_cmd});// 管道
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result += line;
                break;
            }
            br.close();
        } catch (IOException e) {
            logUtils.error("获取主板信息错误", e);
        }
        return result;
    }
}

