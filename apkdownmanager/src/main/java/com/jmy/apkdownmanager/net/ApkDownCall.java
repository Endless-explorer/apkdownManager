package com.jmy.apkdownmanager.net;

import java.io.File;

/**
 * create by johnseg(@320838955@qq.com)
 * 时间：2019/11/9
 */
public interface ApkDownCall {

     void onSuccess(File file);

     void onProgress(int progress);

     void onFailure(final  Throwable throwable);

}
