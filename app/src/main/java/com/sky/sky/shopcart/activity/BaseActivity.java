package com.sky.sky.shopcart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.sky.shopcart.R;
import com.sky.sky.shopcart.db.DBManager;
import com.sky.sky.shopcart.event.ShopCartNumEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseActivity extends AppCompatActivity {
    ImageView img_left;
    ImageView img_right;
    TextView tv_title;
    Button btn_num;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected  void init(){
        img_left = (ImageView) findViewById(R.id.img_left);
        img_right = (ImageView) findViewById(R.id.img_right);
        tv_title = (TextView) findViewById(R.id.title);
        btn_num= (Button) findViewById(R.id.btn_num);
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = getLayoutInflater().inflate(R.layout.activity_base, null);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.content);
        // 将传入的layout加载到activity_base的relativeLayout里面
        getLayoutInflater().inflate(layoutResID, relativeLayout, true);
        super.setContentView(view);
        init();
        btn_num.setText(String.valueOf(DBManager.getDbManager(this).queryAll().size()));

    }

    public  void setTv_title(String title){
        tv_title.setText(title);
    };

    public  void setLeftVisible(boolean isVisible){
        img_left.setVisibility((isVisible==true)?View.VISIBLE:View.GONE);
    };
    public  void setRightVisible(boolean isVisible){
        img_right.setVisibility((isVisible==true)?View.VISIBLE:View.GONE);
        btn_num.setVisibility((isVisible==true)?View.VISIBLE:View.GONE);
    };
    public void back(View view){
        finish();
    }
    public void lookShoppingCart(View view){
        startActivity(new Intent(this, ShoppingCartActivity.class));
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShopCartNumEvent(ShopCartNumEvent event) {
        Log.e("====","==onShopCartNumEvent====");
        btn_num.setText(String.valueOf(DBManager.getDbManager(this).queryAll().size()));

    };
}
