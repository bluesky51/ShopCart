package com.sky.sky.shopcart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sky.sky.shopcart.R;
import com.sky.sky.shopcart.bean.Goods;
import com.sky.sky.shopcart.bean.ItemShopCart;
import com.sky.sky.shopcart.db.DBManager;
import com.sky.sky.shopcart.event.ShopCartNumEvent;
import com.sky.sky.shopcart.pay.PayDemoActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class GoodsDetailActivity extends BaseActivity {

    String path = "http://m.hichao.com/lib/interface.php?m=goodsdetail&sid=%s";
    WebView webView;
    ProgressBar progressBar;
    Goods.DataBean.ItemsBean.ComponentBean componentBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        findView();
        String sourceId = getIntent().getStringExtra("sourceId");
        componentBean = (Goods.DataBean.ItemsBean.ComponentBean) getIntent().getSerializableExtra("bean");
        setTv_title(componentBean.getDescription());
        String url = String.format(path, sourceId);
        webView.loadUrl(url);
    }

    public void findView(){
        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }


    public void buy(View view) {
        //立即购买
        startActivity(new Intent(GoodsDetailActivity.this, PayDemoActivity.class));
    }

    public void insertDB(View view) {
        //加入到购物车(说明：真实项目情况是post发送给网络服务器存储，模拟插入到数据库)
        long id = Long.parseLong(componentBean.getAction().getSourceId());

        List<ItemShopCart> list = DBManager.getDbManager(this).queryOne(id);
        ItemShopCart itemShopCart = new ItemShopCart();
        itemShopCart.setDescription(componentBean.getDescription());
        itemShopCart.setId(id);
        itemShopCart.setImgUrl(componentBean.getPicUrl());
        if (list!=null&&list.size()>0){
            //表示插入过
            itemShopCart.setNum(list.get(0).getNum()+1);
        }else{
            itemShopCart.setNum(1);
        }
        itemShopCart.setPrice(componentBean.getPrice());
        DBManager.getDbManager(this).insert(itemShopCart);

        Toast.makeText(this,"添加成功",Toast.LENGTH_LONG).show();
        EventBus.getDefault().post(new ShopCartNumEvent());
    }
}
