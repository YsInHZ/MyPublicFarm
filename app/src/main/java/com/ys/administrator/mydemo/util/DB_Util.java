//package com.example.administrator.mydemo.util;
//import MyApplication;
//import com.example.administrator.mydemo.db.FileDownLoadBeanDao;
//import com.example.administrator.mydemo.model.FileDownLoadBean;
//import org.greenrobot.greendao.query.QueryBuilder;
//import java.util.List;
//public class DB_Util {
//    /**
//     * 保存Fileinfo
//     * 通过URL作为唯一判断标识
//     * 如果有则更改信息，没有则保存
//     * @param file
//     */
//    public static void upDataFileInfo( FileDownLoadBean file){
//        QueryBuilder<FileDownLoadBean> fileDownLoadBeanQueryBuilder = MyApplication.getDaoSession().getFileDownLoadBeanDao().queryBuilder();
//        List<FileDownLoadBean> list = fileDownLoadBeanQueryBuilder.where(FileDownLoadBeanDao.Properties.Url.eq(file.getUrl())).build().list();
//        if(list.size()!=0){
//            MyApplication.getDaoSession().getFileDownLoadBeanDao().update(file);
//        }else {
//            MyApplication.getDaoSession().getFileDownLoadBeanDao().insert(file);
//        }
//    }
//
//    /**
//     * 更改文件名
//     * 如果数据库存在此信息，保存文件名
//     * @param file
//     * @param oldFileName
//     */
//    public static void upDataFileName( FileDownLoadBean file,String oldFileName){
//        QueryBuilder<FileDownLoadBean> fileDownLoadBeanQueryBuilder = MyApplication.getDaoSession().getFileDownLoadBeanDao().queryBuilder();
//        List<FileDownLoadBean> list = fileDownLoadBeanQueryBuilder
//                .where(FileDownLoadBeanDao.Properties.FileName.eq(oldFileName),FileDownLoadBeanDao.Properties.FilePath.eq(file.getFilePath()))
//                .build().list();
//        if(list.size()!=0){
//            MyApplication.getDaoSession().getFileDownLoadBeanDao().update(file);
//        }
//    }
//}
