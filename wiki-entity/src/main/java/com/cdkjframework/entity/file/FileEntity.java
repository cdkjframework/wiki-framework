package com.cdkjframework.entity.file;


import lombok.Data;

import java.util.List;

/**
 * @ProjectName: com.cdkjframework.webcode
 * @Package: com.cdkjframework.core.entity.file
 * @ClassName: FileEnity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Data
public class FileEntity {

    /**
     * 压缩文件名
     */
    private String fileName;

    /**
     * 压缩文件保存路径（可为空）
     */
    private String filePath;
    /**
     * 需要压缩文件的根目录
     */
    private String rootPath;

    /**
     * 需要压缩的文件
     */
    private List<String> fileList;
}
