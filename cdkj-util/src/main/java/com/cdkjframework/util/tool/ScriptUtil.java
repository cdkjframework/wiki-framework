package com.cdkjframework.util.tool;

import com.cdkjframework.util.log.LogUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.util.tool
 * @ClassName: ScriptUtil
 * @Description: 脚本处理
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
@ConfigurationProperties(prefix = "spring.script")
public class ScriptUtil {
    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger(ScriptUtil.class);

    /**
     * 地址
     */
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 解析脚本数据
     *
     * @param jsonString   JSON 对象串
     * @param findLocation 执行方法
     * @return 返回结果
     * @throws IOException 异常信息
     */
    public String ScriptParsing(String jsonString, String findLocation) throws IOException {
        //验证是否为空
        if (StringUtils.isNullAndSpaceOrEmpty(jsonString)) {
            return "";
        }

        //得到一个ScriptEngine对象
        ScriptEngineManager maneger = new ScriptEngineManager();
        ScriptEngine engine = maneger.getEngineByName("JavaScript");
        URL url = new URL(address);
        //读js文件
        Reader scriptReader = new InputStreamReader(url.openStream(), "utf-8");
        String objReturn = "";
        try {
            engine.eval(scriptReader);
            if (engine instanceof Invocable) {
                Invocable invocable = (Invocable) engine;
                objReturn = (String) invocable.invokeFunction(findLocation, jsonString);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logUtil.error("解析脚本数据异常：" + ex.getMessage());
        } finally {
            scriptReader.close();
        }

        //返回执行结果
        return objReturn;
    }
}
