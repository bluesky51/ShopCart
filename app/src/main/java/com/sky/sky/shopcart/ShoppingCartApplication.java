package com.sky.sky.shopcart;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by BlueSky on 16/12/22.
 */

public class ShoppingCartApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //设置统计的场景类型(可以认为是统计的入口)
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }
}
