package com.sky.sky.shopcart.callback;

import com.sky.sky.shopcart.bean.Goods;

/**
 * Created by BlueSky on 16/12/21.
 */

public interface OnItemClicklistener {

    void onItemClickListener(Goods.DataBean.ItemsBean.ComponentBean componentBean, int pos);

}
