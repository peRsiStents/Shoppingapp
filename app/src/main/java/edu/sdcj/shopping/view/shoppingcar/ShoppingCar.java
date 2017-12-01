package edu.sdcj.shopping.view.shoppingcar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import edu.sdcj.shopping.dao.OrderDao;
import edu.sdcj.shopping.dao.ShoppingCarDao;
import edu.sdcj.shopping.dao.UserDao;
import edu.sdcj.shopping.entity.CarGoods;
import edu.sdcj.shopping.view.Interface.ICheckInterface;
import edu.sdcj.shopping.view.Interface.IModifyCountInterface;
import edu.sdcj.shopping.view.User.LoginActivity;
import edu.sdcj.shopping.view.User.MainActivity;
import edu.sdcj.shopping.view.User.ObtainName;
import edu.sdcj.shopping.view.User.R;
import edu.sdcj.shopping.view.goods.GoodsDetailActivity;
import edu.sdcj.shopping.view.order.OrderActivity;

/**
 * Created by liangshan on 2017/11/9.
 */

public class ShoppingCar extends Activity implements ICheckInterface,IModifyCountInterface{
    //声明
    private ObtainName obtainName;
    private String username;
    private int userid;//判断是否登录
    public TextView tv_settlement,tv_allPrice,tv_delete;//编辑 结算 合计
    public Button bt_edit;//编辑
    private FrameLayout frameLayout;
    private CheckBox ck_all;//全选
    private ImageView iv_hint;
    private ImageView return_back;
    private LinearLayout ll_car,ll_car_foot;
    private ListView listview;//购物车商品列表
    private CarAdapter adapter;//自定义适配器

    private boolean flag = false;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    private List<CarGoods> list;//从SQLite数据库中查询购物车中商品
    private OrderDao orderDao;
    private UserDao userDao;
    private ShoppingCarDao shoppingCarDao;
    List<CarGoods> carGoodsListBychecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_car);
        init();//初始化
        initData();
    }
    /**
     * 单选
     *
     * @param position  组元素位置
     * @param isChecked 组元素选中与否
     */
    @Override
    public void checkGroup(int position, boolean isChecked) {
         list.get(position).setChecked(isChecked);
        if(isAllCheck()){
            ck_all.setChecked(true);
        }else{
            ck_all.setChecked(false);
        }
        adapter.notifyDataSetChanged();
        statistics();

    }

    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {
        CarGoods carGoods=list.get(position);
        int count=carGoods.getGoodsQutantity();
        count++;
        carGoods.setGoodsQutantity(count);
        adapter.notifyDataSetChanged();
        statistics();

    }
    /*
    减少商品数量
    不能小于零
     */
    @Override
    public void doDecrease(int position, View showCountView, boolean isChecked) {

        CarGoods carGoods=list.get(position);
        int count=carGoods.getGoodsQutantity();
        if(count<=1){
            return;
        }
        count--;
        carGoods.setGoodsQutantity(count);//给carGoods重新设置数量
        ((TextView)showCountView).setText(count+"");
        adapter.notifyDataSetChanged();
        statistics();
    }
    /*初始化
    获取各组件
    获取用户名
    * */
    public void init(){
        tv_settlement=(TextView)findViewById(R.id.tv_settlement);
        tv_allPrice=(TextView)findViewById(R.id.tv_show_price);
        tv_delete=(TextView)findViewById(R.id.tv_delete);
        bt_edit=(Button)findViewById(R.id.bt_edit);
        iv_hint=(ImageView)findViewById(R.id.iv_hint);
        return_back=(ImageView) findViewById(R.id.return_back);
        listview=(ListView)findViewById(R.id.car_listview);
        ck_all=(CheckBox)findViewById(R.id.ck_all);
        ll_car=(LinearLayout)findViewById(R.id.ll_car);
        ll_car_foot=(LinearLayout)findViewById(R.id.ll_car_foot);
        frameLayout=(FrameLayout)findViewById(R.id.frame1);
        obtainName=new ObtainName(this);
        username=obtainName.getUsernameBysp();//获取用户名
    }

    private void initData(){
        //检测是否登录，没登录直接跳到登陆页面
        if(username==null||username.equals("")){
            Intent intent=new Intent(ShoppingCar.this, LoginActivity.class);
            startActivity(intent);
            finish();
            }else{
            userDao=new UserDao(ShoppingCar.this);
            userid=userDao.query(username).getUserid();
            shoppingCarDao=new ShoppingCarDao(this);
            list=shoppingCarDao.getCarDetials(userid);
            //若购物车为空则显示默认图片，其他组件隐藏
            if(list.size()==0){
                iv_hint.setVisibility(View.VISIBLE);
                ll_car.setVisibility(View.GONE);
                ll_car_foot.setVisibility(View.GONE);
                bt_edit.setVisibility(View.GONE);
            }else{
//                iv_hint.setVisibility(View.GONE);
//                ll_car.setVisibility(View.VISIBLE);
//                ll_car_foot.setVisibility(View.VISIBLE);
//                bt_edit.setVisibility(View.VISIBLE);

                adapter = new CarAdapter(this,list);
                adapter.setCheckInterface(this);
                adapter.setModifyCountInterface(this);
                listview.setAdapter(adapter);
            }
            //点击会跳到商品详情页
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent =new Intent(ShoppingCar.this, GoodsDetailActivity.class);
                    intent.putExtra("goodsid",list.get(i).getGoodsId());
                    startActivity(intent);
                }
            });
            //结算
            tv_settlement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    carGoodsListBychecked = new ArrayList();
                    for(int i=0;i<list.size();i++){
                        if(list.get(i).getChecked()){
                            carGoodsListBychecked.add(list.get(i));
                        }
                    }
                    //如果选中商品数量为零，则显示提示信息
                    if(totalCount==0){
                        TextView textView=new TextView(ShoppingCar.this);
                        textView.setTextColor(Color.RED);
                        textView.setText("请选择要购买的商品！");
                        Toast toast=new Toast(ShoppingCar.this);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setView(textView);
                        toast.show();
                    }else{

                        AlertDialog alertDialog=new AlertDialog.Builder(ShoppingCar.this).create();
                        alertDialog.setTitle("操作提示");
                        alertDialog.setMessage("总计："+totalCount+"种商品\n"+totalPrice+"元");
                        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                OrderDao orderDao=new OrderDao(ShoppingCar.this);
                                //插入订单
                                orderDao.addOrder(userid,String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())),totalPrice,username);
                                //获取订单号详情
                                int  max=orderDao.getOrderDetailMaxOrderID();
                                //插入订单详情
                                for(int j=0;j<carGoodsListBychecked.size();j++){
                                    orderDao.addOrderDetail(carGoodsListBychecked.get(j).getGoodsId(),max,carGoodsListBychecked.get(j).getGoodsPrice(),carGoodsListBychecked.get(j).getGoodsQutantity(),carGoodsListBychecked.get(j).getGoodsPhoto(),carGoodsListBychecked.get(j).getGoodsCostPrice(),carGoodsListBychecked.get(j).getGoodsName());

                                }
                                //更新购物车内容
                                for(int j=0;j<carGoodsListBychecked.size();j++){
                                    shoppingCarDao.deleteGoods(carGoodsListBychecked.get(j).getUserId(),carGoodsListBychecked.get(j).getCarId(),carGoodsListBychecked.get(j).getGoodsId());

                                }
                                //刷新，进入订单页面
                                refresh();
                                Intent intent=new Intent(ShoppingCar.this, OrderActivity.class);
                                intent.putExtra("orderid",max);
                                startActivity(intent);
                            }
                        });
                        alertDialog.show();
                    }


                }
            });
            //返回键
            return_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ShoppingCar.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            //删除
            tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(totalCount==0){
                        //若没有选中商品，则提示用户选择要删除的商品
                        TextView textView=new TextView(ShoppingCar.this);
                        textView.setTextColor(Color.RED);
                        textView.setText("请选择要删除的商品！");
                        Toast toast=new Toast(ShoppingCar.this);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.setView(textView);
                        toast.show();
                    }
                    //给出操作提示框，看是否确定删除商品
                    AlertDialog alertDialog=new AlertDialog.Builder(ShoppingCar.this).create();
                    alertDialog.setTitle("操作提示");
                    alertDialog.setMessage("您确定将这些商品移除购物车？");
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            doDelete();//调用删除方法删除选中商品
                        }
                    });
                    alertDialog.show();
                }
            });
        }
        }
         //刷新页面
    public  void refresh(){
        finish();
        Intent intent=new Intent(ShoppingCar.this,ShoppingCar.class);
        startActivity(intent);
    }
    //删除
    private void doDelete(){
        List<CarGoods> deleteGoods=new ArrayList<CarGoods>();
        //将要删除的放入一个链表中
        for(int i=0;i<list.size();i++){
            CarGoods cargoods=list.get(i);
            if(cargoods.getChecked()){
                deleteGoods.add(cargoods);
                //从数据库中删除
                shoppingCarDao.deleteGoods(cargoods.getUserId(),cargoods.getCarId(),cargoods.getGoodsId());
            }
        }
        //页面中的内容删掉
        list.removeAll(deleteGoods);
        adapter.notifyDataSetChanged();//刷新
        statistics();///计算


    }

    //是否全选
public Boolean isAllCheck(){
    for(CarGoods carGoods:list){
        if (!carGoods.getChecked()){
            return false;}
    }
    return true;
}
    //统计价钱
    public void statistics(){
        totalPrice=0.00;
        totalCount=0;
        DecimalFormat df=new DecimalFormat("######.00");
        for(int i=0;i<list.size();i++){
            if(list.get(i).getChecked()){
                totalCount++;
                totalPrice+=list.get(i).getGoodsPrice()*list.get(i).getGoodsQutantity();
            }

        }
        tv_allPrice.setText("合计："+df.format(totalPrice));//
        tv_settlement.setText("结算（"+totalCount+"");

    }
    //给全选和编辑按钮设置监听
    public void doClick(View v) {
        switch (v.getId()) {
            //全选按钮
            case R.id.ck_all:
                if (list.size() != 0) {
                    if (ck_all.isChecked()) {//全选
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setChecked(true);
                        }
                        adapter.notifyDataSetChanged();
                    } else {//全不选
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setChecked(false);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                statistics();
                break;
            case R.id.bt_edit:
                flag = !flag;
                //如果flag为true 则bt-edit显示完成，隐藏其他页面
                if (flag) {
                    bt_edit.setText("完成");
                    adapter.isShow(false);
                    frameLayout.setVisibility(View.GONE);
                    tv_allPrice.setVisibility(View.GONE);
                    tv_delete.setVisibility(View.VISIBLE);
                } else {
                    bt_edit.setText("编辑");
                    adapter.isShow(true);
                    frameLayout.setVisibility(View.VISIBLE);
                    tv_allPrice.setVisibility(View.VISIBLE);
                    tv_delete.setVisibility(View.GONE);
                }
                break;
        }
    }
}
