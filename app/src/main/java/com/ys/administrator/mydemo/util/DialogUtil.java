package com.ys.administrator.mydemo.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ys.administrator.mydemo.R;
import com.ys.administrator.mydemo.custom_view.MyFillDialog;


/**
 * Created by Administrator on 2018/6/6.
 * dialog的设置工具
 */

public class DialogUtil {
    /**
     * 设置dialog为横向全屏显示
     * @param dialog
     */
    public static void setFilleDialog(Dialog dialog){
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        Window window = dialog.getWindow();
        window.setContentView(R.layout.activity_index);
        WindowManager.LayoutParams lp = window.getAttributes();
        //这句就是设置dialog横向满屏了。
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }




}
