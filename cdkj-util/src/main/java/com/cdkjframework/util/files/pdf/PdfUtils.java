package com.cdkjframework.util.files.pdf;

import com.cdkjframework.util.tool.StringUtils;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.files.pdf
 * @ClassName: PdfUtils
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class PdfUtils {

    /**
     * 获取字休设置
     *
     * @param fontPath 字体路径
     * @return 返回字体
     */
    public static BaseFont getBaseFont(String fontPath) throws IOException, DocumentException {
        if (StringUtils.isNullAndSpaceOrEmpty(fontPath)) {
            return BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } else {
            return BaseFont.createFont();
        }
    }

}
