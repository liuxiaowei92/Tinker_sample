package com.study.tinker_sample;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.study.library.FixDexUtils;
import com.study.library.uitls.Constants;
import com.study.library.uitls.FileUitls;
import com.study.tinker_sample.utils.ParamsSort;

import java.io.File;
import java.io.IOException;

/**
 * @authour lxw
 * @function
 * @date 2019/10/14
 */
public class SecondActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    //显示
    public void show(View view) {
        ParamsSort sort=new ParamsSort();
        sort.math(this);

    }
    //修复
    public void fix(View view) {
        //复制下载的修复包到私有目录
        //模拟网络下载到sdCard中
        File sourceFile=new File(Environment.getExternalStorageDirectory(), Constants.DEX_NAME);
        //私有目录
        File targetFile=new File(getDir(Constants.DEX_DIR, Context.MODE_PRIVATE).getAbsolutePath()
            +File.separator+Constants.DEX_NAME);
        //如果私有目录有存在的修复包，说明是之前修复的
        if(targetFile.exists()){
            targetFile.delete();
            Toast.makeText(this,"删除以前的dex",Toast.LENGTH_SHORT).show();
        }
        try {
            FileUitls.copyFile(sourceFile,targetFile);
            Toast.makeText(this,"文件复制成功",Toast.LENGTH_SHORT).show();
            FixDexUtils.loadFixeDex(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
