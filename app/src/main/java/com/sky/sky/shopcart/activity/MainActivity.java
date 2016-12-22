package com.sky.sky.shopcart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.sky.sky.shopcart.R;
import com.sky.sky.shopcart.adapter.GoodsAdapter;
import com.sky.sky.shopcart.apiservice.HttpApiService;
import com.sky.sky.shopcart.bean.Goods;
import com.sky.sky.shopcart.callback.OnItemClicklistener;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 友盟统计的AppKey:585b76b5e88bad58390002ca
 */
public class MainActivity extends BaseActivity {

    //RecyclerView负责布局复用,不负责布局，布局由布局管理器
    //用有限的窗口加载大量数据集合
    RecyclerView recyclerView;

    GoodsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLeftVisible(false);
        setTv_title("商品列表");
        setRightVisible(false);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //网格布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);


        //一进入程序加载空数据
        adapter = new GoodsAdapter(this);
        recyclerView.setAdapter(adapter);

        //点击事件
        adapter.setOnItemClicklistener(new OnItemClicklistener() {
            @Override
            public void onItemClickListener(Goods.DataBean.ItemsBean.ComponentBean componentBean, int pos) {
                Log.e("======", "+=======" + componentBean.getDescription());
                String sourceId = componentBean.getAction().getSourceId();
                Intent intent = new Intent(MainActivity.this, GoodsDetailActivity.class);
                intent.putExtra("sourceId", sourceId);
                intent.putExtra("bean", componentBean);
                startActivity(intent);
            }

        });

        //填充数据
        getGoodsData("连衣裙");

    }

    //http://api-v2.mall.hichao.com/search/skus?query=连衣裙&sort=all&ga=%252Fsearch%252Fskus&flag=&cat=&asc=1
    public void getGoodsData(String key) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api-v2.mall.hichao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        HttpApiService httpApiService = retrofit.create(HttpApiService.class);
        Observable<Goods> observable = httpApiService.getGoodsList(
                key, "all", "%252Fsearch%252Fskus", "", "", "1"
        );
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Goods>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Goods goods) {
                        // Log.e("=====","===onNext===="+goods.getData().getItems().size());
                        adapter.setItemsBeanList(goods.getData().getItems());
                    }
                });


    }


}
