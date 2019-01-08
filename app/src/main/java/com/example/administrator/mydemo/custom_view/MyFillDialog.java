package com.example.administrator.mydemo.custom_view;

/**
 * Created by Administrator on 2018/12/31.
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mydemo.R;

/**
 * 自定义弹出DIALOG
 */
public class MyFillDialog extends Dialog {
    private View view;
    private Context context;
    ;

    public MyFillDialog(Context context,int layout) {
        super(context, R.style.Translucent_NoTitle);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(layout, null);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(view);
    }
    @Override
    public View findViewById(int id) {
        return view.findViewById(id);
    }
    public void setText(int id,String text){
        ((TextView)findViewById(id)).setText(text);
    }
    public void setImageBitmap(int id,Bitmap bitmap){
        ((ImageView)findViewById(id)).setImageBitmap(bitmap);
    }
    public View getView(){
        return view;
    }



}