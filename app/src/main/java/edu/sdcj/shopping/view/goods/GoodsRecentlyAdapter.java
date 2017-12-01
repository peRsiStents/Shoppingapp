package edu.sdcj.shopping.view.goods;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import edu.sdcj.shopping.dao.RecentlyGoodsDao;
import edu.sdcj.shopping.entity.*;
import edu.sdcj.shopping.view.User.R;

/**
 * Created by liangshan on 2017/11/8.
 */

public class GoodsRecentlyAdapter extends BaseAdapter {
    private RecentlyGoodsDao recentlyGoodsDao;
    private Context context;//上下文
    private List<RecentlyGoods> list;
    private boolean isShow = true;//是否显示编辑/完成

    public GoodsRecentlyAdapter(Context context, List<RecentlyGoods> list) {
        this.context = context;
        this.list = list;
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.activity_myfootitem,null);
            viewHolder.tv_price=(TextView)view.findViewById(R.id.tv_price);
            viewHolder.tv_title=(TextView)view.findViewById(R.id.tv_title);
            viewHolder.tv_delete=(TextView)view.findViewById(R.id.tv_deletes);
            viewHolder.iv_Img=(ImageView)view.findViewById(R.id.iv_firstImg);
            viewHolder.tv_count=(TextView)view.findViewById(R.id.tv_count);
            view.setTag(viewHolder);//数据的重用
        }else{
            viewHolder=(ViewHolder)view.getTag();
        }
       // viewHolder.tv_count.setText(list.get(position).g);
        viewHolder.tv_title.setText(list.get(position).getGoodsName());
        viewHolder.tv_price.setText(list.get(position).getGoodsPrice()+"");
        viewHolder.tv_count.setText("浏览次数："+list.get(position).getPrioritycount());
        ImageLoader.getInstance().displayImage(list.get(position).getGoodsPhoto(),viewHolder.iv_Img);

        viewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog=new AlertDialog.Builder(context).create();
                alertDialog.setTitle("操作提示");
                alertDialog.setMessage("您确定要将此记录移除吗？");

                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                recentlyGoodsDao=new RecentlyGoodsDao(context);
                                recentlyGoodsDao.deleteGoods(list.get(position).getGoodsId());


                                list.remove(position);//移除
                                notifyDataSetChanged();//重绘当前可见区域
                            }
                        });
                alertDialog.show();
            }
        });
        //判断是否在编辑状态下
        if (isShow) {
            viewHolder.tv_delete.setVisibility(View.GONE);
        } else {
            viewHolder.tv_delete.setVisibility(View.VISIBLE);
        }
        return view;
    }

    /**
     * 是否显示可编辑
     */
    public void isShow(boolean flag) {
        isShow = flag;
        notifyDataSetChanged();//重绘当前可见区域
    }

    /**
     * 内部类
     */
    private class ViewHolder{
        TextView tv_title,tv_price,tv_delete,tv_count;
        ImageView iv_Img;
    }
}
