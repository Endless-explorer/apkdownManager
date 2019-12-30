package com.jmy.testdownloaddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jmy.apkdownmanager.ApkDownManager;
import com.jmy.apkdownmanager.OkHttpDownManager;
import com.jmy.apkdownmanager.net.ApkDownCall;
import com.jmy.apkdownmanager.net.iUiCallBack;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button=findViewById(R.id.update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="http://p.gdown.baidu.com/4d5dc9a856b5dd63747666b4c6474ec7f89aa82c8c435624ae9eb8f03e13990fc0a1c3901103a326b882b00f235fa0532ac7973b9c8b2db45121e28e33ecee72269dc0c1087e0876b27e735c3da25ae9adda3c73771dde38474ca9db86e06ae0aea385d90238c160b363032bf055a6f5cd515ab984d249d2ed3735635ddb76817c3227a70a9f2fda6a06c875951ba80dde16643b19525d9062216d0338e5c563486f78df69a09917c77aa08585948215d57a34a7c908e1974b810b92539c0849bb2372f69110188ce863a8c61ccb19836e730a3802bf415680de9758c1cb31b7918055740a7e101be08bc1783cbb1e22387252037a701c9052651f73c5d0e6347f90b5ecec3a91a3acd256cf359fb01ce2d6bf44aed682d51e5fd03be74ec1d4c4233f889863cbb7901d242f9f5090d5b9cad0054102fcd847a9babe01cde84fc5bc9fc066dd894cb8420f96f1a4d67c";
                String apkName=getResources().getString(R.string.app_name);
                ApkDownManager.instance().setiDownManager(ApkDownManager.OKHTTP);
                ApkDownManager.instance().download(apkName, MainActivity.this, url, new iUiCallBack() {
                    @Override
                    public void uiDownSuccess() {
                        Toast.makeText(MainActivity.this,"下载Akp成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void uiProgress(String progress) {
                        button.setText(progress);
                    }

                    @Override
                    public void uiDownFailure(Throwable e) {
                        Toast.makeText(MainActivity.this,"下载失败：  "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
