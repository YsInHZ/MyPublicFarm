//package com.example.administrator.mydemo.download_util;
//
//import android.util.Log;
//
//import MyApplication;
//import com.example.administrator.mydemo.model.FileDownLoadBean;
//import FilePathUtil;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.RandomAccessFile;
//
//import okhttp3.Headers;
//import okhttp3.MediaType;
//
//
//
//import okhttp3.ResponseBody;
//import retrofit2.Response;
//
//
//public class DownLoadManager {
//    //Log标记
//    private static final String TAG = "DownLoad";
//    //APK文件类型
////    private static String APK_CONTENTTYPE = "application/vnd.android.package-archive";
////    //PNG文件类型
////    private static String PNG_CONTENTTYPE = "image/png";
////    //JPG文件类型
////    private static String JPG_CONTENTTYPE = "image/jpg";
////    //ZIP类型
////    private static String ZIP_CONTENTTYPE = "application/zip";
////    //文件后缀名
////    private static String fileSuffix="";
//
//    /**
//     * 写入文件到本地
//     * @param fileBean
//     * @param
//     * @return
//     */
//    public static boolean  writeResponseBodyToDisk(FileDownLoadBean fileBean, Response<ResponseBody> response) {
//        Headers headers = response.headers();
//        String lm = headers.get("Last-Modified");
//        if(lm!=null && lm.equals(fileBean.getFilemtime())){
////            fileBean
//        }else {
//            fileBean.setSeek(0);
//        }
//        fileBean.setFilemtime(lm);
//        String url = response.raw().request().url().toString();
//        int i = url.lastIndexOf("/");
//        String substring = url.substring(i+1);
//        ResponseBody body =response.body();
////
//        Log.d(TAG, "contentType:>>>>" + body.contentType().toString());
//        //下载文件类型判断，并对fileSuffix赋值
//        long l = body.contentLength();
//        String type = body.contentType().toString();
//        MediaType mediaType = body.contentType();
//        String type1 = mediaType.type();
////        if (type.equals(APK_CONTENTTYPE)) {
////            fileSuffix = ".apk";
////        } else if (type.equals(PNG_CONTENTTYPE)) {
////            fileSuffix = ".png";
////        }
////        else if (type.equals(ZIP_CONTENTTYPE)) {
////            fileSuffix = ".zip";
////        }
//        //设置文件长度
//        fileBean.setLength(l);
//        fileBean.setFileName(substring);
//        fileBean.setFilePostfix(substring.substring(substring.indexOf(".")+1));
//        String s = fileBean.getFilePath()+ substring;
//        File file = FilePathUtil.creatFile(s);
//        // 其他类型同上 需要的判断自己加入.....
//
//        //下面就是一顿写入，文件写入的位置是通过参数file来传递的
//        InputStream is = null;
//        byte[] buf = new byte[2048];
//        int len = 0;
//        try {
//            RandomAccessFile desc = new RandomAccessFile(file, "rw");
//            desc.setLength(fileBean.getLength());
//            desc.seek(fileBean.getSeek());
//
//            is = body.byteStream();
//            long total = body.contentLength();
//            long sum = 0;
//            Log.d(TAG, "downLoadStart: 文件长度："+total+"\n"+"文件路径："+s);
//            while ((len = is.read(buf)) != -1) {
//
//                desc.write(buf, 0, len);
//                sum += len;
//                fileBean.setSeek(sum);
//                int progress = (int) (sum * 1.0f / total * 100);
//            }
//
//            desc.close();
//            Log.d(TAG, "downLoadFinish: 下载关闭");
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d(TAG, "downLoadError:"+e.getLocalizedMessage());
//            return false;
//        } finally {
////            MyApplication.getDaoSession().getFileDownLoadBeanDao().delete(fileBean);
////            MyApplication.getDaoSession().getFileDownLoadBeanDao().update(fileBean);
////            MyApplication.getDaoSession().getFileDownLoadBeanDao().update(fileBean);
//            try {
//                if (is != null)
//                    is.close();
//            } catch (IOException e) {
//            }
//
//        }
//    }
//}
//
