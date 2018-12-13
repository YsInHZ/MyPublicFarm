package com.example.administrator.mydemo.base;

/**
 * Created by Administrator on 2018/8/2.
 */

public interface IPersenter<V extends IBaseView> {

    /**
     * 绑定视图
     *
     * @param view
     */
    void attachView(V view);

    /**
     * 解除绑定（每个V记得使用完之后解绑，主要是用于防止内存泄漏问题）
     */
    void dettachView();
}
