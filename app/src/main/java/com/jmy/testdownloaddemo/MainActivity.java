package com.jmy.testdownloaddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jmy.apkdownmanager.ApkDownManager;
import com.jmy.apkdownmanager.net.IUiCallBack;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button=findViewById(R.id.update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="http://p.gdown.baidu.com/07aecb4e46127553729ea9137908b4362f9f1d42e814794e3463f75b328fb294c3868af2386741d195dec95b86beb20bdf843d2e792dc038e3f42d2d758f31b8c89db5c4c8608f4de93a9f46abf9ec6b6d20dc0c58422d40f6d7d1a22c43aa9ccbaa22c22eba60f33afe00f0dd138f5cfd72bb721b8ab5932a8b6f0318eddba0e2f48e1b41a0d4cebc08522b3cb5c2f8469dd34213890053aa0c921248662be6e8c9bc01e28b80d6b9742033e72b07c84dd4e14dc8d79310269387d03218084d15ac8d346690f16bfcc5772889701593cd88492235593de8dc520fa2b38c54b0b4c2d68fc83f439b4ed655a27f0c8a545be16ede96ceb7c9a71d2b528a895461";
                String apkName="baiDuMap";
                ApkDownManager.instance().download(apkName, MainActivity.this, url, new IUiCallBack() {
                    @Override
                    public void uiDownSuccess() {
                        Toast.makeText(MainActivity.this,"下载Akp成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void uiProgress(int progress) {
                        button.setText(progress+"%");
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
