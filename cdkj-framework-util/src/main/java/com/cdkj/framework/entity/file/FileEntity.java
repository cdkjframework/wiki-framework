package com.cdkj.framework.entity.file;


import java.util.List;

/**
 * @ProjectName: com.cdkj.framework.webcode
 * @Package: com.cdkj.framework.core.entity.file
 * @ClassName: FileEnity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

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
     * 需要压缩的文件
     */
    private List<String> fileList;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }
}
