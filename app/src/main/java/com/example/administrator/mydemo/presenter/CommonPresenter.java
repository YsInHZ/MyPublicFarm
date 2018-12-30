package com.example.administrator.mydemo.presenter;

import com.example.administrator.mydemo.base.BasePresenter;
import com.example.administrator.mydemo.base.ICallBack;
import com.example.administrator.mydemo.base.MyModel;
import com.example.administrator.mydemo.view.CommonView;

/**
 * Created by Administrator on 2018/12/29.
 */

public class CommonPresenter extends BasePresenter<CommonView>{
    /**
     * 获取网络数据
     * @param params 参数
     */
    public void getData(String params){
        if (!isViewAttached()){
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        getView().showLoading();
        // 调用Model请求数据
        MyModel.getNetData(params, new ICallBack<String>() {
            @Override
            public void onSuccess(String data) {
                //调用view接口显示数据
                if(isViewAttached()){
                    getView().showData(data);
                }
            }

            @Override
            public void onFailure(String msg) {
                //调用view接口提示失败信息
                if(isViewAttached()){
                    getView().showToast(msg);
                }
            }

            @Override
            public void onError() {
                if(isViewAttached()){
                    getView().showErr();
                }
            }

            @Override
            public void onComplete() {
                // 隐藏正在加载进度条
                if(isViewAttached()){
                    getView().hideLoading();
                }
            }
        });
    }
}
