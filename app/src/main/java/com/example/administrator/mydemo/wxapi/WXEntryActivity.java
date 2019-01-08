package com.example.administrator.mydemo.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.administrator.mydemo.activity.LoginActivity;
import com.example.administrator.mydemo.util.Constant;
import com.example.administrator.mydemo.util.WeiXinUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;


/**
 * Created by Administrator on 2018/5/21.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        api = WeiXinUtil.getIWXAPI();
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:

                if(baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH){//微信登录
                    String code = ((SendAuth.Resp) baseResp).code;

                    //请求成功
                    Constant.WX_LGOIN = true;
                    Intent intent = new Intent(this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("code",code);
                    startActivity(intent);
                }else if(baseResp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX){//微信分享

                    WXEntryActivity.this.finish();
                }else {
                    WXEntryActivity.this.finish();
                }


                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                WXEntryActivity.this.finish();
                //取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                WXEntryActivity.this.finish();
                //拒绝
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                WXEntryActivity.this.finish();
                //不支持
                break;
            default:
                //未知
                WXEntryActivity.this.finish();
                break;
        }
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        Toast.makeText(this,"onNewIntent", Toast.LENGTH_SHORT).show();
    }
}
