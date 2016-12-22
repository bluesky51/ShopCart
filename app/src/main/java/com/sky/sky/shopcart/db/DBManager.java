package com.sky.sky.shopcart.db;

import android.content.Context;

import com.sky.sky.shopcart.bean.DaoMaster;
import com.sky.sky.shopcart.bean.DaoSession;
import com.sky.sky.shopcart.bean.ItemShopCart;
import com.sky.sky.shopcart.bean.ItemShopCartDao;

import java.util.List;

/**
 * Created by BlueSky on 16/12/21.
 */

public class DBManager {

    ItemShopCartDao itemShopCartDao;
    private static DBManager dbManager = null;

    public static DBManager getDbManager(Context context) {
        if (dbManager == null) {
            dbManager = new DBManager(context);

        }
        return dbManager;
    }

    public DBManager(Context context) {
        DaoSession daoSession = DaoMaster.newDevSession(context, "shopCart");
        itemShopCartDao = daoSession.getItemShopCartDao();
    }

    public void insert(ItemShopCart itemShopCart) {
        itemShopCartDao.insertOrReplaceInTx(itemShopCart);
    }

    public List<ItemShopCart> queryAll() {
        return itemShopCartDao.queryBuilder().list();
    }

    public List<ItemShopCart> queryOne(long sourceId) {
        List<ItemShopCart> list = itemShopCartDao.queryBuilder().where(ItemShopCartDao.Properties.Id.eq(sourceId)).list();
        return list;
    }


    public void updateNum(ItemShopCart itemShopCart) {
        itemShopCartDao.update(itemShopCart);
    }
}
