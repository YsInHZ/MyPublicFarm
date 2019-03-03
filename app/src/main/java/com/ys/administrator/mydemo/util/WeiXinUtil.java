package com.ys.administrator.mydemo.util;

import android.graphics.Bitmap;

import com.ys.administrator.mydemo.application.MyApplication;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * 微信工具类
 * Created by Administrator on 2018/8/2.
 */

public class WeiXinUtil {
    public static IWXAPI api = null;
     public static IWXAPI getIWXAPI() {
         if(api==null){
             api = WXAPIFactory.createWXAPI(MyApplication.getInstance(),Constant.APP_ID,true);
             api.registerApp(Constant.APP_ID);
         }
        return api;
    }

    /**
     * 微信登录
     */
    public static void WXLogin(){
         if(api==null){
             getIWXAPI();
         }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_gengnong_login";
        api.sendReq(req);
    }



    /**
     * 分享APP
     * @param aa 分享类型：SendMessageToWX.Req.WXSceneTimeline朋友圈 WXSceneSession对话
     */
    public static void share(int aa){//SendMessageToWX.Req.WXSceneTimeline  WXSceneSession

        if(api==null){
            getIWXAPI();
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "https://www.hzmyxf119.com/static/app-release.apk";

        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "名邑消防";
        msg.description = "加入名邑消防，开启智慧管理";

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.scene = aa;
        api.sendReq(req);
    }

    /**
     * 分享图片
     * @param bitmap
     * @param aa 分享类型：SendMessageToWX.Req.WXSceneTimeline朋友圈 WXSceneSession对话
     */
    public static void share(Bitmap bitmap, int aa){
        if(api==null){
            getIWXAPI();
        }
        WXImageObject imageObject = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.title = "极客耘耕";
        msg.description = "扫码加入我的农场吧";
        msg.mediaObject = imageObject;
        SendMessageToWX.Req req= new SendMessageToWX.Req();
//        req.transaction = System.currentTimeMillis()+""+Constant.getUSERID();
        req.message = msg;
        req.scene = aa;
        api.sendReq(req);
    }
    public static void detach(){
        api.detach();
        api = null;
    }
}
