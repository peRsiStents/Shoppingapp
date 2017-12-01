package edu.sdcj.shopping.dbutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import edu.sdcj.shopping.entity.User;

public class DBOpenHelper extends SQLiteOpenHelper {
    public String TABLE_NAME_Goods="goods_tb";//商品表
    public  String TABLE_NAME_message="message_tb";//留言表
    public  String TABLE_NAME_Order="ordertb";//订单表
    public  String TABLE_NAME_OrderDetail="orderdetail_tb";//订单详情表
    public String TABLE_NAME_GoodsRecently="goodsrecently_tb";//最近查询商品表
    public  String TABLE_NAME_Car = "shoppingcar_tb";//购物车表


    public String TABLE_NAME_USER="userInfotb";
    private static final String DB_NAME="eshopping.db";
    private static final int VERSION=1;
    public DBOpenHelper(Context context){//创建带参数的构造函数
        super(context,DB_NAME,null,VERSION);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME_USER+"(userid integer primary key autoincrement," +
                "username text not null," +
                "password text not null," +
                "sex text not null," +
                "age integer not null," +
                "phone text not null," +
                "address text not null );" );
        //创建商品表
        sqLiteDatabase.execSQL("create table "+TABLE_NAME_Goods+"(goodsid integer primary key autoincrement," +
                "goodsname text not null," +
                "goodsphoto text not null," +
                "goodstype text not null," +
                "goodsintroduce text not null,"+
                "goodsbuycount integer not null,"+
                "goodsprice real not null,"+
                "goodscostprice real not null,"+
                "goodsdelivery text not null)"
        );

        //创建留言表    "messagerating real not null,"+
        sqLiteDatabase.execSQL("create table "+TABLE_NAME_message+"(messageid integer primary key autoincrement," +
                "userid integer not null," +
                "username text not null," +
                "goodsid integer not null," +
                "messagecontent text not null,"+
                "messagedate text not null," +
                "foreign key(userid) references "+TABLE_NAME_USER+"(userid),"+
                "foreign key(goodsid) references "+TABLE_NAME_Goods+"(goodsid))");

        //订单信息
        sqLiteDatabase.execSQL("create table "+TABLE_NAME_Order+"(orderid integer primary key autoincrement," +
                "userid integer not null," +
                "username text not null," +
                "orderdate text not null,"+
                "totalprice real not null," +
                "foreign key(userid) references "+TABLE_NAME_USER+"(userid))");

        //订单详情表
        sqLiteDatabase.execSQL("create table "+TABLE_NAME_OrderDetail+"(orderdetailid integer primary key autoincrement," +
                "goodsid integer not null," +
                "orderid integer not null," +
                "goodsprice real not null,"+
                "goodsnum integer not null,"+
                "goodsphoto text not null,"+
                "goodscostprice real not null,"+
                "goodsname text not null," +
                "foreign key(goodsid) references "+TABLE_NAME_Goods+"(goodsid)," +
                "foreign key(orderid) references "+TABLE_NAME_Order+"(orderid))");

        //最近浏览商品表
        sqLiteDatabase.execSQL("create table "+TABLE_NAME_GoodsRecently+"(goodsrencentid integer not null," +
                "username text not null,"+
                "goodsphoto text not null," +
                "goodsname text not null," +
                "goodstype text not null," +
                "goodsintroduce text not null,"+
                "goodsbuycount integer not null,"+
                "goodsprice real not null,"+
                "goodscostprice real not null,"+
                "goodsdelivery text not null,"+
                "prioritycount integer not null," +
                "foreign key(username) references "+TABLE_NAME_USER+"(username))"
        );


        //创建购物车表
        sqLiteDatabase.execSQL("create table " + TABLE_NAME_Car + "(carid integer primary key autoincrement," +
                "userid integer not null," +
                "goodsid integer not null," +
                "goodsname text not null," +
                "goodsphoto text not null," +
                "goodsprice real not null," +
                "goodsquantity integer not null," +
                "goodscostprice real not null,"+
                "goodsdelivery text not null,"+
                "checked text not null," +
                "foreign key(goodsid) references "+TABLE_NAME_Goods+"(goodsid)," +
                "foreign key(userid) references "+TABLE_NAME_USER+"(userid))");




    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("TAG","onUpgrade()-------------");


    }
}
