package com.sky.sky.shopcart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.sky.sky.shopcart.R;
import com.sky.sky.shopcart.adapter.MyAdapter;
import com.sky.sky.shopcart.bean.ItemShopCart;
import com.sky.sky.shopcart.callback.OnItemCheckedListener;
import com.sky.sky.shopcart.callback.OnNumAscListener;
import com.sky.sky.shopcart.callback.OnNumDescListener;
import com.sky.sky.shopcart.db.DBManager;
import com.sky.sky.shopcart.pay.PayDemoActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sky.sky.shopcart.db.DBManager.getDbManager;

public class ShoppingCartActivity extends BaseActivity {

    ListView listView;

    MyAdapter myAdapter;
    CheckBox cb_all;

    Map<Integer, Boolean> map;
    List<ItemShopCart> shopCartList;

    TextView tv_all_money;
    //监听的是状态发生的变化setOnCheckedChangeListener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        setTv_title("购物车");
        setRightVisible(false);
        initData();
        setListener();


    }
    public void payMoney(View view){
        startActivity(new Intent(ShoppingCartActivity.this, PayDemoActivity.class));
    }

    public void initData() {

        listView = (ListView) findViewById(R.id.listview);
        cb_all = (CheckBox) findViewById(R.id.cb_all);
        tv_all_money = (TextView) findViewById(R.id.tv_all_money);
        //先加载空列表
        myAdapter = new MyAdapter(this, cb_all);
        listView.setAdapter(myAdapter);

        //getSupportActionBar().setTitle("购物车");
        //真实情况，调用服务器接口地址查询购物车列表，列表展示(模拟数据库查询列表展示)
        shopCartList = getDbManager(this).queryAll();
        myAdapter.setShopCartList(shopCartList);

        totalMoney();
        map = new HashMap<>();
        for (int i = 0; i < shopCartList.size(); i++) {
            map.put(i, false);
        }
        //初始默认值设置
        myAdapter.setMap(map);
    }

    public void setListener() {
        myAdapter.setOnItemCheckedListener(new OnItemCheckedListener() {
            @Override
            public void onItemCheckedListener(CheckBox checkBox, int pos) {
                ItemChecked(checkBox, pos);
                totalMoney();
            }
        });
        myAdapter.setOnNumAscListener(new OnNumAscListener() {
            @Override
            public void onNumAscListener(ItemShopCart itemShopCart) {
                //更新数据库中的num数量刷新，
                int num = itemShopCart.getNum();
                num++;
                itemShopCart.setNum(num);
                getDbManager(ShoppingCartActivity.this)
                        .updateNum(itemShopCart);
                myAdapter.notifyDataSetChanged();
                totalMoney();
            }
        });

        myAdapter.setOnNumDescListener(new OnNumDescListener() {
            @Override
            public void onNumDescListener(ItemShopCart itemShopCart) {
                int num = itemShopCart.getNum();
                if ((num - 1) >= 0) {
                    num = num - 1;
                    itemShopCart.setNum(num);
                    getDbManager(ShoppingCartActivity.this)
                            .updateNum(itemShopCart);
                    myAdapter.notifyDataSetChanged();
                }
                totalMoney();

            }
        });

        cb_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < shopCartList.size(); i++) {
                    map.put(i, cb_all.isChecked());
                }
                //设置点击发生变化刷新
                myAdapter.setMap(map);
                totalMoney();

            }
        });

    }

    public void ItemChecked(CheckBox cb, int pos) {
        int trueCount = 0;
        if (cb.isChecked()) {

            //选中，
            if (cb.getTag() != null
                    && cb.getTag().equals(pos)) {
                map.put(pos, true);
            }


            //统计true的个数
            for (int i = 0; i < map.size(); i++) {
                boolean flag = map.get(i);
                if (flag == true) {
                    trueCount++;
                }
            }
            if (trueCount == myAdapter.getCount()) {
                //表示全选按钮要选中
                cb_all.setChecked(true);
            }

        } else {
            //不选中，
            if (cb.getTag() != null
                    && cb.getTag().equals(pos)) {
                map.put(pos, false);
            }

            //表示全选按钮要取消
            cb_all.setChecked(false);
        }
    }

    public void totalMoney() {
        List<ItemShopCart> shopCartList = DBManager.getDbManager(this).queryAll();
        int totalMoney = 0;
        for (int i = 0; i < shopCartList.size(); i++) {
            Map<Integer, Boolean> map1 = myAdapter.getMap();
            if (map1 != null) {
                if (map1.get(i) == true) {
                    ItemShopCart item = shopCartList.get(i);
                    int OnePrice = Integer.parseInt(item.getPrice()) * item.getNum();
                    totalMoney += OnePrice;
                }
            }
        }

        tv_all_money.setText("合计:"+totalMoney+"元");

    }
}
