package edu.sdcj.shopping.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.sdcj.shopping.dbutil.DBOpenHelper;
import edu.sdcj.shopping.entity.Goods;

/**
 * Created by liangshan on 2017/11/7.
 */

public class GoodsDao {
    private DBOpenHelper dbHelp_goods;//数据库
    private List<Goods> listGoods=new ArrayList<>();
    private SQLiteDatabase goodsIofo;
    //构造函数
    public GoodsDao(Context context) {
        dbHelp_goods=new DBOpenHelper(context);
        goodsIofo=dbHelp_goods.getWritableDatabase();
    }
    //向数据库插入商品信息
    public void insert(){
        //食品
       goodsIofo.execSQL("insert into "+dbHelp_goods.TABLE_NAME_Goods+
                "('goodsname','goodsphoto','goodstype','goodsintroduce','goodsbuycount','goodsprice','goodscostprice','goodsdelivery') values ('德运全脂牛奶 1L*10','https://img11.360buyimg.com/n7/jfs/t3112/329/5142322648/303045/23f34e14/58649978N2ca539d6.jpg','食品区','澳大利亚 进口牛奶 整箱装','0','99.00','108.00','包邮')");
        goodsIofo.execSQL("insert into "+dbHelp_goods.TABLE_NAME_Goods+
                "('goodsname','goodsphoto','goodstype','goodsintroduce','goodsbuycount','goodsprice','goodscostprice','goodsdelivery') values ('精制猪肉铺 200g/袋','https://img12.360buyimg.com/n7/jfs/t2782/118/3406783312/150099/a3c976c6/578deaedNf62f3e74.jpg','食品区','休闲零食 靖江猪肉脯','0','16.90','19.90','包邮')");
        goodsIofo.execSQL("insert into "+dbHelp_goods.TABLE_NAME_Goods+
                "('goodsname','goodsphoto','goodstype','goodsintroduce','goodsbuycount','goodsprice','goodscostprice','goodsdelivery') values ('百味草 年货牛肉干 100g/袋','https://img11.360buyimg.com/n7/jfs/t3298/119/241707660/330921/bd52264f/57ac3177Ne80e8da3.jpg','食品区','肉类零食 五香牛肉粒 肉制品','0','14.8','32.8','包邮')");
//        goodsIofo.execSQL("insert into "+dbHelp_goods.TABLE_NAME_Goods+
//                "('goodsname','goodsphoto','goodstype','goodsintroduce','goodsbuycount','goodsprice','goodscostprice','goodsdelivery') values ('贡院 绿茶 200g','https://img11.360buyimg.com/n7/jfs/t4051/35/767474366/260233/ff89436e/585b825eN6471a2b8.jpg','食品区','梅家坞明前特级西湖龙井礼盒','0','428.90','888.00','包邮')");

    }

    //判断是不是为空 isEmpty、若为空就插入商品信息
    public  boolean isEmpty(){
        Cursor c=goodsIofo.query(dbHelp_goods.TABLE_NAME_Goods,null,null,null,null,null,null,null);
        /*
        注：好像不能用游标等于null,判断是否为空
         */
        if(c.getCount()==0){
            return true;
        }else{
        return false;
    }}
    //按照type 查询
    public List<Goods> queryGoodsByType(String type){
        Cursor c=goodsIofo.query(dbHelp_goods.TABLE_NAME_Goods,null,"goodstype=?",new String[]{type},null,null,null,null);
        if(c!=null){
            while(c.moveToNext()){
                int goodsId=c.getInt(c.getColumnIndex("goodsid"));
                String goodsName=c.getString(c.getColumnIndex("goodsname"));
                String photoUrl=c.getString(c.getColumnIndex("goodsphoto"));
                String goodsIntroduce=c.getString(c.getColumnIndex("goodsintroduce"));
                int goodsBuyCount=c.getInt(c.getColumnIndex("goodsbuycount"));
                Double goodsPrice=c.getDouble(c.getColumnIndex("goodsprice"));
                Double goodsCostPrice=c.getDouble(c.getColumnIndex("goodscostprice"));
                String goodsDelivery=c.getString(c.getColumnIndex("goodsdelivery"));
                Goods goods=new Goods(goodsId,goodsBuyCount,photoUrl,goodsName,goodsPrice,type,goodsIntroduce,goodsCostPrice,goodsDelivery);
                listGoods.add(goods);
            }
            return listGoods;

        }
            return  null;
    }
    //根据goodsid查询商品
    public Goods queryGoodsById(int goodsid){
        Cursor c=goodsIofo.query(dbHelp_goods.TABLE_NAME_Goods,null,"goodsid=?",new String[]{goodsid +""},null,null,null,null);
        if(c!=null){
            while(c.moveToNext()){
//                int goodsId=c.getInt(c.getColumnIndex("goodsid"));
                String goodsName=c.getString(c.getColumnIndex("goodsname"));//商品名
                String photoUrl=c.getString(c.getColumnIndex("goodsphoto"));//商品图片
                String goodsIntroduce=c.getString(c.getColumnIndex("goodsintroduce"));//商品描述
                int goodsBuyCount=c.getInt(c.getColumnIndex("goodsbuycount"));//销售数量
                Double goodsPrice=c.getDouble(c.getColumnIndex("goodsprice"));//商品价格
                Double goodsCostPrice=c.getDouble(c.getColumnIndex("goodscostprice"));//商品原价
                String goodsDelivery=c.getString(c.getColumnIndex("goodsdelivery"));//邮费信息
                String goodsType=c.getString(c.getColumnIndex("goodstype"));//商品类型
                Goods goods=new Goods(goodsid,goodsBuyCount,photoUrl,goodsName,goodsPrice,goodsType,goodsIntroduce,goodsCostPrice,goodsDelivery);
//                listGoods.add(goods);
                return goods;
            }
        }
        return  null;
    }
    /*
    根据用户名查询商品
    可以模糊查询
     */
    public List<Goods> queryGoodsByName(String name){
        List<Goods> result = new LinkedList<Goods>();
        Cursor c=goodsIofo.query(dbHelp_goods.TABLE_NAME_Goods,null,"goodsname like ?",new String[]{"%"+name +"%"},null,null,null,null);
        if(c!=null){
            //遍历游标
            while(c.moveToNext()){
                int goodsId=c.getInt(c.getColumnIndex("goodsid"));
                String goodsName=c.getString(c.getColumnIndex("goodsname"));
                String photoUrl=c.getString(c.getColumnIndex("goodsphoto"));
                String goodsIntroduce=c.getString(c.getColumnIndex("goodsintroduce"));
                int goodsBuyCount=c.getInt(c.getColumnIndex("goodsbuycount"));
                Double goodsPrice=c.getDouble(c.getColumnIndex("goodsprice"));
                Double goodsCostPrice=c.getDouble(c.getColumnIndex("goodscostprice"));
                String goodsDelivery=c.getString(c.getColumnIndex("goodsdelivery"));
                String goodsType=c.getString(c.getColumnIndex("goodstype"));
                Goods goods=new Goods(goodsId,goodsBuyCount,photoUrl,goodsName,goodsPrice,goodsType,goodsIntroduce,goodsCostPrice,goodsDelivery);
                result.add(goods);

            }
            return result;
        }
        return  null;
    }
    //获取商品名数组，用于查询的自动完成输入框
    public String[] getGoodsNameArray(){
        ArrayList<String> arrayList=new ArrayList<String>();
        Cursor c=goodsIofo.query(dbHelp_goods.TABLE_NAME_Goods,null,null,null,null,null,null);
        if(c!=null){
            while (c.moveToNext()){
                String  goodsName=c.getString(c.getColumnIndex("goodsname"));
                arrayList.add(goodsName);
            }
            String []str=arrayList.toArray(new String[arrayList.size()]);//将arrlist转化为数组
            return  str;
        }
        return null;

    }

//删除所有数据 、用于多次插入
public void deleteAll(){
    goodsIofo.execSQL("delete  from "+dbHelp_goods.TABLE_NAME_Goods);
}
}