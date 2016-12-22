package com.sky.sky.shopcart.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by BlueSky on 16/12/21.
 */
@Entity
public class ItemShopCart {
    @Id
    long id;
    String imgUrl;
    String description;
    String price;
    int num;
    @Generated(hash = 1773412415)
    public ItemShopCart(long id, String imgUrl, String description, String price,
            int num) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.description = description;
        this.price = price;
        this.num = num;
    }
    @Generated(hash = 719667463)
    public ItemShopCart() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getImgUrl() {
        return this.imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPrice() {
        return this.price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public int getNum() {
        return this.num;
    }
    public void setNum(int num) {
        this.num = num;
    }


}
