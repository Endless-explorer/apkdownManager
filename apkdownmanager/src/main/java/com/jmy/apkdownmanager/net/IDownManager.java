package com.jmy.apkdownmanager.net;

import java.io.File;

/**
 * create by johnseg(@320838955@qq.com)
 * 时间：2019/11/9
 */
public interface IDownManager {

    void download(String url, File targetFile,ApkDownCall call);
}
