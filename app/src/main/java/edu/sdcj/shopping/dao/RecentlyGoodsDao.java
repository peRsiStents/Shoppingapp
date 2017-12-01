package edu.sdcj.shopping.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import edu.sdcj.shopping.dbutil.DBOpenHelper;
import edu.sdcj.shopping.entity.Goods;
import edu.sdcj.shopping.entity.RecentlyGoods;

/**
 * Created by liangshan on 2017/11/8.
 */

public class RecentlyGoodsDao {
    List<Goods>goodsList;
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase rencentlygoodsdb;
    public RecentlyGoodsDao(Context context){
        dbOpenHelper=new DBOpenHelper(context);
        rencentlygoodsdb=dbOpenHelper.getWritableDatabase();
    }


    public void insertRecentlyGoods(String username, int goodsId,int goodsBuyCount, String goodsPhoto, String goodsName, double goodsPrice, String goodsType, String goodsIntroduce, double goodsCostPrice,String goodsdelivery,int prioritycount){
            rencentlygoodsdb.execSQL("insert into "+dbOpenHelper.TABLE_NAME_GoodsRecently+
                    " (goodsrencentid,username,goodsphoto,goodsname,goodstype,goodsintroduce,goodsbuycount,goodsprice,goodscostprice,goodsdelivery,prioritycount) values ('"+goodsId+"','"+username+"','"+goodsPhoto+"','"+goodsName+"','"+goodsType+"','"+goodsIntroduce+"','"+goodsBuyCount+"','"+goodsPrice+"','"+goodsCostPrice+"','"+goodsdelivery+"','"+prioritycount+"')");
    }

    /**
     * 查询最近浏览
     */
    public List<RecentlyGoods>  searchRecentlyGoods(String username){
        List<RecentlyGoods> result = new LinkedList<RecentlyGoods>();
        Cursor c=rencentlygoodsdb.query(dbOpenHelper.TABLE_NAME_GoodsRecently,null,"username=?",new String[]{username},null,null,null,null);
        if(c!=null){
            while(c.moveToNext()){
                int goodsId=c.getInt(c.getColumnIndex("goodsrencentid"));
//                int goodsId=c.getInt(c.getColumnIndex("goodsid"));
                String goodsName=c.getString(c.getColumnIndex("goodsname"));
                String photoUrl=c.getString(c.getColumnIndex("goodsphoto"));
                String goodsIntroduce=c.getString(c.getColumnIndex("goodsintroduce"));
                int goodsBuyCount=c.getInt(c.getColumnIndex("goodsbuycount"));
                Double goodsPrice=c.getDouble(c.getColumnIndex("goodsprice"));
                Double goodsCostPrice=c.getDouble(c.getColumnIndex("goodscostprice"));
                String goodsDelivery=c.getString(c.getColumnIndex("goodsdelivery"));
                String goodsType=c.getString(c.getColumnIndex("goodstype"));
                int prioritycount=c.getInt(c.getColumnIndex("prioritycount"));
                RecentlyGoods goodsRecently=new RecentlyGoods(goodsId,goodsBuyCount,photoUrl,goodsName,goodsPrice,goodsType,goodsIntroduce,goodsCostPrice,goodsDelivery,prioritycount);
                result.add(goodsRecently);
            }
        return result;
        }
        return  null;
    }
    //删除记录
    /**
     * 删除足迹中的指定商品
     * @param goodsid 商品id
     */
    public void deleteGoods(int goodsid){
        rencentlygoodsdb.execSQL("delete from "+dbOpenHelper.TABLE_NAME_GoodsRecently+" where goodsrencentid="+goodsid);
    }
    /*
    查询此产品是否浏览过
     */
    public boolean queryGoods(int goodsId){
        boolean  flag=false;
        Cursor c=rencentlygoodsdb.query(dbOpenHelper.TABLE_NAME_GoodsRecently,null,"goodsrencentid=?",new String[]{goodsId +""},null,null,null,null);
        if(c!=null){
            while (c.moveToNext()){
                int  kew=c.getInt(c.getColumnIndex("goodsrencentid"));

                if(kew==goodsId){
                    flag=true;
                }
            }
            return  flag;
        }
        return flag;

    }

    /*增加浏览次数
     */
    public void updateCount(int goodsid){
        rencentlygoodsdb.execSQL("update "+dbOpenHelper.TABLE_NAME_GoodsRecently+" set prioritycount=prioritycount+1  where goodsrencentid="+goodsid);

    }
}
