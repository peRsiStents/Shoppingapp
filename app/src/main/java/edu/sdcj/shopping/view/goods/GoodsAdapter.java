package edu.sdcj.shopping.view.goods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import edu.sdcj.shopping.entity.Goods;
import edu.sdcj.shopping.view.User.R;

/**
 * Created by liangshan on 2017/11/8.
 */

public class GoodsAdapter extends BaseAdapter {
    private Context context;//上下文
    private List<Goods> list;
    //构造方法
    public GoodsAdapter(List<Goods> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view=LayoutInflater.from(context).inflate(R.layout.activity_goodsitme,null);
            viewHolder.tv_title=(TextView)view.findViewById(R.id.tv_title);//获取title组件，用来显示用户名
            viewHolder.iv_Img=(ImageView)view.findViewById(R.id.iv_fristImag);//获取iv_Img组件，用来显示商品图片
            viewHolder.tv_price=(TextView)view.findViewById(R.id.tv_price);//获取tv_price,用来显示价格
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.tv_title.setText(list.get(i).getGoodsName());//设置商品名
        viewHolder.tv_price.setText("￥"+list.get(i).getGoodsPrice()+"");//设置商品价格
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(list.get(i).getGoodsPhoto(),viewHolder.iv_Img);//显示图片
//        ImageLoader.getInstance().displayImage(list.get(i).getGoodsPhoto(),viewHolder.iv_Img);
          return view;
    }
    /**
     * 内部类
     */
    private class ViewHolder{
        TextView tv_title,tv_price;
        ImageView iv_Img;
    }
}
