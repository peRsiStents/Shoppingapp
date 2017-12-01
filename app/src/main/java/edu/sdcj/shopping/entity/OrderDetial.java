package edu.sdcj.shopping.entity;

/**
 * Created by liangshan on 2017/11/9.
 */

public class OrderDetial {
    private int  orderDetailid,goodsNum,goodsId,orderId;//订单id,商品数量、商品id\订单id
    private  String  goodsPhoto,goodsName;//商品图pain、商品名
    private  double goodsPrice,goodsCostPrice;//商品价格、商品原价
    //构造方法
    public OrderDetial(int orderDetailid, int goodsNum, int goodsId,  String goodsPhoto, String goodsName, double goodsPrice, double goodsCostPrice, int orderId) {
        this.orderDetailid = orderDetailid;
        this.goodsNum = goodsNum;
        this.goodsId = goodsId;
        this.goodsPhoto = goodsPhoto;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsCostPrice = goodsCostPrice;
        this.orderId=orderId;}
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getOrderDetailid() {
        return orderDetailid;
    }

    public void setOrderDetailid(int orderDetailid) {
        this.orderDetailid = orderDetailid;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }



    public String getGoodsPhoto() {
        return goodsPhoto;
    }

    public void setGoodsPhoto(String goodsPhoto) {
        this.goodsPhoto = goodsPhoto;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public double getGoodsCostPrice() {
        return goodsCostPrice;
    }

    public void setGoodsCostPrice(double goodsCostPrice) {
        this.goodsCostPrice = goodsCostPrice;
    }
}
