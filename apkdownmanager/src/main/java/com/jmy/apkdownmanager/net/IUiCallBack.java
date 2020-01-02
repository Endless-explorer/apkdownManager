package com.jmy.apkdownmanager.net;

/**
 * create by johnseg(@320838955@qq.com)
 * 时间：2019/11/9
 */
public interface IUiCallBack {
    void uiDownSuccess();

    void uiProgress(int progress);

    void uiDownFailure(Throwable e);

}
