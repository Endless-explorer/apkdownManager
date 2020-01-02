package com.jmy.apkdownmanager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.core.content.FileProvider;
import com.jmy.apkdownmanager.net.ApkDownCall;
import com.jmy.apkdownmanager.net.IDownManager;
import com.jmy.apkdownmanager.net.IUiCallBack;

import java.io.File;

/**
 * create by johnseg(@320838955@qq.com)
 * 时间：2019/11/9
 */
public class ApkDownManager {
    private static ApkDownManager manager;
    private  IDownManager iDownManager;
    private static Handler handler=new Handler(Looper.getMainLooper());

    private ApkDownManager() {
        this.iDownManager=new OkHttpDownManager();
    }

    public static ApkDownManager instance(){
        if(manager==null){
            synchronized (ApkDownManager.class){
                if(manager==null){
                    manager=new ApkDownManager();
                }
            }
        }
        return manager;
    }

    public void download(String apkName, final Activity activity, String url, final IUiCallBack uiCallBack){
        if(apkName==null){
            apkName=System.currentTimeMillis()+"";
        }
        File file=new File(activity.getApplicationContext().getCacheDir(),apkName+".apk");
        Log.i("jmy","文件路径为:"+file.getAbsolutePath());
        iDownManager.download(url, file, new ApkDownCall() {
            @Override
            public void onSuccess(File file) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(uiCallBack!=null){
                            uiCallBack.uiDownSuccess();
                        }
                    }
                });
                install(activity,file);
            }

            @Override
            public void onProgress(final int progress) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(uiCallBack!=null){
                            uiCallBack.uiProgress(progress+"%");
                        }
                    }
                });
            }

            @Override
            public void onFailure(final Throwable throwable) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(uiCallBack!=null){
                            uiCallBack.uiDownFailure(throwable);
                        }
                    }
                });
            }
        });
    }


    private void install(Activity activity, File file){
        Intent intent=new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri= FileProvider.getUriForFile(activity,activity.getPackageName()+".fileprovider",file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }else {
            uri=Uri.fromFile(file);
        }
        intent.setDataAndType(uri,"application/vnd.android.package-archive");
        activity.startActivity(intent);

    }



}
