package edu.sdcj.shopping.entity;

import edu.sdcj.shopping.view.User.StartActivity;

/**
 * Created by liangshan on 2017/11/9.
 */

public class Order {
    private int userId,orderId;//用户id、订单id
    private  double totalPrice;//总价
    private String orderDate,userName;//订单日期、用户名
    //构造方法
    public Order(int userId, int order, double totalPrice, String orderDate,String userName) {
        this.userId = userId;
        this.orderId = order;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.userName=userName;
    }
//setget方法
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int order) {
        this.orderId = order;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
