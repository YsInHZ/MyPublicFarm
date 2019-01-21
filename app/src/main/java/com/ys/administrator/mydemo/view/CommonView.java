package com.ys.administrator.mydemo.view;

import com.ys.administrator.mydemo.base.IBaseView;

/**
 * Created by Administrator on 2018/12/29.
 */

public interface CommonView extends IBaseView {
    /**
     * 当数据请求成功后，调用此接口显示数据
     * @param data 数据源
     */
    void showData(Object data);
}
