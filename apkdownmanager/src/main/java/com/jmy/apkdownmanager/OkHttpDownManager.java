package com.jmy.apkdownmanager;

import android.util.Log;

import androidx.annotation.NonNull;

import com.jmy.apkdownmanager.net.ApkDownCall;
import com.jmy.apkdownmanager.net.IDownManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * create by johnseg(@320838955@qq.com)
 * 时间：2019/11/9
 */
public class OkHttpDownManager implements IDownManager {

    private OkHttpClient okHttpClient=new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60,TimeUnit.SECONDS)
            .writeTimeout(60,TimeUnit.SECONDS)
            .build();


    @Override
    public void download(String url, final File targetFile, final ApkDownCall downCall) {
        if(!targetFile.exists()){
            targetFile.getParentFile().mkdir();
        }
        final Request request=new Request.Builder()
                .url(url).get().build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull final Call call,final  @NonNull IOException e) {
                downCall.onFailure(e);
                Log.i("jmy","返回的下载错误="+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                InputStream is=null;
                OutputStream os=null;
                try {
                    try {
                        targetFile.setExecutable(true,false);
                        targetFile.setReadable(true,false);
                        targetFile.setWritable(true,false);
                    } catch (final Exception e) {
                        e.printStackTrace();
                        downCall.onFailure(e);
                    }
                    final long totalLen=response.body().contentLength();
                    is=response.body().byteStream();
                    os=new FileOutputStream(targetFile);

                    byte[] buffer=new byte[10*1024];
                    long currLen=0;
                    int bufferLen=0;

                    while ((bufferLen=is.read(buffer))!=-1){
                        os.write(buffer,0,bufferLen);
                        os.flush();
                        currLen+=bufferLen;

                        final long finalCurrLen = currLen;
                        //记录下载进度
                        downCall.onProgress((int) (finalCurrLen *1.0f/totalLen*100));
                        //当100%的时候成功
                        if(finalCurrLen==totalLen){
                            downCall.onSuccess(targetFile);
                        }
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                    downCall.onFailure(e);
                } finally {
                    is.close();
                    os.close();
                }


            }
        });
    }
}
