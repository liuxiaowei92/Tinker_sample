package com.study.tinker_sample;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.study.library.FixDexUtils;

/**
 * @authour lxw
 * @function
 * @date 2019/10/14
 */
public class BaseApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
        FixDexUtils.loadFixeDex(this);
    }

}
