package com.ys.administrator.mydemo.util;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

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
}
