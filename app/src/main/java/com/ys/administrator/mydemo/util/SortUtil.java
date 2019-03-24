package com.ys.administrator.mydemo.util;

public class SortUtil {
    public static int sort(String typename,String str1,String str2){
        int weight1 = getWeight(typename, str1);
        int weight2 = getWeight(typename, str2);
        if(weight1==weight2){
            return 0;
        }else if(weight1<weight2){
            return -1;
        }else {
            return 1;
        }
    }
    public static int getWeight(String typename,String str2){
        if(typename.indexOf("基础资料")!=-1){
           return baseData(str2);
        }else if(typename.indexOf("土建图审上传")!=-1){
           return tushenData2(str2);
        }else if(typename.indexOf("装修图审上传")!=-1){
            return tushenData(str2);
        }else /*if(typename.indexOf("设计图纸")!=-1)*/{
           return shejiData(str2);
        }

    }

    private static int baseData(String typename) {
        if(typename.indexOf("营业执照")!=-1){
            return 1;
        }else if(typename.indexOf("项目立项")!=-1 ||typename.indexOf("租赁合同")!=-1  ){
            return 2;
        }else if(typename.indexOf("建设用地规划")!=-1||typename.indexOf("水电暖图纸")!=-1){
            return 3;
        }else if(typename.indexOf("规划红线图")!=-1||typename.indexOf("消防验收意见书")!=-1){
            return 5;
        }else if(typename.indexOf("面积预测绘")!=-1||typename.indexOf("建筑功能改变")!=-1){
            return 6;
        }else if(typename.indexOf("工程地址勘察")!=-1||typename.indexOf("现场视频")!=-1){
            return 7;
        }else if(typename.indexOf("其他文件")!=-1){
            return 8;
        }else {
            return 4;
        }
    }
    private static int tushenData(String typename) {
        if(typename.indexOf("设计合同")!=-1){
            return 1;
        }else if(typename.indexOf("授权委托书")!=-1  ){
            return 2;
        }else if(typename.indexOf("委托人身份证复印件")!=-1){
            return 3;
        }else if(typename.indexOf("初步设计批复或会议纪要")!=-1||typename.indexOf("项目技术复核表")!=-1){
            return 4;
        }else if(typename.indexOf("项目投资概算表")!=-1||typename.indexOf("建筑节能审查意见书")!=-1){
            return 6;
        }else if(typename.indexOf("勘察和设计资质证书复印件")!=-1){
            return 7;
        }else if(typename.indexOf("勘察和设计合同复印件")!=-1){
            return 8;
        }else if(typename.indexOf("设计合同")!=-1){
            return 9;
        }else if(typename.indexOf("项目投资概算")!=-1){
            return 10;
        }else if(typename.indexOf("其他文件")!=-1){
            return 11;
        }else {
            return 5;
        }
    }
    private static int tushenData2(String typename) {
         if(typename.indexOf("授权委托书")!=-1  ){
            return 1;
        }else if(typename.indexOf("委托人身份证复印件")!=-1){
            return 2;
        }else if(typename.indexOf("初步设计批复或会议纪要")!=-1){
            return 3;
        }else if(typename.indexOf("建筑节能审查意见书")!=-1){
            return 4;
        }else if(typename.indexOf("勘察和设计资质证书复印件")!=-1){
            return 6;
        }else if(typename.indexOf("勘察和设计合同复印件")!=-1){
            return 7;
        }else if(typename.indexOf("设计合同")!=-1){
            return 8;
        }else if(typename.indexOf("项目投资概算")!=-1){
            return 9;
        }else if(typename.indexOf("其他文件")!=-1){
            return 10;
        }else {
            return 5;
        }
    }
    private static int shejiData(String typename) {
        if(typename.indexOf("装饰")!=-1){
            return 1;
        }else if(typename.indexOf("给排水")!=-1   ){
            return 2;
        }else if(typename.indexOf("电气")!=-1){
            return 3;
        }else if(typename.indexOf("暖通")!=-1){
            return 4;
        }else if(typename.indexOf("结构")!=-1){
            return 6;
        }else if(typename.indexOf("节能")!=-1){
            return 7;
        }else if(typename.indexOf("其他文件")!=-1){
            return 8;
        }else {
            return 5;
        }
    }
}
