package edu.sdcj.shopping.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import edu.sdcj.shopping.dbutil.DBOpenHelper;
import edu.sdcj.shopping.entity.Order;
import edu.sdcj.shopping.entity.OrderDetial;

/**
 * Created by liangshan on 2017/11/9.
 */

public class OrderDao {
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase orderDb;
    //构造方法
    public OrderDao(Context context) {
        dbOpenHelper=new DBOpenHelper(context);
        orderDb=dbOpenHelper.getWritableDatabase();
    }
    //add  oerder
    public void addOrder(int userId,String orderDate,double totalPrice,String username){
        orderDb.execSQL("insert into "+dbOpenHelper.TABLE_NAME_Order+"(userid,username,orderdate,totalprice ) values ('"+userId+"','"+username+"','"+orderDate+"','"+totalPrice+"')");

    }
    //add  orderDetial
    public void addOrderDetail(int goodsId,int orderId,double goodsPrice,int goodsnum,String goodsPhoto,double goodsCostPrice,String goodsName){
        orderDb.execSQL("insert into " +dbOpenHelper.TABLE_NAME_OrderDetail+"('goodsid','orderid','goodsprice','goodsnum','goodsphoto','goodscostprice','goodsname')values('"+goodsId+"','"+orderId+"','"+goodsPrice+"','"+goodsnum+"','"+goodsPhoto+"','"+goodsCostPrice+"','"+goodsName+"')");
    }
   //查看所有订单
    public List<Order> getOrder(int userId){
        List<Order> result=new LinkedList<Order>();//声明初始化list<Order>
        Cursor c=orderDb.query(dbOpenHelper.TABLE_NAME_Order,null,"userid=?",new String[]{userId+""},null,null,null,null);
        if (c!=null){
            while (c.moveToNext()){
                int orderId=c.getInt(c.getColumnIndex("orderid"));
                double totalprice=c.getDouble(c.getColumnIndex("totalprice"));//获取总价
                String orderdate=c.getString(c.getColumnIndex("orderdate"));//获取订单的创建日期
                String username=c.getString(c.getColumnIndex("username"));//获取用户名
                Order order=new Order(userId,orderId,totalprice,orderdate,username);
                result.add(order);//将订单插入result
            }
            return  result;
        }
        return null;
    }
    //通过orderid 获取订单
    public  Order getOrders(int orderId) {
        Cursor c = orderDb.query(dbOpenHelper.TABLE_NAME_Order, null, "orderid=?", new String[]{orderId + ""}, null, null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                int userId = c.getInt(c.getColumnIndex("userid"));//获取用户id
                double totalprice = c.getDouble(c.getColumnIndex("totalprice"));//获取总价
                String orderdate = c.getString(c.getColumnIndex("orderdate"));//获取订单日期
                String username = c.getString(c.getColumnIndex("username"));//获取订单的用户名
                Order order = new Order(userId, orderId, totalprice, orderdate,username);
                return  order;
            }
        }
        return  null;
    }

    //查询订单详情
    public  List<OrderDetial>  getDetail(int orderId){
        List<OrderDetial> result=new LinkedList<OrderDetial>();
        Cursor c=orderDb.query(dbOpenHelper.TABLE_NAME_OrderDetail,null,"orderid=?",new String[]{orderId+""},null,null,null,null);
        if(c!=null){
            while (c.moveToNext()){
                int orderDetailid=c.getInt(c.getColumnIndex("orderdetailid"));//获取订单id
                int goodsId=c.getInt(c.getColumnIndex("goodsid"));//商品id
                int goodsnum=c.getInt(c.getColumnIndex("goodsnum"));//商品数量
                double goodsPrice=c.getDouble(c.getColumnIndex("goodsprice"));//商品价格
                String goodsPhoto=c.getString(c.getColumnIndex("goodsphoto"));//商品图片
                double goodscostprice=c.getDouble(c.getColumnIndex("goodscostprice"));//商品原价
                String goodsname=c.getString(c.getColumnIndex("goodsname"));//商品名
                OrderDetial orderDetial=new OrderDetial(orderDetailid,goodsnum,goodsId,goodsPhoto,goodsname,goodsPrice,goodscostprice,orderId);
                result.add(orderDetial);
            }
            return  result;
        }
        return null;
    }
//获取定单的详细信息
    public OrderDetial  getOrderGoods(int orderId,int goodsId){
        OrderDetial orderDetial;
        //获取游标
        Cursor c=orderDb.query(dbOpenHelper.TABLE_NAME_OrderDetail,null,"orderid=? and goodsid=?",new String[]{orderId+"",goodsId+""},null,null,null,null);
        if(c!=null){
            while (c.moveToNext()){
                int orderDetailid=c.getInt(c.getColumnIndex("orderdetailid"));//详细订单 id  就是orderid
                int goodsnum=c.getInt(c.getColumnIndex("goodsnum"));
                double goodsPrice=c.getDouble(c.getColumnIndex("goodsprice"));
                String goodsPhoto=c.getString(c.getColumnIndex("goodsphoto"));
                double goodscostprice=c.getDouble(c.getColumnIndex("goodscostprice"));
                String goodsname=c.getString(c.getColumnIndex("goodsname"));
                orderDetial=new OrderDetial(orderDetailid,goodsnum,goodsId,goodsPhoto,goodsname,goodsPrice,goodscostprice,orderId);
                return  orderDetial;
            }
        }
        return  null;
    }

    //获取最新订单号、用于添加获取订单明细
    public int getOrderDetailMaxOrderID() {
        int max = 0;
        Cursor c = orderDb.rawQuery("select max(orderId) from " + dbOpenHelper.TABLE_NAME_Order, null);
        if (c != null) {
            while(c.moveToNext()){
                max=c.getInt(0);
            }
        }
        return max;
    }
//删除订单、详细订单
public void deleteOrder(int orderID){
    orderDb.execSQL("delete from "+dbOpenHelper.TABLE_NAME_Order+" where orderid="+orderID);
    orderDb.execSQL("delete from "+dbOpenHelper.TABLE_NAME_OrderDetail+ " where orderid="+orderID);
}
}
