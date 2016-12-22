package com.sky.sky.shopcart.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sky.sky.shopcart.R;
import com.sky.sky.shopcart.bean.Goods;
import com.sky.sky.shopcart.callback.OnItemClicklistener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BlueSky on 16/12/21.
 */

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.MyViewHolder> {

    private  Context context;
    private List<Goods.DataBean.ItemsBean> itemsBeanList = new ArrayList<>();

    private OnItemClicklistener onItemClicklistener;
    //创建得到adapter对象
    public GoodsAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemClicklistener(OnItemClicklistener onItemClicklistener) {
        this.onItemClicklistener = onItemClicklistener;
    }

    //刷新数据
    public void setItemsBeanList(List<Goods.DataBean.ItemsBean> itemsBeanList) {
        this.itemsBeanList = itemsBeanList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
       final Goods.DataBean.ItemsBean.ComponentBean componentBean = itemsBeanList.get(position).getComponent();
        Glide.with(context).load(componentBean.getPicUrl())
                .placeholder(R.mipmap.ic_launcher)
                .skipMemoryCache(true)
                .thumbnail(0.5f)
                .into(holder.imageView);
        holder.tv_description.setText(componentBean.getDescription());
        holder.tv_original_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        holder.tv_original_price.setText(componentBean.getOrigin_price()+"元");
        holder.tv_price.setText(componentBean.getPrice()+"元");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClicklistener!=null){
                    onItemClicklistener.onItemClickListener(componentBean,holder.getLayoutPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsBeanList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tv_description;
        TextView tv_original_price;
        TextView tv_price;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            tv_description = (TextView) itemView.findViewById(R.id.tv_descripition);
            tv_original_price = (TextView) itemView.findViewById(R.id.tv_original_price);
            tv_price = (TextView) itemView.findViewById(R.id.textView3);

        }
    }
}
