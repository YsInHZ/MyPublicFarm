package com.ys.administrator.mydemo.util;

import android.content.ClipboardManager;
import android.content.Context;

public class ClipBoardUtil {
    public static void CopyToClipboard(Context context, String text){
        ClipboardManager clip = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        //clip.getText(); // 粘贴
        clip.setText(text); // 复制
    }
}
