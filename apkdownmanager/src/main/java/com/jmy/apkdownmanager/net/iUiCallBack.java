package com.jmy.apkdownmanager.net;

/**
 * create by johnseg(@320838955@qq.com)
 * 时间：2019/11/9
 */
public interface iUiCallBack {
    void uiDownSuccess();

    void uiProgress(String progress);

    void uiDownFailure(Throwable e);

}
