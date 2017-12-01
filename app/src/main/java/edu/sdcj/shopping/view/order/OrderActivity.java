package edu.sdcj.shopping.view.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.sdcj.shopping.dao.GoodsDao;
import edu.sdcj.shopping.dao.OrderDao;
import edu.sdcj.shopping.dao.UserDao;

import edu.sdcj.shopping.entity.Order;
import edu.sdcj.shopping.entity.OrderDetial;

import edu.sdcj.shopping.view.User.MyselfActivity;
import edu.sdcj.shopping.view.User.ObtainName;
import edu.sdcj.shopping.view.User.R;
import edu.sdcj.shopping.view.goods.GoodsDetailActivity;


/**
 * Created by liangshan on 2017/11/9.
 */

public class OrderActivity extends Activity {
    private ExpandableListView expandableListView;
    private Button bt_edit;//编辑按钮
    private List<Order> parentList;//父目录的内容
    private Map<Integer,List<OrderDetial>> map;//子目录内容
    private ImageView  return_back;//返回
    private  List<OrderDetial> orderDetialList;//详情
    private ObtainName obtainName;//获取用户名
    private GoodsDao goodsDao;
    private String userName;
    private int  userId;
    private OrderDao orderDao;
    private UserDao userDao;
    private  boolean flag=false;//判断是否在编辑状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        obtainName=new ObtainName(this);
        userName=obtainName.getUsernameBysp();//获取用户名
        userDao=new UserDao(this);
        userId=userDao.query(userName).getUserid();//获取用户id
        return_back=(ImageView)findViewById(R.id.return_back);
        bt_edit=(Button) findViewById(R.id.bt_edit);
        expandableListView=(ExpandableListView) findViewById(R.id.expandableListView);

        orderDao=new OrderDao(OrderActivity.this);
        parentList=orderDao.getOrder(userId);//获取订单列表
        map=new HashMap<Integer, List<OrderDetial>>();
        //遍历parentList  将orderdetialList加入map
        for(Order order:parentList){
            orderDetialList=orderDao.getDetail(order.getOrderId());
            map.put(order.getOrderId(),orderDetialList);
        }

        goodsDao=new GoodsDao(OrderActivity.this);
        final OrderAdapter orderAdapter=new OrderAdapter(OrderActivity.this,parentList,map);
        expandableListView.setAdapter(orderAdapter);
        //如果点击子项就会进入订单详情页
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Intent intent=new Intent(OrderActivity.this,GoodsDetailActivity.class);
                OrderDetial orderDetial=orderDetialList.get(i1);
                intent.putExtra("goodsid",orderDetial.getGoodsId());
                startActivity(intent);
                return true;
            }
        });
//返回上一页
        return_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(OrderActivity.this, MyselfActivity.class);
                startActivity(intent);
            }
        });
//设置按钮是编辑还是完成
     bt_edit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             flag=!flag;
             if(flag){
                 bt_edit.setText("完成");
                 orderAdapter.isShow(false);
             }else{
                 bt_edit.setText("编辑");
                 orderAdapter.isShow(true);
             }
         }
     });
    }
}
