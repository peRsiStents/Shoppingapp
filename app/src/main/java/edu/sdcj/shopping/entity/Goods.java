package edu.sdcj.shopping.entity;

/**
 * Created by liangshan on 2017/11/7.
 */

public class Goods {
    private int goodsId;//商品编号
    //private int goodsAccount;//商品数目
    private String goodsName;//商品名
    private String goodsPhoto;//商品图片
    private double goodsPrice;//商品价格
    private String goodsType;//商品类型
    private String goodsIntroduce;//商品简介
    private double goodsCostPrice;//原价
    private int goodsBuyCount;//商品购买数量
    private String goodsdelivery;



    public Goods(int goodsId, int goodsBuyCount, String goodsPhoto, String goodsName, double goodsPrice, String goodsType, String goodsIntroduce, double goodsCostPrice,String goodsdelivery) {
        this.goodsId = goodsId;
        this.goodsBuyCount = goodsBuyCount;
        this.goodsPhoto = goodsPhoto;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsType = goodsType;
        this.goodsIntroduce = goodsIntroduce;
        this.goodsCostPrice = goodsCostPrice;
        this.goodsdelivery=goodsdelivery;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public String getGoodsdelivery() {
        return goodsdelivery;
    }

    public void setGoodsdelivery(String goodsdelivery) {
        this.goodsdelivery = goodsdelivery;
    }

    public double getGoodsCostPrice() {
        return goodsCostPrice;
    }

    public void setGoodsCostPrice(double goodsCostPrice) {
        this.goodsCostPrice = goodsCostPrice;
    }

    public int getGoodsBuyCount() {
        return goodsBuyCount;
    }

    public void setGoodsBuyCount(int goodsBuyCount) {
        this.goodsBuyCount = goodsBuyCount;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsIntroduce() {
        return goodsIntroduce;
    }

    public void setGoodsIntroduce(String goodsIntroduce) {
        this.goodsIntroduce = goodsIntroduce;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
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
}
