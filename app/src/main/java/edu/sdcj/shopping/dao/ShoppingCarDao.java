package edu.sdcj.shopping.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import edu.sdcj.shopping.dbutil.DBOpenHelper;
import edu.sdcj.shopping.entity.CarGoods;

/**
 * Created by liangshan on 2017/11/8.
 */

public class ShoppingCarDao {
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase shoppingcardb;//数据库
//构造方法
    public  ShoppingCarDao(Context context){
        dbOpenHelper=new DBOpenHelper(context);
        shoppingcardb=dbOpenHelper.getWritableDatabase();
    }
//往购物车里添加商品
    public boolean addGoods(String goodsName, String goodsPhoto, String goodsDelivery, int goodsId, int userId, int goodsQutantity, Double goodsPrice, Double goodsCostPrice){
        shoppingcardb.execSQL("insert into "+dbOpenHelper.TABLE_NAME_Car+
                "('userid','goodsid','goodsname','goodsphoto','goodsprice','goodsquantity','goodscostprice','goodsdelivery','checked') values ('"+userId+"','"+goodsId+"','"+goodsName+"','"+goodsPhoto+"','"+goodsPrice+"','"+goodsQutantity+"','"+goodsCostPrice+"','"+goodsDelivery+"','false')");
        return  true;
    }
//获取所有的购物车里的商品
    public List<CarGoods> getCarDetials(int userid){
        List<CarGoods> list=new LinkedList<CarGoods>();
        Cursor c=shoppingcardb.query(dbOpenHelper.TABLE_NAME_Car,null,"userid=?",new String[]{userid+""},null,null,null,null);
        if(c!=null){
            while(c.moveToNext()){
                int carId=c.getInt(c.getColumnIndex("carid"));//id
                int userId=c.getInt(c.getColumnIndex("userid"));//用户id
                int goodId=c.getInt(c.getColumnIndex("goodsid"));//商品id
                int goodsQuantity =c.getInt(c.getColumnIndex("goodsquantity"));//数量
                String goodsName=c.getString(c.getColumnIndex("goodsname"));//商品名
                String goodsPhoto=c.getString(c.getColumnIndex("goodsphoto"));//商品图片
                String goodsDelivery=c.getString(c.getColumnIndex("goodsdelivery"));//邮费信息
                double goodsPrice=c.getDouble(c.getColumnIndex("goodsprice"));//商品价格
                double goodscostprice=c.getDouble(c.getColumnIndex("goodscostprice"));//商品原价
                String checked=c.getString(c.getColumnIndex("checked"));//是否选中
                CarGoods carGoods=new CarGoods(carId,goodsName,goodsPhoto,goodsDelivery,goodId,userId,goodsQuantity,goodsPrice,goodscostprice,Boolean.parseBoolean(checked));
                list.add(carGoods);
            }
            return  list;
        }
        return null;
    }
    /*
        查询此产品是否在购物车里
         */
    public boolean queryGoods(int goodsId){
        boolean  flag=false;
        Cursor c=shoppingcardb.query(dbOpenHelper.TABLE_NAME_Car,null,"goodsid=?",new String[]{goodsId +""},null,null,null,null);
        if(c!=null){
            while (c.moveToNext()){
                int  kew=c.getInt(c.getColumnIndex("goodsid"));

                if(kew==goodsId){
                    flag=true;
                }
            }
            return  flag;
        }
        return flag;

    }

    /*增加此商品的数量
     */
    public void updateCount(int goodsid,int count){
        shoppingcardb.execSQL("update "+dbOpenHelper.TABLE_NAME_Car+" set goodsquantity=goodsquantity+"+count+"  where goodsid="+goodsid);

    }
    /*
    删除所有商品
     */
    public void deleteGoods(int userid,int carid,int goodsid){
            shoppingcardb.execSQL("delete  from "+dbOpenHelper.TABLE_NAME_Car+" where userid="+userid+" and carid="+carid+" and goodsid="+goodsid);

    }}
