package edu.sdcj.shopping.view.order;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import edu.sdcj.shopping.dao.OrderDao;
import edu.sdcj.shopping.entity.Goods;
import edu.sdcj.shopping.entity.Order;
import edu.sdcj.shopping.entity.OrderDetial;
import edu.sdcj.shopping.view.User.R;
import edu.sdcj.shopping.view.User.StartActivity;


/**
 * Created by liangshan on 2017/11/9.
 */

public class OrderAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Order> list;//父目录内容
    private Map<Integer,List<OrderDetial>> map;//子目录内容
    private OrderDao orderDao;
    private  OrderDetial orderDetial;
    private boolean isShow = true;//是否显示编辑/完成
    //构造方法
    public OrderAdapter(Context context, List<Order> list, Map<Integer, List<OrderDetial>> map) {
        this.context = context;
        this.list = list;
        this.map = map;
    }


    @Override
    public int getGroupCount() {
        return list.size();
    }
    //获取子项的个数
    @Override
    public int getChildrenCount(int i) {
        int key=list.get(i).getOrderId();
        int size=map.get(key).size();
        return size;
    }

    @Override
    public Object getGroup(int i) {
        return list.get(i);
    }
    //获取自选项
    @Override
    public Object getChild(int i, int i1) {
        int key=list.get(i).getOrderId();
        return map.get(key).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }
    //获取自选项id
    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int position, boolean b, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.activity_order,null);
        //获取订单id、总价、订单日期、删除组件
        TextView tv_orderID=(TextView) view.findViewById(R.id.tv_orderID);
        TextView tv_totalPrice=(TextView) view.findViewById(R.id.tv_totalPrice);
        TextView tv_orderDate=(TextView) view.findViewById(R.id.tv_orderDate);
        TextView tv_delete=(TextView) view.findViewById(R.id.tv_delete);
        //
        tv_orderID.setText("订单"+list.get(position).getOrderId()+"");
        tv_totalPrice.setText("￥"+list.get(position).getTotalPrice()+"");
        tv_orderDate.setText(list.get(position).getOrderDate());
        //给删除按钮设监听
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击后显示提示框
                AlertDialog alertDialog=new AlertDialog.Builder(context).create();
                alertDialog.setTitle("操作提示");
                alertDialog.setMessage("确定删除此订单？");
                //给dialog设置取消按钮
                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                //给dialog设置确定按钮
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                orderDao =new OrderDao(context);
                                orderDao.deleteOrder(list.get(position).getOrderId());
                                list.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                alertDialog.show();
            }
        });
        /*判断是否在编辑状态下
        * 编辑状态下显示按钮
        * 不是编辑状态不显示编辑按钮
        * */
        if(isShow){
            tv_delete.setVisibility(View.GONE);
        }else{
            tv_delete.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        view=LayoutInflater.from(context).inflate(R.layout.activity_order1,null);
        //获取各组件
        TextView tv_price=(TextView)view.findViewById(R.id.tv_price);
        TextView tv_goodsname=(TextView)view.findViewById(R.id.tv_title);
        TextView tv_buycount=(TextView)view.findViewById(R.id.tv_bnum);
        ImageView iv_image=(ImageView)view.findViewById(R.id.iv_firstImgss);
         TextView tv_pingjia=(TextView)view.findViewById(R.id.tv_pingjia);
        orderDao=new OrderDao(context);//初始化OrderDao对象，用来查询订单信息
        int key=list.get(i).getOrderId();
        int goodsId=map.get(key).get(i1).getGoodsId();
         orderDetial =orderDao.getOrderGoods(key,goodsId);

        tv_price.setText("￥"+orderDetial.getGoodsPrice()+"");//设置商品价格
        tv_buycount.setText("X"+orderDetial.getGoodsNum()+"");//设置商品数量
        tv_goodsname.setText(orderDetial.getGoodsName());//设置商品名
        ImageLoader.getInstance().displayImage(orderDetial.getGoodsPhoto(),iv_image);//加载商图片
        tv_pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,PingjiaActivity.class);//初始化intent
                intent.putExtra("goodsid",orderDetial.getGoodsId());//传输信息goodsid
                intent.putExtra("orderid",orderDetial.getOrderId());//传输orderid
                context.startActivity(intent);//调用评价的activiy
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    //设置是不是显示状态
    public void isShow(boolean flag) {
        isShow = flag;
        notifyDataSetChanged();
    }
}
