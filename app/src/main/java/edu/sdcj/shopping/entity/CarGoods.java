package edu.sdcj.shopping.entity;

/**
 * Created by liangshan on 2017/11/9.
 */

public class CarGoods {
    private  String  goodsName,goodsPhoto,goodsDelivery;//商品名、商品图片、商品邮费信息
    private int goodsId,userId,goodsQutantity,carId;//商品id、用户id、商品数量、id
    private Double goodsPrice,goodsCostPrice;//商品价格、商品原价
    private Boolean  Checked;//是否选择

    public CarGoods(int carId,String goodsName, String goodsPhoto, String goodsDelivery, int goodsId, int userId, int goodsQutantity, Double goodsPrice, Double goodsCostPrice, Boolean checked) {
        this.goodsName = goodsName;
        this.goodsPhoto = goodsPhoto;
        this.goodsDelivery = goodsDelivery;
        this.goodsId = goodsId;
        this.userId = userId;
        this.goodsQutantity = goodsQutantity;
        this.goodsPrice = goodsPrice;
        this.goodsCostPrice = goodsCostPrice;
        Checked = checked;
        this.carId=carId;

    }
    //set  get 方法
    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPhoto() {
        return goodsPhoto;
    }

    public void setGoodsPhoto(String goodsPhoto) {
        this.goodsPhoto = goodsPhoto;
    }

    public String getGoodsDelivery() {
        return goodsDelivery;
    }

    public void setGoodsDelivery(String goodsDelivery) {
        this.goodsDelivery = goodsDelivery;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoodsQutantity() {
        return goodsQutantity;
    }

    public void setGoodsQutantity(int goodsQutantity) {
        this.goodsQutantity = goodsQutantity;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Double getGoodsCostPrice() {
        return goodsCostPrice;
    }

    public void setGoodsCostPrice(Double goodsCostPrice) {
        this.goodsCostPrice = goodsCostPrice;
    }

    public Boolean getChecked() {
        return Checked;
    }

    public void setChecked(Boolean checked) {
        Checked = checked;
    }
}

