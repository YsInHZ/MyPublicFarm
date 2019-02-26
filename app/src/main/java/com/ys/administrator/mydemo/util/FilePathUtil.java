package com.ys.administrator.mydemo.util;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilePathUtil {
    /**
     * 根据传入的文件路径创建文件
     * @param path 根目录以上的路径
     */
    public static File creatFile(String path){
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        absolutePath = absolutePath +"/MyProjece";
        if(path.indexOf("/")!=0){
            absolutePath = absolutePath+"/"+path;
        }else {
            absolutePath = "/"+path;
        }
        File file = new File(absolutePath);
        File parentFile = file.getParentFile();
        if(!parentFile.exists()){
            boolean mkdirs = parentFile.mkdirs();
        }
        if(!file.exists()){
            try {
                boolean newFile = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    public static String getFilePath(){
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        absolutePath = absolutePath +"/MyProjece/";
        return absolutePath;
    }
    public static String getFilePathWithOutEnd(){
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        absolutePath = absolutePath +"/MyProjece";
        return absolutePath;
    }
    /**
     * 根据传入的文件夹路径 获取子文件（不包含子文件夹）
     * @param path 文件夹路径
     * @return
     */
    public static List<File> readFiles(String path) {
        //根据路径拿到文件
        File file = new File(path);
        //判断 文件已存在并且为文件夹
        if(file.exists() && file.isDirectory()){
            //获取文件列表
            File[] files = file.listFiles();
            //如果文件为空返回空数组
            if(files ==null || files.length==0){
                return new ArrayList<>();
            }
            //转换为list
            List<File> fileList = new ArrayList<>();
            for (int i = 0; i <files.length ; i++) {
                if(!files[i].isDirectory()){
                    fileList.add(files[i]);
                }
            }

            //一切顺利返回数组
            return fileList;

        }else {
            return new ArrayList<>();
        }

    }
    /**
     * 根据传入的文件夹路径 获取子文件（含子文件夹）
     * @param path 文件夹路径
     * @return
     */
    public static List<File> readFilesWithDirectory(String path) {
        //根据路径拿到文件
        File file = new File(path);
        //判断 文件已存在并且为文件夹
        if(file.exists() && file.isDirectory()){
            //获取文件列表
            File[] files = file.listFiles();
            //如果文件为空返回空数组
            if(files ==null || files.length==0){
                return new ArrayList<>();
            }
            //转换为list
            List<File> fileList = new ArrayList<>();
            for (int i = 0; i <files.length ; i++) {
               fileList.add(files[i]);

            }

            //一切顺利返回数组
            return fileList;

        }else {
            return new ArrayList<>();
        }

    }
}
