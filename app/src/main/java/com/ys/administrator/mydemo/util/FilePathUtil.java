package com.ys.administrator.mydemo.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
            Collections.sort(fileList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return o2.lastModified() == o1.lastModified()?0:(o2.lastModified()> o1.lastModified()?1:-1);
                }
            });
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

            Collections.sort(fileList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return  o2.lastModified() == o1.lastModified()?0:(o2.lastModified()> o1.lastModified()?1:-1);
                }
            });
            //一切顺利返回数组
            return fileList;

        }else {
            return new ArrayList<>();
        }

    }
    /**
     * 根据传入的文件夹路径 获取子文件夹
     * @param path 文件夹路径
     * @return
     */
    public static List<File> readDirectory(String path) {
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
                if(files[i].isDirectory()){
                    fileList.add(files[i]);
                }
            }

            Collections.sort(fileList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return  o2.lastModified() == o1.lastModified()?0:(o2.lastModified()> o1.lastModified()?1:-1);
                }
            });
            //一切顺利返回数组
            return fileList;

        }else {
            return new ArrayList<>();
        }

    }

    /**
     * 根据传入的文件夹路径 获目标文件
     * @return
     */
    public static File findLocalFile(File file,String  localName){
        if(!file.exists() || !file.isDirectory()){
            return null;
        }
        File[] files = file.listFiles();
        for (File ff:files) {
            if(ff.isDirectory() && ff.getName().equals(localName)){
                return ff;
            }
        }
        return null;
    }
    /**
     * 根据传入的文件夹路径 获目标后缀文件列表
     * @return
     */
    public static  List<File> findLocalSuffix(File file,String  localSuffix){
        List<File> results = new ArrayList<>();
        if(file==null || !file.exists() || !file.isDirectory()){
            return results;
        }
        File[] files = file.listFiles();
        for (File ff:files) {
            if(!ff.isDirectory()){
                String name = ff.getName();
                if(name.endsWith(localSuffix)){
                    results.add(ff);
                }

            }
        }
        return results;
    }
    public static void openFiles(Context context, String filesPath) {


//        Uri uri = Uri.parse("file://" + filesPath);
        Uri uri ;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.ys.administrator.mydemo.fileprovider", new File(filesPath));
        } else {
            uri = Uri.fromFile(new File(filesPath));
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.setAction(Intent.ACTION_VIEW);

        String type = getMIMEType(filesPath);
        intent.setDataAndType(uri, type);
        if (!TextUtils.isEmpty(type) && !type.equals("*/*")) {
            try {
                context.startActivity(intent);
            } catch (Exception e) {
                context.startActivity(showOpenTypeDialog(filesPath));
            }
        } else {
//            context.startActivity(showOpenTypeDialog(filesPath));
            Toast.makeText(context,"无法解析文件类型，请到"+filesPath+"下查看",Toast.LENGTH_LONG).show();
        }
    }

    //显示打开方式
    public static void show(Context context,String filesPath){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        context.startActivity(showOpenTypeDialog(filesPath));
    }

    public static Intent showOpenTypeDialog(String param) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    /**
     * --获取文件类型 --
     */
    public static String getMIMEType(String filePath) {
        String type = "*/*";
        String fName = filePath;

        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }

        String end = fName.substring(dotIndex+1).toLowerCase();
        if (end == "") {
            return type;
        }

        type = MediaTypeUtil.guessMimeTypeFromExtension(end);
        return type;

    }
}
