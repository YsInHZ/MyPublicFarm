package com.example.administrator.mydemo.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 文件下载类
 */
@Entity
public class FileDownLoadBean {
    @Id
    private long id;
    private String url;
    private String fileName;
    private String filePath;
    private long length;
    private long seek = 0;
    private int finished;
    private String filemtime;
    private String filePostfix ;

    public FileDownLoadBean() {

    }

    /**
     *
     * @param id
     * @param url
     * @param fileName      文件名称
     * @param filePath      文件的相对路径
     * @param length
     * @param finished
     * @param filemtime     文件修改时间
     * @param filePostfix  文件后缀
     */
    public FileDownLoadBean(int id, String url, String fileName, String filePath, long length, int finished, String filemtime, String filePostfix,int seek) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.filePath = filePath;
        this.length = length;
        this.finished = finished;
        this.filemtime = filemtime;
        this.filePostfix = filePostfix;
        this.seek = seek;
    }

    @Generated(hash = 1460569446)
    public FileDownLoadBean(long id, String url, String fileName, String filePath, long length, long seek, int finished, String filemtime, String filePostfix) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.filePath = filePath;
        this.length = length;
        this.seek = seek;
        this.finished = finished;
        this.filemtime = filemtime;
        this.filePostfix = filePostfix;
    }

    public long getSeek() {
        return seek;
    }

    public void setSeek(long seek) {
        this.seek = seek;
    }

    public String getFilemtime() {
        return filemtime;
    }

    public void setFilemtime(String filemtime) {
        this.filemtime = filemtime;
    }

    public String getFilePostfix() {
        return filePostfix;
    }

    public void setFilePostfix(String filePostfix) {
        this.filePostfix = filePostfix;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "FileDownLoadBean{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", length=" + length +
                ", finished=" + finished +
                '}';
    }
}
