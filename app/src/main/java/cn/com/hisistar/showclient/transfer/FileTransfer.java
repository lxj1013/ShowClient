package cn.com.hisistar.showclient.transfer;

import java.io.Serializable;

/**
 * @author lxj
 * @date on 2018/8/14
 */
public class FileTransfer implements Serializable {
    private String fileName;

    private String srcFilePath;

    private String dstFilePath;

    private long fileSize;

    private String md5;

    public FileTransfer() {
    }

    public FileTransfer(String fileName, String srcFilePath, String dstFilePath, long fileSize) {
        this.fileName = fileName;
        this.srcFilePath = srcFilePath;
        this.dstFilePath = dstFilePath;
        this.fileSize = fileSize;
    }

    public FileTransfer(String fileName, String srcFilePath, String dstFilePath, long fileSize, String md5) {
        this.fileName = fileName;
        this.srcFilePath = srcFilePath;
        this.dstFilePath = dstFilePath;
        this.fileSize = fileSize;
        this.md5 = md5;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSrcFilePath() {
        return srcFilePath;
    }

    public void setSrcFilePath(String srcFilePath) {
        this.srcFilePath = srcFilePath;
    }

    public String getDstFilePath() {
        return dstFilePath;
    }

    public void setDstFilePath(String dstFilePath) {
        this.dstFilePath = dstFilePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "FileTransfer{" +
                "fileName='" + fileName + '\'' +
                ", srcFilePath='" + srcFilePath + '\'' +
                ", dstFilePath='" + dstFilePath + '\'' +
                ", fileSize=" + fileSize +
                ", md5='" + md5 + '\'' +
                '}';
    }
}
